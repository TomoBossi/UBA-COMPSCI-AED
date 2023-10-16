package aed;

class VectorDeInts implements SecuenciaDeInts {
    private static final int CAPACIDAD_INICIAL = 100;
    private int _cantidad_actual;
    private int[] _elementos;

    public VectorDeInts() {
        _cantidad_actual = 0;
        _elementos = new int[CAPACIDAD_INICIAL];
    }

    public VectorDeInts(VectorDeInts vector) {
        VectorDeInts vector_copiado = vector.copiar();
        _cantidad_actual = vector_copiado._cantidad_actual;
        _elementos = vector_copiado._elementos;
    }

    public int longitud() {
        return _cantidad_actual;
    }

    public void agregarAtras(int i) {
        _elementos[_cantidad_actual] = i;
        _cantidad_actual += 1;
    }

    public int obtener(int i) {
        return _elementos[i];
    }

    public void quitarAtras() {
        _cantidad_actual -= 1;
    }

    public void modificarPosicion(int indice, int valor) {
        _elementos[indice] = valor;
    }

    public VectorDeInts copiar() {
        VectorDeInts copia = new VectorDeInts();
        for (int i = 0; i < _cantidad_actual; i++) {
            copia.agregarAtras(_elementos[i]);
        }
        return copia;
    }
}
