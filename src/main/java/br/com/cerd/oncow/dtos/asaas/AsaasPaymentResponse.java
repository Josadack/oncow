package br.com.cerd.oncow.dtos.asaas;

import java.math.BigDecimal;

public record AsaasPaymentResponse(
        String id,
        String status,
        BigDecimal value,
        String billingType
) {}