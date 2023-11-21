package aed;

// import java.lang.ref.Reference;

public class InternetToolkit {
    public InternetToolkit() {
    }

    public Fragment[] tcpReorder(Fragment[] fragments) {
        // Dados conocimientos superficiales del dominio del problema y del enunciado, 
        // se pueden hacer ciertos supuestos:
        // - Por conocimiento superficial del dominio, si un dato
        //   está desordenado entonces le corresponde una posición anterior;
        //   es decir, un dato puede llegar más tarde pero no más temprano
        //   de lo que le corresponde. Por ejemplo, para los paquetes ordenados
        //   [1,2,3,4,5], podría recibirse [1,2,4,5,3] pero no [1,2,5,3,4].
        // - Por enunciado, para un dado input, un dato incorrectamente ordenado
        //   está a lo sumo a k posiciones de la que le corresponde con k cte.
        // - El valor de k puede variar entre inputs, pero siempre existe.
        // - Por conocimiento superficial del dominio, k no depende de n;
        //   depende del protocolo y de la calidad de la conexión, pero en
        //   principio no tiene relación directa con la cantidad de fragmentos
        //   por mensaje. Podría ocurrir que a mayor largo de mensaje (> n)
        //   sea esperable un mayor k, pero sólo por razones probabilisticas.

        // Con estas consideraciones, puede usarse insertion sort para que en el
        // caso promedio la complejidad sea O(n + m_desordenados*k_promedio) == O(n).
        insertion_sort_fragment(fragments); // O(n**2)
        return fragments; // O(1)
    } // O(n**2)

    public Router[] kTopRouters(Router[] routers, int k, int umbral) {
        // Heapify + k desencolados
        // Requiere MaxHeap
        Router router_umbral = new Router(-1, umbral);
        Router[] res = new Router[k]; // O(k)
        MaxHeap<Router> heap = new MaxHeap<Router>(routers, routers.length); // O(n)
        for (int i = 0; i < k && heap.longitud() > 0; i++) { // O(1)
            Router max = heap.desencolar(); // O(log(n))
            if (max.compareTo(router_umbral) > 0) { // O(1)
                res[i] = max; // O(1)
            } else {
                res[i] = null;
            }
        } // O(k*log(n))
        return res; // O(1)
    } // O(n + k*log(n))

    public IPv4Address[] sortIPv4(String[] ipv4) {
        // Radix sort; 4 bucket sort sucesivos en arrays de 0 a 255, 
        // comenzando desde el octeto menos (3) hacia el más (0) significativo.
        // Requeriría ListaEnlazada.

        // Por dificultades en la implementación dadas por la falta de punteros,
        // insertion sort sucesivos... Seguro se podría hacer radix sort usando
        // referencias, pero en este preciso momento de necesidad de resolución 
        // del TP no sé usarlas. 
        
        // Conversion de String[] a IPv4Address[]
        IPv4Address[] res = new IPv4Address[ipv4.length]; // O(n)
        for (int idx = 0; idx < ipv4.length; idx++) { // O(1)
            IPv4Address address = new IPv4Address(ipv4[idx]); // O(1)
            res[idx] = address; // O(1)
        } // O(n)

        // Ordenamiento
        // ListaEnlazada<Integer>[] buckets;
        // for (int octeto_idx = 3; octeto_idx >= 0; octeto_idx--) { // O(1)                          
        //     buckets = new ListaEnlazada<Integer>[256]; // O(1) // requiere usar referencias
        // }

        // Ordenamiento
        for (int octeto_idx = 3; octeto_idx >= 0; octeto_idx--) { // O(1)
            insertion_sort_address(res, octeto_idx); // O(n**2)
        } // O(n**2)

        return res;
    } // O(n**2)

    public static void insertion_sort_fragment(Fragment[] array) {
        int n = array.length;
        for (int i = 0; i < n; i++) {
            Fragment temp = array[i];
            int j = i - 1;
            while (j >= 0 && array[j].compareTo(temp) > 0) {
                array[j+1] = array[j];
                j--;
            }
            array[j+1] = temp;
        }
    }

    public static void insertion_sort_address(IPv4Address[] array, int key) {
        int n = array.length;
        for (int i = 0; i < n; i++) {
            IPv4Address temp = array[i];
            int j = i - 1;
            while (j >= 0 && array[j].getOctet(key) > temp.getOctet(key)) {
                array[j+1] = array[j];
                j--;
            }
            array[j+1] = temp;
        }
    }

    // public static Integer[] list2array(ListaEnlazada<Integer> lista) {
    //     Integer[] res = new Integer[lista.longitud()];
    //     Iterador<Integer> it = lista.iterador();
    //     int idx = 0;
    //     while (it.haySiguiente()) {
    //         Integer valor = it.siguiente();
    //         res[idx] = valor;
    //         idx++;
    //     }
    //     return res;
    // }
}
