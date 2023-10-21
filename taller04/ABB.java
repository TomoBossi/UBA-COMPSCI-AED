package aed;

import java.util.*;

// Todos los tipos de datos "Comparables" tienen el método compareTo()
// elem1.compareTo(elem2) devuelve un entero. Si es mayor a 0, entonces elem1 > elem2
public class ABB<T extends Comparable<T>> implements Conjunto<T> {
    private Nodo _raiz;
    private int _cardinal;

    private class Nodo {
        T valor;
        Nodo i;
        Nodo d;
        Nodo p;

        Nodo(T v) {
            valor = v;
            i = null;
            d = null;
            p = null;
        }
    }

    public ABB() {
        _raiz = null;
        _cardinal = 0;
    }

    public int cardinal() {
        return _cardinal;
    }

    public T minimo(){
        T res = null;
        if (_raiz != null) {
            Nodo actual = _raiz;
            while (actual != null && actual.i != null) {
                actual = actual.i;
            }
            res = actual.valor;
        }
        return res;
    }

    public T maximo(){
        T res = null;
        if (_raiz != null) {
            Nodo actual = _raiz;
            while (actual != null && actual.d != null) {
                actual = actual.d;
            }
            res = actual.valor;
        }
        return res;
    }

    private Nodo _buscar_nodo(T elem) {
        Nodo res = null;
        if (elem != null) {
            Nodo actual = _raiz;
            boolean puede_estar_izq = actual != null && actual.i != null && elem.compareTo(actual.valor) < 0;
            boolean puede_estar_der = actual != null && actual.d != null && elem.compareTo(actual.valor) > 0;
            while(actual != null && elem.compareTo(actual.valor) != 0 && (puede_estar_izq || puede_estar_der)) {
                if (puede_estar_izq) {
                    actual = actual.i;
                } else if (puede_estar_der) {
                    actual = actual.d;
                }
                puede_estar_izq = actual.i != null && elem.compareTo(actual.valor) < 0;
                puede_estar_der = actual.d != null && elem.compareTo(actual.valor) > 0;
            }
            res = actual;
        }
        return res;
    }

    public void insertar(T elem){
        Nodo nuevo = new Nodo(elem);
        if (_raiz == null) {
            _raiz = nuevo;
            _cardinal++;
        } else {
            Nodo buscado = _buscar_nodo(elem);
            if (elem.compareTo(buscado.valor) != 0) {
                nuevo.p = buscado;
                if (elem.compareTo(buscado.valor) < 0) {
                    buscado.i = nuevo;
                } else {
                    buscado.d = nuevo;
                }
                _cardinal++;
            }
        }
    }

    public boolean pertenece(T elem){
        Nodo actual = _raiz;
        boolean puede_estar_izq = actual != null && actual.i != null && elem.compareTo(actual.valor) < 0;
        boolean puede_estar_der = actual != null && actual.d != null && elem.compareTo(actual.valor) > 0;
        while(actual != null && elem.compareTo(actual.valor) != 0 && (puede_estar_izq || puede_estar_der)) {
            if (puede_estar_izq) {
                actual = actual.i;
            } else if (puede_estar_der) {
                actual = actual.d;
            }
            puede_estar_izq = actual.i != null && elem.compareTo(actual.valor) < 0;
            puede_estar_der = actual.d != null && elem.compareTo(actual.valor) > 0;
        }
        return (actual != null && elem.compareTo(actual.valor) == 0);
    }

    public void eliminar(T elem){
        Nodo buscado = _buscar_nodo(elem);
        // Caso -1: el elemento no está
        if (buscado != null && elem.compareTo(buscado.valor) == 0) {

            // Caso 0: el elemento es único en el conjunto
            if (buscado == _raiz && _cardinal == 1) {
                _raiz = null;
            }

            // Caso 1: El elemento es hoja, sin hijos
            else if (buscado.i == null && buscado.d == null) {
                if (elem.compareTo(buscado.p.valor) < 0) {
                    buscado.p.i = null;
                } else {
                    buscado.p.d = null;
                }
                buscado.p = null;
            }

            // Caso 2: El nodo asociado al elemento tiene un solo hijo
            else if ((buscado.i != null && buscado.d == null) || (buscado.i == null && buscado.d != null)) {
                if (buscado.p != null) {
                    if (buscado.i != null) {
                        if (elem.compareTo(buscado.p.valor) < 0) {
                            buscado.p.i = buscado.i;
                            buscado.i.p = buscado.p;
                        } else {
                            buscado.p.d = buscado.i;
                            buscado.i.p = buscado.p;
                        }
                    } else {
                        if (elem.compareTo(buscado.p.valor) < 0) {
                            buscado.p.i = buscado.d;
                            buscado.d.p = buscado.p;
                        } else {
                            buscado.p.d = buscado.d;
                            buscado.d.p = buscado.p;
                        }
                    }
                    buscado = null;
                } else {
                    if (buscado.i != null) {
                        _raiz = buscado.i;
                        _raiz.p = null;
                    } else {
                        _raiz = buscado.d;
                        _raiz.p = null;
                    }
                }
            }

            // Caso 3: El nodo asociado al elemento tiene dos hijos
            else {
                Nodo maximo_menor = buscado.i;
                while (maximo_menor.d != null) {
                    maximo_menor = maximo_menor.d;
                }
                eliminar(maximo_menor.valor);
                buscado.valor = maximo_menor.valor;
                _cardinal++; // para compensar el eliminado auxiliar
            }

            _cardinal--;
        }
    }

    private T _sucesor(T elem) {
        Vector<T> posibles_sucesores = new Vector<T>();
        Nodo actual = _raiz;
        boolean puede_estar_izq = actual != null && actual.i != null && elem.compareTo(actual.valor) < 0;
        boolean puede_estar_der = actual != null && actual.d != null && elem.compareTo(actual.valor) > 0;
        while(actual != null && elem.compareTo(actual.valor) != 0 && (puede_estar_izq || puede_estar_der)) {
            if (elem.compareTo(actual.valor) < 0) {
                posibles_sucesores.add(actual.valor);
            }
            if (puede_estar_izq) {
                actual = actual.i;
            } else if (puede_estar_der) {
                actual = actual.d;
            }
            puede_estar_izq = actual.i != null && elem.compareTo(actual.valor) < 0;
            puede_estar_der = actual.d != null && elem.compareTo(actual.valor) > 0;
        }
        if (actual.d == null) {
            if (posibles_sucesores.size() > 0) {
                return posibles_sucesores.lastElement();
            } else {
                return null;
            }
        } else {
            Nodo minimo_mayor = actual.d;
            while (minimo_mayor.i != null) {
                minimo_mayor = minimo_mayor.i;
            }
            return minimo_mayor.valor;
        }
    }

    public String toString(){ // Quiero algo de la forma "{4,5,12,15,19,21,22,24,25}"
        T valor_maximo = maximo();
        T valor_minimo = minimo();
        T valor_actual = _sucesor(valor_minimo);
        String res = "{" + valor_minimo.toString();
        while (valor_actual.compareTo(valor_maximo) < 0) {
            res += "," + valor_actual.toString();
            valor_actual = _sucesor(valor_actual);
        }
        res += "," + valor_maximo.toString() + "}";
        return res;
    }

    private class ABB_Iterador implements Iterador<T> {
        private Nodo _actual = _buscar_nodo(minimo());

        public boolean haySiguiente() {    
            return _actual != null;
        }
    
        public T siguiente() {
            T res = _actual.valor;
            T valor_siguiente = _sucesor(_actual.valor);
            _actual = _buscar_nodo(valor_siguiente);
            return res;
        }
    }

    public Iterador<T> iterador() {
        return new ABB_Iterador();
    }
}
