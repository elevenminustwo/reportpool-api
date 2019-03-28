package tr.edu.akdeniz.reportpool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import tr.edu.akdeniz.reportpool.file_upload.FileStorageProperties;

// Push test to Mert branch

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class ReportpoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReportpoolApplication.class, args);
    }
}