package carrental.carrentalweb.utilities;

import java.util.Iterator;

public class DatabaseRequestBody implements Iterator<Object> {
    private Object[] objects;
    private int index = 0;

    public DatabaseRequestBody(Object... objects) {
        this.objects = objects;
    }

    public int size() {
        return objects.length;
    }

    @Override
    public boolean hasNext() {
        return index < objects.length;
    }

    @Override
    public Object next() {
        return objects[index++];
    }
}
