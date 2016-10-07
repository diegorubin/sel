package lang.sel.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Operator annotation
 * <p>
 * This annotation is used to declare an operator.
 * <p>
 * The value of annotation it will be the name used in the expression.
 * <p>
 * In engine core there are two types of operators, binary and unary. The operator class should implements
 * BinaryOperator or UnaryOperator interface.
 *
 * @author diegorubin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Operator {

  /**
   * The name of operator used in expression.
   *
   * @return the name of operator
   */
  String value() default "";

}
