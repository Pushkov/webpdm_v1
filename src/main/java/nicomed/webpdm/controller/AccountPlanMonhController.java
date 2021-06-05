package nicomed.webpdm.controller;

import nicomed.webpdm.forms.PlanSelectionForm;
import nicomed.webpdm.service.PlanMonthService;
import nicomed.webpdm.utils.MyUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/mypdm")
public class AccountPlanMonhController {

    private final PlanMonthService planMonthService;

    public AccountPlanMonhController(PlanMonthService planMonthService) {
        this.planMonthService = planMonthService;
    }

    @GetMapping(value = "/{acc_login}/planing/planmoth")
    public String getPlanMonth(@PathVariable String acc_login){
        return "redirect:/mypdm/" + acc_login + "/planing/planmoth/all";
    }

    @GetMapping(value = "/{acc_login}/planing/planmoth/all")
    public String getAllPlanMonth(@PathVariable String acc_login, ModelMap model){
        model.addAttribute("account_main_section", "acc_plan");
        model.addAttribute("user", acc_login);
        model.addAttribute("fr_nav", "nav_account");
        model.addAttribute("fr_plan_main","planmonth_all");
        model.addAttribute("pm_list",planMonthService.findAll());
        model.addAttribute("year_list", MyUtils.getYears(planMonthService.findAll()));
        model.addAttribute("planselection", new PlanSelectionForm(-1,0));
//        model.addAttribute("testmap", Mes.MAP_MES1());
        return "account";
    }

    @GetMapping(value = "/{acc_login}/planing/planmoth/list")
    public String getListPlanMonth(@PathVariable String acc_login,
                                   @ModelAttribute("planselection") PlanSelectionForm planSelection_form,
                                   ModelMap model){
        model.addAttribute("account_main_section", "acc_plan");
        model.addAttribute("user", acc_login);
        model.addAttribute("fr_nav", "nav_account");
        model.addAttribute("fr_plan_main","planmonth_all");
        model.addAttribute("year_list", MyUtils.getYears(planMonthService.findAll()));
        if (planSelection_form.getMonth() == -1 && planSelection_form.getYear() == 0)
                return "redirect:/mypdm/" + acc_login + "/planing/planmoth/all";
        if (planSelection_form.getMonth() == -1 && planSelection_form.getYear() != 0)
            model.addAttribute("pm_list",planMonthService.findAllByYear(planSelection_form.getYear()));
        else
            model.addAttribute("pm_list",planMonthService.findByMonthAndYear(
              planSelection_form.getMonth(),
              planSelection_form.getYear()
            ));
        return "account";
    }

    @GetMapping(value = "/{acc_login}/planing/planmoth/create")
    public String createPlanMonth(@PathVariable String acc_login, ModelMap model){
        model.addAttribute("account_main_section", "acc_plan");
        model.addAttribute("user", acc_login);
        model.addAttribute("fr_nav", "nav_account");
        model.addAttribute("fr_plan_main","planmonth_all");
        return "redirect:/mypdm/" + acc_login + "/planing/planmoth/all";
    }

    @GetMapping(value = "/{acc_login}/planing/planmoth/{pm_id}")
    public String createPlanMonth(@PathVariable String acc_login,
                                  @PathVariable(name="pm_id") String pm_id,
                                  ModelMap model){
        model.addAttribute("account_main_section", "acc_plan");
        model.addAttribute("user", acc_login);
        model.addAttribute("fr_nav", "nav_account");
        model.addAttribute("fr_plan_main","planmonth_all");
        return "redirect:/mypdm/" + acc_login + "/planing/planmoth/all";
    }

    @GetMapping(value = "/{acc_login}/planing/planmoth/{pm_id}/edit")
    public String editPlanMonth(@PathVariable String acc_login,
                                  @PathVariable(name="pm_id") String pm_id,
                                  ModelMap model){
        model.addAttribute("account_main_section", "acc_plan");
        model.addAttribute("user", acc_login);
        model.addAttribute("fr_nav", "nav_account");
        model.addAttribute("fr_plan_main","planmonth_all");
        return "redirect:/mypdm/" + acc_login + "/planing/planmoth/all";
    }

}
