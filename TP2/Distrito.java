package aed;

public class Distrito {
    private int _P; // P+1
    private int _cantidadBancas; // Dd
    private String _nombre;
    private int[] _votosDiputados; // |P+1|
    private int _votosDiputadosTotales;
    private int[] _bancasDiputados; // |P|
    private boolean _bancasDesactualizadas;
    private MaxHeap<Fraccion> _votosDhont;

    // InvRep
    /*
     * _P >= 0 && _cantidadBancas >= 0 &&
     *  |_votosDiputados| == _P && forall :: i: Z :: _votosDiputados[i] >= 0 &&
     *  _votosDiputadosTotales == sum :: i: Z :: 0 <= i < _P :: _votosDiputados[i] &&
     *  |_bancasDiputados| = _P - 1 && forall :: i: Z :: _bancasDiputados[i] >= 0 &&
     *  
     * !_bancasDesactualizadas ->
     *    _bancasDiputados almacena las bancas asignadas a cada partido según el método D'Hondt y
     *    _votosDhondt almacena los cocientes de la matriz D'Hondt procesada en un heap
     * _bancasDesactualizadas ->
     *    forall :: i: Z :: _bancasDiputados[i] == 0 &&
     *    _votosDhondt almacena los votos de los partidos que superan el umbral con denominador 1
     */

    public Distrito(String nombre, int cantidadBancas, int cantidadPartidos) {
        this._P = cantidadPartidos; // O(1)
        this._nombre = nombre; // O(1) (copia de valor de tipo básico)
        this._cantidadBancas = cantidadBancas; // O(1) (copia de valor de tipo básico)
        this._votosDiputados = new int[cantidadPartidos]; // O(P) (creación e inicialización por defecto en 0 de array de tamaño P)
        this._bancasDiputados = new int[cantidadPartidos - 1]; // O(P)
        this._bancasDesactualizadas = true; // O(1)
        this._votosDiputadosTotales = 0; // O(1)

        Fraccion[] fracciones = new Fraccion[cantidadPartidos - 1]; // O(P)
        for (int idPartido = 0; idPartido < cantidadPartidos - 1; idPartido++) {
            fracciones[idPartido] = new Fraccion(0,1,idPartido); // O(1)
        } // O(P)
        this._votosDhont = new MaxHeap<Fraccion>(fracciones, cantidadPartidos - 1); // O(P)
    } // O(P)

    public String nombre() {
        return this._nombre; // O(1) (acceso a var)
    } // O(1)

    public int cantidadBancas() {
        return this._cantidadBancas; // O(1) (acceso a var)
    } // O(1)

    public int[] votosDiputados() {
        return this._votosDiputados; // O(1) (acceso a var)
    } // O(1)

    public int[] bancasDiputados() {
        return this._bancasDiputados; // O(1) (acceso a var)
    } // O(1)

    public boolean bancasDesactualizadas() {
        return this._bancasDesactualizadas; // O(1) (acceso a var)
    } // O(1)

    public MaxHeap<Fraccion> votosDhont() {
        return this._votosDhont; // O(1) (acceso a var)
    } // O(1)

    public void sumarVotosDiputados(int votos, int idPartido) {
        this._votosDiputados[idPartido] += votos; // O(1)
        this._votosDiputadosTotales += votos; // O(1)
    } // O(1)

    public void resetearDhont() {
        boolean[] partidosSupraUmbral = new boolean[this._P - 1]; // O(P)
        if (this._votosDiputadosTotales > 0) {
            for (int idPartido = 0; idPartido < this._P - 1; idPartido++) {
                partidosSupraUmbral[idPartido] = 100*this._votosDiputados[idPartido]/this._votosDiputadosTotales >= 3; // O(1)
            } // O(P)
        }

        Fraccion[] fracciones = new Fraccion[this._P - 1]; // O(P)
        for (int idPartido = 0; idPartido < this._P - 1; idPartido++) {
            int cantidadVotos = 0; // O(1)
            if (partidosSupraUmbral[idPartido]) { // O(1)
                cantidadVotos = this._votosDiputados[idPartido]; // O(1)
            }
            fracciones[idPartido] = new Fraccion(cantidadVotos, 1, idPartido); // O(1)
        } // O(P)
        this._votosDhont = new MaxHeap<Fraccion>(fracciones, this._P - 1); // O(P)
        this._bancasDiputados = new int[this._P - 1]; // O(P)
        this._bancasDesactualizadas = true; // O(1)
    } // O(P)

    public void asignarBancasDhont() {
        for (int i = 0; i < this._cantidadBancas; i++) {
            Fraccion max = this._votosDhont.desencolar(); // O(log(P))
            int idPartidoMax = max.id(); // O(1)
            this._bancasDiputados[idPartidoMax]++; // O(1)
            this._votosDhont.encolar(new Fraccion(max.numerador(), max.denominador()+1, idPartidoMax)); // O(log(P))
        } // O(Dd*log(P))
        this._bancasDesactualizadas = false; // O(1)
    } // O(Dd*log(P))
}
