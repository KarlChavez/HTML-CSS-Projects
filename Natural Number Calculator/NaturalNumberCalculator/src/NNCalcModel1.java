import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;

/**
 * Model class.
 *
 * @author Karl Chavez
 */
public final class NNCalcModel1 implements NNCalcModel {

    /**
     * Model variables.
     */
    private final NaturalNumber top, bottom;

    /**
     * Default constructor.
     */
    public NNCalcModel1() {
        this.top = new NaturalNumber2();
        this.bottom = new NaturalNumber2();
    }

    @Override
    public NaturalNumber top() {

        /*
         * Returns the top Natural Number value
         */
        return this.top;

    }

    @Override
    public NaturalNumber bottom() {

        /*
         * Returns the bottom Natural Number value
         */
        return this.bottom;
    }

}
