package commity.web.service;

import commity.web.domain.Admin;
import commity.web.domain.Committer;
import commity.web.dto.SavePointDto;
import commity.web.repository.AdminRepository;
import commity.web.repository.CommitterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommitterService {

    private final CommitterRepository committerRepository;
    private final AdminRepository adminRepository;

    public List<Committer> getCommitters() {
        return committerRepository.findAll();
    }

    @Transactional
    public void update(SavePointDto savePointDto) {
        Integer adminCode = savePointDto.getAdminCode();
        Admin admin = adminRepository.findAll().get(0);

        if (!Objects.equals(adminCode, admin.getCode())) {
            throw new IllegalArgumentException("비밀번호가 일차하지 않습니다.");
        }
        List<Committer> committers = getCommitters();

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
