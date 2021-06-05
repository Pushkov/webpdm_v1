package nicomed.webpdm.controller;

import nicomed.webpdm.enums.UserRole;
import nicomed.webpdm.forms.DesignerAdminForm;
import nicomed.webpdm.models.Designer;
import nicomed.webpdm.service.DesignerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/mypdm")
public class AccountDesignerController {

    private final DesignerService designerService;
    @Autowired
    public AccountDesignerController(DesignerService designerService) {
        this.designerService = designerService;
    }

    @GetMapping(value = "/{acc_login}/users/all")
    public String getAllDesigners(@PathVariable String acc_login, ModelMap model){
        model.addAttribute("account_main_section", "acc_usr");

        model.addAttribute("user", acc_login);

        model.addAttribute("fr_nav", "nav_account");
        model.addAttribute("fr_user_main","usr_all");

        model.addAttribute("designer_list", designerService.findAll());
        if(designerService.findDesignerByLogin(acc_login).getRole() == UserRole.ADMIN){
            model.addAttribute("action", "edit");
        }
        else{
            model.addAttribute("action", "view");
        }
        return "account";
    }

    @GetMapping("/{acc_login}/users/{user_id}")
    public String getDesignerById(@PathVariable String acc_login, @PathVariable("user_id") Long user_id,  ModelMap model){
        model.addAttribute("account_main_section", "acc_usr");
        model.addAttribute("user", acc_login);
        model.addAttribute("fr_nav", "nav_account");
        model.addAttribute("fr_user_main","usr_single");
        Designer designer = designerService.findOneById(user_id);
        model.addAttribute("designer", new DesignerAdminForm().from(designer));
        if(designerService.findDesignerByLogin(acc_login).getRole() == UserRole.ADMIN){
            model.addAttribute("action", "edit");
        }
        else{
            model.addAttribute("action", "view");
        }
        return "account";
    }

    @PostMapping("/{acc_login}/users/{user_id}/update")
    public String updateDesignerById(@PathVariable String acc_login,
                                     @PathVariable("user_id") Long user_id,
                                     @ModelAttribute("designer") DesignerAdminForm designer_adminform
                                     ){
        if(designerService.findDesignerByLogin(acc_login).getRole() == UserRole.ADMIN){
            Designer designer = designerService.findOneById(user_id);

            designer.from(designer_adminform);
            designerService.save(designer);
            System.out.println(designer_adminform.getFirstName() + " id -" + designer.getId());
        }else{
        }
        return "redirect:/mypdm/" + acc_login + "/users/all";
    }



    @GetMapping("/{acc_login}/users/create")
    public String getCreateDesignerPage(@PathVariable String acc_login, ModelMap model){
        if(designerService.findDesignerByLogin(acc_login).getRole() == UserRole.ADMIN){
        model.addAttribute("account_main_section", "acc_usr");
        model.addAttribute("user", acc_login);
        model.addAttribute("fr_nav", "nav_account");
        model.addAttribute("fr_user_main","usr_single");
        model.addAttribute("designer", new DesignerAdminForm());
        model.addAttribute("action", "edit");
            return "account";
        }
        else{
            return "redirect:/mypdm/" + acc_login + "/users/all";
        }
    }

    @PostMapping("/{acc_login}/users/create")
    public String createDocumentPage(@PathVariable String acc_login, @ModelAttribute("designer") DesignerAdminForm designer){
        designerService.save(new Designer().from(designer));
        return "redirect:/mypdm/" + acc_login + "/users/all";
    }

    @GetMapping("/{acc_login}/users/{user_id}/delete")
    public String deleteDesigner(@PathVariable String acc_login,
                                 @PathVariable("user_id") Long user_id){
        Designer designer = designerService.findOneById(user_id);
        designerService.delete(designer);
        return "redirect:/mypdm/" + acc_login + "/users/all";
    }




//    @ModelAttribute(value = "designer")
//    public Designer getDesigner(){
//        return new Designer();
//    }

//    @ModelAttribute(value = "designer")
//    public DesignerForm getDesignerForm(){
//        return new DesignerForm();
//    }

    @ModelAttribute(value = "designer")
    public DesignerAdminForm getDesignerAdminForm(){
        return new DesignerAdminForm();
    }
}
