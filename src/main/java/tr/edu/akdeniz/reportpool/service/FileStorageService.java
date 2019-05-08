package tr.edu.akdeniz.reportpool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import tr.edu.akdeniz.reportpool.entity.Attachment;
import tr.edu.akdeniz.reportpool.file_upload.FileStorageException;
import tr.edu.akdeniz.reportpool.file_upload.FileStorageProperties;
import tr.edu.akdeniz.reportpool.file_upload.MyFileNotFoundException;
import tr.edu.akdeniz.reportpool.repository.AttachmentRepository;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Transactional
    public String storeFile(MultipartFile file, int reportId) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());


        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            Attachment attachment = new Attachment(fileName, file.getBytes(), 1, reportId);
            attachmentRepository.save(attachment);


            return fileName;
        } catch (Exception ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    @Transactional(readOnly = true)
    public List<byte[]> loadImagesFromDb(int reportId) {

        List<Attachment> attachments = attachmentRepository.findAllByReportId(reportId);

        if (attachments != null && !attachments.isEmpty()) {
            List<byte[]> resources = new ArrayList<>();
            for (int i = 0; i < attachments.size(); i++) {
                resources.add(attachments.get(i).getFile());
            }
            return resources;

        } else {
            throw new MyFileNotFoundException("File not found ");
        }

    }

    @Transactional
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }
}