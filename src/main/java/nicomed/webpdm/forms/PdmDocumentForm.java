package nicomed.webpdm.forms;

import lombok.Data;
import nicomed.webpdm.enums.Workshop;
import nicomed.webpdm.models.Designer;
import nicomed.webpdm.models.DocFormat;

import java.util.List;

@Data
public class PdmDocumentForm {
    private String des;
    private String name;
    private Workshop workshop;
    private Designer owner;
    private List<DocFormat> formats;
    private String basics;
    private String comment;

}
