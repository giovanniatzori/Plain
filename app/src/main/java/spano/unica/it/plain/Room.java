package spano.unica.it.plain;

/**
 * Created by giovy on 25/07/2017.
 */

public class Room {


    private double latoZero =1.0;
    private double latoUno=1.0;

    private double area = latoUno*latoZero;

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
        return area;
    }

}
