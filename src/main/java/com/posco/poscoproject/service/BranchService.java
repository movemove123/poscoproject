package com.posco.poscoproject.service;
import com.posco.poscoproject.entity.Branch;
import com.posco.poscoproject.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BranchService {

    private final BranchRepository branchRepository;

    public Branch findBranchByEmail(String email) {

        Optional<Branch> branch = branchRepository.findByEmail(email);

        return branch.orElse(null);
    }

    public Page<Branch> branchList(Pageable pageable) {
        return branchRepository.findAllBranch(pageable);
    }

    public Branch findById(Long id){

        Optional<Branch> branch = branchRepository.findById(id);

        return branch.orElse(null);
    }
    @Transactional
    public Long savePost(Branch branch, Long id) {
        Optional<Branch> existingBranch = branchRepository.findById(id);
        if (existingBranch.isPresent()) {
            // 기존 엔티티가 존재하는 경우, 엔티티 업데이트
            Branch updatedBranch = existingBranch.get();
            updatedBranch.setEmail(branch.getEmail());
            updatedBranch.setBranchName(branch.getBranchName());
            updatedBranch.setPhone(branch.getPhone());
            updatedBranch.setAddress(branch.getAddress());
            updatedBranch.setBranchStatus(branch.getBranchStatus());
            updatedBranch.setAuthority(branch.getAuthority());
            // 업데이트된 엔티티 저장 및 반환
            branchRepository.save(updatedBranch);
            return updatedBranch.getBranchId();
        } else {
            // 기존 엔티티가 존재하지 않는 경우, 예외 처리
            throw new EntityNotFoundException("branch with id " + branch.getBranchId() + " not found.");
        }
    }

    public Branch saveBranch(Branch branch){
        validateDuplicateBranch(branch);
        return branchRepository.save(branch);
    }

    private void validateDuplicateBranch(Branch branch){
        Optional<Branch> findBranch = branchRepository.findByEmail(branch.getEmail());
        if(findBranch.isPresent()){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    /******* 본사 *******/
    public Optional<Long> dailySales() {
        Optional<Long> daily = Optional.ofNullable(branchRepository.getDailySalesAll(LocalDate.now()));
        return daily;
    }

    public Long getYesterdaySales() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        return branchRepository.getDailyLastSalesAll(yesterday);
    }

    public double compareLastDaily(double daily) {
        double lastDaily = getYesterdaySales();
        return ((daily - lastDaily) / lastDaily) * 100;
    }

    public Long weekSales() {
        return branchRepository.getWeekSalesAll();
    }

    public double compareLastWeek(double week) {
        double lastWeek = branchRepository.getLastWeekSalesAll();

        return ((week - lastWeek) / lastWeek) * 100;
    }

    public Long monthSales() {
        return branchRepository.getMonthSalesAll();
    }

    public double compareLastMonth(double month) {
        double lastMonth = branchRepository.getLastMonthSalesAll();

        return ((month - lastMonth) / lastMonth) * 100;
    }

    public int getBranchCount() {
        return branchRepository.getBranchCount();
    }

    /******* 지점 *******/
    public Optional<Long> dailySalesById(Long id) {
        Optional<Long> daily = Optional.ofNullable(branchRepository.getDailySalesById(LocalDate.now(), id));
        return daily;
    }

    public Long getYesterdaySalesById(Long id) {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        return branchRepository.getDailySalesById(yesterday, id);
    }

    public double compareLastDailyById(double daily, Long id) {
        double lastDaily = getYesterdaySalesById(id);
        return ((daily - lastDaily) / lastDaily) * 100;
    }

    public Long weekSalesById(Long id) {
        return branchRepository.getWeekSalesById(id);
    }

    public double compareLastWeekById(double week, Long id) {
        double lastWeek = branchRepository.getLastWeekSalesById(id);

        return ((week - lastWeek) / lastWeek) * 100;
    }

    public Long monthSalesById(Long id) {
        return branchRepository.getMonthSalesById(id);
    }

    public double compareLastMonthById(double month, Long id) {
        double lastMonth = branchRepository.getLastMonthSalesById(id);

        return ((month - lastMonth) / lastMonth) * 100;
    }

    public int orderCountById(Long id) {
        return branchRepository.getOrderCountById(id);
    }

    // branch 에 username 을 받아오며 username.getId로 brachId를 가져옴
    public Long findBranchIdByUsername(String username) {
        Branch branch = findBranchByEmail(username);
        System.out.println("------------------" + branch);
        if (branch != null) {
            return branch.getBranchId(); // Branch 객체에서 ID(즉, branchId)를 반환
        }
        return null; // 사용자를 찾을 수 없는 경우 null 반환
    }

    public String findBranchNameByUsername(String username) {
        Branch branch = findBranchByEmail(username);
        System.out.println("------------------" + branch);
        if (branch != null) {
            return branch.getBranchName(); // Branch 객체에서 ID(즉, branchName)를 반환
        }
        return null; // 사용자를 찾을 수 없는 경우 null 반환
    }

    // 모든지점을 찾는 메서드
    public List<Branch> findAllBranches() {
        return branchRepository.findAll();
    }

    // 지점명을 포함하는 모든 지점을 검색하는 메서드
    public Page<Branch> searchByBranchName(String branchName, Pageable pageable) {
        return branchRepository.findByBranchNameSearch(branchName, pageable);
    }

    // 지점 금일 주문량
    public int getTodaysOrderCountByBranchId(Long branchId) {
        return branchRepository.findTodaysOrderCountByBranchId(branchId);
    }
}
