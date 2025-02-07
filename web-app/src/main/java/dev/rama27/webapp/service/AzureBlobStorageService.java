package dev.rama27.webapp.service;

import com.azure.storage.blob.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

        import com.azure.storage.blob.models.BlobItem;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

@Service
public class AzureBlobStorageService {

    @Autowired
    private BlobServiceClient blobServiceClient;

    @Value("${azure.storage.container-name}")
    private String containerName;

    public Mono<Void> uploadBlob(String blobName, String data) {
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);

        // Create the container if it doesn't exist
        if (!containerClient.exists()) {
            containerClient.create();
        }

        BlobClient blobClient = containerClient.getBlobClient(blobName);
        byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
        return Mono.just(ByteBuffer.wrap(bytes))
                .flatMap(buffer -> Mono.from(blobClient.upload(buffer, bytes.length)));
    }

    public Mono<String> downloadBlob(String blobName) {
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
        BlobClient blobClient = containerClient.getBlobClient(blobName);

        return Mono.from(blobClient.downloadContent())
                .map(blobDownloadContent -> new String(blobDownloadContent.getValue(), StandardCharsets.UTF_8));
    }

    public Flux<String> listBlobs() {
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
        return Flux.fromIterable(containerClient.listBlobs())
                .map(BlobItem::getName);
    }
}