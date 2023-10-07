package dev.cristovantamayo.ecommerce.service;

import dev.cristovantamayo.ecommerce.model.Purchase;

public interface InvoiceService {
    void generate(Purchase purchase);
}
