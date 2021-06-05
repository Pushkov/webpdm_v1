package nicomed.webpdm.controller;

import com.lowagie.text.DocumentException;
import nicomed.webpdm.enums.MyEnum;
import nicomed.webpdm.forms.PlanPointForm;
import nicomed.webpdm.forms.PlanSelectionForm;
import nicomed.webpdm.models.Designer;
import nicomed.webpdm.models.PdmDocument;
import nicomed.webpdm.models.PlanMonth;
import nicomed.webpdm.models.PlanPoint;
import nicomed.webpdm.service.*;
import nicomed.webpdm.utils.MyUtils;
import nicomed.webpdm.utils.SheetUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/mypdm")
public class AccountPointPlanController {

    private final PlanPointServise planPointServise;
    private final PlanMonthService planMonthService;
    private final PdmDocumentService documentService;
    private final DesignerService designerService;
    private final PdfService pdfService;

    public AccountPointPlanController(PlanPointServise planPointServise,
                                      PdmDocumentService documentService,
                                      DesignerService designerService,
                                      PdfService pdfService,
                                      PlanMonthService planMonthService
    ) {
        this.planPointServise = planPointServise;
        this.documentService = documentService;
        this.designerService = designerService;
        this.planMonthService = planMonthService;
        this.pdfService = pdfService;
    }




    @GetMapping(value = "/{acc_login}/planing/all")
    public String getAllPlanPoints(@PathVariable String acc_login,
                                   @ModelAttribute("planselection") PlanSelectionForm planSelection_form,
                                   ModelMap model){
        model.addAttribute("account_main_section", "acc_plan");
        model.addAttribute("user", acc_login);
        model.addAttribute("fr_nav", "nav_account");
        model.addAttribute("fr_plan_main","planpoint_all");
        model.addAttribute("planpoint_list", planPointServise.findAll());
        model.addAttribute("year_list", MyUtils.getYears(planMonthService.findAll()));
        model.addAttribute("planselection", new PlanSelectionForm(-1,0));
        model.addAttribute("month_list", MyEnum.MonthSHORT());


        return "account";
    }

    @GetMapping(value = "/{acc_login}/planing/planpoint/{pp_id}")
    public String getDocumentsByPlanPoint(@PathVariable String acc_login,
                                          @PathVariable(name="pp_id") Long pp_id,
                                          ModelMap model){
        model.addAttribute("account_main_section", "acc_plan");
        model.addAttribute("user", acc_login);
        model.addAttribute("fr_nav", "nav_account");
        model.addAttribute("fr_plan_main","planpoint_docs");
        PlanPoint pp = planPointServise.getOne(pp_id);
        model.addAttribute("planpoint", pp);
        model.addAttribute("docs_inpp_list", documentService.findAllByPlanpoint(pp));
        model.addAttribute("month_list", MyEnum.MonthLONG());
        int count = 0;
        float a1 = 0;
        for(PdmDocument document : documentService.findAllByPlanpoint(pp)){
            count += SheetUtils.getDocumentSheetsCount(document);
            a1 += SheetUtils.getDocumentSheetsA1(document);
        }
        model.addAttribute("sheets_count", count);
        model.addAttribute("a1_count", a1);
        return "account";
    }

    @GetMapping(value = "/{acc_login}/planing/planpoint/{pp_id}/edit")
    public String editPlanPointPage(@PathVariable String acc_login,
                                    @PathVariable(name="pp_id") Long pp_id,
                                    ModelMap model){
        model.addAttribute("account_main_section", "acc_plan");
        model.addAttribute("user", acc_login);
        model.addAttribute("fr_nav", "nav_account");
        model.addAttribute("fr_plan_main","planpoint_single");
        PlanPoint pp = planPointServise.getOne(pp_id);
        model.addAttribute("planpoint", pp);
        model.addAttribute("designer_list", designerService.findAll());
        return "account";
    }

    @PostMapping("/{acc_login}/planing/planpoint/{pp_id}/update")
    public String updateDocument(@PathVariable String acc_login,
                                 @PathVariable(name="pp_id") Long pp_id,
                                 @ModelAttribute("planpoint") PlanPointForm planPoint
    ){
        PlanPoint pp = planPointServise.getOne(pp_id);
        pp.setName(planPoint.getName());
        pp.setOrderInPlan(planPoint.getOrderInPlan());
        pp.setDesignation(planPoint.getDesignation());
//        System.out.println("*** " + planPoint.getDesigner().getId().toString());
        Designer desig = designerService.findOneById(planPoint.getDesigner().getId());
        pp.setDesigner(desig);
        pp.setWorkshop(planPoint.getWorkshop());
        pp.setBasics(planPoint.getBasics());
        pp.setComment(planPoint.getComment());
        planPointServise.save(pp);
        return "redirect:/mypdm/" + acc_login + "/planing/all"; //planpoint/" + pp_id;
    }


    @GetMapping(value = "/{acc_login}/planing/planpoint/create")
    public String createPlanPointPage(@PathVariable String acc_login, ModelMap model){
        model.addAttribute("account_main_section", "acc_plan");
        model.addAttribute("user", acc_login);
        model.addAttribute("fr_nav", "nav_account");
        model.addAttribute("fr_plan_main","planpoint_single");
//        model.addAttribute("planpoint", pp);
        model.addAttribute("designer_list", designerService.findAll());
        return "account";
    }

    @PostMapping("/{acc_login}/planing/planpoint/create")
    public String createDocument(@PathVariable String acc_login,
                                 @ModelAttribute("planpoint") PlanPointForm planPoint_form
    ){
        PlanPoint pp = new PlanPoint();
        pp.setName(planPoint_form.getName());
        pp.setOrderInPlan(planPoint_form.getOrderInPlan());
        pp.setDesignation(planPoint_form.getDesignation());
        pp.setWorkshop(planPoint_form.getWorkshop());
        pp.setBasics(planPoint_form.getBasics());
        pp.setComment(planPoint_form.getComment());

        pp.setDesigner(designerService.findOneById(planPoint_form.getDesigner().getId()));
        pp.setPlanmonth(planMonthService.findByMonthAndYear(MyUtils.getNowMonth(), MyUtils.getNowYear()));

        planPointServise.save(pp);
        return "redirect:/mypdm/" + acc_login + "/planing/all";
    }

    @GetMapping("/{acc_login}/planing/planpoint/{pp_id}/delete")
    public String deletePointPlan(@PathVariable String acc_login,
                                  @PathVariable(name="pp_id") String pp_id){
        PlanPoint pp = planPointServise.getOne(Long.valueOf(pp_id));
        planPointServise.delete(pp);
        return "redirect:/mypdm/" + acc_login + "/planing/all";
    }

    @GetMapping("/{acc_login}/planing/planpoint/list")
    public String selectionPlanPoint(@PathVariable String acc_login,
                                     @ModelAttribute("planselection") PlanSelectionForm planSelection_form,
                                     ModelMap model
    ){
        model.addAttribute("account_main_section", "acc_plan");
        model.addAttribute("user", acc_login);
        model.addAttribute("fr_nav", "nav_account");
        model.addAttribute("fr_plan_main","planpoint_all");
        model.addAttribute("year_list", MyUtils.getYears(planMonthService.findAll()));

        if(planSelection_form.getYear() == 0){
            return "redirect:/mypdm/" + acc_login + "/planing/all";
        }
        if(planSelection_form.getMonth() == -1 && planSelection_form.getYear() != 0){
            List<PlanPoint> pplist = new ArrayList<>();
            List<PlanMonth> pmlist = planMonthService.findAllByYear(planSelection_form.getYear());
            for (PlanMonth pm : pmlist){
                pplist.addAll(planPointServise.findByPlanmonth(pm));
            }
            model.addAttribute("planpoint_list", pplist);
        }
        else{
            PlanMonth pm = planMonthService.findByMonthAndYear(planSelection_form.getMonth(),
                    planSelection_form.getYear());
            model.addAttribute("planpoint_list", planPointServise.findByPlanmonth(pm));
            System.out.println("***" + planSelection_form.getMonth() + " - " + Year.of(planSelection_form.getYear()) + " *** "); // + pm.getId());
        }
        model.addAttribute("month_list", MyEnum.MonthSHORT());
        return "account";
    }


    @GetMapping("/{acc_login}/planing/planpoint/{pp_id}/pdf")
    public void exportToPDF(HttpServletResponse response, @PathVariable("pp_id") Long pp_id)  {

        try {
            Path file = Paths.get(pdfService.generatePdf(pp_id).getPath());
//            Path file = Paths.get(pdfService.generatePdf().getAbsolutePath());
            if (Files.exists(file)) {
                response.setContentType("application/pdf");
                response.addHeader("Content-Disposition",
                        "attachment; filename=" + file.getFileName());
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();
            }
        } catch (DocumentException | IOException ex) {
            ex.printStackTrace();
        }
    }


    @ModelAttribute(value = "planpoint")
    public PlanPointForm getPlanPointForm() {
        return new PlanPointForm();
    }

    @ModelAttribute(value = "planselection")
    public PlanSelectionForm getPlanSelectionForm() {
        return new PlanSelectionForm();
    }
}
