package carrental.carrentalweb.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import carrental.carrentalweb.entities.Booking;
import carrental.carrentalweb.entities.Car;
import carrental.carrentalweb.entities.Invoice;
import carrental.carrentalweb.entities.InvoiceSpecification;
import carrental.carrentalweb.entities.User;
import carrental.carrentalweb.records.InvoiceRecord;
import carrental.carrentalweb.repository.BookingRepository;
import carrental.carrentalweb.repository.CarRepository;
import carrental.carrentalweb.repository.InvoiceRepository;
import carrental.carrentalweb.repository.InvoiceSpecificationRepository;
import carrental.carrentalweb.repository.UserRepository;

/*
 * Written by Nicolai Berg Andersen
 */
@Service
public class CarInvoiceService {

    private static final int dueDays = 30;
    
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InvoiceSpecificationRepository invoiceSpecificationRepository;

    @Autowired
    private InvoiceMailerService invoiceMailerService;
    
    private InvoiceRecord generateInvoiceRecord(Booking booking, Car car) {
        long bookingId = booking.getId();
        LocalDateTime due = LocalDateTime.now().plusDays(dueDays);

        /* Build invoice */
        Invoice invoice = new Invoice();
        invoice.setBookingId(bookingId);
        invoice.setDueDate(due);
        invoiceRepository.insert(invoice);

        Invoice last = invoiceRepository.last();
        String description = String.format("Salg af bil: %s %s", car.getBrand(), car.getModel());

        /* Add invoice specification */
        
        InvoiceSpecification[] specifications = new InvoiceSpecification[1];
        specifications[0] = new InvoiceSpecification();
        specifications[0].setInvoiceId(last.getId());
        specifications[0].setDescription(description);
        specifications[0].setPrice(car.getSellPrice());

        invoiceSpecificationRepository.insert(specifications[0]);
        
        return new InvoiceRecord(invoice, specifications);
    }

    private User getBookingOwner(long userId) {
        return userRepository.find("id", userId);
    }

    private void emailOwner(User user, InvoiceRecord invoiceRecord) {
        invoiceMailerService.send(
            user, 
            invoiceRecord.invoice(), 
            invoiceRecord.specifications());
    }

    public void execute(long bookingId) {
        Booking booking = bookingRepository.findByBookingId(bookingId);
        Car car = carRepository.findCarByVehicleNumber(booking.getVehicleNumber());

        InvoiceRecord invoiceRecord = generateInvoiceRecord(booking, car);
        User user = getBookingOwner(booking.getUserId());

        emailOwner(user, invoiceRecord);
    }
}
