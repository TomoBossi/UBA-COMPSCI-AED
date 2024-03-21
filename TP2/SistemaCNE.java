package aed;

public class SistemaCNE {
    private int _P; // P+1
    private int _D; // D
    private String[] _nombrePartidos; // |P+1|
    private boolean _ballotage;
    private Distrito[] _distritos; // |D|
    private int[] _votosPresidenciales; // |P+1|
    private int[] _cantidadMesasAcumuladas; // |D|
    private ListaEnlazada<Integer> _mesasEscrutadas;

    // InvRep
    /*
     * _P >= 0 && _D >= 0 &&
     *  |_nombrePartidos| == _P &&
     *  |_distritos| == _D &&
     *   _ballotage <-> hayBallotage() &&
     *  |_votosPresidenciales| == _P && forall :: i: Z :: _votosPresidenciales[i] >= 0 &&
     *  |_cantidadMesasAcumuladas| == _D && estrictamenteCreciente(_cantidadMesasAcumuladas) &&
     *  sinRepetidos(_mesasEscrutadas)
     */


    public class VotosPartido {
        private int presidente;
        private int diputados;

        VotosPartido(int presidente, int diputados) {
            this.presidente = presidente;
            this.diputados = diputados;
        }

        public int votosPresidente() {
            return presidente;
        } // O(1)

        public int votosDiputados() {
            return diputados;
        } // O(1)
    }

    public SistemaCNE(String[] nombresDistritos, int[] diputadosPorDistrito, String[] nombresPartidos, int[] ultimasMesasDistritos) {
        this._P = nombresPartidos.length; // O(1) (acceso a var length de Array, ver https://docs.oracle.com/javase/specs/jls/se8/html/jls-10.html#jls-10.7)
        this._D = nombresDistritos.length; // O(1) (acceso a var length de Array)
        this._ballotage = true; // O(1) (copia de valor de tipo básico)
        this._nombrePartidos = nombresPartidos; // O(P) (copia de array de tamaño P)
        this._votosPresidenciales = new int[this._P]; // O(P) (creación e inicialización por defecto en 0 de array de tamaño P)
        this._mesasEscrutadas = new ListaEnlazada<Integer>(); // O(1) (llamado al constructor de ListaEnlazada, que es O(1))
        this._cantidadMesasAcumuladas = ultimasMesasDistritos; // O(D) (copia de array de tamaño D)

        this._distritos = new Distrito[this._D]; // O(D) (creación de array de tamaño D)
        for (int i = 0; i < this._D; i++) {
            this._distritos[i] = new Distrito(nombresDistritos[i], diputadosPorDistrito[i], this._P); // O(P)
        } // O(P) para cada una de las D iteraciones == O(D*P)
    } // O(D*P) (tomando máximo)

    public String nombrePartido(int idPartido) {
        return this._nombrePartidos[idPartido]; // O(1) (acceso a var) + O(1) (acceso a array) == O(1)
    } // O(1)

    public String nombreDistrito(int idDistrito) {
        return this._distritos[idDistrito].nombre(); // O(1) (acceso a var) + O(1) (acceso a array) + O(1) (llamado a .nombre() de Distrito, que es O(1)) == O(1)
    } // O(1)

    public int diputadosEnDisputa(int idDistrito) {
        return this._distritos[idDistrito].cantidadBancas(); // O(1) (acceso a var) + O(1) (acceso a array) + O(1) (llamado a .cantidadBancas() de Distrito, que es O(1)) == O(1)
    } // O(1)

    public String distritoDeMesa(int idMesa) {
        int idDistrito = binarySearchIndex(this._cantidadMesasAcumuladas, idMesa); // O(log(D)) (búsqueda binaria sobre array de tamaño D)
        return nombreDistrito(idDistrito); // O(1) (llamado a nombreDistrito, que es O(1))
    } // O(log(D))

    public void registrarMesa(int idMesa, VotosPartido[] actaMesa) {
        int idDistrito = binarySearchIndex(this._cantidadMesasAcumuladas, idMesa); // O(log(D)) (búsqueda binaria sobre array de tamaño D)
        Distrito distrito = this._distritos[idDistrito]; // O(1)

        for (int idPartido = 0; idPartido < this._P; idPartido++) {
            int votosPresidencialesPartido = actaMesa[idPartido].votosPresidente(); // O(1)
            int votosDiputadosPartido = actaMesa[idPartido].votosDiputados(); // O(1)
            this._votosPresidenciales[idPartido] += votosPresidencialesPartido; // O(1)
            distrito.sumarVotosDiputados(votosDiputadosPartido, idPartido); // O(1)
        } // O(1) para cada una de los P partidos == O(P)

        // Actualizar condición de ballotage
        this.actualizarBallotage(); // O(P)

        // Actualizar heap de votos de diputados
        distrito.resetearDhont(); // O(P)

        // Agregar a la mesa a la lista de mesas escrotadas
        this._mesasEscrutadas.insertar(idMesa); // O(1)
    } // Tomando máximos se obtiene O(P + log(D))

    public int votosPresidenciales(int idPartido) {
        return this._votosPresidenciales[idPartido]; // O(1) + O(1) (acceso a var + accesos a array) == O(1)
    } // O(1)

    public int votosDiputados(int idPartido, int idDistrito) {
        return this._distritos[idDistrito].votosDiputados()[idPartido]; // O(1) + O(1) + O(1) (acceso a var + accesos a array) == O(1)
    } // O(1)

    public int[] resultadosDiputados(int idDistrito){
        if (this._distritos[idDistrito].bancasDesactualizadas()) {
            this._distritos[idDistrito].asignarBancasDhont(); // O(Dd*log(P))
        }
        return this._distritos[idDistrito].bancasDiputados(); // O(1)
    } // O(Dd*log(P))

    public boolean hayBallotage(){
        return this._ballotage; // O(1) (acceso a var)
    } // O(1)

    public void actualizarBallotage() {
        int votosTotales = 0; // O(1)
        Fraccion[] votos = new Fraccion[this._P - 1]; // O(P)
        for (int idPartido = 0; idPartido < this._P; idPartido++) {
            if (idPartido < this._P - 1) {
                votos[idPartido] = new Fraccion(this._votosPresidenciales[idPartido], 1, idPartido); // O(1)
            }
            votosTotales += this._votosPresidenciales[idPartido]; // O(1)
        } // O(P)
        MaxHeap<Fraccion> rankingPresidencial = new MaxHeap<Fraccion>(votos, this._P - 1); // O(P)
        Fraccion primero = rankingPresidencial.desencolar(); // O(log(P))
        Fraccion segundo = rankingPresidencial.desencolar(); // O(log(P))
        
        if (votosTotales > 0) {
            float porcentajePrimero = 100*primero.numerador()/votosTotales; // O(1)
            float porcentajeSegundo = 100*segundo.numerador()/votosTotales; // O(1)
            boolean supera40 = porcentajePrimero >= 40; // O(1)
            boolean supera45 = porcentajePrimero >= 45; // O(1)
            boolean supera40ConDifDe10 = supera40 && (porcentajePrimero - porcentajeSegundo) > 10; // O(1)
            this._ballotage = !(supera45 || supera40ConDifDe10); // O(1)
        }
    } // Tomando máximos se obtiene O(P)

    private static int binarySearchIndex(int[] s, int e) { // búsqueda binaria, tomado de la téorica
        if (s.length == 0 || e < s[0]) { // O(1)
            return 0; // O(1)
        } else {
            int low = 0; // O(1)
            int high = s.length - 1; // O(1)
            while(low+1 < high && s[low] != e) { // O(1)
                int mid = (low + high) / 2; // O(1)
                if(s[mid] <= e) { // O(1)
                    low = mid; // O(1)
                } else {
                    high = mid; // O(1)
                }
            } // O(log(n))
            return low + 1; // O(1)
        }
    } // O(log(n))
}
