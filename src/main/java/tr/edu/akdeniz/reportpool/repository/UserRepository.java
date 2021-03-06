package tr.edu.akdeniz.reportpool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.edu.akdeniz.reportpool.entity.User;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUserId(int id);
    Boolean existsUserByUsernameAndPassword(String name,String psw);
    Boolean existsUserByUsernameAndPasswordAndIsActive(String name,String psw, byte isActive);
    User findByUsernameAndPassword(String name,String psw);
    User findByUsername(String username);
    User findByEmail(String email);
    User findFirstByEmail(String email);

    // after security
    Boolean existsUserByUsernameAndIsActive(String name, byte isActive);
}
