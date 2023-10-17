package dev.cristovantamayo.ecommerce.listeners;

import dev.cristovantamayo.ecommerce.model.Purchase;
import dev.cristovantamayo.ecommerce.service.InvoiceService;
import dev.cristovantamayo.ecommerce.service.InvoiceServiceImpl;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class GenerateInvoiceListener {

    private InvoiceService invoiceService =
            new InvoiceServiceImpl();

    @PrePersist
    @PreUpdate
    public void generate(Purchase purchase){
        if(purchase.itsPaid()
                && purchase.getInvoice() == null)
                        invoiceService.generate(purchase);
    }
}
