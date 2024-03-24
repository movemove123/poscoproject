package com.posco.poscoproject.test;

import com.posco.poscoproject.entity.Branch;
import com.posco.poscoproject.enumtype.Authority;
import com.posco.poscoproject.enumtype.BranchStatus;
import com.posco.poscoproject.repository.BranchRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class TotalTest {

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    //본사 계정 생성
    @Test
    @DisplayName("데이터 넣기")
    public void branchtest(){
        String encodedPassword = passwordEncoder.encode("12345678"); // 비밀번호 암호화

        Branch admin = Branch.builder()
                .branchId(0L)
                .email("admin@kokeetea.com")
                .password(encodedPassword)
                .branchName("본사")
                .address("서울시")
                .phone("010-0000-0000")
                .branchStatus(BranchStatus.영업)
                .authority(Authority.ADMIN)
                .build();
        System.out.println("branch : " + admin);

        branchRepository.save(admin);
    }

    //지점 계정 생성
    @Test
    @DisplayName("데이터 넣기2")
    public void branchtest2(){
        String encodedPassword = passwordEncoder.encode("1234"); // 비밀번호 암호화

        for(int i = 1; i < 11; i++ ){
            Branch user = Branch.builder()
                    .branchId(0L)
                    .email("branch" + i +"@kokeetea.com")
                    .password(encodedPassword)
                    .branchName("branch" + i)
                    .address("인천시")
                    .phone("010-0000-0000")
                    .branchStatus(BranchStatus.영업)
                    .authority(Authority.USER)
                    .build();
            System.out.println("branch : " + user);

            branchRepository.save(user);
        }
    }
}
