package tr.edu.akdeniz.reportpool.service.impl;

import org.hibernate.jpa.internal.schemagen.ScriptTargetOutputToFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.edu.akdeniz.reportpool.model.PaginationDto;
import tr.edu.akdeniz.reportpool.model.PersonDto;
import tr.edu.akdeniz.reportpool.model.UserDto;
import tr.edu.akdeniz.reportpool.repository.UserRepository;
import tr.edu.akdeniz.reportpool.service.GenericUserService;

import javax.persistence.*;
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
    public PaginationDto getPersons(String draw,String length,String skip,String sortDir,String sortColumnIndex,String search){
        Query q = entityManager
                .createNativeQuery(
                        "SELECT u.Username as username,u.Name as name ,u.Surname,u.Email,u.IsActive" +
                                " FROM user u" +
                                " LEFT JOIN userroles u2 on u.UserID = u2.user_id" +
                                " LEFT JOIN role r on u2.role_id = r.RoleID"+
                                " LEFT JOIN userunit u3 on u.UserID = u3.user_id" +
                                " WHERE u.Username LIKE ?1" +
                                " ORDER BY ?2 ?3")
                .setParameter(1,"%"+search+"%")
                .setParameter(2,sortColumnIndex)
                .setParameter(3,sortDir)
                .setMaxResults(Integer.parseInt(length))
                .setFirstResult(Integer.parseInt(skip));
        int recordsTotal = q.getResultList().size();
        PaginationDto paginationDto = new PaginationDto();
        paginationDto.setDraw(Integer.parseInt(draw));
        paginationDto.setRecordsFiltered(recordsTotal-Integer.parseInt(skip));
        paginationDto.setRecordsTotal(recordsTotal);
        paginationDto.setArray(q.getResultList());

        return paginationDto;
    }







}
