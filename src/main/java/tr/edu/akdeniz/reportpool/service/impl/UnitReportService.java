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
                sortColumnIndex="Path";
                break;

        }
        Query q=entityManager
                .createNativeQuery(
                        "SELECT us.Name  AS ?0," +
                                " us.Surname AS ?1," +
                                "r.DateCompleted AS ?2," +
                                "att.Path AS ?3" +
                                " FROM department d,departmentunit du,unit u,user us,report r,attachment att,userdepartmentunit udu" +
                                " WHERE d.DepartmentID=du.department_id AND " +
                                "u.UnitID=du.unit_id " +
                                "AND du.DepartmentUnitID= udu.departmentunit_id" +
                                " AND du.DepartmentUnitID=r.departmentunit_id " +
                                "AND r.user_id=us.UserID " +
                                "AND r.ReportID=att.report_id " +
                                " AND us.Name LIKE ?4"+
                                " AND d.DepartmentID="+dept+
                                "  AND u.UnitID= "+unit+
                                "  ORDER BY "+sortColumnIndex+" "+sortDir.toUpperCase())
                .setParameter(0,"userName")
                .setParameter(1,"surName")
                .setParameter(2,"dateCompleted")
                .setParameter(3,"Path")
                .setParameter(4,"%"+search+"%")

                .setMaxResults(Integer.parseInt(length))
                .setFirstResult(Integer.parseInt(skip));
        int recordsTotal=((Number)entityManager
                .createNativeQuery("SELECT COUNT(*) " +
                        "FROM department d,departmentunit du,unit u,user us,report r,attachment att,userdepartmentunit udu" +
                        " WHERE d.DepartmentID = du.department_id AND du.DepartmentUnitID= udu.departmentunit_id" +
                        " AND us.UserID = r.user_id " +
                        " AND r.user_id=us.UserID  " +
                        "AND r.ReportID=att.report_id" +
                        " AND us.Name LIKE ?7 ")
                .setParameter(7,"%"+search+"%")
                .getSingleResult()).intValue();
        PaginationDto paginationDto = new PaginationDto(Integer.parseInt(draw),recordsTotal-Integer.parseInt(skip),recordsTotal,q.getResultList());
        return paginationDto;
    }

}