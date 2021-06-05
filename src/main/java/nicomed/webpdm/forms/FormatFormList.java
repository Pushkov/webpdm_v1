package nicomed.webpdm.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import nicomed.webpdm.models.DocFormat;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class FormatFormList {
    public FormatFormList(){
        listFormats = new ArrayList<>();
    }



    private List<DocFormat> listFormats;

    public void add(DocFormat dfs){
        this.listFormats.add(dfs);
    }


}
