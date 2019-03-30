package tr.edu.akdeniz.reportpool.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tr.edu.akdeniz.reportpool.entity.Userroles;
import tr.edu.akdeniz.reportpool.model.PaginationDto;
import tr.edu.akdeniz.reportpool.model.UserDto;
import tr.edu.akdeniz.reportpool.model.UserrolesDto;
import tr.edu.akdeniz.reportpool.repository.UserRepository;
import tr.edu.akdeniz.reportpool.repository.UserroleRepository;
import tr.edu.akdeniz.reportpool.service.GenericUserService;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements GenericUserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserroleRepository userroleRepository;

    @Autowired
    EntityManager entityManager;


    public List<UserDto> getUser(){
        return userRepository.findAll().stream()
                .map(t -> new UserDto(t.getUserId(),t.getUsername(),t.getEmail(),t.getPassword().toString(),t.getName(),t.getSurname(),t.getIsActive()))
                .collect(Collectors.toList());
    }
    public PaginationDto getPersons(String draw,String length,String skip,String sortDir,String sortColumnIndex,String search){

        switch (Integer.parseInt(sortColumnIndex)){
            case 0:
                sortColumnIndex="userid";
                break;
            case 1:
                sortColumnIndex="username";
                break;
            case 2:
                sortColumnIndex="name";
                break;
            case 3:
                sortColumnIndex="surname";
                break;
            case 4:
                sortColumnIndex="email";
                break;
            case 5:
                sortColumnIndex="isactive";
                break;
            case 6:
                sortColumnIndex="rolename";
                break;
            case 7:
                sortColumnIndex="unitname";
                break;

        }

        Query q = entityManager
                .createNativeQuery(
                        "SELECT u.UserID as ?0," +
                                "u.Username as ?1," +
                                "u.Name as ?2 ," +
                                "u.Surname as ?3," +
                                "u.Email as ?4, " +
                                "u.IsActive as ?5," +
                                "r.RoleName as ?6," +
                                "u5.Name as ?7" +
                                " FROM user u" +
                                " LEFT JOIN userroles u2 on u.UserID = u2.user_id" +
                                " LEFT JOIN role r on u2.role_id = r.RoleID"+
                                " LEFT JOIN userdepartmentunit u3 on u.UserID = u3.user_id" +
                                " LEFT JOIN departmentunit u4 on u3.departmentunit_id = u4.DepartmentUnitID" +
                                " LEFT JOIN unit u5 on u4.unit_id = u5.UnitID" +
                                " WHERE u.Username LIKE ?8" +
                                " ORDER BY "+sortColumnIndex+" "+sortDir.toUpperCase())
                .setParameter(0,"userid")
                .setParameter(1,"username")
                .setParameter(2,"name")
                .setParameter(3,"surname")
                .setParameter(4,"email")
                .setParameter(5, "isactive")
                .setParameter(6,"rolename")
                .setParameter(7,"unitname")
                .setParameter(8,"%"+search+"%")
                .setMaxResults(Integer.parseInt(length))
                .setFirstResult(Integer.parseInt(skip));
        int recordsTotal = ((Number)entityManager
                .createNativeQuery(
                        "SELECT COUNT(*) FROM user u" +
                                " LEFT JOIN userroles u2 on u.UserID = u2.user_id" +
                                " LEFT JOIN role r on u2.role_id = r.RoleID"+
                                " LEFT JOIN userdepartmentunit u3 on u.UserID = u3.user_id" +
                                " LEFT JOIN departmentunit u4 on u3.departmentunit_id = u4.DepartmentUnitID" +
                                " LEFT JOIN unit u5 on u4.unit_id = u5.UnitID" +
                                " WHERE u.Username LIKE ?7 " )
                                .setParameter(7,"%"+search+"%")
                                .getSingleResult()).intValue();
        PaginationDto paginationDto = new PaginationDto(Integer.parseInt(draw),recordsTotal-Integer.parseInt(skip),recordsTotal,q.getResultList());

        return paginationDto;
    }

    public ResponseEntity addRole(UserrolesDto userrolesDto){
        Userroles userroles = new Userroles();
        userroles.setRoleId(userrolesDto.getRoleId());
        userroles.setUserId(userrolesDto.getUserId());
        userroleRepository.save(userroles);
        return new ResponseEntity(HttpStatus.OK);
    }
    public ResponseEntity delRole(UserrolesDto userrolesDto){
        Userroles userroles = userroleRepository.findByUserId(userrolesDto.getUserId());
        if(userroles!=null){
            userroleRepository.delete(userroles);
        }
        return new ResponseEntity(HttpStatus.OK);
    }








}
