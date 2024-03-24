package com.posco.poscoproject.dto;

import com.posco.poscoproject.entity.Branch;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class EmployeeDTO {

    private long epId;

    private String epName;

    private String phone;

    private String epPosition;

    private String epSalary;

    private Branch branch;
}

