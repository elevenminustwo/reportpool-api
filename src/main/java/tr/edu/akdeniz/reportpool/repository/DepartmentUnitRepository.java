package tr.edu.akdeniz.reportpool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.edu.akdeniz.reportpool.entity.Departmentunit;

import java.util.List;

public interface DepartmentUnitRepository extends JpaRepository<Departmentunit,Integer> {
    List<Departmentunit> findAllByDepartmentId(int id);
    Departmentunit findFirstByDepartmentIdAndUnitId(int depId,int uId);
}
