package com.posco.poscoproject.service;

import com.posco.poscoproject.entity.Branch;
import com.posco.poscoproject.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LoginService implements UserDetailsService {

    private final BranchRepository branchRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("유저아이디 : "+username);

        Branch branch = branchRepository.findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        return User.builder()
                .username(branch.getEmail())
                .password(branch.getPassword())
                .roles(branch.getAuthority().toString())
                .build();
    }
}
