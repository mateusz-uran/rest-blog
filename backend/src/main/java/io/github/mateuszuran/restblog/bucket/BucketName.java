package io.github.mateuszuran.restblog.bucket;

public enum BucketName {

    POST_IMAGE("blog-post-images");

    private final String bucketName;

    BucketName(final String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return bucketName;
    }
}
