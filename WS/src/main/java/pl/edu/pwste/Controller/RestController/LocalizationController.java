package pl.edu.pwste.Controller.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwste.Entity.Localization;
import pl.edu.pwste.Entity.SavedLocalization;
import pl.edu.pwste.Entity.Senior;
import pl.edu.pwste.Service.LocalizationService;
import pl.edu.pwste.Service.UserService;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;


@RequestMapping(value = "/localization")
@RestController
public class LocalizationController {

    @Autowired
    LocalizationService localizationService;

    @Autowired
    UserService userService;

    /* Save current localization for senior
     * 	Method: POST
     *  URL: address: /localization/(SeniorSecurityString)/addCurrentLocalization
     *  Input: JSON Localization
     *  JSON Example: {
              "latitude": 200,
              "longitude": 100,
            }
     *  Output: HTTP Status No_CONTENT (204) if not added
     *  		HTTP Status Ok (200) if added
     * */
    @RequestMapping(value = "/{SeniorSecurityString}/addCurrentLocalization", method = RequestMethod.POST)
    public ResponseEntity<Localization> addCurrentLocalization(@PathVariable(value = "SeniorSecurityString") String seniorSecurityString,
                                                               @RequestBody Localization localization) {
        try {
            localizationService.addCurrentLocation(localization, seniorSecurityString);
            return new ResponseEntity<Localization>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Localization>(HttpStatus.NO_CONTENT);
        }
    }

    /* Save localization for senior
     * 	Method: POST
     *  URL: address: /localization/(seniorSecurityString)/saveLocalization
     *  Input: JSON SavedLocalization
     *  JSON Example: {
              "name": "Name of place",
              "latitude": 1,
              "longitude": 2
            }
     *  Output: HTTP Status No_CONTENT (204) if not added
     *  		HTTP Status Ok (200) if added
     */
    @RequestMapping(value = "/{seniorSecurityString}/saveLocalization", method = RequestMethod.POST)
    public ResponseEntity<SavedLocalization> saveLocalization(@PathVariable(value = "seniorSecurityString") String seniorSecurityString,
                                                              @RequestBody SavedLocalization savedLocalization) {
        try {
            localizationService.addSavedLocation(savedLocalization, seniorSecurityString);
            return new ResponseEntity<SavedLocalization>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<SavedLocalization>(HttpStatus.NO_CONTENT);
        }
    }

    /* Get localizations of selected day
     * 	Method: GET
     *  URL: address: /localization/(seniorSecurityString)/getLocalizations/(date format YYYY:mm:dd)
     *  Input: -
     *  Output: HTTP Status No_CONTENT (204) if error
     *  		HTTP Status Ok (200) if selected
     */
    @RequestMapping(value = "/{seniorSecurityString}/getLocalizations/{date}", method = RequestMethod.GET)
    public ResponseEntity<List<Localization>> getLocalizationsOfDay(@PathVariable(value = "seniorSecurityString") String seniorSecurityString,
                                                                    @PathVariable(value = "date") String stringDate) {
        try {
            //TODO repair format date - 1970 years and -1 month

            SimpleDateFormat sdf1 = new SimpleDateFormat("YYYY:mm:dd");
            java.util.Date date = sdf1.parse(stringDate);
            java.sql.Date sqlStartDate = new Date(date.getTime());
            System.out.println(sqlStartDate);
            List<Localization> listOfLocalization = localizationService.getLocalizationsOfDay(sqlStartDate, seniorSecurityString);
            return new ResponseEntity<List<Localization>>(listOfLocalization, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<Localization>>(HttpStatus.NO_CONTENT);
        }
    }

    /* Save localization for senior
     * 	Method: POST
     *  URL: address: /localization/(seniorSecurityString)/saveLocalization
     *  Input: JSON SavedLocalization
     *  JSON Example: {
              "name": "Name of place",
              "latitude": 1,
              "longitude": 2
            }
     *  Output: HTTP Status No_CONTENT (204) if not added
     *  		HTTP Status Ok (200) if added
     */
    @RequestMapping(value = "/{seniorSecurityString}/saveHomeLocation", method = RequestMethod.POST)
    public ResponseEntity<SavedLocalization> saveHomeLocalization(@PathVariable(value = "seniorSecurityString") String seniorSecurityString,
                                                                  @RequestBody SavedLocalization savedLocalization) {
        try {
            savedLocalization.setName("home");
            localizationService.addSavedLocation(savedLocalization, seniorSecurityString);
            return new ResponseEntity<SavedLocalization>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<SavedLocalization>(HttpStatus.NO_CONTENT);
        }
    }

    /*  Get saved locations for senior
     * 	Method: GET
     *  URL: address: /localization/(seniorSecurityString)/getSavedLocations
     *  Input: JSON SavedLocalization Array
     *  JSON Example: [
            {
                "name": "domek",
                "latitude": 50.0323376,
                "longitude": 22.0327753
            },
            {
                "name": "sklep",
                "latitude": 50.0323028,
                "longitude": 22.032787
            }]
     *  Output: HTTP Status No_CONTENT (204) if exception
     *  		HTTP Status Ok (200) if ok
     */
    @RequestMapping(value = "/{seniorSecurityString}/getSavedLocations", method = RequestMethod.GET)
    public ResponseEntity<List<SavedLocalization>> getSavedLocalizations(@PathVariable(value = "seniorSecurityString") String seniorSecurityString
    ) {
        try {
            Senior senior = userService.findSeniorBySecStr(seniorSecurityString);
            List<SavedLocalization> savedLocalizationList = localizationService.getSavedLocalizationsForSenior(senior.getId());

            return new ResponseEntity<List<SavedLocalization>>(savedLocalizationList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<SavedLocalization>>(HttpStatus.NO_CONTENT);
        }
    }


    /*  Get home location for senior
     * 	Method: GET
     *  URL: address: /localization/(seniorSecurityString)/getHomeLocation
     *  Input: JSON SavedLocalization Array
     *  JSON Example: [
            {
                "name": "Home",
                "latitude": 50.0323376,
                "longitude": 22.0327753
            }]
     *  Output: HTTP Status No_CONTENT (204) if exception
     *  		HTTP Status Ok (200) if home location is not null
     *  	    HTTP Status NOT_FOUND (404) if home location is null
     */
    @RequestMapping(value = "/{seniorSecurityString}/getHomeLocation", method = RequestMethod.GET)
    public ResponseEntity<SavedLocalization> getHomeLocalization(@PathVariable(value = "seniorSecurityString") String seniorSecurityString
    ) {
        try {
            Senior senior = userService.findSeniorBySecStr(seniorSecurityString);
            SavedLocalization savedLocalization = localizationService.getHomeLocalizationForSenior(senior.getId());
           if(savedLocalization != null)
           {
               return new ResponseEntity<SavedLocalization>(savedLocalization, HttpStatus.OK);
           }
           else
           {
               return new ResponseEntity<SavedLocalization>(HttpStatus.NOT_FOUND);
           }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<SavedLocalization>(HttpStatus.NO_CONTENT);
        }
    }
}