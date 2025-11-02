package uet.oop.UA.logic;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class LinearEquation {
    double A;
    double B;

    public void calculateCoefficient(double X0, double Y0, double X1, double Y1) {
        this.A = (Y0 - Y1) / (X0 - X1);
        this.B = Y0 - (A * X0);
    }

    public double getA() {
        return this.A;
    }

    public double getB() {
        return this.B;
    }

    public double calculateDistant(double X0, double Y0, double X1, double Y1) {
        return sqrt((X0 - X1) * (X0 - X1) + (Y0 - Y1) * (Y0 - Y1));
    }

    public boolean onTheLine(double cX, double cY, double X0, double Y0, double X1, double Y1, boolean nor) {
        //if (cX != X0 && cY != Y0) {
        if (cX == X0 && cY == Y0) {
            return false;
        }
        if (nor) {
            if (abs(A * cX + B - cY) <= 0.0001) {
                return abs(calculateDistant(X0, Y0, cX, cY) + calculateDistant(X1, Y1, cX, cY)
                        - calculateDistant(X0, Y0, X1, Y1)) <= 0.0001;
            } else {
                return false;
            }
        }
        return abs(calculateDistant(X0, Y0, cX, cY) + calculateDistant(X1, Y1, cX, cY)
                - calculateDistant(X0, Y0, X1, Y1)) <= 0.0001;
        //}
        //return false;
    }

    public int nearStart(double X0, double Y0, double cX1, double cY1
            , double cX2, double cY2, boolean nor, double X1, double Y1) {
        boolean o1 = onTheLine(cX1, cY1, X0, Y0, X1, Y1, nor);
        boolean o2 = onTheLine(cX2, cY2, X0, Y0, X1, Y1, nor);
        if (o1 && o2) {
            if (abs(calculateDistant(X0, Y0, cX1, cY1) - calculateDistant(X1, Y1, cX2, cY2))
                    <= 0.0001 || calculateDistant(X0, Y0, cX1, cY1) == calculateDistant(X0, Y0, cX2, cY2)) {
                return 1;
            } else if (abs(calculateDistant(X0, Y0, cX1, cY1) - calculateDistant(X1, Y1, cX2, cY2))
                    > 0.0001 || calculateDistant(X0, Y0, cX1, cY1) != calculateDistant(X0, Y0, cX2, cY2)) {
                return 2;
            }
        } else if (o1) {
            return 1;
        } else if (o2) {
            return 2;
        }
        return -1;
    }



}
