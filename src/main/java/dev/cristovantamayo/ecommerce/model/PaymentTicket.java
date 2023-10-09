package dev.cristovantamayo.ecommerce.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "payment_by_ticket")
public class PaymentTicket extends EntityBaseInteger {

    @Column(name = "purchase_id")
    private Integer purchaseId;

    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(name = "bar_code")
    private String barCode;

    public static PaymentTicket of (Integer purchaseId, PaymentStatus paymentStatus, String barCode){
        return new PaymentTicket(purchaseId, paymentStatus, barCode);
    }

}
