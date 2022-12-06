package carrental.carrentalweb.utilities;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DatabaseError {
    private SQLException sqlException;

    private static final int DUPLICATE_ENTRY = 1062;
    private static final int COLUMN_CANNOT_BE_NULL = 1048;

    public DatabaseError(SQLException sqlException) {
        this.sqlException = sqlException;
    }

    public String getHumanMessage() {
        int code = sqlException.getErrorCode();

        if (code == DUPLICATE_ENTRY) {
            return handleDuplicateEntry();
        }

        if (code == COLUMN_CANNOT_BE_NULL) {
            return handleColumnCannotBeNull();
        }

        return String.valueOf(sqlException.getMessage());
    }

    private String handleDuplicateEntry() {
        List<String> matches = scan(sqlException.getMessage(), "'(.*?)'");
        String value = DatabaseLocalization.getLocalization(matches.get(0).replace("'", ""));

        String[] matches2 = matches.get(1).split("\\.", 2);
        String table = DatabaseLocalization.getLocalization(matches2[0].replace("'", ""));
        String attribute = DatabaseLocalization.getLocalization(matches2[1].replace("'", ""));

        return String.format("Det er ikke muligt at oprette en %s, fordi en %s med %s '%s' allerede eksisterer!",
                table, table, attribute, value);
    }

    private String handleColumnCannotBeNull() {
        List<String> matches = scan(sqlException.getMessage(), "'(.*?)'");
        String column = DatabaseLocalization.getLocalization(matches.get(0).replace("'", ""));

        return String.format("%s skal udfyldes!", column);
    }

    private List<String> scan(String string, String regex) {
        List<String> matches = new ArrayList<String>();
        Matcher m = Pattern.compile(regex).matcher(string);
        while (m.find())
            matches.add(m.group());
        return matches;
    }
}
