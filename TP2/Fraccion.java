package aed;

// InvRep
// _denominador > 0
// _id >= 0

public class Fraccion implements Comparable<Fraccion> {
    private int _numerador;
    private int _denominador;
    private int _id;

    Fraccion(int numerador, int denominador, int id) {
        this._numerador = numerador; // O(1)
        this._denominador = denominador; // O(1)
        this._numerador = this.signo()*abs(numerador); //O(1)
        this._denominador = abs(denominador);// O(1)
        this._id = id; // O(1)
    } // O(1)

    public int numerador() {
        return this._numerador; // O(1)
    } // O(1)

    public int denominador() {
        return this._denominador; // O(1)
    } // O(1)

    public int id() {
        return this._id; // O(1)
    } // O(1)

    public int compareTo(Fraccion that) {
        return this._numerador * that._denominador - this._denominador * that._numerador; // O(1)
    } // O(1)

    public String toString() {
        return Integer.toString(this._numerador) + "/" + Integer.toString(this._denominador); // O(1)
    } // O(1)

    private static int abs(int n) {
        int res = n; // O(1)
        if (n < 0) { // O(1)
            res = -n; // O(1)
        }
        return res;
    } // O(1)

    private int signo() {
        if (this._numerador == 0) { // O(1)
            return 1;
        }
        return this._numerador/abs(this._numerador) * this._denominador/abs(this._denominador); // O(1)
    } // O(1)
}
