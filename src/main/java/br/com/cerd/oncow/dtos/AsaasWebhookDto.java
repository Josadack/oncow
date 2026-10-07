package br.com.cerd.oncow.dtos;

public record AsaasWebhookDto(
        String id,
        String event,
        PaymentDto payment
) {

    public record PaymentDto(
            String id
    ) {
    }
}
