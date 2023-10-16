package aed;

import java.util.*;

public class ListaEnlazada<T> implements Secuencia<T> {
    private Nodo primer;
    private Nodo ultimo;
    private int size;

    private class Nodo {
        T valor;
        Nodo sig;
        Nodo pre;

        Nodo(T v) {
            valor = v;
            sig = null;
            pre = null;
        }
    }

    public ListaEnlazada() {
        primer = null;
        ultimo = null;
        size = 0;
    }

    public int longitud() {
        return size;
    }

    public void agregarAdelante(T elem) {
        Nodo nuevo = new Nodo(elem);
        if (size == 0) {
            primer = nuevo;
            ultimo = nuevo;
        } else {
            nuevo.sig = primer;
            primer.pre = nuevo;
            primer = nuevo;
            // nuevo.sig.pre = nuevo; // alternativa a primer.pre = nuevo
        }
        size++;
    }

    public void agregarAtras(T elem) {
        Nodo nuevo = new Nodo(elem);
        if (size == 0) {
            primer = nuevo;
            ultimo = nuevo;
        } else {
            nuevo.pre = ultimo;
            ultimo.sig = nuevo;
            ultimo = nuevo;
            // nuevo.pre.sig = nuevo; // alternativa a ultimo.sig = nuevo
        }
        size++;
    }

    public T obtener(int i) {
        Nodo actual = primer;
        for (int j = 0; j != i; j++) {
            actual = actual.sig;
        }
        return actual.valor;
    }

    public void eliminar(int i) {
        if (size == 1) {
            primer = null;
            ultimo = null;
        } else {
            if (i == 0) {
                primer = primer.sig;
                primer.pre = null;
            } else if (i == size - 1) {
                ultimo = ultimo.pre;
                ultimo.sig = null;
            } else {
                Nodo actual = primer;
                for (int j = 0; j != i; j++) {
                    actual = actual.sig;
                }
                actual.pre.sig = actual.sig;
                actual.sig.pre = actual.pre;
            }
        }
        size--;
    }

    public void modificarPosicion(int i, T elem) {
        Nodo actual = primer;
        for (int j = 0; j != i; j++) {
            actual = actual.sig;
        }
        actual.valor = elem;
    }

    public ListaEnlazada<T> copiar() {
        ListaEnlazada<T> c = new ListaEnlazada<>();
        Nodo actual = primer;
        while (actual != null) {
            c.agregarAtras(actual.valor);
            actual = actual.sig;
        }
        return c;
    }

    public ListaEnlazada(ListaEnlazada<T> lista) {
        ListaEnlazada<T> c = lista.copiar();
        primer = c.primer;
        ultimo = c.ultimo;
        size = c.size;
    }
    
    @Override
    public String toString() {
        String res = "[";
        Nodo actual = primer;
        while (actual != null) {
            res += actual.valor.toString();
            if (actual.sig != null) {
                res += ", ";
            }
            actual = actual.sig;
        }
        res += "]";
        return res;
    }

    private class ListaIterador implements Iterador<T> {
    	int dedo = 0;
        Nodo actual = primer;

        public boolean haySiguiente() {
	        return dedo < size;
        }
        
        public boolean hayAnterior() {
	        return dedo > 0;
        }

        public T siguiente() {
            T res = actual.valor;
            dedo++;
            actual = actual.sig;
            if (actual == null) {
                actual = new Nodo(null);
                actual.pre = ultimo;
            }
            return res;
        }

        public T anterior() {
            actual = actual.pre;
            T res = actual.valor;
            dedo--;
            if (actual == null) {
                actual = new Nodo(null);
                actual.sig = primer;
            }
            return res;
        }
    }

    public Iterador<T> iterador() {
	    return new ListaIterador();
    }
}
