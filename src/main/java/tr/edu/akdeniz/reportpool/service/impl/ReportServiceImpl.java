package tr.edu.akdeniz.reportpool.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tr.edu.akdeniz.reportpool.controller.FileController;
import tr.edu.akdeniz.reportpool.entity.Report;
import tr.edu.akdeniz.reportpool.repository.ReportRepository;
import tr.edu.akdeniz.reportpool.repository.UserRepository;
import tr.edu.akdeniz.reportpool.service.FileStorageService;
import tr.edu.akdeniz.reportpool.service.ReportService;

import java.beans.Transient;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private FileController fileController;
    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<Report> findAll() {
        return reportRepository.findAll();
    }

    @Transactional
    public Report save(Report report, MultipartFile[] files) {
        int userId = report.getUserId();
        reportRepository.deleteAllByUserIdAndIsCompleted(userId, (byte) 0);
        Report r = reportRepository.save(report);
        fileController.uploadMultipleFiles(files, r.getReportId());

        try (FileWriter fileWriter = new FileWriter("log.txt", true)) {
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            PrintWriter out = new PrintWriter(fileWriter);
            out.println(formatter.format(date) + " " + userRepository.findByUserId(userId).getUsername() + " isimli kullanici yeni rapor ekledi.");
        }
        catch (Exception e){
            System.out.println("not found file");
        }
        return r;
    }

    @Transactional(readOnly = true)
    public Report getIncompleteReportOf(int userId) {
        return reportRepository.findTopByUserIdAndIsCompletedOrderByReportIdDesc(userId, (byte) 0);
    }


}
