package br.com.cerd.oncow.controllers;

import br.com.cerd.oncow.services.ContributionService;
import org.springframework.beans.factory.annotation.Value;
import br.com.cerd.oncow.dtos.AsaasWebhookDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin( "*" )
public class AsaasWebhookController {

    @Value("${asaas.webhook.token}")
    private String webhookToken;

    private final ContributionService contributionService;

    public AsaasWebhookController(ContributionService contributionService){
        this.contributionService = contributionService;
    }

    @PostMapping("/webhook/asaas")
    public ResponseEntity<Void> receberWebhook(
            @RequestHeader("asaas-access-token") String token,
            @RequestBody AsaasWebhookDto webhook) {

        if (!webhookToken.equals(token)) {
            return ResponseEntity.status(401).build();
        }

        if (!"PAYMENT_RECEIVED".equals(webhook.event())) {
            return ResponseEntity.ok().build();
        }

        contributionService.processarPagamentoRecebido(
                webhook.payment().id()
        );

        return ResponseEntity.ok().build();
    }
}
