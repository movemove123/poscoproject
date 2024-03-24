package com.posco.poscoproject.controller;

import com.posco.poscoproject.dto.EmployeeFormDTO;

import com.posco.poscoproject.entity.Branch;
import com.posco.poscoproject.entity.Employee;
import com.posco.poscoproject.service.BranchService;
import com.posco.poscoproject.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final BranchService branchService;
    //로그로 내용확인을 위한 객체 추가
    private static final Logger logger = LoggerFactory.getLogger(BranchController.class);

    @GetMapping("/test/employeeList")
    public String employeeList(Principal principal, Model model){

        //nav 유저 표시
        Branch branch = branchService.findBranchByEmail(principal.getName());
        model.addAttribute("user", branch);

        //직원 목록
        Long branchId = branch.getBranchId();
        System.out.println(branchId);
        List<Employee> list = employeeService.employeeListByBranch(branchId);
        model.addAttribute("list", list);

        return "test/employeeList";
    }

    @GetMapping("/test/employeeUpdate/{id}")
    public String update(@PathVariable("id") Long id, Model model, Principal principal){

        //nav 유저 표시
        Branch navBranch = branchService.findBranchByEmail(principal.getName());
        model.addAttribute("user", navBranch);

        Employee employee = employeeService.findByEpId(id);

        logger.info("employee with EpId: {}, EpName: {}, EpPosition: {}, Phone: {} BranchId: {}",
                employee.getEpId(),
                employee.getEpName(),
                employee.getEpPosition(),
                employee.getPhone(),
                employee.getBranch().getBranchId());

        model.addAttribute("employee", employee);

        return "test/employeeUpdate";
    }

    @PutMapping("/test/employeeUpdate/{epId}")
    public String update(@PathVariable("epId") Long epId, Employee employee, Principal principal, Model model) {

        //redirect시 필요
        Branch navBranch = branchService.findBranchByEmail(principal.getName());
        model.addAttribute("user", navBranch);

        //branchId가 제대로 반인딩 되지 않아 직접 주입
        employee.setBranch(navBranch);

        //employee 내용 확인
        logger.info("Updating Employee with epId: {}, {}", epId, employee.toString());

        employeeService.savePost(employee);

        return "redirect:/test/employeeList";
    }

    @DeleteMapping(value = "/test/employeeDelete/{epId}")
    public @ResponseBody ResponseEntity deleteEmployee(@PathVariable("epId") Long epId){

        employeeService.deleteEmployee(epId);

        return new ResponseEntity<Long>(epId, HttpStatus.OK);
    }

    @GetMapping("/test/employeeForm")
    public String employeeForm(Model model, Principal principal){

        //nav 유저 표시
        Branch branch = branchService.findBranchByEmail(principal.getName());
        model.addAttribute("user", branch);

        model.addAttribute("employeeFormDTO", new EmployeeFormDTO());
        model.addAttribute("testBranch", branch);

        return "/test/employeeForm";
    }

    @PostMapping("/test/employeeForm")
    public String newEmployee(@Valid EmployeeFormDTO employeeFormDTO, BindingResult bindingResult, Model model, Principal principal){

        //nav 유저 표시
        Branch navBranch = branchService.findBranchByEmail(principal.getName());
        model.addAttribute("user", navBranch);

        if(bindingResult.hasErrors()){
            System.out.println("입력오류");
            return "/test/employeeForm";
        }

        //employeeFormDTO 내용 확인
        logger.info("employeeFormDTO with epId: {}, epPosition: {}, epName: {}, testBranch: {}",
                employeeFormDTO.getEpId(),
                employeeFormDTO.getEpPosition(),
                employeeFormDTO.getEpName(),
                employeeFormDTO.getBranch());
        //testBranch 전달 안됨
        //logger.info("testBranch with branchId: {}", employeeFormDTO.getTestBranch().getBranchId());
        //testBranch 직접 주입(navBranch == 접속 지점)
        employeeFormDTO.setBranch(navBranch);

        try {
            Employee employee = Employee.createEmployee(employeeFormDTO);
            employeeService.saveEmployee(employee);
            System.out.println("직원 등록 성공!");
        } catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            System.out.println("예외 발생");
            return "/test/employeeForm";
        }

        return "redirect:/test/employeeList";
    }
}
