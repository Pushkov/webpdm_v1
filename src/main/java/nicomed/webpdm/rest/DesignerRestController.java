package nicomed.webpdm.rest;

import nicomed.webpdm.models.Designer;
import nicomed.webpdm.models.PdmDocument;
import nicomed.webpdm.service.DesignerService;
import nicomed.webpdm.service.PdmDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/mypdm/rest/designers")
public class DesignerRestController {

    private final DesignerService designerService;

    @Autowired
    public DesignerRestController(DesignerService designerService) {
        this.designerService = designerService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Designer>> getDocByDes(){
            List<Designer> list = designerService.findAll();
           return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
