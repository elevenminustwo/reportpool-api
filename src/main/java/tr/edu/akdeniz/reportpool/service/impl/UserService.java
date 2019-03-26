package tr.edu.akdeniz.reportpool.service.impl;

import org.hibernate.jpa.internal.schemagen.ScriptTargetOutputToFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.edu.akdeniz.reportpool.model.PaginationDto;
import tr.edu.akdeniz.reportpool.model.UserDto;
import tr.edu.akdeniz.reportpool.repository.UserRepository;
import tr.edu.akdeniz.reportpool.service.GenericUserService;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements GenericUserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager entityManager;


    public List<UserDto> getUser(){
        return userRepository.findAll().stream()
                .map(t -> new UserDto(t.getUserId(),t.getUsername(),t.getEmail(),t.getPassword(),t.getName(),t.getSurname(),t.getIsActive()))
                .collect(Collectors.toList());
    }
    public PaginationDto getPersons(){
        Query q = entityManager
                .createNativeQuery(
                        "SELECT u.Username,u.Name,u.Surname,u.Email,u.IsActive" +
                                " FROM user u" +
                                " LEFT JOIN userroles u2 on u.UserID = u2.user_id" +
                                " LEFT JOIN role r on u2.role_id = r.RoleID"+
                                " LEFT JOIN userunit u3 on u.UserID = u3.user_id").setMaxResults(5);

        System.out.println(q.getResultList().size());
        return null;
    }

}
