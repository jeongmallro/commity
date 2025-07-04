package commity.web.service;

import commity.web.domain.Admin;
import commity.web.dto.AdminDto;
import commity.web.dto.AdminSetForm;
import commity.web.repository.AdminRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
class AdminServiceTest {

    @Autowired
    private AdminService adminService;
    @MockitoSpyBean
    private AdminRepository adminRepository;

    @Test
    void 캐시_조회() {
        //given
        Admin admin = Admin.ofCreate(0);
        when(adminRepository.findAll()).thenReturn(List.of(admin));

        //when
        AdminDto foundAdmin = adminService.getAdmin();
        AdminDto foundAdmin2 = adminService.getAdmin();

        //then
        verify(adminRepository, times(1)).findAll();
    }


    @Test
    void 업데이트후_캐시_조회() {
        //given
        Admin admin = Admin.ofCreate(0);
        when(adminRepository.findAll()).thenReturn(List.of(admin));

        AdminSetForm adminSetForm = new AdminSetForm(5, 0, 2, 0);
        adminService.setAdmin(adminSetForm);

        //when
        AdminDto foundAdmin = adminService.getAdmin();
        AdminDto foundAdmin2 = adminService.getAdmin();

        //then
        verify(adminRepository, times(2)).findAll();

        assertThat(foundAdmin.getSolvedCountGoal()).isEqualTo(5);
        assertThat(foundAdmin.getPointLimit()).isEqualTo(0);
        assertThat(foundAdmin.getPointPerProblem()).isEqualTo(2);
        assertThat(foundAdmin2.getSolvedCountGoal()).isEqualTo(5);
        assertThat(foundAdmin2.getPointLimit()).isEqualTo(0);
        assertThat(foundAdmin2.getPointPerProblem()).isEqualTo(2);
    }

}