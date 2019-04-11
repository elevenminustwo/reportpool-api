package tr.edu.akdeniz.reportpool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.edu.akdeniz.reportpool.entity.Report;

public interface ReportRepository extends JpaRepository<Report, Integer> {

    Report findTopByUserIdAndIsCompletedOrderByReportIdDesc(int userId, byte isCompleted);



}
