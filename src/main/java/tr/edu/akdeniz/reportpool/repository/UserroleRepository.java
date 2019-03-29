package tr.edu.akdeniz.reportpool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.edu.akdeniz.reportpool.entity.Userroles;

public interface UserroleRepository  extends JpaRepository<Userroles,Integer> {
    Userroles findByUserId(int userid);
    Userroles findByUserIdAndRoleId(int userid,int roleid);
}
