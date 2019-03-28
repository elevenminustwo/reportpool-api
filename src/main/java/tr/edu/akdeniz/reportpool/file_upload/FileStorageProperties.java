package tr.edu.akdeniz.reportpool.file_upload;


import org.springframework.boot.context.properties.ConfigurationProperties;

//POJO to bind all storage properties from application.properties (file.upload-dir=./uploads)

@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {

    private String uploadDir;

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

}
