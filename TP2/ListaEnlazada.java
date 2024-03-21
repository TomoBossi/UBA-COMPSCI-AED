package aed;

public class ListaEnlazada<T> implements ConjuntoBase<T> {
    private Nodo nodoInicial, nodoFinal;
    private int longitud;

    // InvRep
    /* 1. Si la lista está vacía, entonces primero es null y size vale 0.
       2. Si la lista no está vacía, entonces primero apunta al primer
          nodo de la lista y size es la cantidad de nodos.
       3. Todos los nodos de la lista apuntan al siguiente, excepto el último.
       4. El último nodo apunta a null. */ 

    private class Nodo {
        private T valor;
        private Nodo nodoSiguiente;

        public Nodo(T valor) {
            this.valor = valor; // O(1)
            this.nodoSiguiente = null; // O(1)
        } // O(1)

        public T obtenerValor() {
            return this.valor;
        }

        public void definirSiguiente(Nodo siguiente) {
            this.nodoSiguiente = siguiente; // O(1)
        } // O(1)

        public boolean haySiguiente() {
            return this.nodoSiguiente != null;
        }

        public Nodo nodoSiguiente() {
            return this.nodoSiguiente;
        }
    }

    public ListaEnlazada() {
        this.nodoInicial = null; // O(1) 
        this.nodoFinal = null; // O(1)
        this.longitud = 0; // O(1)
    } // O(1)

    public int cardinal() {
        return this.longitud;
    }

    public void insertar(T elem) {
        Nodo nuevoNodo = new Nodo(elem); // O(1)
        if (this.nodoFinal == null)  // O(1)
            this.nodoInicial = this.nodoFinal = nuevoNodo; // O(1)
        else {
            this.nodoFinal.definirSiguiente(nuevoNodo); // O(1)
            this.nodoFinal = nuevoNodo; // O(1)
        }
        this.longitud++; // O(1)
    } // O(1)

    public void eliminar(T elem) {
        Nodo anterior = null;
        Nodo actual = this.nodoInicial;

        while (actual.obtenerValor() != elem && actual.haySiguiente()) {
            anterior = actual;
            actual = actual.nodoSiguiente();
        }
        
        if (actual.obtenerValor() != elem) return;

        if (anterior == null)
            this.nodoInicial = actual.nodoSiguiente();
        else
            anterior.definirSiguiente(actual.nodoSiguiente());

        this.longitud--;
    }

    public boolean pertenece(T elem) {
        Nodo actual = this.nodoInicial;

        while (actual != null && actual.obtenerValor() != elem && actual.haySiguiente()) 
            actual = actual.nodoSiguiente();
        
        return actual.obtenerValor() == elem;
    }
    
    @Override
    public String toString() {
        String res = "[";
        Nodo actual = this.nodoInicial;
        for (int i = 0; i < this.longitud; i++) {
            res += actual.obtenerValor().toString();
            if (i != this.longitud - 1) { 
                res += ", ";
            }
            actual = actual.nodoSiguiente();
        }
        res += "]";
        return res;
    }

    private class ListaIterador implements IteradorBase<T> {
    	private Nodo actual;

        public ListaIterador() {
            Nodo comienzo = new Nodo(null);
            comienzo.definirSiguiente(nodoInicial);
            this.actual = comienzo;
        }

        public boolean haySiguiente() {
	        return this.actual.haySiguiente();
        }

        public T siguiente() {
            this.actual = this.actual.nodoSiguiente();
            return this.actual.obtenerValor();
        }
    }

    public IteradorBase<T> iterador() {
	    return new ListaIterador();
    }
}
