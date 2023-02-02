import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * The program of Newton iteration is a square root solver that repeatedly
 * computes a number until it outputs a result. The more repetitions it goes
 * through, the more accurate the answer will be.
 *
 * @author Karl Chavez.149
 *
 */
public final class Newton3 {

    private Newton3() {
    }

    //to pass a user's choice of relative error, we need to create another
    //parameter for the method "sqrt"
    private static double sqrt(double x, double y) {

        //this part of the code catches if "x" is 0. We don't want our program
        //computing 0 so it will return a 0 instead.

        if (x == 0.0) {
            return 0;
        }

        //the relative error is now changed to the user's preference
        double rError = y;

        //this is our guess which changes throughout the while loop
        double r = x;

        //This while statement is equivalent to the equation of
        // "|r2 – x |/x < ε2" it repeats until the equation is satisfied
        while (Math.abs((r * r) - x) / x >= rError * rError) {
            r = (r + (x / r)) / 2;
        }

        return r;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        //asking user to calculate a square root and answer is stored in choice
        out.print("Want to calculate a square root? (y/n) ");

        String choice = in.nextLine();

        //when user says yes
        if (choice.equals("y")) {
            while (choice.equals("y")) {
                //we're assuming that the user doesn't input negatives or 0
                out.print("Enter a positive number: ");
                double number = in.nextDouble();

                //asks the user for the input of relative error
                out.print("Enter the value of ε or relative error: ");
                double relative = in.nextDouble();
                double result = sqrt(number, relative);

                //when the user has inputed 0
                if (result == 0) {
                    out.println("You've entered the value 0 which can't be com"
                            + "puted.");
                }

                else {
                    //printing out the result
                    out.println("The square root value of " + number + " is: "
                            + result);
                }

                //asking the user again if they want to compute another square
                //root
                out.print(
                        "\nDo you want to compute another square root? (y/n) ");
                choice = in.nextLine();

                if (choice.equals("n")) {
                    out.println("Goodbye!");
                }

            }
            //when user says no
        } else {
            out.println("Goodbye!");
        }

        in.close();

        out.close();
    }

}
