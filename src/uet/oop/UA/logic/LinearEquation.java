package uet.oop.UA.logic;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class LinearEquation {
    double A;
    double B;

    /**
     * tính toán hệ số.
     * @param X0 gốc X.
     * @param Y0 gốc Y.
     * @param X1 diểm xét X.
     * @param Y1 điểm xét Y.
     */
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

    /**
     * tính khoảng cách điểm 0 và 1.
     * @param X0 x0.
     * @param Y0 y0.
     * @param X1 x1.
     * @param Y1 y1.
     * @return khoảng cách.
     */
    public double calculateDistant(double X0, double Y0, double X1, double Y1) {
        return sqrt((X0 - X1) * (X0 - X1) + (Y0 - Y1) * (Y0 - Y1));
    }


    /**
     * kiểm tra trên đoạn thẳng.
     * @param cX x ktra.
     * @param cY y ktra.
     * @param X0 gốc x.
     * @param Y0 gốc y.
     * @param X1 điểm sau x.
     * @param Y1 điểm sau y.
     * @param nor trường hợp đặc biệt
     * nor = true nếu bình thường.
     * @return true nếu nằm từ (x1, y1) đến (x0, y0).
     */
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

    /**
     * kiểm tra để lấy điểm gần gốc.
     * @param X0 gốc x.
     * @param Y0 gốc y.
     * @param cX1 x1.
     * @param cY1 y1,
     * @param cX2 x2.
     * @param cY2 y2.
     * @param nor điểu kiện đặc biệt.
     * @param X1 điểm sau x.
     * @param Y1 điểm sau y.
     * @return 1 nếu là điểm 1, 2 nếu điểm 2
     * -1 nếu không có điểm nào.
     */
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
