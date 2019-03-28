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
import java.util.ArrayList;
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

        switch (Integer.parseInt(sortColumnIndex)){
            case 0:
                sortColumnIndex="username";
                break;
            case 1:
                sortColumnIndex="name";
                break;
            case 2:
                sortColumnIndex="surname";
                break;
            case 3:
                sortColumnIndex="email";
                break;
            case 4:
                sortColumnIndex="rolename";
                break;
            case 5:
                sortColumnIndex="unitname";
                break;

        }

        Query q = entityManager
                .createNativeQuery(
                        "SELECT u.Username as ?1," +
                                "u.Name as ?2 ," +
                                "u.Surname as ?3," +
                                "u.Email as ?4," +
                                "r.RoleName as ?5," +
                                "u4.Name as ?6" +
                                " FROM user u" +
                                " LEFT JOIN userroles u2 on u.UserID = u2.user_id" +
                                " LEFT JOIN role r on u2.role_id = r.RoleID"+
                                " LEFT JOIN userunit u3 on u.UserID = u3.user_id" +
                                " LEFT JOIN unit u4 on u3.unit_id = u4.UnitID" +
                                " WHERE u.Username LIKE ?7" +
                                " ORDER BY "+sortColumnIndex+" "+sortDir.toUpperCase())
                .setParameter(1,"username")
                .setParameter(2,"name")
                .setParameter(3,"surname")
                .setParameter(4,"email")
                .setParameter(5,"rolename")
                .setParameter(6,"unitname")
                .setParameter(7,"%"+search+"%")
                .setMaxResults(Integer.parseInt(length))
                .setFirstResult(Integer.parseInt(skip));
        int recordsTotal = q.getResultList().size();
        PaginationDto paginationDto = new PaginationDto(Integer.parseInt(draw),recordsTotal-Integer.parseInt(skip),recordsTotal,q.getResultList());

        return paginationDto;
    }







}
