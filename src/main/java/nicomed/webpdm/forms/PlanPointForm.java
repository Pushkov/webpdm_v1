package nicomed.webpdm.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nicomed.webpdm.enums.Workshop;
import nicomed.webpdm.models.Designer;

import java.util.Calendar;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanPointForm {

    private Long id;
    private int orderInPlan;
    private String name;
    private Workshop workshop;
    private String basics;
    private String designation;
    private Calendar startMonth;
    private Calendar endMonth;
    private Calendar confirmationUpir;
    private Calendar confirmationOhers;
    private String comment;
    private Designer designer;
    private int readiness;

}
