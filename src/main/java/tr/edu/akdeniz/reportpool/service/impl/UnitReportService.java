package tr.edu.akdeniz.reportpool.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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


    public List<UnitReportDto> getUnitReports(int i) {
        Query unitreport = entityManager.createNativeQuery(
                "SELECT d.Name AS departmenName, u.Name AS unitName , us.Name AS userName , us.Surname , r.DateCompleted , att.Path FROM Department d , Departmentunit du , Unit u , User us , Report r , Attachment att" +
                        " WHERE d.DepartmentID = du.department_id AND du.unit_id = u.UnitID AND us.UserID = r.user_id AND r.ReportID = att.report_id " +
                        "AND d.DepartmentID= ?1");
        unitreport.setParameter(1,i);

        return  unitreport.getResultList();
    }
}
