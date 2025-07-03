package commity.web.service;

import commity.web.dto.AdminDto;
import commity.web.dto.AdminSetForm;
import commity.web.domain.Admin;
import commity.web.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    public void initAdmin(Integer code) {
        Admin admin = Admin.ofCreate(code);
        adminRepository.save(admin);
    }

    @Transactional
    @CachePut("admin")
    public void setAdmin(AdminSetForm adminSetForm) {
        int code = adminSetForm.getCode();
        Admin admin = adminRepository.findAll().get(0);
        if (admin.getCode() != code) {
            throw new IllegalArgumentException("어드민 코드가 일치하지 않습니다.");
        }

        admin.updateSet(
                adminSetForm.getSolvedCountGoal(),
                adminSetForm.getPointLimit(),
                adminSetForm.getPointPerProblem());

    }

    @Cacheable("admin")
    public AdminDto getAdmin() {
        Admin admin = adminRepository.findAll().get(0);
        return AdminDto.from(admin);
    }
}
