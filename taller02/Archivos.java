package aed;

import java.util.Scanner;
import java.io.PrintStream;

class Archivos {
    float[] leerVector(Scanner entrada, int largo) {
        float[] res = new float[largo];
        for (int i = 0; i < largo; i++) {
            res[i] = entrada.nextFloat();
        }
        return res;
    }

    float[][] leerMatriz(Scanner entrada, int filas, int columnas) {
        float[][] res = new float[filas][columnas];
        for (int i = 0; i < filas; i++) {
            res[i] = leerVector(entrada, columnas);
        }
        return res;
    }   

    void imprimirPiramide(PrintStream salida, int alto) {
        for (int i = 0; i < alto; i++) {
            String fila = "";
            for (int j = 0; j < 2*alto - 1; j++) {
                if (j > alto - i - 2 && j < alto + i) {
                    fila += "*";
                } else {
                    fila += " ";
                }
            }
            salida.println(fila);
        }
    }
}
