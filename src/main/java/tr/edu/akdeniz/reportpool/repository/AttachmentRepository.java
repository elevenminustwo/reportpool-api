package tr.edu.akdeniz.reportpool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.edu.akdeniz.reportpool.entity.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {



}
