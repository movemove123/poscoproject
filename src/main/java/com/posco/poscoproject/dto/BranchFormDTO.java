package com.posco.poscoproject.dto;

import com.posco.poscoproject.enumtype.BranchStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class BranchFormDTO {

    private Long branchId;

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    @Length(min=4, max=8, message = "비밀번호는 4자 이상, 8자 이하로 입력해주세요")
    private String password;

    @NotBlank(message = "지점명은 필수 입력 값입니다.")
    private String branchName;

    @NotBlank(message = "주소는 필수 입력 값입니다.")
    private String address;

    @NotBlank(message = "전화번호는 필수 입력 값입니다.")
    private String phone;

    private BranchStatus branchStatus;
}
