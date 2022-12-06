package carrental.carrentalweb.utilities;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import carrental.carrentalweb.enums.DatabaseResponseState;
import carrental.carrentalweb.records.DatabaseRecord;

public class DatabaseResponse implements Iterator<DatabaseRecord> {        
    private DatabaseResponseState state = DatabaseResponseState.success;
    private List<DatabaseRecord> databaseRecords;    
    private DatabaseError databaseError;
    
    private int index = 0;
    
    public DatabaseResponse() {            
        this.databaseRecords = new LinkedList<DatabaseRecord>();        
    }

    public void setState(DatabaseResponseState state) {
        this.state = state;
    }

    public void setDatabaseError(DatabaseError databaseError) {
        this.databaseError = databaseError;       
    }

    public void add(DatabaseRecord record) {
        databaseRecords.add(record);            
    }

    public DatabaseResponseState getState() {
        return this.state;
    }

    public DatabaseError getDatabaseError() {
        return databaseError;
    }

    public boolean isSuccessful() {
        return this.state == DatabaseResponseState.success;
    }

    @Override
    public boolean hasNext() {
        return index < databaseRecords.size();
    }

    @Override
    public DatabaseRecord next() {
        return databaseRecords.get(index++);
    }
}