package nicomed.webpdm.rest;

import nicomed.webpdm.enums.Workshop;
import nicomed.webpdm.forms.PdmDocumentRestForm;
import nicomed.webpdm.models.DocFormat;
import nicomed.webpdm.models.PdmDocument;
import nicomed.webpdm.models.PlanPoint;
import nicomed.webpdm.service.DesignerService;
import nicomed.webpdm.service.FormatService;
import nicomed.webpdm.service.PdmDocumentService;
import nicomed.webpdm.service.PlanPointServise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.GregorianCalendar;
import java.util.List;


@RestController
@RequestMapping("/mypdm/rest/documents")
public class PdmDocumentRestController {

    private final PdmDocumentService pdmDocumentService;
    private final DesignerService designerService;
    private final FormatService formatService;
    private final PlanPointServise planPointServise;

    @Autowired
    public PdmDocumentRestController(PdmDocumentService pdmDocumentService,
                                     DesignerService designerService,
                                     PlanPointServise _planPointServise,
                                     FormatService _formatService) {
        this.pdmDocumentService = pdmDocumentService;
        this.designerService = designerService;
        this.formatService = _formatService;
        this.planPointServise = _planPointServise;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<PdmDocument> saveDocRest(PdmDocumentRestForm form){
        if(form == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        else{
            PdmDocument document = new PdmDocument();
            System.out.println(form.toString());
            document.setDes(form.getDes());
            document.setName(form.getName());
            document.setCreation_date(new GregorianCalendar());
            document.setBasics(form.getBasics());
            document.setComment(form.getComment());
//            PlanPoint pp = planPointServise.getOne(form.getPlanPointId());
//            document.setPlanpoint(pp);

            document.setWorkshop(Workshop.valueOf(form.getWorkshop()));
            document.setOwner(designerService.findOneById(Long.valueOf(form.getOwner())));
            pdmDocumentService.save(document);

//            DocFormatSizing format = new DocFormatSizing();
//            format.setQty(1);
//            format.setFormat(FormatSheet.valueOf(form.getFormat()));
//            format.setDocs(document);
//            formatService.save(format);

            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }
    @RequestMapping(value = "/{des}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<PdmDocument> getDocByDes(@PathVariable String des){
       if(des.equals(""))
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       else {
           PdmDocument document = pdmDocumentService.getByDes(des);
           return new ResponseEntity<>(document, HttpStatus.OK);
       }
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<PdmDocument>> getAllDocs(){
            List<PdmDocument> document = pdmDocumentService.findAll();
            return new ResponseEntity<>(document, HttpStatus.OK);
    }


    @RequestMapping(value = "/format", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<DocFormat>> getFormats(){
            List<DocFormat> formats = formatService.findAll();
            return new ResponseEntity<>(formats, HttpStatus.OK);
    }

    @RequestMapping(value = "/planpoint", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<PlanPoint>> getPlanPoints(){
        List<PlanPoint> planpoint_list = planPointServise.findAll();
        return new ResponseEntity<>(planpoint_list, HttpStatus.OK);
    }


//    @RequestMapping(value = "{des}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public ResponseEntity<PdmDocument> saveDoc(@PathVariable String des){
//        if(des.equals(""))
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        else {
//            PdmDocument document = pdmDocumentService.findOne(des);
//            return new ResponseEntity<>(document, HttpStatus.OK);
//        }
//    }

}
