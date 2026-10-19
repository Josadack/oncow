package br.com.cerd.oncow.dtos;

import br.com.cerd.oncow.enums.ContributionStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ContributionCreatedDto(
        Long id,
        String name,
        BigDecimal amount,
        ContributionStatus status,
        LocalDateTime createdAt,
        String paymentId,
        String pixPayload,
        String qrCodeBase64,
        String expirationDate) {
}
