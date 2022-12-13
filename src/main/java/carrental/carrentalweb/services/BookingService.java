package carrental.carrentalweb.services;

import carrental.carrentalweb.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


//Mikkel
@Service
public class BookingService {

    public BookingService (BookingRepository bookingRepository) {this.bookingRepository = bookingRepository;}

    @Autowired
    private BookingRepository bookingRepository;

    public List<Integer> getBookingAmountsOfTheWeek() {
        ArrayList<Integer> bookingsThisWeek = new ArrayList<>();
        LocalDate now = LocalDate.now();
        LocalDateTime lastMonday = now.with(DayOfWeek.MONDAY).atStartOfDay();
        for (int i = 0; i < 7; i++) {
            LocalDateTime day = lastMonday;
            bookingsThisWeek.add(bookingRepository.getAmountOfBookingsAtDate(day));
            day = day.plusDays(1);
        }
        return bookingsThisWeek;
    }
}
