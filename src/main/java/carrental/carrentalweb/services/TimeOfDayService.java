package carrental.carrentalweb.services;

import java.util.Calendar;
import org.springframework.stereotype.Service;
import carrental.carrentalweb.enums.TimeOfDay;

/*
 * Written by Nicolai Berg Andersen
 */
@Service
public class TimeOfDayService {
    
    public TimeOfDay get() {
        Calendar c = Calendar.getInstance();
        int hourOfDay = c.get(Calendar.HOUR_OF_DAY);

        if (hourOfDay > 5 && hourOfDay < 12){
            return TimeOfDay.Morning;       
        } else if (hourOfDay > 12 && hourOfDay < 16){
            return TimeOfDay.Afternoon;     
        } else if (hourOfDay > 16 && hourOfDay < 21){
            return TimeOfDay.Evening;     
        } else {
            return TimeOfDay.Night;     
        } 
    }

    public String getGreeting() {
        TimeOfDay timeOfDay = get();
        switch(timeOfDay) {
            case Morning:
                return "Godmorgen";
            case Afternoon:
                return "Godeftermiddag";
            case Evening:
                return "Godaften";
            default:
                return "Godaften";
        }
    }

    public String getImage() {
        TimeOfDay timeOfDay = get();
        switch(timeOfDay) {
            case Morning:
                return "/images/morning.jpg";
            case Afternoon:
                return "/images/afternoon.jpg";
            case Evening:
                return "/images/evening.jpg";
            default:
                return "/images/night.jpg";
        }
    }
}
