package dev.rama27.webapp;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Service
public class S3Service {
    @Value("${aws.s3.endpoint}")
    private String s3Endpoint;
    private AmazonS3 s3Client;

    @PostConstruct
    public void init() throws URISyntaxException{
        s3Client = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(
                                new URI(s3Endpoint).toString(),"us-east-1"
                        )
                )
                .withPathStyleAccessEnabled(true).build();
    }

    public List<Bucket> listBuckets(){
        return s3Client.listBuckets();
    }
    public void createBucket(String bucketName){
        s3Client.createBucket(bucketName);
    }
}
