package com.company_software.invoices_service.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "invoices")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    private Long clientId;

    private LocalDate date;

    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    private StateInvoice state;

    public enum StateInvoice {
        PENDIENTE,
        PAGADO,
        CANCELADO
    }
}
