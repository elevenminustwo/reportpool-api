package tr.edu.akdeniz.reportpool.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tr.edu.akdeniz.reportpool.entity.Departmentunit;
import tr.edu.akdeniz.reportpool.entity.User;
import tr.edu.akdeniz.reportpool.entity.Userdepartmentunit;
import tr.edu.akdeniz.reportpool.entity.Userroles;
import tr.edu.akdeniz.reportpool.model.*;
import tr.edu.akdeniz.reportpool.repository.DepartmentUnitRepository;
import tr.edu.akdeniz.reportpool.repository.UserDUnitRepository;
import tr.edu.akdeniz.reportpool.repository.UserRepository;
import tr.edu.akdeniz.reportpool.repository.UserroleRepository;
import tr.edu.akdeniz.reportpool.service.GenericUserService;

import javax.persistence.*;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements GenericUserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserroleRepository userroleRepository;

    @Autowired
    DepartmentUnitRepository departmentUnitRepository;

    @Autowired
    UserDUnitRepository userDUnitRepository;

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
            case 8:
                sortColumnIndex="departmentname";
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
                                "u5.Name as ?7," +
                                "d.Name as ?8," +
                                "u5.UnitID as ?9," +
                                "d.DepartmentID as ?10" +
                                " FROM user u" +
                                " LEFT JOIN userroles u2 on u.UserID = u2.user_id" +
                                " LEFT JOIN role r on u2.role_id = r.RoleID"+
                                " LEFT JOIN userdepartmentunit u3 on u.UserID = u3.user_id" +
                                " LEFT JOIN departmentunit u4 on u3.departmentunit_id = u4.DepartmentUnitID" +
                                " LEFT JOIN unit u5 on u4.unit_id = u5.UnitID" +
                                " LEFT JOIN department d on u4.department_id = d.DepartmentID" +
                                " WHERE u.Username LIKE ?11" +
                                " ORDER BY "+sortColumnIndex+" "+sortDir.toUpperCase())
                .setParameter(0,"userid")
                .setParameter(1,"username")
                .setParameter(2,"name")
                .setParameter(3,"surname")
                .setParameter(4,"email")
                .setParameter(5, "isactive")
                .setParameter(6,"rolename")
                .setParameter(7,"unitname")
                .setParameter(8,"departmentname")
                .setParameter(9,"unitID")
                .setParameter(10,"departmentID")
                .setParameter(11,"%"+search+"%")
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
                                " LEFT JOIN department d on u4.department_id = d.DepartmentID" +
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

        // Mert was here ***********
        User user = userRepository.findByUserId(userrolesDto.getUserId());
        if (user != null) {
            user.setIsActive((byte)1);
            userRepository.save(user);
        }
        // *************************
        try (FileWriter fileWriter = new FileWriter("log.txt", true)) {
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            PrintWriter out = new PrintWriter(fileWriter);
            out.println(formatter.format(date) + " " + userrolesDto.getUserId() + " idli kullaniciya " + userrolesDto.getRoleId() + " idli rol eklendi.");
        }
        catch (Exception e){
            System.out.println("not found file");
        }
        return new ResponseEntity(HttpStatus.OK);
    }
    public ResponseEntity delRole(UserrolesDto userrolesDto){
        Userroles userroles = userroleRepository.findByUserId(userrolesDto.getUserId());
        if(userroles!=null){
            userroleRepository.delete(userroles);
        }
        // Mert was here ***********
        User user = userRepository.findByUserId(userrolesDto.getUserId());
        if (user != null) {
            user.setIsActive((byte)0);
            userRepository.save(user);
        }
        // *************************
        try (FileWriter fileWriter = new FileWriter("log.txt", true)) {
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            PrintWriter out = new PrintWriter(fileWriter);
            out.println(formatter.format(date) + " " + userrolesDto.getUserId() + " idli kullanicinin " + userrolesDto.getRoleId() + " idli rolu silindi.");
        }
        catch (Exception e){
            System.out.println("not found file");
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    public ResponseEntity addDunit(UserUnitEditDto userUnitEditDto){
        Departmentunit departmentu = departmentUnitRepository.findFirstByDepartmentIdAndUnitId(userUnitEditDto.departmentId,Integer.parseInt(userUnitEditDto.getUnitId()));
        if(departmentu==null){
            Departmentunit departmentunit = new Departmentunit();
            departmentunit.setDepartmentId(userUnitEditDto.getDepartmentId());
            departmentunit.setUnitId(Integer.parseInt(userUnitEditDto.getUnitId()));
            departmentUnitRepository.save(departmentunit);
            Userdepartmentunit userdepartmentunit = new Userdepartmentunit();
            userdepartmentunit.setDepartmentunitId(departmentunit.getDepartmentUnitId());
            userdepartmentunit.setUserId(userUnitEditDto.getUserId());
            userDUnitRepository.save(userdepartmentunit);
            return new ResponseEntity(HttpStatus.OK);
        }
        Userdepartmentunit udu = new Userdepartmentunit();
        udu.setDepartmentunitId(departmentu.getDepartmentUnitId());
        udu.setUserId(userUnitEditDto.getUserId());
        userDUnitRepository.save(udu);
        try (FileWriter fileWriter = new FileWriter("log.txt", true)) {
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            PrintWriter out = new PrintWriter(fileWriter);
            out.println(formatter.format(date) + " " + userUnitEditDto.departmentId + " idli departmana yeni birim eklendi.");
        }
        catch (Exception e){
            System.out.println("not found file");
        }
        return new ResponseEntity(HttpStatus.OK);

    }
    public ResponseEntity delDunit(UserUnitEditDto userUnitEditDto){
        Departmentunit departmentu = departmentUnitRepository.findFirstByDepartmentIdAndUnitId(userUnitEditDto.departmentId,Integer.parseInt(userUnitEditDto.getUnitId()));
        if(departmentu!=null){
            Userdepartmentunit userdepartmentunit = userDUnitRepository.findFirstByDepartmentunitIdAndUserId(departmentu.getDepartmentUnitId(), userUnitEditDto.getUserId());
            if(userdepartmentunit!=null){
                userDUnitRepository.delete(userdepartmentunit);
            }
        }
        try (FileWriter fileWriter = new FileWriter("log.txt", true)) {
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            PrintWriter out = new PrintWriter(fileWriter);
            out.println(formatter.format(date) + " " + userUnitEditDto.userId + " idli kullanici " + userUnitEditDto.unitId + " idli birimi silme islemi gerceklesti");
        }
        catch (Exception e){
            System.out.println("not found file");
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    public ResponseEntity login(String username,String psw){
        /* commented out by mert
        if(userRepository.existsUserByUsernameAndPassword(username,psw)){
            return new ResponseEntity(HttpStatus.OK);
        }
        */

        // Mert was here *******************
        if(userRepository.existsUserByUsernameAndPasswordAndIsActive(username,psw, (byte)1)){
            int id = userroleRepository.findByUserId(userRepository.findByUsernameAndPassword(username,psw).getUserId()).getRoleId();
            String role = "";
            switch (id){
                case 1:
                    role="Admin";
                    break;
                case 2:
                    role="Manager";
                    break;
                case 3:
                    role="User";
                    break;
            }
            try (FileWriter fileWriter = new FileWriter("log.txt", true)) {
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                PrintWriter out = new PrintWriter(fileWriter);
                out.println(formatter.format(date) + " " + role + " rolune sahip kullanici girisi yapildi");
            }
            catch (Exception e){
                System.out.println("not found file");
            }
            return new ResponseEntity(role,HttpStatus.OK);
        }
        // *********************************

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
    public ResponseEntity register(String email,String username,String psw,String name,String surname){
        User user = new User();
            user.setEmail(email);
            user.setUsername(username);
            user.setPassword(psw);
            user.setName(name);
            user.setSurname(surname);
            //user.setIsActive((byte)1); commented out by Mert
            user.setIsActive((byte)0); // Mert added this (artik register oldugunda inaktif)
            userRepository.save(user);

        try (FileWriter fileWriter = new FileWriter("log.txt", true)) {
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            PrintWriter out = new PrintWriter(fileWriter);
            out.println(formatter.format(date) +" Yeni kullanici kaydi yapildi");
        }
        catch (Exception e){
            System.out.println("not found file");
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    public int getUserIdOfUser(String username) {
        User user = userRepository.findByUsername(username);
        return user.getUserId();
    }

}
