package commity.web.service;

import commity.web.repository.CommitterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommitterService {

    private final CommitterRepository committerRepository;

}
