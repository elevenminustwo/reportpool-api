package tr.edu.akdeniz.reportpool.service;

import tr.edu.akdeniz.reportpool.entity.Report;

import java.util.List;

public interface ReportService {

    List<Report> findAll();

    Report save(Report report);

    Report getIncompleteReportOf(int userId);

}
