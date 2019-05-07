package tr.edu.akdeniz.reportpool.controller;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tr.edu.akdeniz.reportpool.file_upload.UploadFileResponse;
import tr.edu.akdeniz.reportpool.service.FileStorageService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/uploadFile")
    @CrossOrigin
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("reportId") int reportId) {
        String fileName = fileStorageService.storeFile(file, reportId);



        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadMultipleFiles")
    @CrossOrigin
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files, @RequestParam("reportId") int reportId) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file, reportId))
                .collect(Collectors.toList());
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    @CrossOrigin
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/downloadImagesOfReport/{reportId}")
    @CrossOrigin
    public ResponseEntity<List<byte[]>> downloadVoiceRecording(@PathVariable int reportId) {

        List<byte[]> resources = fileStorageService.loadImagesFromDb(reportId);

        return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "")
                    .body(resources);


    }




}
