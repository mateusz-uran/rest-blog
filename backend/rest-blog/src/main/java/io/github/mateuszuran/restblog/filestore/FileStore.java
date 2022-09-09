package io.github.mateuszuran.restblog.filestore;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import io.github.mateuszuran.restblog.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class FileStore {
    private final AmazonS3 s3;

    public FileStore(final AmazonS3 s3) {
        this.s3 = s3;
    }

    public void save(String path, String fileName,
                     Optional<Map<String, String>> optionalMetadata,
                     InputStream stream) {
        ObjectMetadata metadata = new ObjectMetadata();
        optionalMetadata.ifPresent(map -> {
            if (!map.isEmpty()) {
                map.forEach(metadata::addUserMetadata);
            }
        });
        try {
            s3.putObject(path, fileName, stream, metadata);
        } catch (AmazonServiceException e) {
            throw new IllegalStateException("Failed to store file", e);
        }
    }

    public byte[] download(final String path, String key) {
        try {
            S3Object object = s3.getObject(path, key);
            return IOUtils.toByteArray(object.getObjectContent());
        } catch (AmazonServiceException | IOException e) {
            throw new IllegalStateException("Failed to download", e);
        }
    }
}
