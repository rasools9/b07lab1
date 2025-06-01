import java.io.File;

public class Driver {
    public static void main(String[] args) {
        // Polynomial 1: 6 - 2x + 5x^3 + 2x^5
        double[] coeffs1 = {6, -2, 5, 2};
        int[] exps1 = {0, 1, 3, 5};
        Polynomial p1 = new Polynomial(coeffs1, exps1);

        // Polynomial 2: 4 + 3x + 7x^2
        double[] coeffs2 = {4, 3, 7};
        int[] exps2 = {0, 1, 2};
        Polynomial p2 = new Polynomial(coeffs2, exps2);

        // Sum
        Polynomial sum = p1.add(p2);
        System.out.println("Sum of Polynomials:");
        sum.print();

        // Disjoint exponents example
        double[] coeffs3 = {1, 2};
        int[] exps3 = {0, 5};
        Polynomial p3 = new Polynomial(coeffs3, exps3);
        Polynomial result2 = p1.add(p3);
        System.out.println("\nSum with disjoint exponents:");
        result2.print();

        // Multiply
        Polynomial product = p1.multiply(p2);
        System.out.println("\nPolynomial 1:");
        p1.print();
        System.out.println("Polynomial 2:");
        p2.print();
        System.out.println("Product:");
        product.print();

        // Evaluate
        System.out.println("Result evaluated at x = 1: " + product.evaluate(1));
        System.out.println("Does the product have a root at x = 1? " + product.hasRoot(1));

        try {
            File inputFile = new File("poly.txt");
            Polynomial pFromFile = new Polynomial(inputFile);

            System.out.println("\nPolynomial read from file:");
            pFromFile.print();

            double xValue = 2.0;
            System.out.println("Evaluated at x = " + xValue + ": " + pFromFile.evaluate(xValue));
            System.out.println("Has root at x = " + xValue + "? " + pFromFile.hasRoot(xValue));

            pFromFile.saveToFile("output.txt");
            System.out.println("Polynomial saved to output.txt");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

