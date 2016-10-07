package lang.sel.core.wrappers;

import java.util.Optional;

/**
 * Function options
 * <p>
 * Secondary options used on validate expression
 *
 * @author diegorubin
 */
public class FunctionOptions {
  private Integer numberOfArguments;

  public void setNumberOfArguments(Integer numberOfArguments) {
    this.numberOfArguments = numberOfArguments;
  }

  public Optional<Integer> getNumberOfArguments() {
    return Optional.ofNullable(numberOfArguments);
  }
}
