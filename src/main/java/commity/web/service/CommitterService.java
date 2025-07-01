package commity.web.service;

import commity.web.dto.AdminDto;
import commity.web.domain.Committer;
import commity.web.repository.CommitterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommitterService {

    private final CommitterRepository committerRepository;
    private final AdminService adminService;

    public List<Committer> getCommitters() {
        return committerRepository.findAll();
    }

    @Transactional
    public void update() {
        List<Committer> committers = getCommitters();
        AdminDto admin = adminService.getAdmin();
        Integer pointPerProblem = admin.getPointPerProblem();
        Integer solvedCountLimit = admin.getSolvedCountGoal();
        for (Committer committer : committers) {
            Integer solved = committer.getSolvedCount();
            int unsolved = solvedCountLimit - solved;

            committer.updatePoint(solved * pointPerProblem - unsolved * pointPerProblem);
            committer.initSolvedCount();
        }
    }
}
