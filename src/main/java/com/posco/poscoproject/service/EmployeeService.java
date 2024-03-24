package com.posco.poscoproject.service;

import com.posco.poscoproject.entity.Employee;
import com.posco.poscoproject.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import javax.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public List<Employee> employeeListByBranch(Long branchId) {return employeeRepository.findByBranchId(branchId);}

    public Employee findByEpId(Long epId){
        return employeeRepository.findByEpId(epId);
    }

    public Long savePost(Employee employee){ return employeeRepository.save(employee).getEpId(); }

    public Employee saveEmployee(Employee employee){ return employeeRepository.save(employee); }

    public void deleteEmployee(Long epId){
        Employee employee = employeeRepository.findById(epId).
                orElseThrow(EntityNotFoundException::new);
        employeeRepository.delete(employee);
    }
}
