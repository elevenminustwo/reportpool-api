package tr.edu.akdeniz.reportpool.service.impl;

import javafx.scene.control.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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


    @Transactional(readOnly = true)
    public PaginationDto getUnitsReports(int dept,int unit, String fromDate, String toDate, String draw,String length,String skip,String sortDir,String sortColumnIndex,String search){
        switch (Integer.parseInt(sortColumnIndex)){
            case 0:
                sortColumnIndex="us.Name";
                break;
            case 1:
                sortColumnIndex="r.DateCompleted";
                break;
            case 2:
                sortColumnIndex="r.Text";
                break;
            case 3:
                sortColumnIndex="r.ReportID";
                break;

        }

        search = search.replace("+", "");
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


        // very cheap way but works great for now (every report contains the word konu)
        System.out.println("Search: " + search);
        if (search.equals("")) {
            search = "konu";
        }


        Query q=entityManager
                .createNativeQuery(
                        "SELECT " +
                                "CONCAT(us.Name, ' ', us.Surname) AS ?1," +
                                "r.DateCompleted AS ?2, " +
                                "r.Text AS ?3, " +
                                "r.ReportID AS ?10" +
                                " FROM department d,departmentunit du,unit u,user us,report r,userdepartmentunit udu" +
                                " WHERE d.DepartmentID=du.department_id AND " +
                                "u.UnitID=du.unit_id " +
                                "AND du.DepartmentUnitID= udu.departmentunit_id" +
                                " AND us.UserID= udu.user_id" +
                                " AND r.DateCompleted >= ?5" +
                                " AND r.DateCompleted <= ?6" +
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
                .setParameter(5, fromDate)
                .setParameter(6, toDate)
                .setParameter(10, "reportId")

                .setMaxResults(Integer.parseInt(length))
                .setFirstResult(Integer.parseInt(skip));
        int recordsTotal=((Number)entityManager
                .createNativeQuery("SELECT COUNT(*) " +
                        " FROM department d,departmentunit du,unit u,user us,report r,userdepartmentunit udu" +
                        " WHERE d.DepartmentID=du.department_id AND " +
                        "u.UnitID=du.unit_id " +
                        "AND du.DepartmentUnitID= udu.departmentunit_id" +
                        " AND us.UserID= udu.user_id" +
                        " AND r.DateCompleted >= ?8" +
                        " AND r.DateCompleted <= ?9" +
                        " AND du.DepartmentUnitID=r.departmentunit_id " +
                        "AND r.user_id=us.UserID " +
                        " AND d.DepartmentID="+dept+
                        "  AND u.UnitID= "+unit+
                        " AND MATCH(r.Text) AGAINST (?7 IN BOOLEAN MODE)")
                        //" AND (r.Text LIKE ?7)")
                .setParameter(7,"'"+search+"'")
                .setParameter(8, fromDate)
                .setParameter(9, toDate)
                .getSingleResult()).intValue();
        PaginationDto paginationDto = new PaginationDto(Integer.parseInt(draw),recordsTotal-Integer.parseInt(skip),recordsTotal,q.getResultList());
        return paginationDto;
    }







}