package uet.oop.UA.logic;

import static java.lang.Math.sqrt;

public class CollisionEquation {
    double A;
    double B;
    double C;
    double X1;
    double X2;
    double delta;

    /**
     * costructor tạo phương trình va chạm.
     * @param linearEquation quỹ đạo cd.
     * @param X0 điểm gốc X.
     * @param Y0 điểm gốc Y.
     * @param R bán kinh.
     */
    public CollisionEquation(LinearEquation linearEquation, double X0, double Y0, double R) {
        A = 1 + linearEquation.getA() * linearEquation.getA();
        B = 2 * (-X0 + linearEquation.getA() * linearEquation.getB()
                - linearEquation.getA() * Y0);
        C =-(R * R - X0 * X0 - Y0 * Y0 - linearEquation.getB()
                * linearEquation.getB() + 2 * linearEquation.getB() * Y0);
    }

    public double getA() {
        return A;
    }

    public double getB() {
        return B;
    }

    public double getC() {
        return C;
    }

    /**
     * giải pt va chạm.
     */
    public void solve() {
        this.delta = 0;
        if (B == 0 && C == 0) {
            this.X1 = 0;
            this.X2 = 0;
        } else if (B == 0) {
            this.X1 = sqrt(C / A);
            this.X2 = -sqrt(C / A);
        } else if (C == 0) {
            this.X1 = 0;
            this.X2 = -B / A;
        } else {
            this.delta = (B * B) - (4 * A * C);
            if (this.delta >= 0) {
                this.X1 = (-B + sqrt(delta)) / (2 * A);
                this.X2 = (-B - sqrt(delta)) / (2 * A);
            } else {
                return;
            }
        }
    }

    public double getX1() {
        return X1;
    }

    public double getX2() {
        return X2;
    }

    public double getDelta() {
        return delta;
    }
}
