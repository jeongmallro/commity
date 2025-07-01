package commity.web.controller;

import commity.web.dto.WebhookPayload;
import commity.web.dto.CommitterUpdateDto;
import commity.web.service.SseEmitterService;
import commity.web.service.WebhookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/github")
public class WebhookController {

    private final WebhookService webhookService;
    private final SseEmitterService sseEmitterService;

    @PostMapping("/webhook")
    public void getWebhook(@RequestBody WebhookPayload payload) {
        List<CommitterUpdateDto> response = webhookService.update(payload.getCommits());
        sseEmitterService.broadcast(response);
    }

    @GetMapping("/commits")
    public void getCommits() {
        webhookService.getCommits();
    }
}
