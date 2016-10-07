package lang.sel.commons.operators;

import lang.sel.exceptions.ExpressionSemanticException;
import lang.sel.interfaces.OperationResult;
import lang.sel.interfaces.OperatorArgument;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * Common Operator
 *
 * @author diegorubin
 */
public class OverloadOperator {

  private static final String FOR_ARGUMENT_TYPE = " for argument type ";

  protected boolean respondTo(Object object, final String methodName, final Class parameterType) {
    return Optional.ofNullable(getTarget(object, methodName, parameterType)).isPresent();
  }

  protected boolean respondTo(Object object, final String methodName) {
    return Optional.ofNullable(getTarget(object, methodName)).isPresent();
  }

  protected OperationResult execute(OperatorArgument arg1, OperatorArgument arg2, String methodName) {
    if (!respondTo(arg1, methodName, arg2.getClass())) {
      throw new ExpressionSemanticException(arg1.getClass().getName() +
          " should respond to " + methodName + FOR_ARGUMENT_TYPE + arg2.getClass().getName());
    }
    Method method = getTarget(arg1, methodName, arg2.getClass());
    try {
      return (OperationResult) method.invoke(arg1, arg2);
    } catch (IllegalAccessException e) {
    } catch (InvocationTargetException e) {
    }
    throw new ExpressionSemanticException(arg1.getClass().getName() +
        " error on execute " + methodName + FOR_ARGUMENT_TYPE + arg2.getClass().getName());
  }

  protected OperationResult execute(OperatorArgument argument, String methodName) {
    if (!respondTo(argument, methodName)) {
      throw new ExpressionSemanticException(argument.getClass().getName() + " should respond to " + methodName);
    }
    Method method = getTarget(argument, methodName);
    try {
      return (OperationResult) method.invoke(argument);
    } catch (IllegalAccessException e) {
    } catch (InvocationTargetException e) {
    }
    throw new ExpressionSemanticException(argument.getClass().getName() + " error on execute " + methodName);
  }

  private Method getTarget(Object object, final String methodName) {
    return getTarget(object, methodName, null);
  }

  private Method getTarget(Object object, final String methodName, final Class parameterType) {
    Class target = object.getClass();
    Method method = null;

    do {
      try {
        method = parameterType == null ? target.getDeclaredMethod(methodName) :
            target.getDeclaredMethod(methodName, parameterType);
        method.setAccessible(true);
        break;
      } catch (NoSuchMethodException e) {
      }

      target = target.getSuperclass();
    } while (target != null);

    return method;
  }
}
