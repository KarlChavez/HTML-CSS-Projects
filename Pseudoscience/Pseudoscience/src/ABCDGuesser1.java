import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.FormatChecker;

public class ABCDGuesser1 {
// TODO Auto-generated method stub
    /*
     * Essentially in this project, we are going through every of the 17 numbers
     * until we find a result that is as close as possible to the variable u. To
     * accomplish this, we will have while loops inside of while loops to go
     * through every single combination.
     */

    //We start by creating the methods provided for us

    private static double getPositiveDouble(SimpleReader in, SimpleWriter out) {

        boolean x = true;
        while (x) {
            //asks for input
            String y = in.nextLine();

            //checks whether it is a number
            if (FormatChecker.canParseDouble(y)) {
                double number = Double.parseDouble(y);

                //if the number is positive, then loop breaks and returns number
                if (number > 0) {
                    x = false;
                    return number;
                }
            }
            //if it doesn't pass the "if" statements, then it loops again
            out.print("Enter a positive double: ");
        }
        return 0;
    }

    private static double getPositiveDoubleNotOne(SimpleReader in,
            SimpleWriter out) {

        boolean x = true;
        while (x) {
            //asks for input
            String y = in.nextLine();

            //checks whether it is a number
            if (FormatChecker.canParseDouble(y)) {
                double number = Double.parseDouble(y);

                //if the number is positive and not 1, then loop breaks
                //and returns number
                if (number > 0 && number != 1) {
                    x = false;
                    return number;
                }
            }
            //if it doesn't pass the "if" statements, then it loops again
            out.print("Enter a positive double that's not one:  ");
        }
        return 0;
    }

    public static void main(String[] args) {

        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        //asking for all the inputs
        out.print("Enter a positive double for u: ");
        Double u = getPositiveDouble(in, out);

        out.print("\nEnter a positive double for w that's not one: ");
        Double w = getPositiveDoubleNotOne(in, out);

        out.print("\nEnter a positive double for x that's not one: : ");
        Double x = getPositiveDoubleNotOne(in, out);

        out.print("\nEnter a positive double for y that's not one: : ");
        Double y = getPositiveDoubleNotOne(in, out);

        out.print("\nEnter a positive double for z that's not one: : ");
        Double z = getPositiveDoubleNotOne(in, out);

        //creating an place to store the 17 numbers by creating an arraylist
        double[] deJager = { -5, -4, -3, -2, -1, -0.5, (-1.0 / 3.0), -.25, 0,
                .25, (1.0 / 3.0), .5, 1, 2, 3, 4, 5 };

        //these variables will be the exponents of our personal numbers
        int a = 0;
        int b = 0;
        int c = 0;
        int d = 0;

        //these variables are needed for each while loop
        double value1 = 0;
        double value2 = 0;
        double value3 = 0;
        double value4 = 0;

        //We use this variable to compare u
        double approx = 0;

        //We use this variable to keep track of the closest number
        double close = 0;

        //Will keep track of the exponents that have the best estimate
        double e1 = 0;
        double e2 = 0;
        double e3 = 0;
        double e4 = 0;

        /*
         * The premise is to iterate through all the exponents and combinations
         * so we can find the closest value (at least 0.08%) to u. To do that, I
         * will stack while loops on top of while loops or a nested loop. Each
         * loop represents the personal numbers
         */
        while (a < deJager.length) {
            value1 = (Math.pow(w, deJager[a]));
            while (b < deJager.length) {
                value2 = (Math.pow(x, deJager[b]));
                while (c < deJager.length) {
                    value3 = (Math.pow(y, deJager[c]));
                    while (d < deJager.length) {
                        value4 = (Math.pow(z, deJager[d]));
                        approx = (value1 * value2 * value3 * value4);
                        /*
                         * To find the closest value to u, we will subtract u
                         * from the approximate value and the best value and
                         * compare which difference is the smallest.
                         */
                        if (Math.abs(u - approx) < (Math.abs(u - close))) {
                            close = approx;
                            e1 = deJager[a];
                            e2 = deJager[b];
                            e3 = deJager[c];
                            e4 = deJager[d];

                        }
                        d++;
                    }
                    c++;
                    if (d == 17) {
                        d = 0;
                    }
                }
                b++;
                if (c == 17) {
                    c = 0;
                }
            }
            a++;
            if (b == 17) {
                b = 0;
            }
        }

        //outputting results
        out.print("\nThe smallest error when approximating u is ");
        out.print(close, 2, false);
        out.println("\n\nThe exponents used were " + e1 + ", " + e2 + ", " + e3
                + ", " + e4);
        out.print("\n" + w + "^" + e1 + " * " + x + "^" + e2 + " * " + y + "^"
                + e3 + " * " + z + "^" + e4 + " = ");
        out.print(close, 2, false);
        in.close();
        out.close();
    }

}
