package nicomed.webpdm.utils;

import nicomed.webpdm.forms.PlanSelectionForm;
import nicomed.webpdm.models.Designer;
import nicomed.webpdm.models.PlanMonth;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class MyUtils {

    public static List<Integer> getYears(List<PlanMonth> list){
        List<Integer> years = new ArrayList<>();
        for (PlanMonth pm : list){
            if (!years.contains(pm.getYear()))
                years.add(pm.getYear());
        }
        return years;
    }

    public static List<String> getDesignersLogin(List<Designer> list){
        List<String> designers = new ArrayList<>();
        for (Designer d : list){
            designers.add(d.getLogin());
        }
        return designers;
    }

    public static PlanSelectionForm getNowPlanSelectionForm(){
        Calendar c = new GregorianCalendar();
        PlanSelectionForm planselection = new PlanSelectionForm();
        planselection.setMonth( c.get(Calendar.MONTH));
        planselection.setYear( c.get(Calendar.YEAR));
        return planselection;
    }

    public static int getNowMonth(){
        return  new GregorianCalendar().get(Calendar.MONTH);
    }
    public static int getNowYear(){
        return  new GregorianCalendar().get(Calendar.YEAR);
    }

    public static Calendar getCalendarFormForm(int month, int year){
        Calendar c = new GregorianCalendar();




        return c;
    }



}
