package tr.edu.akdeniz.reportpool.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.edu.akdeniz.reportpool.model.UserUnitDto;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Service
public class UserUnitsService {

    @Autowired
    EntityManager entityManager;


    public List<UserUnitDto> getUserUnits(int i) {
        Query userunit = entityManager.createNativeQuery(
                "SELECT du.DepartmentUnitID AS departmentUnitId, d.Name AS departmentName, u.Name AS unitName FROM Unit u, Department d, Departmentunit du, Userdepartmentunit udu" +
                        " WHERE du.department_id = d.DepartmentID AND du.unit_id = u.UnitID AND udu.departmentunit_id = du.DepartmentUnitID AND udu.user_id= ?1");
        userunit.setParameter(1,i);

        return  userunit.getResultList();
    }

}
