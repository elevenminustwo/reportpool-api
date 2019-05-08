package tr.edu.akdeniz.reportpool.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.edu.akdeniz.reportpool.entity.Unit;
import tr.edu.akdeniz.reportpool.model.UnitDto;
import tr.edu.akdeniz.reportpool.repository.DepartmentUnitRepository;
import tr.edu.akdeniz.reportpool.repository.UnitRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UnitService {

    @Autowired
    EntityManager entityManager;

    @Autowired
    UnitRepository unitRepository;

    @Autowired
    DepartmentUnitRepository departmentUnitRepository;

    @Transactional(readOnly = true)
    public List<UnitDto> getAllUnitsByDepartmentId(int id){
          List<UnitDto> unit =
                  entityManager
                            .createNativeQuery(
                        "SELECT" +
                                " u.Name as ?1," +
                                " u.UnitID as ?2 " +
                                 "FROM unit u" +
                                " LEFT JOIN departmentunit d on d.department_id =" +id+"" +
                                " WHERE d.unit_id=u.UnitID")
                                .setParameter(1,"unitname")
                                .setParameter(2,"unitId")
                                .getResultList();
          return unit;
    }
}
