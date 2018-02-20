package ru.otus.lessons.home05;

import java.util.*;

public class SimpleArrayList<T> implements List<T> {
    private int size;
    private int initialCapacity = 12;
    private Object[] data;

    public SimpleArrayList() {
        data = new Object[initialCapacity];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(Object o) {
        for (Object o1 : data) {
            if (o1.equals(o)) return true;
        }
        return false;
    }

    public Iterator<T> iterator() {
        return new SimpleListIterator<>(0);
    }

    public Object[] toArray() {
        return Arrays.copyOf(data, size);
    }

    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    public boolean add(T t) {
        add(size, t);
        return true;
    }

    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(o)) {
                remove(i);
                return  true;
            }
        }
        return false;
    }

    public boolean containsAll(Collection<?> c) {
        return false;
    }

    public boolean addAll(Collection<? extends T> c) {
        Object[] a = c.toArray();
        if (a.length == 0) return false;
        if (size + a.length > data.length) {
            data = Arrays.copyOf(data, data.length + size);
        }
        System.arraycopy(a, 0, data, size, a.length);
        size += a.length;
        return true;
    }

    public boolean addAll(int index, Collection<? extends T> c) {
        return false;
    }

    public boolean removeAll(Collection<?> c) {
        return false;
    }

    public boolean retainAll(Collection<?> c) {
        return false;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            data[i] = null;
        }
        size = 0;
    }

    @SuppressWarnings("unchecked")
    public T get(int index) {
        checkCorrectValue(index);
        return ((T) data[index]);
    }

    @SuppressWarnings("unchecked")
    public T set(int index, T element) {
        checkCorrectValue(index);
        T oldElement = ((T) data[index]);
        data[index] = element;
        return oldElement;
    }

    public void add(int index, T element) {
        checkCorrectValue(index);
        if (size == data.length) {
            data = Arrays.copyOf(data, size + initialCapacity);
        }
        int quantity = size - index;
        System.arraycopy(data, index, data, index + 1, quantity);
        data[index] = element;
        size += 1;
    }

    private void checkCorrectValue(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Wrong index");
        }
    }

    @SuppressWarnings("unchecked")
    public T remove(int index) {
        checkCorrectValue(index);
        T removedValue = ((T) data[index]);
        int quantity = size - index - 1;
        if (quantity > 0)
            System.arraycopy(data, index + 1, data, index, quantity);
        data[--size] = null;
        return removedValue;
    }

    public int indexOf(Object o) {
        return 0;
    }

    public int lastIndexOf(Object o) {
        return 0;
    }

    public ListIterator<T> listIterator() {
        return new SimpleListIterator<>(0);
    }

    public ListIterator<T> listIterator(int index) {
        return new SimpleListIterator<>(index);
    }

    public List<T> subList(int fromIndex, int toIndex) {
        return null;
    }

    private class SimpleListIterator<T> implements ListIterator<T> {
        private int pointer;

        public SimpleListIterator(int pointer) {
            this.pointer = pointer;
        }

        public boolean hasNext() {
            return pointer != size;
        }

        public T next() {
            return ((T) data[pointer++]);
        }

        public boolean hasPrevious() {
            return pointer > -1;
        }

        public T previous() {
            return null;
        }

        public int nextIndex() {
            return pointer + 1;
        }

        public int previousIndex() {
            return pointer - 1;
        }

        public void remove() {

        }

        public void set(T t) {

        }

        public void add(T t) {

        }
    }

}
