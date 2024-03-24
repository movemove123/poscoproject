package com.posco.poscoproject.dto;

import com.posco.poscoproject.entity.Branch;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class EmployeeFormDTO {

    private long epId;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String epName;

    @NotBlank(message = "전화번호는 필수 입력 값입니다.")
    private String phone;

    @NotBlank(message = "직책은 필수 입력 값입니다.")
    private String epPosition;

    @NotBlank(message = "급여는 필수 입력 값입니다.")
    private String epSalary;

    private Branch branch;
}
