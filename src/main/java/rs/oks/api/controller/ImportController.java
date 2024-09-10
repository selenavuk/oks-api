package rs.oks.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.oks.api.service.ImportService;
import com.google.api.client.auth.oauth2.Credential;
import rs.oks.api.misc.GoogleAuthorizeUtil;

@RestController
@RequestMapping("/import")
public class ImportController {

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
        try {
            Credential credential = GoogleAuthorizeUtil.getCredentialFromCode(code);
            importService.updateDatabaseFromSpreadSheetsFile(credential);
            String responseMessage = "Data successfully imported from Google Spread Sheets. <a href=\"http://localhost:4200/\">Click here to go back.</a>";
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