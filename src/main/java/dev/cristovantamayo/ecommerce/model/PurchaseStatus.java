package dev.cristovantamayo.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PurchaseStatus {
    WAITING,
    CANCELED,
    PAID_OUT;
}
