package spano.unica.it.plain.School;

/**
 * Created by giovy on 25/07/2017.
 */

public class Room {


    private double latoZero;
    private double latoUno;

    private double area = 0.0;

    public double getLatoZero() {
        return latoZero;
    }

    public void setLatoZero(double latoZero) {
        this.latoZero = latoZero;
    }

    public double getLatoUno() {
        return latoUno;
    }

    public void setLatoUno(double latoUno) {
        this.latoUno = latoUno;
    }

    public double getArea() {
        return this.latoUno*this.latoZero;
    }

}
