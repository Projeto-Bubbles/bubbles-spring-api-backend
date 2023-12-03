package bubbles.springapibackend.api.util;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class Queue<T> {
    private static final int DEFAULT_CAPACITY = 10;

    private int size;
    private T[] queue;
    private int start;
    private int end;

    public Queue() {
        this(DEFAULT_CAPACITY);
    }

    public Queue(int capacity) {
        this.queue = (T[]) new Object[capacity];
        this.size = 0;
        this.start = 0;
        this.end = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == queue.length;
    }

    public void insert(T info) {
        if (isFull()) {
            throw new IllegalStateException("A fila está cheia");
        }
        queue[end] = info;
        end = (end + 1) % queue.length;
        size++;
    }

    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("A fila está vazia");
        }
        return queue[start];
    }

    public T poll() {
        if (isEmpty()) {
            throw new NoSuchElementException("A fila está vazia");
        }
        T removed = queue[start];
        queue[start] = null;
        start = (start + 1) % queue.length;
        size--;
        return removed;
    }

    public void display() {
        for (int i = 0; i < size; i++) {
            int index = (start + i) % queue.length;
            System.out.print(queue[index] + " ");
        }
        System.out.println();
    }

    public T[] getQueue() {
        return Arrays.copyOf(queue, size);
    }
}
