package com.posco.poscoproject.entity;

import com.posco.poscoproject.dto.EmployeeFormDTO;
import lombok.*;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long epId;

    @Column
    private String epName;

    @Column
    private String phone;

    @Column
    private String epPosition;

    @Column
    private String epSalary;

    @ManyToOne
    @JoinColumn(name="branch_id")
    private Branch branch;

    public static Employee createEmployee(EmployeeFormDTO employeeFormDTO){

        Employee employee = new Employee();
        employee.setEpId(employeeFormDTO.getEpId());
        employee.setEpName(employeeFormDTO.getEpName());
        employee.setPhone(employeeFormDTO.getPhone());
        employee.setEpPosition(employeeFormDTO.getEpPosition());
        employee.setEpSalary(employeeFormDTO.getEpSalary());
        employee.setBranch(employeeFormDTO.getBranch());
        return employee;
    }
}
