package tr.edu.akdeniz.reportpool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.edu.akdeniz.reportpool.entity.Unit;

import java.util.List;

public interface UnitRepository extends JpaRepository<Unit,Integer> {

}
