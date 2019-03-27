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
        Query q = entityManager
                .createNativeQuery(
                        "SELECT u.UserID as userId," +
                                "u.Username as username," +
                                "u.Name as name ," +
                                "u.Surname as surname," +
                                "u.Email as email," +
                                "u.IsActive as isActive," +
                                "r.RoleName as rolename," +
                                "u4.Name as unit" +
                                " FROM user u" +
                                " LEFT JOIN userroles u2 on u.UserID = u2.user_id" +
                                " LEFT JOIN role r on u2.role_id = r.RoleID"+
                                " LEFT JOIN userunit u3 on u.UserID = u3.user_id" +
                                " LEFT JOIN unit u4 on u3.unit_id = u4.UnitID" +
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
        List<Object[]> list = q.getResultList();
        List<PersonDto> personDtos = new ArrayList<>();
        for (Object[] a : list) {
            personDtos.add(new PersonDto(a[0].toString(),a[1].toString(),a[2].toString(),a[3].toString(),a[4].toString(),a[5].toString(),"",""));
        }
        paginationDto.setArray(personDtos);

        return paginationDto;
    }







}
