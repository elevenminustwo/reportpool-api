package tr.edu.akdeniz.reportpool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.edu.akdeniz.reportpool.entity.Attachment;

import java.util.List;

public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {

    List<Attachment> findAllByReportId(int reportId);


}
