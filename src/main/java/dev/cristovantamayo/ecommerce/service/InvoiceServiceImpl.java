package dev.cristovantamayo.ecommerce.service;

import dev.cristovantamayo.ecommerce.model.Invoice;
import dev.cristovantamayo.ecommerce.model.Purchase;

import java.util.Date;

public class InvoiceServiceImpl implements InvoiceService {
    @Override
    public void generate(Purchase purchase) {
        // Place to process
        purchase.setInvoice(Invoice.of(null, purchase, "<?xml></xml>", new Date()));
        System.out.println("-----------> Generated:" + purchase.getInvoice().getXml());
    }
}
