package commity.web.controller;

import commity.web.service.CommitterUpdateResponse;
import commity.web.service.SseEmitterService;
import commity.web.service.WebhookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WebhookController {

    private final WebhookService webhookService;
    private final SseEmitterService sseEmitterService;

    @PostMapping("/webhook")
    public void getWebhook(@RequestBody WebhookPayload payload) {
        List<CommitterUpdateResponse> response = webhookService.update(payload.getCommits());
        sseEmitterService.broadcast(response);
    }

    @GetMapping("/test")
    public void test() {
        List<CommitterUpdateResponse> list = webhookService.getCommitterUpdateResponses();
        sseEmitterService.broadcast(list);
    }

}
