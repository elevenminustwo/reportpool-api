package tr.edu.akdeniz.reportpool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.edu.akdeniz.reportpool.entity.Attachment;

public interface ReportAttachment extends JpaRepository<Attachment,Integer> {
    ReportAttachment findByReportId(int id);
}
