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
public class DesignerAdminForm {

    private Long id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private UserRole role;
    private UserStatus state;
    private String phone;
    private Office office;
    private Office reserve;

    public DesignerAdminForm from(Designer designer){
        return DesignerAdminForm.builder()
                .id(designer.getId())
                .login(designer.getLogin())
                .password(designer.getPassword())
                .firstName(designer.getFirstName())
                .lastName(designer.getLastName())
                .role(designer.getRole())
                .state(designer.getState())
                .phone(designer.getPhone())
                .office(designer.getOffice())
                .reserve(designer.getReserve())
                .build();
    }
}
