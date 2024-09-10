package rs.oks.api.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.oks.api.misc.GoogleDriveImagesConfig.GoogleDriveService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/images")
public class ImagesController {

    @Autowired
    private GoogleDriveService googleDriveService;

    @GetMapping("/gallery")
    public ResponseEntity<List<String>> getImagesForGallery() {
        List<String> imageLinks = new ArrayList<>();
        try {
            String baseUrl = "https://drive.google.com/uc?export=view&id=";
            String folderId = "1OK36QYvREEJbw6CNUw5BrnXdg_gEMqLA";

            String result = googleDriveService.listFiles(folderId);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(result);
            JsonNode filesNode = rootNode.path("files");

            if (filesNode.isArray()) {
                for (JsonNode fileNode : filesNode) {
                    String imageId = fileNode.path("id").asText();
//                    String finalUrl = "https://drive.usercontent.google.com/download?id=" + imageId + "&export=view";
                    imageLinks.add(baseUrl + imageId);
                }
            }

            return new ResponseEntity<>(imageLinks, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/camp")
    public ResponseEntity<List<String>> getImagesForCamp() {
        List<String> imageLinks = new ArrayList<>();
        try {
            String baseUrl = "https://drive.google.com/uc?export=view&id=";
            String folderId = "1OK36QYvREEJbw6CNUw5BrnXdg_gEMqLA";

            String result = googleDriveService.listFiles(folderId);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(result);
            JsonNode filesNode = rootNode.path("files");

            if (filesNode.isArray()) {
                for (JsonNode fileNode : filesNode) {
                    String imageId = fileNode.path("id").asText();
                    imageLinks.add(baseUrl + imageId);
                }
            }

            return new ResponseEntity<>(imageLinks, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
