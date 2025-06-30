package commity.web.controller;

import commity.web.domain.Committer;
import commity.web.repository.CommitterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommitterController {

    private final CommitterRepository committerRepository;

    @GetMapping
    public String getCommitters(Model model) {
        List<Committer> committers = committerRepository.findAll();
        model.addAttribute("committers", committers);
        return "getCommitters";
    }

    @GetMapping("/point")
    public String getPoints(Model model) {
        List<Committer> committers = committerRepository.findAll();
        model.addAttribute("committers", committers);
        return "getPoints";
    }

}
