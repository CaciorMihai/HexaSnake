package utils;

import java.util.ArrayList;

public class CoordinatesQueue {
    private ArrayList<Coordinates> queue = new ArrayList<>();
    public void enQueue(final Coordinates elem) {
        queue.add(queue.size(), elem);
    }
    public Coordinates top() {
        return queue.get(0);
    }
    public Coordinates deQueue() {
        Coordinates elem = queue.get(0);
        queue.remove(0);
        return elem;
    }
    public int size() {
        return queue.size();
    }
}
