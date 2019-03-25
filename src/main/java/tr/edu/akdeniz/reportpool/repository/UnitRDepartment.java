package tr.edu.akdeniz.reportpool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.edu.akdeniz.reportpool.entity.Department;
import tr.edu.akdeniz.reportpool.entity.Departmentunit;

import java.util.List;

public interface UnitRDepartment extends JpaRepository<Departmentunit,Integer> {
    List<Departmentunit> findByDepartmentId(int id);
}
