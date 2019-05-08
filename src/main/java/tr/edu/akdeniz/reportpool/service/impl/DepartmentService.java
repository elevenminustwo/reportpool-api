package tr.edu.akdeniz.reportpool.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.edu.akdeniz.reportpool.model.DepartmentDto;
import tr.edu.akdeniz.reportpool.repository.DepartmentRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    @Autowired
    EntityManager entityManager;

    @Autowired
    DepartmentRepository departmentRepository;

    @Transactional(readOnly = true)
    public List<DepartmentDto> getAllDepartments(){
        return departmentRepository.findAll()
                .stream()
                .map(t-> new DepartmentDto(t.getDepartmentId(),t.getName()))
                .collect(Collectors.toList());
    }
}
