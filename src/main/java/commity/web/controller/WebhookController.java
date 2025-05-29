package commity.web.controller;

import commity.web.service.WebhookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WebhookController {

    private final WebhookService webhookService;

    @PostMapping("/webhook")
    public void getWebhook(@RequestBody WebhookPayload payload) {
        webhookService.update(payload.getCommits());
    }
}
