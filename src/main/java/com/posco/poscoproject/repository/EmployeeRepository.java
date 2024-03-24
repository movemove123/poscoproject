package com.posco.poscoproject.repository;

import com.posco.poscoproject.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT em FROM Employee em WHERE em.epId=:epId" )
    Employee findByEpId(@Param("epId")Long epId);

    @Query("SELECT em FROM Employee em WHERE em.branch.branchId=:branchId" )
    List<Employee> findByBranchId(@Param("branchId")Long branchId);
}
