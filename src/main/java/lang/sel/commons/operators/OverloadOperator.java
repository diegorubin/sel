package lang.sel.commons.operators;

import lang.sel.commons.results.TypedResult;
import lang.sel.exceptions.SelSemanticException;

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

    protected TypedResult execute(TypedResult arg1, TypedResult arg2, String methodName) {
        if (!respondTo(arg1, methodName, arg2.getClass())) {
            throw new SelSemanticException(arg1.getClass().getName() +
                                           " should respond to " + methodName + FOR_ARGUMENT_TYPE + arg2.getClass().getName());
        }
        Method method = getTarget(arg1, methodName, arg2.getClass());
        try {
            return (TypedResult) method.invoke(arg1, arg2);
        } catch (IllegalAccessException e) {
        } catch (InvocationTargetException e) {
        }
        throw new SelSemanticException(arg1.getClass().getName() +
                                       " error on execute " + methodName + FOR_ARGUMENT_TYPE + arg2.getClass().getName());
    }

    protected TypedResult execute(TypedResult argument, String methodName) {
        if (!respondTo(argument, methodName)) {
            throw new SelSemanticException(argument.getClass().getName() + " should respond to " + methodName);
        }
        Method method = getTarget(argument, methodName);
        try {
            return (TypedResult) method.invoke(argument);
        } catch (IllegalAccessException e) {
        } catch (InvocationTargetException e) {
        }
        throw new SelSemanticException(argument.getClass().getName() + " error on execute " + methodName);
    }

    private Method getTarget(Object object, final String methodName) {
        return getTarget(object, methodName, null);
    }

    private Method getTarget(Object object, final String methodName, final Class parameterType) {

        Class target = object.getClass();
        Method method = null;

        do {
            try {
                if (parameterType == null) {
                    method = target.getDeclaredMethod(methodName);
                } else {
                    boolean useSuperClass = parameterType.getSuperclass() == TypedResult.class;
                    method = useSuperClass ? target.getDeclaredMethod(methodName, TypedResult.class) : target.getDeclaredMethod(methodName, parameterType);
                }
                method.setAccessible(true);
                break;
            } catch (NoSuchMethodException e) {
            }

            target = target.getSuperclass();
        } while (target != null);

        return method;
    }
}
