package tr.edu.akdeniz.reportpool.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tr.edu.akdeniz.reportpool.controller.FileController;
import tr.edu.akdeniz.reportpool.entity.Report;
import tr.edu.akdeniz.reportpool.repository.ReportRepository;
import tr.edu.akdeniz.reportpool.service.FileStorageService;
import tr.edu.akdeniz.reportpool.service.ReportService;

import java.beans.Transient;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private FileController fileController;

    public List<Report> findAll() {
        return reportRepository.findAll();
    }

    @Transactional
    public Report save(Report report, MultipartFile[] files) {
        int userId = report.getUserId();
        reportRepository.deleteAllByUserIdAndIsCompleted(userId, (byte) 0);
        Report r = reportRepository.save(report);
        fileController.uploadMultipleFiles(files, r.getReportId());
        return r;
    }

    public Report getIncompleteReportOf(int userId) {
        return reportRepository.findTopByUserIdAndIsCompletedOrderByReportIdDesc(userId, (byte) 0);
    }


}
