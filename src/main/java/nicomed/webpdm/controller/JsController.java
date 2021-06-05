package nicomed.webpdm.controller;

import nicomed.webpdm.enums.MyEnum;
import nicomed.webpdm.enums.UserRole;
import nicomed.webpdm.enums.Workshop;
import nicomed.webpdm.forms.DesignerForm;
import nicomed.webpdm.forms.DocFilteForm;
import nicomed.webpdm.forms.LoginForm;
import nicomed.webpdm.models.Designer;
import nicomed.webpdm.models.DocFormat;
import nicomed.webpdm.models.Office;
import nicomed.webpdm.models.PdmDocument;
import nicomed.webpdm.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

@Controller
@RequestMapping(value = "/js")
public class JsController {

    private static Logger log = Logger.getLogger(JsController.class.getName());

    private final DesignerService designerService;
    private PdmDocumentService documentService;
    private PlanPointServise planPointServise;
    private OfficeService officeService;
    private final FormatService formatService;

    public JsController(DesignerService designerService,
                        PdmDocumentService documentService,
                        PlanPointServise planPointServise,
                        OfficeService officeService,
                        FormatService formatService
    ) {
        this.designerService = designerService;
        this.documentService = documentService;
        this.planPointServise = planPointServise;
        this.officeService = officeService;
        this.formatService = formatService;
    }

    // region LOGIN - LOGOFF
    @GetMapping("")
    public String getJsPage(ModelMap model, RedirectAttributes redirectAttributes) {
        model.addAttribute("fr_nav", "nav_start");
        return "start";
    }

    // ЧЕРЕЗ ПЕРЕБОР ПОЛЬЗОВАТЕЛЕЙ
    /*
        @PostMapping("/login")
        public String loginUserJsForm(@ModelAttribute LoginForm form) {
    //        String login = form.getLogin();
            log.info("form login - " + form.getLogin() + "; pass - " + form.getPassword());
            if (designerService.findAll()
                    .stream()
                    .anyMatch(d -> d.getLogin().equals(form.getLogin()))) {
                Designer designer = designerService.findDesignerByLogin(form.getLogin());
                log.info("ID - " + designer.getId());
                if(designer.getPassword().equals(form.getPassword()))
                    return "redirect:/js/" + designer.getId();
                else
                    return "redirect:/js";
            }
            else
                return "redirect:/js";
        }
        */

    @PostMapping("/login")
    public String loginUserJsForm(@ModelAttribute LoginForm form, RedirectAttributes redirectAttributes) {
        log.info("form login - " + form.getLogin() + "; pass - " + form.getPassword());
        try {
            Designer designer = designerService.findDesignerByLogin(form.getLogin());
            if (designer.getPassword().equals(form.getPassword()))
                return "redirect:/js/" + designer.getId();
            else {
                redirectAttributes.addFlashAttribute("err", "Проверьте имя пользователя или пароль");
                return "redirect:/js";
            }
        } catch (NoSuchElementException ex) {
            redirectAttributes.addFlashAttribute("err", "Проверьте имя пользователя или пароль");
            return "redirect:/js";
        }
    }

    @GetMapping("/{id}/logoff")
    public String getJsLogOffPage(@PathVariable("id") Long id, ModelMap model) {
        return "redirect:/js";
    }
    //endregion LOGIN - LOGOFF

    // region CABINET
    @GetMapping("/{id}")
    public String getJsLoginnedPage(@PathVariable("id") Long id, ModelMap model) {

        model.addAttribute("fr_nav", "nav_acc");
        model.addAttribute("fr_head", "head_acc");
        model.addAttribute("user", designerService.findOneById(id));
        return "start";
    }
    // endregion CABINET

    // region DOCUMENTS
    @GetMapping("/{id}/documents")
    public String getJsDocumentPage(@PathVariable("id") Long id,
                                    ModelMap model,
                                    @ModelAttribute("filter_form") DocFilteForm form,
                                    RedirectAttributes ra) {
        model.addAttribute("user", designerService.findOneById(id));
        model.addAttribute("fr_doc", "doc_list");
        model.addAttribute("month_list", MyEnum.MonthSHORT());
        model.addAttribute("designer_list", designerService.findAll());

        if (model.getAttribute("w_prop") != null) {
            log.info("Ra_prop: " + model.getAttribute("owner_prop"));
            model.addAttribute("document_list", documentService.findAllByWorkshop(Workshop.values()[Integer.valueOf(model.getAttribute("w_prop").toString())]));
            return "acc_document";
        }
        if (model.getAttribute("owner_prop") != null) {
            log.info("owner_prop: " + model.getAttribute("owner_prop"));
            model.addAttribute("document_list", documentService.findAllByOwner(designerService.findOneById(Long.valueOf(model.getAttribute("owner_prop").toString()))));
            return "acc_document";
        }
        String prop = model.getAttribute("ra_prop") != null ? model.getAttribute("ra_prop").toString() : "des";
        log.info("Ra_prop: " + prop);

        model.addAttribute("ws", -1);
        model.addAttribute("document_list", documentService.findAll(prop));
        return "acc_document";
    }

    @GetMapping("/{id}/documents/sort/{prop}")
    public String getJsSortDocuments(@PathVariable("id") Long id,
                                     @PathVariable String prop,
                                     ModelMap model,
                                     RedirectAttributes ra) {
        ra.addFlashAttribute("ra_prop", prop);
        return "redirect:/js/" + id + "/documents";
    }

    @GetMapping("/{id}/documents/{doc_id}")
    public String getJsDocById(@PathVariable Long id,
                               @PathVariable("doc_id") Long doc_id,
                               ModelMap model) {
        model.addAttribute("user", designerService.findOneById(id));

        model.addAttribute("fr_doc", "doc_single");

        PdmDocument document = documentService.getOne(doc_id);
        model.addAttribute("document", document);
        model.addAttribute("designer_list", designerService.findAll());
        model.addAttribute("planpoint_list", planPointServise.findAll());
        model.addAttribute("month_list", MyEnum.MonthLONG());
        return "acc_document";
    }

    @PostMapping("/{id}/documents/{doc_id}/update")
    public String updateDocument(@PathVariable Long id,
                                 @PathVariable("doc_id") Long doc_id,
                                 @ModelAttribute("document") PdmDocument document_form
    ) {
        PdmDocument doc = documentService.getOne(doc_id);
        doc.from(document_form);
        doc.setPlanpoint(planPointServise.getOne(document_form.getPlanpoint().getId()));
        documentService.save(doc);
        for (int i = 0; i < doc.getFormats().size(); i++) {
            DocFormat format = formatService.getOne(document_form.getFormats().get(i).getId());
            format.setFormat(document_form.getFormats().get(i).getFormat());
            format.setQty(document_form.getFormats().get(i).getQty());
            formatService.save(format);
        }

        return "redirect:/js/" + id + "/documents";
    }

    @PostMapping("/{id}/documents")
    public String getJsFilterDocumentPage(@PathVariable("id") Long id,
                                          @ModelAttribute("filter_form") DocFilteForm form,
                                          RedirectAttributes ra) {

        if (form.getWorkshopId() != -1) {
            log.info("WORKSHOP !!!! " + form.getWorkshopId());
            ra.addFlashAttribute("w_prop", form.getWorkshopId());
        }
        if (form.getDesignerId() != -1) {
//            log.info("OWNER !!!! " + form.getDesignerId());
            ra.addFlashAttribute("owner_prop", form.getDesignerId());
        }


        return "redirect:/js/" + id + "/documents";
    }


// endregion DOCUMENTS

    //    region USERS
    @GetMapping("/{id}/users")
    public String getJsAllUsersPage(@PathVariable("id") Long id, ModelMap model, RedirectAttributes redirectAttributes) {
        Designer user = designerService.findOneById(id);
        model.addAttribute("user", user);
        model.addAttribute("fr_user", "user_list");
        model.addAttribute("designer_list", designerService.findAll());
        model.addAttribute("office_list", officeService.findAll());

        if (user.getRole() == UserRole.ADMIN)
            model.addAttribute("action", "1");
        return "acc_user";
    }

    @GetMapping("/{id}/users/{usr_id}")
    public String getJsUserPage(@PathVariable("id") Long id,
                                @PathVariable("usr_id") Long usr_id,
                                HttpServletRequest req,
                                ModelMap model) {
        Designer user = designerService.findOneById(id);
        model.addAttribute("user", user);
        if (user.getRole() == UserRole.ADMIN) {
            model.addAttribute("fr_user", "user_single");
            model.addAttribute("action", "1");

            model.addAttribute("office_list", officeService.findAll());

        } else
            model.addAttribute("fr_user", "user_view");
        try {
            model.addAttribute("designer", designerService.findOneById(usr_id));
            var ref = req.getHeader("referer").substring(req.getHeader("referer").indexOf("/js/"));
            model.addAttribute("req_path", ref);
        } catch (Exception ex) {
            model.addAttribute("err", "Пользователь не найден");
        }
        return "acc_user";
    }

    @GetMapping("/{id}/users/create")
    public String createJsUserPage(@PathVariable("id") Long id,
                                   HttpServletRequest req,
                                   ModelMap model) {
        Designer user = designerService.findOneById(id);
        model.addAttribute("user", user);
        model.addAttribute("fr_user", "user_single");
        model.addAttribute("designer", new DesignerForm());

        model.addAttribute("office_list", officeService.findAll());

        var ref = req.getHeader("referer").substring(req.getHeader("referer").indexOf("/js/"));
        model.addAttribute("req_path", ref);
        return "acc_user";
    }

    @PostMapping("/{id}/users/create")
    public String saveJsCreatedUser(@PathVariable("id") Long id,
                                    @ModelAttribute("designer") DesignerForm form,
                                    RedirectAttributes redirectAttributes,
                                    ModelMap model) {
        log.info("create user");
        Designer user = new Designer().from2(form);
        designerService.save(user);
        if (user.getRole() == UserRole.MN_BURO) {
            Office office = officeService.getOne(user.getOffice().getId());
            office.setBoss(user);
            officeService.save(office);
        }

        redirectAttributes.addFlashAttribute("info_mes", "Пользователь " + user.getLogin() + " создан.");
        return "redirect:/js/" + id + "/users";
    }


    @PostMapping("/{id}/users/{usr_id}/save")
    public String saveJsUser(@PathVariable("id") Long id,
                             @PathVariable("usr_id") Long usr_id,
                             @ModelAttribute("designer") DesignerForm form,
                             RedirectAttributes redirectAttributes,
                             ModelMap model) {
        log.info("update user id: " + form.getId());
        redirectAttributes.addFlashAttribute("info_mes", "Пользователь " + designerService.findOneById(usr_id).getLogin() + " обновлен.");

        Designer user = designerService.findOneById(usr_id);
        user = form.to(user);
        designerService.save(user);

        return "redirect:/js/" + id + "/users";
    }

    @GetMapping("/{id}/users/{usr_id}/delete")
    public String getJsDeleteUserPage(@PathVariable("id") Long id,
                                      @PathVariable("usr_id") Long usr_id,
                                      RedirectAttributes redirectAttributes,
                                      ModelMap model) {
        Designer user = designerService.findOneById(usr_id);
        designerService.delete(user);
        redirectAttributes.addFlashAttribute("info_mes", "Пользователь " + user.getLogin() + " удален.");
        return "redirect:/js/" + id + "/users";
    }


    // endregion USERS

    //    region PLAN POINT
    @GetMapping("/{id}/plan")
    public String getJsPlanPage(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("user", designerService.findOneById(id));
        model.addAttribute("pp_list", planPointServise.findAll());
        return "acc_plan";
    }
    // endregion PLAN POINT

// region PLAN MONTH

// endregion PLAN MONTH

    // region TEST
    @GetMapping("/{id}/test")
        public String getJsTestPage(@PathVariable("id") Long id, ModelMap model, RedirectAttributes redirectAttributes) {
            Designer user = designerService.findOneById(id);
            model.addAttribute("user", designerService.findOneById(id));
            model.addAttribute("fr_user", "user_list");
            model.addAttribute("designer_list", designerService.findAll());
            model.addAttribute("month_list", MyEnum.MonthSHORT());



            if (user.getRole() == UserRole.ADMIN)
                model.addAttribute("action", "1");
            return "test";
        }

// endregion TEST

    // region  MODEL ATTRIBUTES
    @ModelAttribute(value = "designer_form")
    public DesignerForm getDesignerForm() {
        return new DesignerForm();
    }

    @ModelAttribute(value = "docfilter_form")
    public DocFilteForm getDocFilterForm() {
        return new DocFilteForm();
    }

// endregion  MODEL ATTRIBUTES
}
