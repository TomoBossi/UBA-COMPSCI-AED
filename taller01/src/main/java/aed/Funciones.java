package aed;

class Funciones {
    int cuadrado(int x) {
        int res = x * x;
        return res;
    }

    double distancia(double x, double y) {
        double res = Math.sqrt(x*x + y*y);
        return res;
    }

    boolean esPar(int n) {
        return n%2 == 0;
    }

    boolean esBisiesto(int n) {
        return (n%4 == 0 && !(n%100 == 0)) || n%400 == 0;
    }

    int factorialIterativo(int n) {
        int res = 1;
        for (int m = 1; m <= n; m++) {
            res *= m;
        }
        return res;
    }

    int factorialRecursivo(int n) {
        int res;
        if (n < 2) {
            res = 1;
        } else {
            res = n * factorialRecursivo(n - 1);
        }
        return res;
    }

    int fibonacciIterativo(int n) {
        int f0 = 0;
        int f1 = 1;
        int temp;
        for (int i = 0; i < n; i++) {
            temp = f0;
            f0 = f1;
            f1 = temp + f1;
        }
        return f0;
    }

    int fibonacciRecursivo(int n) {
        int res;
        if (n < 2) {
            res = n;
        } else {
            res = fibonacciRecursivo(n - 1) + fibonacciRecursivo(n - 2);
        }
        return res;
    }

    boolean esPrimo(int n) {
        boolean res = true;
        for (int div = 2; div < n; div++) {
            if (n%div == 0) {
                res = false;
            }
        }
        return n > 1 && res;
    }

    int sumatoria(int[] numeros) {
        int suma = 0;
        for (int elem : numeros) {
            suma += elem;
        }
        return suma;
    }

    int busqueda(int[] numeros, int buscado) {
        int res = 0;
        boolean found = false;
        while (res < numeros.length && !found) {
            found = numeros[res] == buscado;
            if (!found) {
                res++;
            }
        }
        return res;
    }

    boolean tienePrimo(int[] numeros) {
        boolean res = false;
        int i = 0;
        while (i < numeros.length && !res) {
            res = esPrimo(numeros[i]);
            i++;
        }
        return res;
    }

    boolean todosPares(int[] numeros) {
        boolean res = true;
        int i = 0;
        while (i < numeros.length && res) {
            res = esPar(numeros[i]);
            i++;
        }
        return res;
    }

    boolean esPrefijo(String s1, String s2) {
        boolean res = false;
        if (s2.length() >= s1.length()) {
            res = true;
            int i = 0;
            while (i < s1.length() && res) {
                res = s1.charAt(i) == s2.charAt(i);
                i++;
            }
        }
        return res;
    }

    String slice(String s, int start, int end) {
        String res = "";
        for (int i = start; (i < s.length() && end < start) || i < end; i++) {
            res += s.charAt(i);
        }
        return res;
    }

    String invertir(String s) {
        String s_inv = "";
        for (int i = s.length() - 1; i >= 0; i--) {
            s_inv += s.charAt(i);
        }
        return s_inv;
    }

    boolean esSufijo(String s1, String s2) {
        return esPrefijo(invertir(s1), invertir(s2));
    }
}
