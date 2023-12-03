package bubbles.springapibackend.api.controller.event.file;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListConfig<T> {
    private T[] array;

    private int numberElement;

    public ListConfig(int size) {
        array = (T[]) new Object[size];
        numberElement = 0;
    }

    public void add(T element) {
        if (numberElement < array.length) {
            array[numberElement] = element;
            numberElement++;
        } else {
            throw new IllegalStateException("Lista Cheia!");
        }
    }

    public int search(T searchElement) {
        for (int i = 0; i < numberElement; i++) {
            if (searchElement.equals(array[i])) {
                return i;
            }
        }

        return -1;
    }

    public boolean removeByIndice(int indice) {
        if (indice < 0 || indice >= numberElement) {
            return false;
        }

        for (int i = indice; i < numberElement - 1; i++) {
            array[i] = array[i + 1];
        }

        numberElement--;

        return true;
    }

    public boolean removeElement(T removedElement) {
        int indice = search(removedElement);

        if (indice != -1) {
            return removeByIndice(indice);
        }

        return false;
    }

    public int getSize() {
        return numberElement;
    }

    public T getElement(int indice) {
        if (indice < 0 || indice >= numberElement) {
            return null;
        }

        return array[indice];
    }

    public void clear() {
        for(int i = 0; i < numberElement; i++){
            array[i] = null;
        }
        numberElement = 0;
    }

    public void exibe() {
        for(int i = 0; i < numberElement; i++){
            System.out.println(array[i]);
        }
    }
}
