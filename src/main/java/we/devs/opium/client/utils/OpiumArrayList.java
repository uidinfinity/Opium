package we.devs.opium.client.utils;

import java.util.ArrayList;
import java.util.List;

public class OpiumArrayList<T> extends ArrayList<T> {

    public OpiumArrayList(List<T> strings) {
        super(strings);
    }

    public int getModCount() {
        return modCount;
    }

}
