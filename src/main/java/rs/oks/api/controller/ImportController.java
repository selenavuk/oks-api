package rs.oks.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rs.oks.api.misc.ExcelFileMapper;
import rs.oks.api.service.ImportService;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/import")
public class ImportController {

    @Autowired
    private ImportService importService;

//    @PostMapping("/local")
//    public /*ResponseEntity<ResponseMessage>*/boolean uploadFile(@RequestParam("file") MultipartFile file) {
//
//        String message = "";
//
//        if (ExcelFileMapper.hasExcelFormat(file)) {
//            try {
//                importService.updateUsersFromLocalExcelFile(file);
//
//                message = "Uploaded the file successfully: " + file.getOriginalFilename();
////                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
//                return true;
//            } catch (Exception e) {
//                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
////                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
//                return false;
//            }
//        }
//
//        message = "Please upload an excel file!";
////        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
//        return false;
//    }

    @PostMapping("/spreadsheets")
    @ResponseStatus(HttpStatus.OK)
    public void uploadFile() throws GeneralSecurityException, IOException, ExecutionException, InterruptedException {
        importService.updateUsersFromSpreadSheetsFile();
    }
}
