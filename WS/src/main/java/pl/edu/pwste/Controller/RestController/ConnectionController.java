package pl.edu.pwste.Controller.RestController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping
@RestController
public class ConnectionController {

    @RequestMapping(value = "/checkConnection", method = RequestMethod.GET)
    public ResponseEntity<String> checkConnection() {
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}
