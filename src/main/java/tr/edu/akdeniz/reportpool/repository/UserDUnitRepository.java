package tr.edu.akdeniz.reportpool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.edu.akdeniz.reportpool.entity.Userdepartmentunit;

public interface UserDUnitRepository extends JpaRepository<Userdepartmentunit,Integer> {
    Userdepartmentunit findFirstByDepartmentunitIdAndUserId(int id,int userId);
}
