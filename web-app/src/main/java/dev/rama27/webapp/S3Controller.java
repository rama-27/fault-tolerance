package dev.rama27.webapp;

import com.amazonaws.services.s3.model.Bucket;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/s3")
public class S3Controller {
    @Autowired
    private S3Service s3Service;

    @GetMapping("/buckets")
    public List<Bucket> listBuckets(){
        return s3Service.listBuckets();
    }

    @PostMapping("/buckets/{bucketName}")
    public void createBucket(@PathVariable String bucketName){
        s3Service.createBucket(bucketName);
    }
}
