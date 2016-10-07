package lang.sel.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Constant Annotation
 * <p>
 * This annotation is used to declare a constant.
 * <p>
 * The value of annotation it will be the name used in the expression.
 * <p>
 * The class should be implements the SimpleConstant interface.
 * <p>
 * In engine core, this annotation is used to declare true and false constants.
 *
 * @author diegorubin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Constant {

  /**
   * The name of constant used in expression.
   *
   * @return the name of constant
   */
  String value() default "";
}
