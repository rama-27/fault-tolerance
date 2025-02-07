package dev.rama27.webapp.controller;

import dev.rama27.webapp.service.AzureBlobStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private AzureBlobStorageService azureBlobStorageService;

    @GetMapping("/test-azurite")
    public ResponseEntity<String> testAzurite() {
        try {
            String blobName = "test-blob.txt";
            String data = "Hello Azurite!";
            azureBlobStorageService.uploadBlob(blobName, data.getBytes());

            byte[] downloadedData = azureBlobStorageService.downloadBlob(blobName);
            String downloadedString = new String(downloadedData);

            if (data.equals(downloadedString)) {
                return new ResponseEntity<>("Azurite test successful!", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Azurite test failed: Data mismatch", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Azurite test failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}