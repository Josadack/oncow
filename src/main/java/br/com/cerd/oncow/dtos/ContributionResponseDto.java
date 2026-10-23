package br.com.cerd.oncow.dtos;

import br.com.cerd.oncow.enums.ContributionStatus;

import java.math.BigDecimal;

public record ContributionResponseDto(
        Long id,
        String name,
        BigDecimal amount,
        String paymentId,
        ContributionStatus status
) {
}
