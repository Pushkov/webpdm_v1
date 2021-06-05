package nicomed.webpdm.forms;

import lombok.Data;
import nicomed.webpdm.enums.FormatSheet;

@Data
public class FormatForm {
    private FormatSheet format;
    private int qty;
}
