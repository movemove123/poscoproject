package com.posco.poscoproject.controller;

import com.posco.poscoproject.entity.Branch;
import com.posco.poscoproject.entity.Ingredient;
import com.posco.poscoproject.entity.StockOrder;
import com.posco.poscoproject.enumtype.Authority;

import com.posco.poscoproject.service.BranchInventoryService;
import com.posco.poscoproject.service.IngredientService;
import com.posco.poscoproject.service.StockOrderService;
import com.posco.poscoproject.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class StockOrderController {

    private final IngredientService ingredientService;
    private final BranchInventoryService branchInventoryService;
    private final BranchService branchService;
    private final StockOrderService stockOrderService;

    @GetMapping("/test/stockOrder")
    public String stockOrder(@RequestParam("branchId") Long branchId, @RequestParam("igId") Long igId, Model model, Principal principal) {

        Ingredient ingredient = ingredientService.findByIgId(igId);
        model.addAttribute("ingredient", ingredient);
        model.addAttribute("branch", branchService.findBranchByEmail(principal.getName()));

        return "/test/stockOrderForm";
    }

    @PostMapping("/test/stockOrderForm")
    public String createStockOrder(@RequestParam("igId") Long igId, @RequestParam("email") String email, @RequestParam("soQuantity") int soQuantity) {

        Ingredient ingredient = ingredientService.findByIgId(igId);
        Branch branch = branchService.findBranchByEmail(email);
        stockOrderService.createStockOrder(ingredient,branch,soQuantity);

        return "redirect:/test/ingredientList";
    }

    @GetMapping({"/test/stockOrderList", "/test/stockOrderList/{page}"})
    public String stockOrderList(Principal principal, Model model,
                            @RequestParam(value = "startDate", required = false, defaultValue = "1900-01-01") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                            @RequestParam(value = "endDate", required = false, defaultValue = "9999-12-31") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                            @PageableDefault(page = 0, size = 10, sort = "soCreatedDate", direction = Sort.Direction.DESC) Pageable pageable) {

        //nav 유저 표시
        Branch branch = branchService.findBranchByEmail(principal.getName());
        model.addAttribute("user", branch);

        //발주목록
        Page<StockOrder> list = null;

        //본점과 지점이 보는 발주목록이 다름
        if (branch.getAuthority().equals(Authority.ADMIN)) {
            //전체 발주목록
            list = stockOrderService.stockOrderList(pageable);
        } else {
            //지점 발주목록
            Long branchId = branch.getBranchId();
            list = stockOrderService.stockOrderListByBranch(pageable, branchId);
        }

        model.addAttribute("list", list);

        //페이징변수 설정 - list가 null이 아닐 때만 페이징 변수 설정
        if (list != null) {
            int nowPage = list.getPageable().getPageNumber() + 1;
            int startPage = Math.max(nowPage - 4, 1);
            int endPage = Math.min(nowPage + 5, list.getTotalPages());
            model.addAttribute("nowPage", nowPage);
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);
        }
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        return "test/stockOrderList";
    }

    @DeleteMapping(value = "/test/stockOrderDelete/{soId}")
    public @ResponseBody ResponseEntity stockOrderDelete(@PathVariable("soId") Long soId){

        stockOrderService.stockOrderDelete(soId);

        return new ResponseEntity<Long>(soId, HttpStatus.OK);
    }

    @GetMapping("/test/stockOrderUpdate/{soId}")
    public String showUpdateForm(@PathVariable("soId") Long soId, Model model, Principal principal) {
        // 현재 사용자(지점) 정보를 가져옴
        Branch navBranch = branchService.findBranchByEmail(principal.getName());
        model.addAttribute("user", navBranch);

        // 수정할 StockOrder 객체를 id를 통해 조회
        StockOrder stockOrder = stockOrderService.findBySoId(soId);
        Branch branch = branchService.findBranchByEmail(stockOrder.getBranch().getEmail());

        // 모델에 StockOrder 객체를 추가하여 뷰에 전달
        model.addAttribute("stockOrder", stockOrder);
        model.addAttribute("branch", branch);

        // 연관된 Ingredient 객체 정보도 필요한 경우, IngredientService를 사용하여 조회 후 모델에 추가
        Ingredient ingredient = ingredientService.findByIgId(stockOrder.getIngredient().getIgId());
        model.addAttribute("ingredient", ingredient);

        // stockOrderUpdate 뷰 반환
        return "test/stockOrderUpdate";
    }


    @PostMapping("/test/stockOrderUpdate/{soId}") // @PutMapping 대신 @PostMapping 사용
    public String updateStockOrder(@PathVariable("soId") Long soId,
                                   @RequestParam("soQuantity") int soQuantity, // 변경될 수량만 받음
                                   Principal principal, Model model) {
        Branch navBranch = branchService.findBranchByEmail(principal.getName());
        model.addAttribute("user", navBranch);

        // 기존 StockOrder 객체를 조회
        StockOrder stockOrder = stockOrderService.findBySoId(soId);

        if (stockOrder != null) {
            // 변경될 필드만 업데이트
            stockOrder.setSoQuantity(soQuantity);
            // branchId가 제대로 바인딩 되지 않아 직접 주입
            stockOrder.setBranch(navBranch);
            // 업데이트된 StockOrder 객체를 저장
            stockOrderService.savePost(stockOrder);
        } else {
            // 적절한 에러 처리
            return "redirect:/error"; // 예시 URL, 실제 존재하는 에러 페이지 URL로 변경해야 함
        }

        return "redirect:/test/stockOrderList";
    }

    @PostMapping("/test/stockOrderConfirm/{soId}")
    public ResponseEntity<?> stockOrderConfirm(@PathVariable("soId") Long soId, Principal principal, Model model) {

        //redirect시 필요
        Branch navBranch = branchService.findBranchByEmail(principal.getName());
        model.addAttribute("user", navBranch);

        //stockOrder 상태 업데이트(승인)
        StockOrder stockOrder = stockOrderService.stockOrderConfirm(soId);
        //stockOrder 승인 시 branchInventory 업데이트
        branchInventoryService.inventoryUpdate(stockOrder);

        return ResponseEntity.ok().body("발주가 승인되었습니다.");
    }

    @PostMapping("/test/stockOrderReject/{soId}")
    public ResponseEntity<?> stockOrderReject(@PathVariable("soId") Long soId, Principal principal, Model model) {

        //redirect시 필요
        Branch navBranch = branchService.findBranchByEmail(principal.getName());
        model.addAttribute("user", navBranch);

        //stockOrder 상태 업데이트(반려)
        stockOrderService.stockOrderReject(soId);

        return ResponseEntity.ok().body("발주가 반려되었습니다.");
    }
}

