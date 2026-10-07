package br.com.cerd.oncow.dtos;

import java.math.BigDecimal;

public record ContributionResponseDto(
        Long id,
        String name,
        BigDecimal amount,
        String paymentId
) {
}
