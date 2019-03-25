package tr.edu.akdeniz.reportpool.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.edu.akdeniz.reportpool.model.PaginationDto;
import tr.edu.akdeniz.reportpool.model.UserDto;
import tr.edu.akdeniz.reportpool.repository.UserRepository;
import tr.edu.akdeniz.reportpool.service.GenericUserService;

import javax.persistence.EntityManager;
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
        List l = entityManager
                .createNativeQuery(
                        "SELECT * FROM user,userroles,role " +
                                "WHERE user.UserID=userroles.user_id " +
                                "AND userroles.role_id=role.RoleID " +
                                "AND ").setMaxResults(3).getResultList();
        return null;
    }

}
