package nicomed.webpdm.rest;

import nicomed.webpdm.forms.PlanSelectionForm;
import nicomed.webpdm.models.PdmDocument;
import nicomed.webpdm.models.PlanPoint;
import nicomed.webpdm.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/mypdm/rest/plan")
public class PlanPointRestController {

    private final PlanPointServise planPointServise;
    private final PlanMonthService planMonthService;

    public PlanPointRestController(PlanPointServise planPointServise, PlanMonthService planMonthService) {
        this.planPointServise = planPointServise;
        this.planMonthService = planMonthService;
    }

    @RequestMapping(value = "/point/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<PlanPoint>> getAllPalnPoints(){
        List<PlanPoint> document = planPointServise.findAll();
        return new ResponseEntity<>(document, HttpStatus.OK);
    }

    @RequestMapping(value = "/point/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<PlanPoint>> getAllPlanPointsByDate(
            PlanSelectionForm form
    ){
        int startMonth = form.getMonth();
        int endMonth  = form.getMonth();
        if (form.getMonth() == -1){
            startMonth =  0;
            endMonth = 12;
        }


        Calendar c = Calendar.getInstance();
        int startDay = c.getActualMinimum(Calendar.DAY_OF_MONTH);
        int endDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);

        System.out.println("month - " + form.getMonth()+ "; start - " + startDay + "; end - " + endDay);


        int startYear = form.getYear() == -1 ? 0 : form.getYear();
        int endYear  = form.getYear() == -1 ? 3000 : form.getYear();
        if(form.getYear() == -1){
            startMonth = 0;
            endMonth = 12;
        }


        List<PlanPoint> document = planPointServise.findAll();
        return new ResponseEntity<>(document, HttpStatus.OK);
    }

}
