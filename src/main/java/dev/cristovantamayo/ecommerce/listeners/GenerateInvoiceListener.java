package dev.cristovantamayo.ecommerce.listeners;

import dev.cristovantamayo.ecommerce.model.Purchase;
import dev.cristovantamayo.ecommerce.service.InvoiceService;
import dev.cristovantamayo.ecommerce.service.InvoiceServiceImpl;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

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
