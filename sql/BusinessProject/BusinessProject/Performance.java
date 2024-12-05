package BusinessProject;
public enum Performance {
    A_PLUS(4.0),
    A(3.7),
    A_MINUS(3.3),
    B_PLUS(3.0),
    B(2.7),
    B_MINUS(2.3),
    C_PLUS(2.0),
    C(1.7),
    C_MINUS(1.3),
    D(1.0),
    F(0.0);

    private final double gradePoint;

    Performance(double gradePoint) {
        this.gradePoint = gradePoint;
    }

    public double getBonus() {
        return gradePoint * 100;
    }

    public double getGradePoint() {
        return gradePoint;
    }
}
