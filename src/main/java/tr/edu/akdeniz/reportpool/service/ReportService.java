package tr.edu.akdeniz.reportpool.service;

import org.springframework.web.multipart.MultipartFile;
import tr.edu.akdeniz.reportpool.entity.Report;

import java.util.List;

public interface ReportService {

    List<Report> findAll();

    Report save(Report report, MultipartFile[] files);

    Report getIncompleteReportOf(int userId);

}
