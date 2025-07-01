package commity.web.controller;

import commity.web.domain.Committer;
import commity.web.service.AdminService;
import commity.web.service.CommitterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommitterController {

    private final CommitterService committerService;
    private final AdminService adminService;

    @GetMapping
    public String getCommitters(Model model) {
        List<Committer> committers = committerService.getCommitters();
        Integer solvedCountLimit = adminService.getAdmin().getSolvedCountGoal();
        model.addAttribute("committers", committers);
        model.addAttribute("solvedCountLimit", solvedCountLimit);
        return "getCommitters";
    }

    @GetMapping("/point")
    public String getPoints(Model model) {
        List<Committer> committers = committerService.getCommitters();
        Integer pointLimit = adminService.getAdmin().getPointLimit();
        model.addAttribute("committers", committers);
        model.addAttribute("pointLimit", pointLimit);
        return "getPoints";
    }

    @PostMapping("/point")
    public String savePoint() {
        committerService.update();
        return "redirect:/";
    }
}
