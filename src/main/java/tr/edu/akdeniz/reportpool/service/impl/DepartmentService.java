package tr.edu.akdeniz.reportpool.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.edu.akdeniz.reportpool.entity.Department;
import tr.edu.akdeniz.reportpool.entity.Departmentunit;
import tr.edu.akdeniz.reportpool.entity.Unit;
import tr.edu.akdeniz.reportpool.model.DepartmentDto;
import tr.edu.akdeniz.reportpool.model.DepartmentUnitAddDto;
import tr.edu.akdeniz.reportpool.model.DepartmentUnitDto;
import tr.edu.akdeniz.reportpool.model.UnitDto;
import tr.edu.akdeniz.reportpool.repository.DepartmentRepository;
import tr.edu.akdeniz.reportpool.repository.DepartmentUnitRepository;
import tr.edu.akdeniz.reportpool.repository.UnitRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    @Autowired
    EntityManager entityManager;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    UnitRepository unitRepository;

    @Autowired
    DepartmentUnitRepository departmentUnitRepository;

    @Transactional(readOnly = true)
    public List<DepartmentDto> getAllDepartments(){
        return departmentRepository.findAll()
                .stream()
                .map(t-> new DepartmentDto(t.getDepartmentId(),t.getName()))
                .collect(Collectors.toList());
    }

    public ResponseEntity addDepartment(String name){
        Department department= new Department();
        department.setName(name);
        departmentRepository.save(department);
        return new ResponseEntity(HttpStatus.OK);
    }

    public ResponseEntity delDepartment(int id){
        departmentRepository.delete(departmentRepository.findByDepartmentId(id));
        return new ResponseEntity(HttpStatus.OK);

    }

    public List<UnitDto> getDepartmentUnits(int id ){
        return departmentUnitRepository.findAllByDepartmentId(id)
                .stream()
                .map(t-> new UnitDto(unitRepository.findByUnitId(t.getUnitId()).getUnitId(),unitRepository.findByUnitId(t.getUnitId()).getName()))
                .collect(Collectors.toList());
    }
    public ResponseEntity addUnit(DepartmentUnitAddDto departmentUnitAddDto){
        Unit check = unitRepository.findFirstByName(departmentUnitAddDto.getName());
        if(check==null){
            Unit u = new Unit();
            u.setName(departmentUnitAddDto.getName());
            unitRepository.save(u);
            Departmentunit du = new Departmentunit();
            du.setUnitId(unitRepository.findFirstByName(u.getName()).getUnitId());
            du.setDepartmentId(departmentUnitAddDto.getDepartmentId());
            departmentUnitRepository.save(du);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.CONFLICT);
    }

    public ResponseEntity delUnit(DepartmentUnitDto departmentUnitDto){
        departmentUnitRepository.delete(departmentUnitRepository.findFirstByDepartmentIdAndUnitId(departmentUnitDto.getDepartmentId(),departmentUnitDto.getUnitId()));
        unitRepository.delete(unitRepository.findByUnitId(departmentUnitDto.getUnitId()));
        return new ResponseEntity(HttpStatus.OK);
    }
}
