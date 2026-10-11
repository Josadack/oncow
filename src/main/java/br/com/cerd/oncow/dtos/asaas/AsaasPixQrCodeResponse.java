package br.com.cerd.oncow.dtos.asaas;

public record AsaasPixQrCodeResponse(
        String encodedImage,
        String payload,
        String expirationDate
) {}