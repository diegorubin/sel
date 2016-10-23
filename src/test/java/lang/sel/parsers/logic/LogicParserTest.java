package lang.sel.parsers.logic;

import lang.sel.commons.constants.logic.FalseConstant;
import lang.sel.commons.constants.logic.TrueConstant;
import lang.sel.commons.operators.logic.AndOperator;
import lang.sel.commons.operators.logic.NotOperator;
import lang.sel.commons.operators.logic.OrOperator;
import lang.sel.core.SelContext;
import lang.sel.core.SelParser;
import lang.sel.core.wrappers.FunctionOptions;
import lang.sel.exceptions.SelSemanticException;
import lang.sel.interfaces.ExecutionData;
import lang.sel.parsers.logic.functions.ReturnNotCommonResultFunction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author diegorubin
 */
@RunWith(MockitoJUnitRunner.class)
public class LogicParserTest {
  private SelContext selContext;

  @Before
  public void setup() {
    selContext = new SelContext();
    selContext.addBinaryOperator("AND", AndOperator.class);
    selContext.addBinaryOperator("OR", OrOperator.class);
    selContext.addUnaryOperator("NOT", NotOperator.class);
    selContext.addConstant("TRUE", TrueConstant.class);
    selContext.addConstant("FALSE", FalseConstant.class);
    selContext.addFunction("ERROR", ReturnNotCommonResultFunction.class, new FunctionOptions());
  }

  @Test
  public void shouldReturnTrue() {
    SelParser parser = new SelParser("TRUE AND TRUE", selContext, new ExecutionData() {
    });
    Assert.assertTrue((Boolean) parser.evaluate().getResult());
  }

  @Test
  public void shouldReturnFalse() {
    SelParser parser = new SelParser("NOT TRUE", selContext, new ExecutionData() {
    });
    Assert.assertFalse((Boolean) parser.evaluate().getResult());
  }

  @Test
  public void shouldReturnTrueWithOrOperation() {
    SelParser parser = new SelParser("TRUE OR FALSE", selContext, new ExecutionData() {
    });
    Assert.assertTrue((Boolean) parser.evaluate().getResult());
  }

  @Test(expected = SelSemanticException.class)
  public void shouldThrowSemanticExceptionIfWrongNotArgument() {
    SelParser parser = new SelParser("NOT 1", selContext, new ExecutionData() {
    });
    parser.evaluate();
  }

  @Test(expected = SelSemanticException.class)
  public void shouldThrowSemanticExceptionIfWrongDataTypeIAnd() {
    SelParser parser = new SelParser("TRUE AND 'test'", selContext, new ExecutionData() {
    });
    parser.evaluate().getResult();
  }

  @Test(expected = SelSemanticException.class)
  public void shouldThrowSemanticExceptionIfWrongDataTypeIOr() {
    SelParser parser = new SelParser("1 OR FALSE", selContext, new ExecutionData() {
    });
    parser.evaluate().getResult();
  }

  @Test(expected = SelSemanticException.class)
  public void shouldNotRespondToInAndOperator() {
    SelParser parser = new SelParser("TRUE AND ERROR()", selContext, new ExecutionData() {
    });
    parser.evaluate();
  }

  @Test(expected = SelSemanticException.class)
  public void shouldNotRespondToForNotOperator() {
    SelParser parser = new SelParser("NOT ERROR()", selContext, new ExecutionData() {
    });
    parser.evaluate();
  }

}
