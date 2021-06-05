package nicomed.webpdm.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nicomed.webpdm.enums.UserRole;
import nicomed.webpdm.enums.UserStatus;
import nicomed.webpdm.models.Designer;
import nicomed.webpdm.models.Office;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DesignerForm {

    private Long id;
    private String login;
    private String firstName;
    private String lastName;
    private UserRole role;
    private UserStatus state;

    private String phone;
    private Office office;
    private Office reserve;

    public Designer to(Designer designer){

        designer.setLogin(this.login);
        designer.setFirstName(this.firstName);
        designer.setLastName(this.lastName);
        designer.setRole(this.role);
        designer.setState(this.state);
        designer.setPhone(this.phone);
        designer.setOffice(this.office);
        designer.setOffice(this.office);
        designer.setReserve(this.reserve);




        return  designer;
    }



}
