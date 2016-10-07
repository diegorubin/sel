package lang.sel.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Function annotation
 * <p>
 * This annotation is used to declare a function.
 * <p>
 * The value of annotation it will be the name used in the expression. The function class should extends
 * AbstractFunction.
 *
 * @author diegorubin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Function {

  /**
   * The name of function used in expression.
   *
   * @return the name of function
   */
  String value();

  /**
   * The number of argument expected for function
   */
  String numberOfArguments() default "";

}
