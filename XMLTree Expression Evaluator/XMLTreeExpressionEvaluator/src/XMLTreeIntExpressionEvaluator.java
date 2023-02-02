import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to evaluate XMLTree expressions of {@code int}. t
 *
 *
 * @author Karl Chavez.149
 */
public final class XMLTreeIntExpressionEvaluator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
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
    private static int evaluate(XMLTree exp) {
        assert exp != null : "Violation of: exp is not null";

        /*
         * This value is the "left" side of the branch. It will be operated
         * first before the the "right" side of the branch. This value is
         * updated throughout and is the one being returned.
         */
        int number1 = 0;
        /*
         * This value is the "right" side of the branch. It's operated after the
         * "left" side has been operated.
         */
        int number2 = 0;

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
            number1 = evaluate(exp.child(0));
            number2 = evaluate(exp.child(1));

            /*
             * There are 4 possible operations and each of them is associated by
             * the name of the tag. It operates occording to the tag label. The
             * child to the left, number1, is operated first. The reason being
             * is because we read math operations from left to right
             */
            if (exp.label().equals("plus")) {
                number1 = number1 + number2;
            } else if (exp.label().equals("minus")) {
                number1 = number1 - number2;
            } else if (exp.label().equals("times")) {
                number1 = number1 * number2;
            } else if (exp.label().equals("divide")) {
                number1 = number1 / number2;
                /*
                 * Note that if anything is divided by 0, Eclipse automatically
                 * outputs an error so we don't have to display the error
                 * ourselves
                 */
            }

            /*
             * If the tree doesn't contain any children, then it MUST be a
             * number. The string is converted into an int.
             */
        } else {
            number1 = Integer.parseInt(exp.attributeValue("value"));
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