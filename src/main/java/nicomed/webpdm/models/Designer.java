package nicomed.webpdm.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nicomed.webpdm.enums.UserRole;
import nicomed.webpdm.enums.UserStatus;
import nicomed.webpdm.forms.DesignerAdminForm;
import nicomed.webpdm.forms.DesignerForm;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="designers")
public class Designer extends BaseEntity {

    @Column(unique = true)
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private UserRole role;
    private UserStatus state;

    private String phone;


    @ManyToOne
    @JoinColumn(name = "office_id")
    private Office office;

    @ManyToOne
    @JoinColumn(name = "reserve_id")
    private Office reserve;

    @OneToMany(mappedBy = "owner")
    @JsonIgnore
    private List<PdmDocument> documents;

    @OneToMany(mappedBy = "designer")
    @JsonIgnore
    private List<PlanPoint> planPoints;

    public Designer from(DesignerAdminForm form){
        return Designer.builder()
                .login(form.getLogin())
                .firstName(form.getFirstName())
                .lastName(form.getLastName())
                .role(form.getRole())
                .state(form.getState())
                .phone(form.getPhone())
                .office(form.getOffice())
                .reserve(form.getReserve())
                .build();
    }

    public Designer from2(DesignerForm form){
        return Designer.builder()
                .login(form.getLogin())
                .firstName(form.getFirstName())
                .lastName(form.getLastName())
                .role(form.getRole())
                .state(form.getState())
                .phone(form.getPhone())
                .office(form.getOffice())
                .reserve(form.getReserve())
                .build();
    }


}
