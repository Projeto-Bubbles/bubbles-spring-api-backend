package bubbles.springapibackend.api.util;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class Stack<T> {
    private static final int DEFAULT_CAPACITY = 10;

    private T[] stack;
    private int top;

    public Stack() {
        this(DEFAULT_CAPACITY);
    }

    public Stack(int capacity) {
        this.stack = (T[]) new Object[capacity];
        this.top = -1;
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public boolean isFull() {
        return top == stack.length - 1;
    }

    public void push(T info) {
        if (isFull()) {
            throw new IllegalStateException("A pilha está cheia");
        }
        top++;
        stack[top] = info;
    }

    public T pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("A pilha está vazia");
        }
        T removed = stack[top];
        stack[top] = null;
        top--;
        return removed;
    }

    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("A pilha está vazia");
        }
        return stack[top];
    }

    public void display() {
        for (int i = 0; i <= top; i++) {
            System.out.print(stack[i] + " ");
        }
        System.out.println();
    }

    public T[] getStack() {
        return Arrays.copyOf(stack, top + 1);
    }
}










