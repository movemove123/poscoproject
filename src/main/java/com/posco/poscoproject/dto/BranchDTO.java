package com.posco.poscoproject.dto;

import com.posco.poscoproject.enumtype.BranchStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BranchDTO {

    private Long branchId;

    private String email;

    private String password;

    private String branchName;

    private String address;

    private String phone;

    private BranchStatus branchStatus;

}
