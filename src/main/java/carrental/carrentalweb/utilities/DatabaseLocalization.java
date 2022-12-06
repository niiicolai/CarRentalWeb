package carrental.carrentalweb.utilities;

import java.util.HashMap;

public class DatabaseLocalization {    
    private static HashMap<String, String> mappings = create();

    private static HashMap<String, String> create() {
        HashMap<String, String> mappings = new HashMap<>();
        
        mappings.put("users", "bruger");
        mappings.put("username", "brugernavnet");
        mappings.put("email", "e-mail");

        return mappings;
    }

    public static String getLocalization(String word) {
        if (mappings.containsKey(word)) {
            return mappings.get(word);
        } else {
            return word;
        }
    }
}
