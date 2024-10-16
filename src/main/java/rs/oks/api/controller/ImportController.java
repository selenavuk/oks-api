package rs.oks.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.oks.api.service.ImportService;
import com.google.api.client.auth.oauth2.Credential;
import rs.oks.api.misc.GoogleAuthorizeUtil;

import java.util.logging.Logger;

@RestController
@RequestMapping("/import")
public class ImportController {

    private static final Logger logger = Logger.getLogger(ImportController.class.getName());

    @Autowired
    private ImportService importService;

    @GetMapping("/spreadsheets/auth")
    public ResponseEntity<String> getAuthorizationUrl() {
        try {
            String authorizationUrl = importService.getAuthorizationUrl();
            return ResponseEntity.ok(authorizationUrl);
        } catch (Exception e) {
            String errorMessage = "Error generating authorization URL: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @GetMapping("/spreadsheets/callback")
    public ResponseEntity<String> handleGoogleCallback(@RequestParam("code") String code) {
        logger.info("DEBUG: Handling Google callback with code: " + code);

        try {
            Credential credential = GoogleAuthorizeUtil.getCredentialFromCode(code);
            GoogleAuthorizeUtil.setAuthorizationCode(code);
            credential = GoogleAuthorizeUtil.refreshAccessToken(credential);
//            logger.info("DEBUG: Credential successfully obtained from code." + code);
//            logger.info("DEBUG: Credential successfully obtained from code." + credential);
            importService.updateDatabaseFromSpreadSheetsFile(credential);
            logger.info("DEBUG: Data successfully imported from Google Spread Sheets.");
//            String responseMessage = "Data successfully imported from Google Spread Sheets. <a href=\"http://localhost:4200/\">Click here to go back.</a>";
//            String responseMessage = "Data successfully imported from Google Spread Sheets. <a href=\"https://rhv.rs/\">Click here to go back.</a>";
            String responseMessage = "Data successfully imported from Google Spread Sheets.";

            return ResponseEntity.ok(responseMessage);
        } catch (Exception e) {
            String errorMessage = "Error importing users from Google Spread Sheets: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

//    @PostMapping("/spreadsheets")
//    public ResponseEntity<String> uploadFile() {
//        try {
//            importService.updateDatabaseFromSpreadSheetsFileTestUrl();
////            importService.updateDatabaseFromSpreadSheetsFile();
//            return ResponseEntity.ok("Users successfully imported from Google Spread Sheets.");
//        } catch (Exception e) {
//            String errorMessage = "Error importing users from Google Spread Sheets: " + e.getMessage();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
//        }
//    }
}