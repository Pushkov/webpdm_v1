package nicomed.webpdm.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Month;
import java.util.Calendar;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "planmonth")
public class PlanMonth extends BaseEntity{

    private int month;
    private int year;
    private Calendar date;

    @ManyToOne
    @JoinColumn(name="plans_id")
    private Office plans;

    @OneToMany(mappedBy = "planmonth")
    @JsonIgnore
    private List<PlanPoint> planPointList;

}
