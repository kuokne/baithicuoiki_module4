package com.example.appspringboot.controllers.admin;

import com.example.appspringboot.entities.Promotion;
import com.example.appspringboot.services.IPromotionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/admin/promotions")
public class PromotionController {

    @Autowired
    private IPromotionService service;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("promotions", service.findAll());
        return "admin/promotions/index";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("promotion", new Promotion());
        return "admin/promotions/create";
    }

    @PostMapping("/create")
    public String save(@Valid @ModelAttribute Promotion promotion, BindingResult result, RedirectAttributes ra) {
        if (result.hasErrors()) return "admin/promotions/create";
        try {
            service.save(promotion);
            ra.addFlashAttribute("message", "Thêm mới thành công!");
            return "redirect:/admin/promotions";
        } catch (Exception e) {
            result.rejectValue("endDate", "error.promotion", e.getMessage());
            return "admin/promotions/create";
        }
    }
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes ra) {
        Promotion promotion = service.findById(id);
        if (promotion == null) {
            ra.addFlashAttribute("message", "Không tìm thấy chương trình khuyến mãi!");
            return "redirect:/admin/promotions";
        }
        model.addAttribute("promotion", promotion);
        return "admin/promotions/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute Promotion promotion, BindingResult result, RedirectAttributes ra) {
        if (result.hasErrors()) {
            return "admin/promotions/edit";
        }
        try {
            promotion.setId(id);
            service.save(promotion);
            ra.addFlashAttribute("message", "Cập nhật thành công!");
            return "redirect:/admin/promotions";
        } catch (Exception e) {
            result.rejectValue("endDate", "error.promotion", e.getMessage());
            return "admin/promotions/edit";
        }
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long id) {
        service.delete(id);
        return "redirect:/admin/promotions";
    }
    @GetMapping("/search")
    public String search(
            @RequestParam(required = false) Double discount,
            @RequestParam(required = false) LocalDate start,
            @RequestParam(required = false) LocalDate end,
            Model model) {
        model.addAttribute("promotions", service.search(discount, start, end));
        return "admin/promotions/index";
    }
}
