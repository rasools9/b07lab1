import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class Polynomial {
    double[] coefficients;
    int[] exponents;

    
    public Polynomial() {
    }

    // Constructor with coefficients array
    public Polynomial(double[] c, int[] e) {
        this.coefficients = new double[c.length];
        this.exponents = new int[e.length];
        for (int i = 0; i < c.length; i++) {
            this.coefficients[i] = c[i];
        }
        for (int i = 0; i < e.length; i++) {
            this.exponents[i] = e[i];
        }
    }
    

    public Polynomial(File file) {
        try {
            Scanner scanner = new Scanner(file);
            String line = scanner.nextLine();
            scanner.close();

            // Preprocess to separate terms by + or -
            ArrayList<Double> coeffList = new ArrayList<>();
            ArrayList<Integer> expList = new ArrayList<>();

            int i = 0;
            while (i < line.length()) {
                // Capture sign
                StringBuilder term = new StringBuilder();
                if (line.charAt(i) == '+' || line.charAt(i) == '-') {
                    term.append(line.charAt(i));
                    i++;
                }

                // Read until next + or -
                while (i < line.length() && line.charAt(i) != '+' && line.charAt(i) != '-') {
                    term.append(line.charAt(i));
                    i++;
                }

                String t = term.toString();
                if (t.contains("x")) {
                    String[] parts = t.split("x");
                    double coeff = Double.parseDouble(parts[0]);
                    int exp = Integer.parseInt(parts[1]);
                    coeffList.add(coeff);
                    expList.add(exp);
                } else {
                    // Constant term (no x), exponent is 0
                    double coeff = Double.parseDouble(t);
                    coeffList.add(coeff);
                    expList.add(0);
                }
            }

            // Convert to arrays
            this.coefficients = new double[coeffList.size()];
            this.exponents = new int[expList.size()];
            for (int j = 0; j < coeffList.size(); j++) {
                this.coefficients[j] = coeffList.get(j);
                this.exponents[j] = expList.get(j);
            }

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + file.getName());
            this.coefficients = new double[0];
            this.exponents = new int[0];
        }
    }

    public void saveToFile(String filename) {
        try {
            FileWriter writer = new FileWriter(filename);
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < coefficients.length; i++) {
                double coeff = coefficients[i];
                int exp = exponents[i];

                if (coeff == 0.0) continue;

                if (i > 0 && coeff > 0) sb.append("+");

                if (exp == 0) {
                    sb.append(coeff);  // Constant term
                } else {
                    sb.append(coeff).append("x").append(exp);
                }
            }

            writer.write(sb.toString());
            writer.close();
        } catch (IOException e) {
            System.err.println("Could not write to file: " + filename);
        }
    }



    // Add method
    
    public Polynomial add(Polynomial other) {
        int n = this.coefficients.length;
        int m = other.coefficients.length;

        double[] tempCoeffs = new double[n + m];
        int[] tempExps = new int[n + m];
        int count = 0;

        // Step 1: Copy terms from "this" polynomial
        for (int i = 0; i < n; i++) {
            tempCoeffs[count] = this.coefficients[i];
            tempExps[count] = this.exponents[i];
            count++;
        }

        // Step 2: Add terms from "other" polynomial
        for (int j = 0; j < m; j++) {
            double co = other.coefficients[j];
            int ex = other.exponents[j];
            boolean found = false;

            for (int z = 0; z < count; z++) {
                if (tempExps[z] == ex) {
                    tempCoeffs[z] += co;
                    found = true;
                    break;
                }
            }

            if (!found) {
                tempCoeffs[count] = co;
                tempExps[count] = ex;
                count++;
            }
        }

        // Step 3: Remove zero-coefficient terms
        int nonZeroCount = 0;
        for (int i = 0; i < count; i++) {
            if (tempCoeffs[i] != 0.0) {
                nonZeroCount++;
            }
        }

        double[] finalCoeffs = new double[nonZeroCount];
        int[] finalExps = new int[nonZeroCount];
        int idx = 0;
        for (int i = 0; i < count; i++) {
            if (tempCoeffs[i] != 0.0) {
                finalCoeffs[idx] = tempCoeffs[i];
                finalExps[idx] = tempExps[i];
                idx++;
            }
        }

        return new Polynomial(finalCoeffs, finalExps);
    }
    
    public Polynomial multiply(Polynomial other) {
        int n = this.coefficients.length;
        int m = other.coefficients.length;

        double[] tempCoeffs = new double[n * m];
        int[] tempExps = new int[n * m];
        int count = 0;

        // Step 1: Multiply all terms
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                double newCoeff = this.coefficients[i] * other.coefficients[j];
                int newExp = this.exponents[i] + other.exponents[j];

                // Check if exponent already exists
                boolean found = false;
                for (int k = 0; k < count; k++) {
                    if (tempExps[k] == newExp) {
                        tempCoeffs[k] += newCoeff;  // Combine like terms
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    tempCoeffs[count] = newCoeff;
                    tempExps[count] = newExp;
                    count++;
                }
            }
        }

        // Step 2: Remove zero-coefficient terms
        int nonZeroCount = 0;
        for (int i = 0; i < count; i++) {
            if (tempCoeffs[i] != 0.0) {
                nonZeroCount++;
            }
        }

        double[] finalCoeffs = new double[nonZeroCount];
        int[] finalExps = new int[nonZeroCount];
        int idx = 0;
        for (int i = 0; i < count; i++) {
            if (tempCoeffs[i] != 0.0) {
                finalCoeffs[idx] = tempCoeffs[i];
                finalExps[idx] = tempExps[i];
                idx++;
            }
        }

        return new Polynomial(finalCoeffs, finalExps);
    }
    
    
    

    // Evaluate method
    public double evaluate(double x) {
        double result = 0.0;

        for (int i = 0; i < this.coefficients.length; i++) {
            result += this.coefficients[i] * Math.pow(x, this.exponents[i]);
        }

        return result;
    }

    // HasRoot method
    public boolean hasRoot(double x) {
        return evaluate(x) == 0.0;
    }
    public void print() {
        for (int i = 0; i < coefficients.length; i++) {
            System.out.println("Coeff: " + coefficients[i] + " | Exp: " + exponents[i]);
        }
    }
}
