package br.com.cerd.oncow.services;

import br.com.cerd.oncow.dtos.asaas.AsaasPixQrCodeResponse;
import br.com.cerd.oncow.dtos.asaas.AsaasCreatePaymentRequest;
import br.com.cerd.oncow.dtos.asaas.AsaasPaymentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class AsaasService {

    private final RestClient restClient;

    public AsaasService(
            @Value("${asaas.api.url}") String apiUrl,
            @Value("${asaas.api.key}") String apiKey
    ) {
        this.restClient = RestClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader("access_token", apiKey)
                .defaultHeader(
                        "Content-Type",
                        MediaType.APPLICATION_JSON_VALUE
                )
                .build();
    }

    /**
     * Cria uma cobrança PIX no Asaas.
     */
    public AsaasPaymentResponse criarCobrancaPix(
            AsaasCreatePaymentRequest request
    ) {
        return restClient.post()
                .uri("/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(AsaasPaymentResponse.class);
    }

    /**
     * Busca o QR Code e o código PIX copia e cola.
     */
    public AsaasPixQrCodeResponse buscarQrCodePix(String paymentId) {
        return restClient.get()
                .uri("/payments/{id}/pixQrCode", paymentId)
                .retrieve()
                .body(AsaasPixQrCodeResponse.class);
    }
}
