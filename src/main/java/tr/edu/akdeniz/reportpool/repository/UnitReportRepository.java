package tr.edu.akdeniz.reportpool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tr.edu.akdeniz.reportpool.entity.Report;

import java.util.List;

public interface UnitReportRepository extends JpaRepository<Report,Integer> {

    List<Report> findAllByDepartmentunitIdEquals(int id);


}
