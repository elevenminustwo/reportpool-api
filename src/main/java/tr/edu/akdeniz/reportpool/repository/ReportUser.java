package tr.edu.akdeniz.reportpool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.edu.akdeniz.reportpool.entity.Report;
import tr.edu.akdeniz.reportpool.entity.User;

public interface ReportUser extends JpaRepository<Report,Integer> {
    Report findByUserId(int id);
}
