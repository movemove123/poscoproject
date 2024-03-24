package com.posco.poscoproject.entity;


import com.posco.poscoproject.dto.BranchFormDTO;
import com.posco.poscoproject.enumtype.Authority;
import com.posco.poscoproject.enumtype.BranchStatus;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

import static com.posco.poscoproject.enumtype.Authority.USER;

@Entity
@Table
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long branchId;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String branchName;

    @Column
    private String address;

    @Column
    private String phone;

    @Column
    @Enumerated(EnumType.STRING)
    private Authority authority; // 권한 : ROLE_USER, ROLE_ADMIN

    @Column
    @Enumerated(EnumType.STRING)
    private BranchStatus branchStatus;

    //테스트에서 지점 생성시 사용됨,
    @Builder
    public Branch(Long branchId, String email, String password, String branchName, String address, String phone, BranchStatus branchStatus, Authority authority) {
        this.branchId = branchId;
        this.email = email;
        this.password = password;
        this.branchName = branchName;
        this.address = address;
        this.phone = phone;
        this.branchStatus = branchStatus;
        this.authority = authority;
    }

    public static Branch createBranch(BranchFormDTO branchFormDTO, PasswordEncoder passwordEncoder){

        Branch branch = new Branch();
        branch.setEmail(branchFormDTO.getEmail());
        String password = passwordEncoder.encode(branchFormDTO.getPassword());
        branch.setPassword(password);
        branch.setBranchName(branchFormDTO.getBranchName());
        branch.setAddress(branchFormDTO.getAddress());
        branch.setPhone(branchFormDTO.getPhone());
        branch.setBranchStatus(branchFormDTO.getBranchStatus());
        branch.setAuthority(USER);
        return branch;
    }
}
