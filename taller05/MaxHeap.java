package aed;

public class MaxHeap<T extends Comparable<T>> {
    private T[] _array;
    private int _n;

    public MaxHeap(T[] array, int n) {
        this._array = array;
        this._n = n;
        this.heapify();
    }

    public T[] cola() {
        return this._array;
    }

    public int longitud() {
        return this._n;
    }

    public T proximo() {
        return this._array[0];
    }

    public boolean pertenece(T elem) {
        int i = 0;
        while (i < this._n && this._array[i] != elem) {
            i++;
        }
        return i < this._n;
    }

    private void swap(int i, int j) {
        T temp = this._array[i];
        this._array[i] = this._array[j];
        this._array[j] = temp;
    }

    private static int hijoIzq(int i) {
        return 2*i + 1;
    }

    private static int hijoDer(int i) {
        return 2*i + 2;
    }

    private T prioridad(int i) {
        return this._array[i];
    }

    private boolean esHoja(int i) {
        return hijoIzq(i) >= this._n;
    }

    private void bajar(int i) {
        for (int hi = hijoIzq(i), hd = hijoDer(i);
            !(this.esHoja(i)) && (
            (hi < this._n && this.prioridad(i).compareTo(prioridad(hi)) < 0) ||
            (hd < this._n && this.prioridad(i).compareTo(prioridad(hd)) < 0));
            hi = hijoIzq(i), hd = hijoDer(i)) {
            if ((hi < this._n && hd < this._n && prioridad(hi).compareTo(prioridad(hd)) > 0) ||
                (hi < this._n && hd >= this._n)) {
                this.swap(i, hi);
                i = hi;
            } else {
                this.swap(i, hd);
                i = hd;
            }
        }
    }

    public T desencolar() {
        T res = this._array[0];
        int i = this._n - 1;
        this.swap(i, 0);
        this._n--;
        this.bajar(0);
        return res;
    }

    public void heapify() {
        for (int i = this._n - 1; i >= 0; i--) {
            this.bajar(i);
        }
    }
}
