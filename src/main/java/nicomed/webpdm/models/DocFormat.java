package nicomed.webpdm.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nicomed.webpdm.enums.FormatSheet;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="formats")
public class DocFormat extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private FormatSheet format;
    private int qty;

    public float getSizeA1() {
        return this.format.getSizeA1();
    }

    @ManyToOne
    @JoinColumn(name = "docs_id")
    @JsonIgnore
    private PdmDocument docs;

    public void from(DocFormat form){
        this.format = form.getFormat();
        this.qty = form.getQty();
    }

}
