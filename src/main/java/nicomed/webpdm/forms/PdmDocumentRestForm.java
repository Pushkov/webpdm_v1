package nicomed.webpdm.forms;

import lombok.Data;
import nicomed.webpdm.models.DocFormat;

import java.util.List;

@Data
public class PdmDocumentRestForm {
    private String des;
    private String name;
    private String workshop;
    private String owner;
    private String format;
    private String basics;
    private String comment;
    private List<DocFormat> formats;
    private Long planPointId;
}
