package io.github.mateuszuran.restblog.controller.integration;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import io.github.mateuszuran.restblog.bucket.BucketName;
import io.github.mateuszuran.restblog.filestore.FileStore;
import io.github.mateuszuran.restblog.model.Post;
import io.github.mateuszuran.restblog.repository.PostRepository;
import io.github.mateuszuran.restblog.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class PostServiceIntegrationTest {
    @Mock
    private PostRepository repository;
    @Mock
    private AmazonS3 s3;
    @Mock
    private FileStore fileStore;
    @InjectMocks
    private PostService service;
    private Post post;

    @BeforeEach
    void setUp() {
        post = Post.builder()
                .id(1L)
                .header("Unit test")
                .intro("testing with mockito")
                .content("foo bar")
                .imageName("")
                .imagePath("")
                .imageType("")
                .projectCodeLink("code link")
                .projectDemoLink("demo link")
                .build();
    }

    @Test
    public void givenUploadImage_whenUpload_thenVerify() {
        //given
        MockMultipartFile file = new MockMultipartFile(
                "image",
                "image.png",
                MediaType.IMAGE_PNG_VALUE,
                "test".getBytes()
        );
        given(repository.findById(post.getId())).willReturn(Optional.of(post));
        //when
        service.uploadImageToPost(post.getId(), file);
        //then
        verify(repository).save(any(Post.class));

    }

    @Test
    public void givenUploadImage_whenDownload_thenVerify() throws IOException {
        //given
        MockMultipartFile file = new MockMultipartFile(
                "image",
                "image.png",
                MediaType.IMAGE_PNG_VALUE,
                "test".getBytes()
        );
        given(repository.findById(post.getId())).willReturn(Optional.of(post));
        //when
        service.uploadImageToPost(post.getId(), file);
        //then
        verify(repository).save(any(Post.class));
    }

    @Test
    public void givenUploadImage_whenSaveFile_thenVerify() throws IOException {
        //given
        MockMultipartFile file = new MockMultipartFile(
                "image",
                "image.png",
                MediaType.IMAGE_PNG_VALUE,
                "test".getBytes()
        );
        String path = String.format("%s/%s", BucketName.POST_IMAGE.getBucketName(), post.getId());
        String fileName = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());
        InputStream inputStream = file.getInputStream();
        Map<String, String> optionalMetadata = new HashMap<>();
        optionalMetadata.put("Content-Type", file.getContentType());
        optionalMetadata.put("Content-Length", String.valueOf(file.getSize()));
        //when
        fileStore.save(path, fileName, Optional.of(optionalMetadata), inputStream);
        //then
        verify(fileStore, times(1)).save(path, fileName, Optional.of(optionalMetadata), inputStream);

    }

    @Test
    public void givenUploadImage_whenPutObject_thenVerify() throws IOException {
        //given
        MockMultipartFile file = new MockMultipartFile(
                "image",
                "image.png",
                MediaType.IMAGE_PNG_VALUE,
                "test".getBytes()
        );
        String path = String.format("%s/%s", BucketName.POST_IMAGE.getBucketName(), post.getId());
        String fileName = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());
        InputStream inputStream = file.getInputStream();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        //when
        s3.putObject(path, fileName, inputStream, metadata);
        //then
        verify(s3, times(1)).putObject(path, fileName, inputStream, metadata);
    }
}
