package br.com.cerd.oncow.dtos.asaas;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AsaasCreatePaymentRequest(
        String customer,
        String billingType,
        BigDecimal value,
        LocalDate dueDate
) {}