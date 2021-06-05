package nicomed.webpdm.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import nicomed.webpdm.enums.Workshop;
import nicomed.webpdm.forms.PlanPointForm;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "planmonth")
@Table(name = "plan_point")
@Entity
public class PlanPoint extends BaseEntity{

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

    @ManyToOne
    @JoinColumn(name = "designer_id")
    private Designer designer;

    @ManyToOne
    @JoinColumn(name = "planmonth_id")
    private PlanMonth planmonth;

    @OneToMany(mappedBy = "planpoint")
    @JsonIgnore
    private List<PdmDocument> documents;

    public static PlanPoint from(PlanPointForm form){
        return PlanPoint.builder()
                .orderInPlan(form.getOrderInPlan())
                .name(form.getName())
                .workshop(form.getWorkshop())
                .basics(form.getBasics())
                .designation(form.getDesignation())
//                .startMonth(form.getStartMonth())
                .comment(form.getComment())
                .build();
    }

    public static PlanPointForm to(PlanPoint pp){
        return PlanPointForm.builder()
                .id(pp.getId())
                .orderInPlan(pp.getOrderInPlan())
                .name(pp.getName())
                .workshop(pp.getWorkshop())
                .basics(pp.getBasics())
                .designation(pp.getDesignation())
                .startMonth(pp.getStartMonth())
//                .startMonth(new SimpleDateFormat("yyyy-MMM-dd").format(pp.getStartMonth().getTime()))
                .designer(pp.designer)
                .comment(pp.getComment())
                .build();
    }

}
