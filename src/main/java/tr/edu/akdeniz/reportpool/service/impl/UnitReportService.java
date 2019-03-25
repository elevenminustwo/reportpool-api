package tr.edu.akdeniz.reportpool.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.edu.akdeniz.reportpool.entity.Department;
import tr.edu.akdeniz.reportpool.entity.Departmentunit;
import tr.edu.akdeniz.reportpool.entity.Report;
import tr.edu.akdeniz.reportpool.model.UnitReportDto;
import tr.edu.akdeniz.reportpool.repository.UnitReportRepository;
import tr.edu.akdeniz.reportpool.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UnitReportService {

    @Autowired
    UnitReportRepository unitReportRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    List<UnitReportDto> getUnitReportRepository(int i) {
       return
               unitReportRepository.findAllByDepartmentunitIdEquals(1)
                       .stream()
                       .map(t -> new UnitReportDto(userRepository.findByUserId(t.getUserId(i)).getName(),userRepository.findByUserId(t.getUserId()).getSurname()))
                       .collect(Collectors.toList());
    }
}
