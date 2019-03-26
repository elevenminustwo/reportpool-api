package tr.edu.akdeniz.reportpool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.edu.akdeniz.reportpool.entity.User;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUserId(int id);

}
