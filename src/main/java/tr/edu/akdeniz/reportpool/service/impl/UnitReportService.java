package tr.edu.akdeniz.reportpool.service.impl;

import javafx.scene.control.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        Query q=entityManager
                .createNativeQuery(
                        "SELECT us.Name  AS ?0," +
                                " us.Surname AS ?1," +
                                "r.DateCompleted AS ?2, " +
                                "r.Text AS ?3" +
                                " FROM department d,departmentunit du,unit u,user us,report r,userdepartmentunit udu" +
                                " WHERE d.DepartmentID=du.department_id AND " +
                                "u.UnitID=du.unit_id " +
                                "AND du.DepartmentUnitID= udu.departmentunit_id" +
                                " AND du.DepartmentUnitID=r.departmentunit_id " +
                                "AND r.user_id=us.UserID " +
                                //" AND (us.Name LIKE ?4 OR r.Text LIKE ?4 OR us.Surname LIKE ?4 OR r.DateCompleted LIKE ?4)"+
                                " AND MATCH(r.Text) AGAINST (?4 IN BOOLEAN MODE)" +
                                " AND d.DepartmentID="+dept+
                                "  AND u.UnitID= "+unit+
                                "  ORDER BY "+sortColumnIndex+" "+sortDir.toUpperCase())
                .setParameter(0,"userName")
                .setParameter(1,"surName")
                .setParameter(2,"dateCompleted")
                .setParameter(3,"text")
                .setParameter(4,"%"+search+"%")

                .setMaxResults(Integer.parseInt(length))
                .setFirstResult(Integer.parseInt(skip));
        int recordsTotal=((Number)entityManager
                .createNativeQuery("SELECT COUNT(*) " +
                        " FROM department d,departmentunit du,unit u,user us,report r,userdepartmentunit udu" +
                        " WHERE d.DepartmentID=du.department_id AND " +
                        "u.UnitID=du.unit_id " +
                        "AND du.DepartmentUnitID= udu.departmentunit_id" +
                        " AND du.DepartmentUnitID=r.departmentunit_id " +
                        "AND r.user_id=us.UserID " +
                        " AND d.DepartmentID="+dept+
                        "  AND u.UnitID= "+unit+
                        " AND MATCH(r.Text) AGAINST (?7 IN BOOLEAN MODE)")
                        //" AND (us.Name LIKE ?7 OR r.Text LIKE ?7 OR us.Surname LIKE ?7 OR r.DateCompleted LIKE ?7)")
                .setParameter(7,"%"+search+"%")
                .getSingleResult()).intValue();
        PaginationDto paginationDto = new PaginationDto(Integer.parseInt(draw),recordsTotal-Integer.parseInt(skip),recordsTotal,q.getResultList());
        return paginationDto;
    }






}