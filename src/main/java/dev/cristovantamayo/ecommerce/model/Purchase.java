package dev.cristovantamayo.ecommerce.model;

import dev.cristovantamayo.ecommerce.listeners.GenerateInvoiceListener;
import dev.cristovantamayo.ecommerce.listeners.GenericListener;
import jakarta.validation.constraints.*;
import lombok.*;

import jakarta.persistence.*;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.engine.spi.PersistentAttributeInterceptable;
import org.hibernate.engine.spi.PersistentAttributeInterceptor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners({ GenerateInvoiceListener.class, GenericListener.class })

@Entity
@Table(name = "purchase")
public class Purchase extends EntityBaseInteger
        //implements PersistentAttributeInterceptable
        {

    @NotNull
    @ManyToOne(optional = false) // cascade = CascadeType.PERSIST
    @JoinColumn(name = "client_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_purchase_client"))
    private Client client;

    @NotNull
    @PastOrPresent
    @Column(name = "purchase_date", updatable = false, nullable = false)
    private LocalDateTime purchaseDate;

    @PastOrPresent
    @Column(name = "updated_at", insertable = false)
    private LocalDateTime updatedAt;

    @NotNull
    @FutureOrPresent
    @Column(name = "purchase_due_date")
    private LocalDateTime purchaseDueDate;

    //@LazyToOne(LazyToOneOption.NO_PROXY)
    @OneToOne(mappedBy = "purchase")
    private Invoice invoice;

    @NotNull
    @Positive
    @Column(nullable = false)
    private BigDecimal total;

    @NotEmpty
    @OneToMany(mappedBy = "purchase", fetch = FetchType.LAZY) //, cascade = CascadeType.PERSIST ,orphanRemoval = true
    @Column(name = "purchase_item")
    private List<PurchaseItem> purchaseItems;

    @NotNull
    @Column(length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private PurchaseStatus status;

    //@LazyToOne(LazyToOneOption.NO_PROXY)
    @OneToOne(mappedBy = "purchase")
    private Payment payment;

    @Embedded
    @Column(name = "delivery_address")
    private DeliveryAddress deliveryAddress;

//    public Invoice getInvoice(){
//        if(this.persistentAttributeInterceptor != null ) {
//            return (Invoice)  persistentAttributeInterceptor
//                    .readObject(this, "invoice", this.invoice);
//        }
//        return this.invoice;
//    }
//
//    public void setInvoice(Invoice invoice){
//        if(this.persistentAttributeInterceptor != null ) {
//            this.invoice = (Invoice) persistentAttributeInterceptor
//                    .writeObject(this, "invoice", this.invoice, invoice);
//        } else {
//            this.invoice = invoice;
//        }
//
//    }
//
//    public Payment getPayment() {
//        if(this.persistentAttributeInterceptor != null ) {
//            return (Payment)  persistentAttributeInterceptor
//                    .readObject(this, "payment", this.payment);
//        }
//        return this.payment;
//    }
//
//    public void setPayment(Payment payment) {
//        if(this.persistentAttributeInterceptor != null ) {
//            this.payment = (Payment) persistentAttributeInterceptor
//                    .writeObject(this, "payment", this.payment, payment);
//        } else {
//            this.payment = payment;
//        }
//
//    }
//
//    @Setter(AccessLevel.NONE) @Getter(AccessLevel.NONE)
//    @Transient
//    private PersistentAttributeInterceptor persistentAttributeInterceptor;
//
//    @Override
//    public PersistentAttributeInterceptor $$_hibernate_getInterceptor() {
//        return this.persistentAttributeInterceptor;
//    }
//
//    @Override
//    public void $$_hibernate_setInterceptor(PersistentAttributeInterceptor persistentAttributeInterceptor) {
//        this.persistentAttributeInterceptor = persistentAttributeInterceptor;
//    }

    public boolean itsPaid() {
        return PurchaseStatus.PAID_OUT.equals(status);
    }
    @PrePersist
    public void prePersist() {
        purchaseDate = LocalDateTime.now();
        calculateTotal();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
        calculateTotal();
    }

    // we can't annotate Callback annotation twice, annotation just in use
    //@PrePersist
    //@PreUpdate
    public void calculateTotal() {
        total = BigDecimal.ZERO;
        if(purchaseItems != null)
            total = purchaseItems.stream().map(
                    i -> new BigDecimal(i.getQuantity()).multiply(i.getProductPrice()))
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @PostPersist
    public void postPersist() {
        System.out.println("After Persists");
    }

    @PostUpdate
    public void postUpdate() {
        System.out.println("After Update");
    }

    @PreRemove
    public void preRemove() {
        System.out.println("Before Delete");
    }

    @PostRemove
    public void postRemove() {
        System.out.println("After Remove");
    }

    @PostLoad
    public void postLoad() {
        System.out.println("After load a Entity");
    }

    public static Purchase of (Client client, LocalDateTime purchaseDate, LocalDateTime updateAt, LocalDateTime purchaseDueDate,
                               Invoice invoice, BigDecimal total, List<PurchaseItem> purchaseItems,
                               PurchaseStatus status, DeliveryAddress deliveryAddress, Payment payment) {

        return new Purchase(client, purchaseDate, updateAt, purchaseDueDate, invoice, total,
                        purchaseItems, status, payment, deliveryAddress);
    }
}
