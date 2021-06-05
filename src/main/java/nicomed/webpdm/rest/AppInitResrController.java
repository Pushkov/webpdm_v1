package nicomed.webpdm.rest;

import nicomed.webpdm.enums.FormatSheet;
import nicomed.webpdm.enums.Workshop;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mypdm/rest/app")
public class AppInitResrController {


    @RequestMapping(value = "/formats", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<FormatSheet[]> getFormats(){
        return new ResponseEntity<>(FormatSheet.values(), HttpStatus.OK);
    }

    @RequestMapping(value = "/workshops", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Workshop[]> getWorkshops(){
        return new ResponseEntity<>(Workshop.values(), HttpStatus.OK);
    }

}
