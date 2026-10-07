package br.com.cerd.oncow.models;

import br.com.cerd.oncow.enums.ContributionStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_CONTRIBUTIONS")
public class ModelContribution {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private ContributionStatus Status;
    private String paymentId;
    private LocalDateTime createdAt;
    private LocalDateTime paidAt;


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public ContributionStatus getStatus() {
        return Status;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }

    public void setStatus(ContributionStatus status) {
        Status = status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setPaidAt(LocalDateTime paidAt) {
        this.paidAt = paidAt;
    }
}
