package aed;

public class Fecha {
    private int _dia;
    private int _mes;

    public Fecha(int dia, int mes) {
        _dia = dia;
        _mes = mes;
    }

    public Fecha(Fecha fecha) { // Asumo que no hay aliasing por ser ints
        _dia = fecha._dia;
        _mes = fecha._mes;
    }

    public Integer dia() {
        return _dia;
    }

    public Integer mes() {
        return _mes;
    }

    @Override
    public String toString() {
        Integer d = _dia;
        Integer m = _mes;
        return d.toString() + "/" + m.toString();
    }

    @Override
    public boolean equals(Object otra) {
        boolean oen = (otra == null);
        boolean cd = otra.getClass() != this.getClass();
        if (oen || cd) {
            return false;
        }
        Fecha otraFecha = (Fecha) otra;
        return _dia == otraFecha._dia && _mes == otraFecha._mes;
    }

    public void incrementarDia() {
        _dia += 1;
        if (_dia > diasEnMes(_mes)) {
            _dia = 1;
            _mes += 1;
            _mes %= 12;
        }
    }

    private int diasEnMes(int mes) {
        int dias[] = {
                // ene, feb, mar, abr, may, jun
                31, 28, 31, 30, 31, 30,
                // jul, ago, sep, oct, nov, dic
                31, 31, 30, 31, 30, 31
        };
        return dias[mes - 1];
    }

}