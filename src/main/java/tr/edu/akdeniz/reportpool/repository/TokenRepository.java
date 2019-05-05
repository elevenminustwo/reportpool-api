package tr.edu.akdeniz.reportpool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.edu.akdeniz.reportpool.entity.Token;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    Token findByToken(String token);
    void deleteAllByUserId(Integer userId);

}
