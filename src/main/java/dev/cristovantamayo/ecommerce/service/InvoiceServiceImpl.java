package dev.cristovantamayo.ecommerce.service;

import dev.cristovantamayo.ecommerce.model.Invoice;
import dev.cristovantamayo.ecommerce.model.Purchase;

import java.util.Date;

public class InvoiceServiceImpl implements InvoiceService {
    @Override
    public void generate(Purchase purchase) {
        // Place to process
        Invoice invoice = new Invoice();
        invoice.setPurchase(purchase);
        invoice.setXml("<?xml></xml>".getBytes());
        invoice.setIssueDate(new Date());
        purchase.setInvoice(invoice);
        System.out.println("-----------> Generated:" + purchase.getInvoice().getXml());
    }
}
