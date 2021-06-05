package nicomed.webpdm.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nicomed.webpdm.enums.Workshop;
import nicomed.webpdm.models.Designer;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocFilteForm {

    private int desFilterType;
    private int nameFilterType;
    private String desFilter;
    private String nameFilter;
    private Long designerId;
    private int workshopId;
}
