package lang.sel.commons.results;

import java.util.List;

import lang.sel.exceptions.SelSemanticException;

/**
 * Typed Result
 * <p>
 * Common logic to simple typed results, like boolean, integer, string, etc...
 *
 * @author diegorubin
 */
public abstract class TypedResult<T> {

    private static final String AND = " and ";
    private static final String ARRAY_RESULT = "ArrayResult";
    private static final String BOOLEAN_RESULT = "BooleanResult";
    private static final String FLOAT_RESULT = "FloatResult";
    private static final String INTEGER_RESULT = "IntegerResult";
    private static final String STRING_RESULT = "StringResult";

    protected T result;

    /**
     * When a typed result is created it is necessary enter the type
     *
     * @param value the content
     * @param type  the type of content
     */
    public TypedResult(final T value) {
        result = value;
    }

    /**
     * Set content of operator result
     *
     * @param result the content of operation result.
     */
    public void setResult(final T result) {
        this.result = result;
    }

    /**
     * Return the content of operation result
     *
     * @return the content
     */
    public T getResult() {
        return result;
    }

    public String getType() {
        return getClass().getSimpleName();
    }

    /**
     * Implementation for how common operator argument respond to AND operator. This method is invoked with java
     * reflection.
     *
     * @param arg the second operator
     *
     * @return boolean result
     */
    public BooleanResult operatorAnd(TypedResult arg) {
        if (!getType().equals(BOOLEAN_RESULT) || !arg.getType().equals(BOOLEAN_RESULT)) {
            throw new SelSemanticException("expecting two BOOLEAN's but get " + getType() + AND + arg.getType());
        }
        return new BooleanResult((Boolean) getResult() && ((Boolean) arg.getResult()));
    }

    /**
     * Implementation for how common operator argument respond to OR operator. This method is invoked with java
     * reflection.
     *
     * @param arg the second operator
     *
     * @return boolean result
     */
    public BooleanResult operatorOr(TypedResult arg) {
        if (!getType().equals(BOOLEAN_RESULT) || !arg.getType().equals(BOOLEAN_RESULT)) {
            throw new SelSemanticException("expecting two BOOLEAN's but get " + getType() + AND + arg.getType());
        }
        return new BooleanResult((Boolean) getResult() || ((Boolean) arg.getResult()));
    }

    /**
     * Implementation for how common operator argument respond to NOT operator. This method is invoked with java
     * reflection.
     *
     * @return boolean result
     */
    public BooleanResult operatorNot() {
        if (!getType().equals(BOOLEAN_RESULT)) {
            throw new SelSemanticException("expecting BOOLEAN but get " + getType());
        }
        return new BooleanResult(!(Boolean) getResult());
    }

    public BooleanResult operatorEq(TypedResult arg) {
        if (onlyIntegers(arg)) {
            return new BooleanResult(((Long) getResult()).equals(arg.getResult()));
        }
        if (onlyStrings(arg)) {
            return new BooleanResult(((String) getResult()).equals(arg.getResult()));
        }
        return new BooleanResult(normalize(this).equals(normalize(arg)));
    }

    public BooleanResult operatorGte(TypedResult arg) {
        checkMathArguments(arg);
        if (onlyIntegers(arg)) {
            return new BooleanResult(((Long) getResult()) >= ((Long) arg.getResult()));
        }
        return new BooleanResult(normalize(this) >= normalize(arg));
    }

    public BooleanResult operatorGt(TypedResult arg) {
        checkMathArguments(arg);
        if (onlyIntegers(arg)) {
            return new BooleanResult(((Long) getResult()) > ((Long) arg.getResult()));
        }
        return new BooleanResult(normalize(this) > normalize(arg));
    }

    public BooleanResult operatorLte(TypedResult arg) {
        checkMathArguments(arg);
        if (onlyIntegers(arg)) {
            return new BooleanResult(((Long) getResult()) <= ((Long) arg.getResult()));
        }
        return new BooleanResult(normalize(this) <= normalize(arg));
    }

    public BooleanResult operatorLt(TypedResult arg) {
        checkMathArguments(arg);
        if (onlyIntegers(arg)) {
            return new BooleanResult(((Long) getResult()) < ((Long) arg.getResult()));
        }
        return new BooleanResult(normalize(this) < normalize(arg));
    }

    public TypedResult operatorPlus(TypedResult arg) {
        checkMathArguments(arg);
        if (onlyIntegers(arg)) {
            IntegerResult result = new IntegerResult(((Long) getResult()) + ((Long) arg.getResult()));
            return ((TypedResult) result);
        }
        FloatResult result = new FloatResult(normalize(this) + normalize(arg));
        return ((TypedResult) result);
    }

    public BooleanResult operatorIn(TypedResult arg) {
        if (!arg.getType().equals(ARRAY_RESULT)) {
            throw new SelSemanticException("IN operator require an array when used with TypedResult");
        }
        return new BooleanResult(((List) arg
                                  .getResult()).stream().filter(o -> ((TypedResult) o).getResult().equals(getResult())).count() > 0);
    }

    private void checkMathArguments(TypedResult arg) {
        if (!((getType().equals(INTEGER_RESULT) || getType().equals(FLOAT_RESULT)) &&
              (!arg.getType().equals(INTEGER_RESULT) || !arg.getType().equals(FLOAT_RESULT)))) {
            throw new SelSemanticException("expecting two numbers but get " + getType() + AND
                                           + arg.getType());
        }
    }

    private boolean onlyIntegers(TypedResult arg) {
        return arg.getType().equals(INTEGER_RESULT) && getType().equals(INTEGER_RESULT);
    }

    private boolean onlyStrings(TypedResult arg) {
        return arg.getType().equals(STRING_RESULT) && getType().equals(STRING_RESULT);
    }

    private Double normalize(TypedResult arg) {
        if (arg.getType().equals(INTEGER_RESULT)) {
            return new Double((Long) arg.getResult());
        }
        return (Double) arg.getResult();
    }
}
