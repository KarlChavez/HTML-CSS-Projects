import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.Reporter;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to evaluate XMLTree expressions of {@code NaturalNumber}.
 *
 * @author Karl Chavez.149
 *
 */
public final class XMLTreeNNExpressionEvaluator {
    /**
     * Evaluate the given expression.
     *
     * @param exp
     *            the {@code XMLTree} representing the expression
     * @return the value of the expression
     * @requires <pre>
     * [exp is a subtree of a well-formed XML arithmetic expression]  and
     *  [the label of the root of exp is not "expression"]
     * </pre>
     * @ensures evaluate = [the value of the expression]
     */
    private static NaturalNumber evaluate(XMLTree exp) {
        assert exp != null : "Violation of: exp is not null";
        /*
         * The evaluate function for Natural Number is very similar to int
         * except that arithmetic operators don't work. Instead, we will use
         * Natural Number operators that correspond with arimthetic operations.
         */

        /*
         * This value is the "left" side of the branch. It will be operated
         * first before the the "right" side of the branch. This value is
         * updated throughout and is the one being returned.
         */
        NaturalNumber number1 = new NaturalNumber2();
        /*
         * This value is the "right" side of the branch. It's operated after the
         * "left" side has been operated.
         */
        NaturalNumber number2 = new NaturalNumber2();
        /*
         * We will use zero to compare in the division method for Divide by 0
         * error
         */
        NaturalNumber zero = new NaturalNumber2(0);
        /*
         * There can only be 0 or two children in a branch exlcuding the root.
         * The value in those children could be either an arithmetic or a
         * number. The recursion method takes care of that difference. Also, we
         * don't need to check if the it's a tag because the "requires" clause
         * states it's a well-formed XML expression
         */
        if (exp.numberOfChildren() != 0) {
            /*
             * We call the function for the "left" and "right" side of the
             * branch. The recursion continues until the tag doesn't contain any
             * children. We use 0 and 1 because there MUST be two children.
             */
            number1.copyFrom(evaluate(exp.child(0)));
            number2.copyFrom(evaluate(exp.child(1)));
            /*
             * There are 4 possible operations and each of them is associated by
             * the name of the tag. It operates occording to the tag label. The
             * child to the left, number1, is operated first. The reason being
             * is because we read math operations from left to right
             */
            if (exp.label().equals("plus")) {
                number1.add(number2);
            }
            /*
             * We have to be careful with the subtraction sign becuase it could
             * produce a negative number.
             */
            else if (exp.label().equals("minus")) {
                /*
                 * If the "left" number is smaller than the "right", then an
                 * error message will be outputted
                 */
                if (number1.compareTo(number2) < 0) {
                    Reporter.fatalErrorToConsole(
                            "The smaller number should be subtracted from the larger number!");
                }
                number1.subtract(number2);

            } else if (exp.label().equals("times")) {
                number1.multiply(number2);
            }
            /*
             * We have to be careful with the division sign because it can
             * produce a divide by 0 error.
             */
            else if (exp.label().equals("divide")) {
                /*
                 * If the "right" number in the operation is zero, then we can't
                 * divide by zero.
                 */
                if (number2.compareTo(zero) == 0) {
                    Reporter.fatalErrorToConsole(
                            "Divide by zero error. The denominator can't be zero!");
                }
                number1.divide(number2);
            }

            /*
             * If the tree doesn't contain any children, then it MUST be a
             * number. The string is converted into a Natural Number.
             */
        } else {

            /*
             * Natural Numbers can't be negative so we have to check if the
             * number is less than 0.
             */
            if (Integer.parseInt(exp.attributeValue("value")) < 0) {
                Reporter.fatalErrorToConsole(
                        "Natural Numbers MUST be positive!");
            }
            //converting the value into a natural number
            number1.setFromString(exp.attributeValue("value"));
        }
        return number1;
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

        out.print("Enter the XML expression file name: ");
        String xml = in.nextLine();
        XMLTree operation = new XMLTree1(xml);

        //prints out the value of the operation
        out.println(evaluate(operation.child(0)));

        in.close();
        out.close();
    }

}