public class Polynomial {
    double[] coefficients;

    // No-argument constructor: sets polynomial to 0
    public Polynomial() {
        this.coefficients = new double[]{0.0};
    }

    // Constructor with coefficients array
    public Polynomial(double[] c) {
        this.coefficients = new double[c.length];
        for (int i = 0; i < c.length; i++) {
            this.coefficients[i] = c[i];
        }
    }

    // Add method
    public Polynomial add(Polynomial other) {
        int maxLength = Math.max(this.coefficients.length, other.coefficients.length);
        double[] result = new double[maxLength];

        for (int i = 0; i < maxLength; i++) {
            double a = 0.0;
            double b = 0.0;

            if (i < this.coefficients.length) {
                a = this.coefficients[i];
            }

            if (i < other.coefficients.length) {
                b = other.coefficients[i];
            }

            result[i] = a + b;
        }

        return new Polynomial(result);
    }

    // Evaluate method
    public double evaluate(double x) {
        double result = 0.0;
        for (int i = 0; i < this.coefficients.length; i++) {
            result += this.coefficients[i] * Math.pow(x, i);
        }
        return result;
    }

    // HasRoot method
    public boolean hasRoot(double x) {
        return evaluate(x) == 0.0;
    }
}
