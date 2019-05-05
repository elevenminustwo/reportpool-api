package tr.edu.akdeniz.reportpool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.edu.akdeniz.reportpool.entity.Department;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department,Integer> {
    @Override
    List<Department> findAll();

    Department findByDepartmentId(int id);

}
