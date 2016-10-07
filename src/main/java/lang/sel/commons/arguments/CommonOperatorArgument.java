package lang.sel.commons.arguments;

import lang.sel.commons.results.BooleanResult;
import lang.sel.commons.results.FloatResult;
import lang.sel.commons.results.IntegerResult;
import lang.sel.core.Keyword;
import lang.sel.exceptions.ExpressionSemanticException;
import lang.sel.interfaces.OperationResult;
import lang.sel.interfaces.OperatorArgument;

/**
 * Common Operator Argument
 *
 * @author diegorubin
 */
public class CommonOperatorArgument implements OperatorArgument {

  private static final String AND = " and ";

  private Keyword argumentType;
  private Object content;

  /**
   * Constructor for common argument. Minimum information for an argument is the semantic type.
   *
   * @param argumentType the type
   */
  public CommonOperatorArgument(final Keyword argumentType) {
    this.argumentType = argumentType;
  }

  /**
   * Set the content of argument
   *
   * @param content the content of argument
   */
  @Override
  public void setContent(final Object content) {
    this.content = content;
  }

  /**
   * Get the content of argument
   *
   * @return the content of argument
   */
  @Override
  public Object getContent() {
    return content;
  }

  /**
   * Get the semantic type of argument
   *
   * @return the type of argument
   */
  @Override
  public Keyword getArgumentType() {
    return argumentType;
  }

  /**
   * Implementation for how common operator argument respond to AND operator. This method is invoked with java
   * reflection.
   *
   * @param arg the second operator
   *
   * @return boolean result
   */
  public BooleanResult operatorAnd(CommonOperatorArgument arg) {
    if (getArgumentType() != Keyword.BOOLEAN || arg.getArgumentType() != Keyword.BOOLEAN) {
      throw new ExpressionSemanticException("expecting two BOOLEAN's but get " + getArgumentType() + AND
          + arg.getArgumentType());
    }
    return new BooleanResult((Boolean) getContent() && ((Boolean) arg.getContent()));
  }

  /**
   * Implementation for how common operator argument respond to OR operator. This method is invoked with java
   * reflection.
   *
   * @param arg the second operator
   *
   * @return boolean result
   */
  public BooleanResult operatorOr(CommonOperatorArgument arg) {
    if (getArgumentType() != Keyword.BOOLEAN || arg.getArgumentType() != Keyword.BOOLEAN) {
      throw new ExpressionSemanticException("expecting two BOOLEAN's but get " + getArgumentType() + AND
          + arg.getArgumentType());
    }
    return new BooleanResult((Boolean) getContent() || ((Boolean) arg.getContent()));
  }

  /**
   * Implementation for how common operator argument respond to NOT operator. This method is invoked with java
   * reflection.
   *
   * @return boolean result
   */
  public BooleanResult operatorNot() {
    if (getArgumentType() != Keyword.BOOLEAN) {
      throw new ExpressionSemanticException("expecting BOOLEAN but get " + getArgumentType());
    }
    return new BooleanResult(!(Boolean) getContent());
  }

  public BooleanResult operatorEq(CommonOperatorArgument arg) {
    checkMathArguments(arg);
    if (onlyIntegers(arg)) {
      return new BooleanResult(((Long) getContent()).equals(arg.getContent()));
    }
    return new BooleanResult(normalize(this).equals(normalize(arg)));
  }

  public BooleanResult operatorGte(CommonOperatorArgument arg) {
    checkMathArguments(arg);
    if (onlyIntegers(arg)) {
      return new BooleanResult(((Long) getContent()) >= ((Long) arg.getContent()));
    }
    return new BooleanResult(normalize(this) >= normalize(arg));
  }

  public BooleanResult operatorGt(CommonOperatorArgument arg) {
    checkMathArguments(arg);
    if (onlyIntegers(arg)) {
      return new BooleanResult(((Long) getContent()) > ((Long) arg.getContent()));
    }
    return new BooleanResult(normalize(this) > normalize(arg));
  }

  public BooleanResult operatorLte(CommonOperatorArgument arg) {
    checkMathArguments(arg);
    if (onlyIntegers(arg)) {
      return new BooleanResult(((Long) getContent()) <= ((Long) arg.getContent()));
    }
    return new BooleanResult(normalize(this) <= normalize(arg));
  }

  public BooleanResult operatorLt(CommonOperatorArgument arg) {
    checkMathArguments(arg);
    if (onlyIntegers(arg)) {
      return new BooleanResult(((Long) getContent()) < ((Long) arg.getContent()));
    }
    return new BooleanResult(normalize(this) < normalize(arg));
  }

  public OperationResult operatorPlus(CommonOperatorArgument arg) {
    checkMathArguments(arg);
    if (onlyIntegers(arg)) {
      return new IntegerResult(((Long) getContent()) + ((Long) arg.getContent()));
    }
    return new FloatResult(normalize(this) + normalize(arg));
  }

  private void checkMathArguments(CommonOperatorArgument arg) {
    if (!((getArgumentType() == Keyword.INTEGER || getArgumentType() == Keyword.FLOAT) &&
        (arg.getArgumentType() != Keyword.INTEGER || arg.getArgumentType() != Keyword.FLOAT))) {
      throw new ExpressionSemanticException("expecting two numbers but get " + getArgumentType() + AND
          + arg.getArgumentType());
    }
  }

  private boolean onlyIntegers(CommonOperatorArgument arg) {
    return arg.getArgumentType() == Keyword.INTEGER && getArgumentType() == Keyword.INTEGER;
  }

  private Double normalize(CommonOperatorArgument arg) {
    if (arg.getArgumentType() == Keyword.INTEGER) {
      return new Double((Long) arg.getContent());
    }
    return (Double) arg.getContent();
  }

}

