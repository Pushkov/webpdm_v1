package nicomed.webpdm.controller;

import nicomed.webpdm.enums.FormatSheet;
import nicomed.webpdm.enums.MyEnum;
import nicomed.webpdm.enums.Workshop;
import nicomed.webpdm.forms.DocFilteForm;
import nicomed.webpdm.forms.FormatForm;
import nicomed.webpdm.forms.PdmDocumentForm;
import nicomed.webpdm.models.*;
import nicomed.webpdm.service.*;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping(value = "/mypdm")
public class AccountDocumentsController {

    private final PdmDocumentService documentService;
    private final DesignerService designerService;
    private final FormatService formatService;
    private final PlanPointServise planPointServise;
    private final PlanMonthService planMonthServise;

    public AccountDocumentsController(PdmDocumentService _documentService,
                                      DesignerService _designerService,
                                      FormatService _formatService,
                                      PlanPointServise _planPointServise,
                                      PlanMonthService _planMonthServise
    ) {
        this.documentService = _documentService;
        this.designerService = _designerService;
        this.formatService = _formatService;
        this.planPointServise = _planPointServise;
        this.planMonthServise = _planMonthServise;
    }

    @GetMapping("/{acc_login}/documents/all")
    public String getAllDocuments(@PathVariable String acc_login, ModelMap model, RedirectAttributes ra) {
        model.addAttribute("account_main_section", "acc_doc");
        model.addAttribute("user", acc_login);
        model.addAttribute("fr_nav", "nav_account");
        model.addAttribute("fr_doc_main", "doc_all");

        String prop = model.getAttribute("ra_prop") != null ? model.getAttribute("ra_prop").toString() :"des";
        model.addAttribute("document_list", documentService.findAll(prop));
        model.addAttribute("month_list", MyEnum.MonthSHORT());
        model.addAttribute("filter_list", MyEnum.FILTER);
        model.addAttribute("designer_list", designerService.findAll());
        return "account";
    }

    @GetMapping("/{acc_login}/documents/all/{sort}")
    public String getSortDocuments(@PathVariable("acc_login") String acc_login,
                                     @PathVariable String sort,
                                     RedirectAttributes ra) {
        ra.addFlashAttribute("ra_prop", sort);
        return "redirect:/mypdm/" + acc_login + "/documents/all";
    }


    @GetMapping("/{acc_login}/documents/my")
    public String getAccAllDocuments(@PathVariable String acc_login, ModelMap model) {
        model.addAttribute("account_main_section", "acc_doc");
        model.addAttribute("user", acc_login);
        model.addAttribute("fr_nav", "nav_account");
        model.addAttribute("fr_doc_main", "doc_all");
        Designer designer = designerService.findDesignerByLogin(acc_login);
        model.addAttribute("document_list", documentService.findAllByOwner(designer));
        model.addAttribute("month_list", MyEnum.MonthSHORT());
        model.addAttribute("filter_list", MyEnum.FILTER);
        model.addAttribute("designer_list", designerService.findAll());
        return "account";
    }

    @GetMapping("/{acc_login}/documents/{doc_id}")
    public String getDocumentByDesignation(@PathVariable String acc_login,
                                           @PathVariable("doc_id") Long doc_id,
                                           ModelMap model) {
        model.addAttribute("account_main_section", "acc_doc");
        model.addAttribute("user", acc_login);
        model.addAttribute("fr_nav", "nav_account");
        model.addAttribute("fr_doc_main", "doc_single");

        PdmDocument document = documentService.getOne(doc_id);
        model.addAttribute("document", document);
        model.addAttribute("designer_list", designerService.findAll());
        model.addAttribute("planpoint_list", planPointServise.findAll());
        model.addAttribute("month_list", MyEnum.MonthLONG());
        return "account";
    }


//    @GetMapping("/{acc_login}/documents/{des}")
//    public String getDocumentByDesignation(@PathVariable String acc_login, @PathVariable String des, ModelMap model) {
//        model.addAttribute("account_main_section", "acc_document");
//        model.addAttribute("user", acc_login);
//        model.addAttribute("fr_nav", "nav_account");
//        model.addAttribute("fr_doc_main", "documents_account_single");
//
//        PdmDocument document = documentService.getByDes(des);
//        model.addAttribute("document", document);
//        model.addAttribute("designers", designerService.findAll());
//        model.addAttribute("planpoint_list", planPointServise.findAll());
//        model.addAttribute("month_list", MyEnum.MonthLONG());
//        return "account";
//    }




    @PostMapping("/{acc_login}/documents/{doc_id}/update")
    public String updateDocument(@PathVariable String acc_login,
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

        return "redirect:/mypdm/" + acc_login + "/documents/all";
    }

    @PostMapping("/{acc_login}/documents/{doc_id}/addformat")
    public String addFormat(@PathVariable String acc_login,
                            @PathVariable("doc_id") Long doc_id,
                            @ModelAttribute("document") PdmDocument document_form
    ) {
        PdmDocument doc = documentService.getOne(doc_id);
        DocFormat format = new DocFormat();
        format.setQty(1);
        format.setFormat(FormatSheet.ND);
        format.setDocs(doc);
        formatService.save(format);
        return "redirect:/mypdm/" + acc_login + "/documents/" + doc_id;
    }

    @PostMapping("/{acc_login}/documents/{doc_id}/deleteformat")
    public String deleteFormat(@PathVariable String acc_login,
                               @PathVariable("doc_id") Long doc_id) {
        List<DocFormat> list = formatService.findAllByDocs(
                documentService.getOne(doc_id));
        if (list.size() > 0)
            formatService.delete(list.get(list.size() - 1));

        return "redirect:/mypdm/" + acc_login + "/documents/" + doc_id;
    }

    @GetMapping("/{acc_login}/documents/{doc_id}/delete")
    public String deleteDocument(@PathVariable String acc_login,
                                 @PathVariable("doc_id") Long doc_id) {
        PdmDocument document = documentService.getOne(doc_id);
        documentService.delete(document);
        return "redirect:/mypdm/" + acc_login + "/documents/all";
    }

    @GetMapping("/{acc_login}/documents/create")
    public String createDocumentPage(@PathVariable String acc_login, ModelMap model) {
        model.addAttribute("account_main_section", "acc_doc");
        model.addAttribute("user", acc_login);
        model.addAttribute("fr_nav", "nav_account");
        model.addAttribute("fr_doc_main", "doc_single");
        model.addAttribute("designer_list", designerService.findAll());
        model.addAttribute("planpoint_list", planPointServise.findAll());
        model.addAttribute("month_list", MyEnum.MonthSHORT());
        return "account";
    }

    @PostMapping("/{acc_login}/documents/create")
    public String updateDocument(@PathVariable String acc_login, @ModelAttribute PdmDocument document) {
        document.setCreation_date(new GregorianCalendar());
        documentService.save(document);
        return "redirect:/mypdm/" + acc_login + "/documents/all";
    }


    @GetMapping("/{acc_login}/documents/selection")
    public String getSelectedDocuments(@PathVariable String acc_login,
                                       @ModelAttribute("filter_form") DocFilteForm form,
                                       ModelMap model) {
        model.addAttribute("account_main_section", "acc_doc");
        model.addAttribute("user", acc_login);
        model.addAttribute("fr_nav", "nav_account");
        model.addAttribute("fr_doc_main", "doc_all");
        model.addAttribute("month_list", MyEnum.MonthSHORT());
        model.addAttribute("filter_list", MyEnum.FILTER);
        model.addAttribute("designer_list", designerService.findAll());
        if( form.getDesFilter().equals("") &&
            form.getNameFilter().equals("") &&
            form.getDesignerId() == -1 &&
            form.getWorkshopId() == -1){
            return "redirect:/mypdm/" + acc_login + "/documents/all";
        }
        if( form.getDesFilter().equals("") &&
                form.getNameFilter().equals("") &&
                form.getDesignerId() != -1 &&
                form.getWorkshopId() == -1)
                {
            model.addAttribute("document_list", documentService.findAllByOwner(designerService.findOneById(form.getDesignerId())));
            return "account";
        }
        if( form.getDesFilter().equals("") &&
                form.getNameFilter().equals("") &&
                form.getDesignerId() == -1 &&
                form.getWorkshopId() != -1){
            model.addAttribute("document_list", documentService.findAllByWorkshop(Workshop.values()[form.getWorkshopId()]));
            return "account";
        }

        else {
            model.addAttribute("document_list", documentService.findAllByOwner(designerService.findDesignerByLogin(acc_login)));
            return "account";
        }
    }


/***

    */


    @ModelAttribute(value = "document")
    public PdmDocument getPdmDocumentForEdit() {
        return new PdmDocument();
    }

    @ModelAttribute(value = "document_form")
    public PdmDocumentForm getPdmDocumentForm() {
        return new PdmDocumentForm();
    }

    @ModelAttribute(value = "format_form")
    public FormatForm getFormatForm() {
        return new FormatForm();
    }

    @ModelAttribute(value = "docfilter_form")
    public DocFilteForm getDocFilterForm() {
        return new DocFilteForm();
    }

//    @ModelAttribute(value = "docformat_form")
//    public DocFormatForm getDocFormatForm() {
//        return new DocFormatForm();
//    }
}
