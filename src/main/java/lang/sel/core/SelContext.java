package lang.sel.core;

import lang.sel.core.wrappers.FunctionOptions;
import lang.sel.interfaces.AbstractFunction;
import lang.sel.interfaces.BinaryOperator;
import lang.sel.interfaces.SimpleConstant;
import lang.sel.interfaces.UnaryOperator;

import java.util.HashMap;
import java.util.Map;

/**
 * Rule engine context
 * <p>
 * This class contains all global information of engine.
 *
 * @author diegorubin
 */
public class SelContext {

  private Map<String, Class<? extends BinaryOperator>> binaryOperators;
  private Map<String, Class<? extends UnaryOperator>> unaryOperators;
  private Map<String, Class<? extends AbstractFunction>> functions;
  private Map<String, Class<? extends SimpleConstant>> constants;

  private Map<String, FunctionOptions> functionOptions;

  /**
   * Create a instance of context.
   */
  public SelContext() {
    binaryOperators = new HashMap<>();
    unaryOperators = new HashMap<>();
    functions = new HashMap<>();
    functionOptions = new HashMap<>();
    constants = new HashMap<>();
  }

  /**
   * Add an implementation of a binary operator.
   * <p>
   * Example of binary operator are AND, OR.
   *
   * @param name     the name of operator used on expression
   * @param operator the class of operator implementation
   */
  public void addBinaryOperator(final String name, final Class<? extends BinaryOperator> operator) {
    binaryOperators.put(name, operator);
  }

  /**
   * Add an implementation of an unary operator.
   * <p>
   * Example of binary operator is NOT.
   *
   * @param name     the name of operator used on expression
   * @param operator the class of operator implementation
   */
  public void addUnaryOperator(final String name, final Class<? extends UnaryOperator> operator) {
    unaryOperators.put(name, operator);
  }

  /**
   * Add an implementation of a function
   *
   * @param name     the name of function used on expression
   * @param function the class of function implementation
   */
  public void addFunction(final String name, final Class<? extends AbstractFunction> function,
      final FunctionOptions options) {
    functions.put(name, function);
    functionOptions.put(name, options);
  }

  /**
   * Add an implementation of a constant
   *
   * @param name     the name of constant used on expression
   * @param constant the class of constant implementation
   */
  public void addConstant(final String name, final Class<? extends SimpleConstant> constant) {
    constants.put(name, constant);
  }

  /**
   * Get the binary operator implementation by operator name
   *
   * @param operator the name of binary operator used on expression
   *
   * @return class of operator
   */
  public Class<? extends BinaryOperator> getBinaryOperator(final String operator) {
    return binaryOperators.get(operator);
  }

  /**
   * Get the unary operator implementation by operator name
   *
   * @param operator the name of unary operator used on expression
   *
   * @return class of operator
   */
  public Class<? extends UnaryOperator> getUnaryOperator(final String operator) {
    return unaryOperators.get(operator);
  }

  /**
   * Get the function implementation by function name
   *
   * @param function the name of function used on expression
   *
   * @return class of function
   */
  public Class<? extends AbstractFunction> getFunction(final String function) {
    return functions.get(function);
  }

  /**
   * Get the function options by function name
   *
   * @param function the name of function used on expression
   *
   * @return options for function
   */
  public FunctionOptions getFunctionOptions(final String function) {
    return functionOptions.get(function);
  }

  /**
   * Get the binary operator implementation by constant name
   *
   * @param constant the name of constant used on expression
   *
   * @return class of constant
   */
  public Class<? extends SimpleConstant> getConstant(final String constant) {
    return constants.get(constant);
  }

  /**
   * Check if function exists
   *
   * @param function the name of function used on expression
   *
   * @return the is returned if functions was declared
   */
  public boolean isFunction(final String function) {
    return functions.containsKey(function);
  }

  /**
   * Check if operator exists
   *
   * @param operator the name of operator used on expression
   *
   * @return true is returned if operator was declared
   */
  public boolean isBinaryOperator(final String operator) {
    return binaryOperators.containsKey(operator);
  }

  /**
   * Check if operator exists
   *
   * @param operator the name of operator used on expression
   *
   * @return true is returned if operator was declared
   */
  public boolean isUnaryOperator(final String operator) {
    return unaryOperators.containsKey(operator);
  }

  /**
   * Check if constant exists
   *
   * @param constant the name of constant used on expression
   *
   * @return true is returned if constant was declared
   */
  public boolean isConstant(final String constant) {
    return constants.containsKey(constant);
  }
}

