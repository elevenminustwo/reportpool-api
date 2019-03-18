package tr.edu.akdeniz.reportpool.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.edu.akdeniz.reportpool.entity.Report;
import tr.edu.akdeniz.reportpool.model.ReportDto;
import tr.edu.akdeniz.reportpool.repository.ReportRepository;
import tr.edu.akdeniz.reportpool.service.ReportService;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportRepository reportRepository;

    public List<Report> findAll() {
        return reportRepository.findAll();
    }

    public Report save(Report report) {
        return reportRepository.save(report);
    }


}
