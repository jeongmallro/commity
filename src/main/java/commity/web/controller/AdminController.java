package commity.web.controller;

import commity.web.dto.AdminDto;
import commity.web.dto.AdminSetForm;
import commity.web.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping
    public String getAdmin(Model model) {
        AdminDto admin = adminService.getAdmin();
        model.addAttribute("admin", admin);
        return "getAdmin";
    }

    @PostMapping("/init")
    public String initAdmin(@RequestBody Integer code) {
        adminService.initAdmin(code);
        return "redirect:/";
    }

    @PostMapping("/set")
    public String set(@ModelAttribute AdminSetForm adminSetForm) {
        adminService.setAdmin(adminSetForm);
        return "redirect:/";
    }

}
