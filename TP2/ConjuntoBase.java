package aed;

interface ConjuntoBase<T> {
    /**
     * Devuelve la cantidad de elementos del conjunto.
     * 
     */
    public int cardinal();

    /**
     * Agrega un elemento al conjunto
     * 
     */
    public void insertar(T elem);

    /**
     * Devuelve verdadero si el elemento pertenece al conjunto
     * 
     */
    public boolean pertenece(T elem);

    /**
     * Elimina el elemento del donjunto
     * 
     */
    public void eliminar(T elem);

    /**
     * Imprime el conjunto
     *
     */
    public String toString();
}