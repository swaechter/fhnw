package ch.fhnw.algd1.simplearraylist;

import java.util.AbstractList;
import java.util.List;

// Source: https://docs.oracle.com/javase/8/docs/api/java/util/AbstractList.html
public class SimpleArrayList<E> extends AbstractList<E> implements List<E> {

    private int minArrLen = 16;

    private Object[] arr = new Object[minArrLen];

    private int size = 0;

    private long counter = minArrLen;

    @Override
    public int size() {
        return size;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E get(int index) {

        // Check the index
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Invalid index");
        }

        counter++;
        return (E) arr[index];
    }

    @Override
    public E set(int index, E element) {

        // UnsupportedOperationException: Not required

        // ClassCastException: Not required

        // NullPointerException: Not required

        // IllegalArgumentException: Not required


        E old = get(index); // includes index checking
        counter++;
        arr[index] = element;
        return old;
    }

    @Override
    public void add(int index, E element) {

        // UnsupportedOperationException: Not required

        // ClassCastException: Not required

        // NullPointerException: Not required

        // IllegalArgumentException: Not required

        // Check the index
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("Invalid index");
        }

        // In case no empty slot is available, double the array and copy the old content
        if (arr.length == size) {
            Object[] temparr = new Object[size * 2];
            for (int i = 0; i < size; i++) {
                counter += 2;
                temparr[i] = arr[i];
            }
            arr = temparr;
        }

        // Shift all elements
        for (int i = size; i > index; i--) {
            counter += 2;
            arr[i] = arr[i - 1];
        }

        // Set the element
        counter++;
        arr[index] = element;
        size++;
    }

    @Override
    public E remove(int index) {
        E old = get(index); // includes index checking
        while (index + 1 < size) {
            counter += 2;
            arr[index] = arr[index + 1];
            index++;
        }

        counter++;
        arr[size] = null;
        size--;
        return old;
    }

    public long getCounter() {
        return counter;
    }
}
