package tr.edu.akdeniz.reportpool.service.impl;

import javafx.scene.control.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.edu.akdeniz.reportpool.entity.Report;
import tr.edu.akdeniz.reportpool.model.PaginationDto;
import tr.edu.akdeniz.reportpool.model.UnitReportDto;
import tr.edu.akdeniz.reportpool.repository.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UnitReportService {

    @Autowired
    EntityManager entityManager;



    public PaginationDto getUnitsReports(int dept,int unit,String draw,String length,String skip,String sortDir,String sortColumnIndex,String search){
        switch (Integer.parseInt(sortColumnIndex)){
            case 0:
                sortColumnIndex="userName";
                break;
            case 1:
                sortColumnIndex="Surname";
                break;
            case 2:
                sortColumnIndex="DateCompleted";
                break;
            case 3:
                sortColumnIndex="Text";
                break;

        }


        String[] words = search.split(" ");
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < words.length - 1; i++) {
            if (!words[i].equals(""))
                sb.append("+" + words[i] + "* ");
        }
        if (words.length > 0) {
            if (!words[words.length - 1].equals(""))
                sb.append("+" + words[words.length - 1] + "*");
            search = sb.toString();
        }


        // very cheap way but works great for now
        System.out.println("Search: " + search);
        if (search.equals("")) {
            search = "konu";
        }


        /*
        String[] words = search.split(" ");
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < words.length - 1; i++) {
            sb.append(words[i] + "%");
        }
        if (words.length > 0) {
            sb.append(words[words.length - 1]);
            search = sb.toString();
        }
        */





        Query q=entityManager
                .createNativeQuery(
                        "SELECT " +
                                "CONCAT(us.Name, ' ', us.Surname) AS ?1," +
                                "r.DateCompleted AS ?2, " +
                                "r.Text AS ?3" +
                                " FROM department d,departmentunit du,unit u,user us,report r,userdepartmentunit udu" +
                                " WHERE d.DepartmentID=du.department_id AND " +
                                "u.UnitID=du.unit_id " +
                                "AND du.DepartmentUnitID= udu.departmentunit_id" +
                                " AND us.UserID= udu.user_id" +
                                " AND du.DepartmentUnitID=r.departmentunit_id " +
                                "AND r.user_id=us.UserID " +
                                //" AND (r.Text LIKE ?4)"+
                                " AND MATCH(r.Text) AGAINST (?4 IN BOOLEAN MODE)" +
                                " AND d.DepartmentID="+dept+
                                "  AND u.UnitID= "+unit+
                                "  ORDER BY "+sortColumnIndex+" "+sortDir.toUpperCase())
                //.setParameter(0,"username")
                .setParameter(1,"fullName")
                .setParameter(2,"dateCompleted")
                .setParameter(3,"text")
                .setParameter(4,"'"+search+"'")

                .setMaxResults(Integer.parseInt(length))
                .setFirstResult(Integer.parseInt(skip));
        int recordsTotal=((Number)entityManager
                .createNativeQuery("SELECT COUNT(*) " +
                        " FROM department d,departmentunit du,unit u,user us,report r,userdepartmentunit udu" +
                        " WHERE d.DepartmentID=du.department_id AND " +
                        "u.UnitID=du.unit_id " +
                        "AND du.DepartmentUnitID= udu.departmentunit_id" +
                        " AND us.UserID= udu.user_id" +
                        " AND du.DepartmentUnitID=r.departmentunit_id " +
                        "AND r.user_id=us.UserID " +
                        " AND d.DepartmentID="+dept+
                        "  AND u.UnitID= "+unit+
                        " AND MATCH(r.Text) AGAINST (?7 IN BOOLEAN MODE)")
                        //" AND (r.Text LIKE ?7)")
                .setParameter(7,"'"+search+"'")
                .getSingleResult()).intValue();
        PaginationDto paginationDto = new PaginationDto(Integer.parseInt(draw),recordsTotal-Integer.parseInt(skip),recordsTotal,q.getResultList());
        return paginationDto;
    }



    public void downloadReports(int dept,int unit,String draw,String length,String skip,String sortDir,String sortColumnIndex,String search){


        if(search != null) {
            String[] words = search.split(" ");
            StringBuilder sb = new StringBuilder("");
            for (int i = 0; i < words.length - 1; i++) {
                if (!words[i].equals(""))
                    sb.append("+" + words[i] + "* ");
            }
            if (words.length > 0) {
                if (!words[words.length - 1].equals(""))
                    sb.append("+" + words[words.length - 1] + "*");
                search = sb.toString();
            }
        } else {
            search = "bilgisayar";
        }


        // very cheap way but works great for now
        System.out.println("Search: " + search);
        if (search.equals("")) {
            search = "konu";
        }


        Query q=entityManager
                .createNativeQuery(
                        "SELECT " +
                                "r.Text " +
                                " FROM Department d,Departmentunit du,Unit u,User us,Report r,Userdepartmentunit udu" +
                                " WHERE d.DepartmentID=du.department_id AND " +
                                "u.UnitID=du.unit_id " +
                                "AND du.DepartmentUnitID= udu.departmentunit_id" +
                                " AND us.UserID= udu.user_id" +
                                " AND du.DepartmentUnitID=r.departmentunit_id " +
                                "AND r.user_id=us.UserID " +
                                //" AND (r.Text LIKE ?4)"+
                                " AND MATCH(r.Text) AGAINST (?4 IN BOOLEAN MODE)" +
                                " AND d.DepartmentID="+dept+
                                "  AND u.UnitID= "+unit)

                .setParameter(4,"'"+search+"'");

        List<String> reports = q.getResultList();

        System.out.println("Reports: " + reports.toString());




    }







}