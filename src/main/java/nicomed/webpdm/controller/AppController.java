package nicomed.webpdm.controller;

import nicomed.webpdm.forms.LoginForm;
import nicomed.webpdm.models.Designer;
import nicomed.webpdm.service.DesignerService;
import nicomed.webpdm.service.PdmDocumentService;
import nicomed.webpdm.utils.MyUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/mypdm")
public class AppController {

    private static Logger log = Logger.getLogger(AppController.class.getName());

    private final PdmDocumentService pdmDocumentService;

    private final DesignerService designerService;

    public AppController(PdmDocumentService pdmDocumentService, DesignerService designerService) {
        this.pdmDocumentService = pdmDocumentService;
        this.designerService = designerService;
    }


    @GetMapping("")
    public String getMainPage() {
        return "index";
    }


    @PostMapping("/login")
    public String loginUserForm(@ModelAttribute LoginForm form) {
        String login = form.getLogin();
        List<String> logins = MyUtils.getDesignersLogin(designerService.findAll());
        if (logins.contains(login)) {
            Designer designer = designerService.findDesignerByLogin(login);
            if(designer.getPassword().equals(form.getPassword()))
                return "redirect:/mypdm/" + login;
            else
                return "redirect:/mypdm";
        }
        else
            return "redirect:/mypdm";
    }

    @ModelAttribute(value = "loginForm")
    public LoginForm newLoginForm() {
        return new LoginForm();
    }
}
