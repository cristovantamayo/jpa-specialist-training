package dev.cristovantamayo.ecommerce.model;

import lombok.*;

@Getter
@AllArgsConstructor
public enum PaymentStatus {

    IN_PROCESS,
    CANCELED,
    RECEIVED

}
