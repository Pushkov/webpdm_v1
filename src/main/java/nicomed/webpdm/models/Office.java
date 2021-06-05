package nicomed.webpdm.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="office")
public class Office extends BaseEntity{


    private String name;
    private String fullName;

    @OneToOne
    @JsonIgnore
    private Designer boss;

    @OneToMany(mappedBy = "plans")
    @JsonIgnore
    private List<PlanMonth> officePlans;

    @OneToMany(mappedBy = "reserve")
    @JsonIgnore
    private List<Designer> reserve;

    @OneToMany(mappedBy = "office")
    @JsonIgnore
    private List<Designer> designers;
}
