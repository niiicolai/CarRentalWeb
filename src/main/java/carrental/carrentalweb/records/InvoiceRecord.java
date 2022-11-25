package carrental.carrentalweb.records;

import carrental.carrentalweb.entities.Invoice;
import carrental.carrentalweb.entities.InvoiceSpecification;

public record InvoiceRecord(Invoice invoice, InvoiceSpecification specification) {
}
