package carrental.carrentalweb.records;

import carrental.carrentalweb.entities.Invoice;
import carrental.carrentalweb.entities.InvoiceSpecification;

/*
 * Written by Nicolai Berg Andersen
 */
public record InvoiceRecord(Invoice invoice, InvoiceSpecification[] specifications) {
}
