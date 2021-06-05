package nicomed.webpdm.controller;

import nicomed.webpdm.enums.Months;
import nicomed.webpdm.enums.MyEnum;
import nicomed.webpdm.forms.PlanSelectionForm;
import nicomed.webpdm.models.PlanMonth;
import nicomed.webpdm.service.DesignerService;
import nicomed.webpdm.service.PlanMonthService;
import nicomed.webpdm.service.PlanPointServise;
import nicomed.webpdm.utils.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Controller
@RequestMapping(value = "/mypdm")
public class AccountController {

    private final PlanPointServise planPointServise;
    private final PlanMonthService planMonthService;
    private final DesignerService designerService;

    public AccountController(PlanPointServise planPointServise,
                             DesignerService designerService,
                             PlanMonthService planMonthService) {
        this.planPointServise = planPointServise;
        this.planMonthService = planMonthService;
        this.designerService = designerService;
    }


    @GetMapping("/{acc_login}")
    public String getAccPage(@PathVariable String acc_login, ModelMap model) {
//        model.addAttribute("account_main_section", "");
        model.addAttribute("user", acc_login);
        model.addAttribute("fr_nav", "nav_account");
        return "account";
    }

    @GetMapping("/{acc_login}/documents")
    public String getAccPdmPage(@PathVariable String acc_login) {
        return "redirect:/mypdm/" + acc_login + "/documents/all";
    }

    @GetMapping("/{acc_login}/users")
    public String getAccUsersPage(@PathVariable String acc_login) {
        return "redirect:/mypdm/" + acc_login + "/users/all";
    }

    @GetMapping("/{acc_login}/planing")
    public String getAccPlaningPage(@PathVariable String acc_login,
                                    @ModelAttribute("planselection") PlanSelectionForm planSelection_form,
                                    ModelMap model) {
        model.addAttribute("account_main_section", "acc_plan");
        model.addAttribute("user", acc_login);
        model.addAttribute("fr_nav", "nav_account");
        model.addAttribute("fr_plan_main", "planpoint_all");

        model.addAttribute("planselection", MyUtils.getNowPlanSelectionForm());

        PlanMonth pm = planMonthService.findByMonthAndYear(MyUtils.getNowMonth(), MyUtils.getNowYear());

        model.addAttribute("planpoint_list", planPointServise.findByPlanmonth(pm));
        model.addAttribute("year_list", MyUtils.getYears(planMonthService.findAll()));
        model.addAttribute("month_list", MyEnum.MonthSHORT());
        return "account";
    }


    @GetMapping("/{acc_login}/logoff")
    public String getAccPlanPage(@PathVariable String acc_login, ModelMap model) {
        return "redirect:/mypdm";
    }

    @ModelAttribute(value = "planselection")
    public PlanSelectionForm getPlanSelectionForm() {
        return new PlanSelectionForm();
    }


}
