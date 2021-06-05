package nicomed.webpdm.models;

import lombok.*;
import nicomed.webpdm.enums.Workshop;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"owner","planpoint"})
@Table(name = "docums")
@Entity
public class PdmDocument extends BaseEntity {

    @Column(unique = true)
    private String des;
    private String name;

    private Workshop workshop;
    private String basics;
    private String comment;

    @OneToMany(mappedBy = "docs")
    private List<DocFormat> formats;

    private Calendar creation_date;
    private Calendar finish_date;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Designer owner;

    @ManyToOne
    @JoinColumn(name = "planpoint_id")
    private PlanPoint planpoint;

    public int getCreationDay(){
        return creation_date.get(Calendar.DAY_OF_MONTH);
    }

    public String getCreationYear(){
        int s = creation_date.get(Calendar.YEAR);
        return String.valueOf(s);
    }

    public String getCreationMonth(){
        return this.creation_date.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
    }

    public String getCreationDate(){
        DateFormat df = new SimpleDateFormat("dd-MMM-yyy");
        return df.format( this.creation_date.getTime());
    }

    public void from(PdmDocument documentForm){
        this.des = documentForm.getDes();
        this.name = documentForm.getName();
        this.workshop = documentForm.getWorkshop();
        this.owner = documentForm.getOwner();
//        this.formats = documentForm.getFormats();
        this.basics = documentForm.getBasics();
        this.comment = documentForm.getComment();
    }


}