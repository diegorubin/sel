package lang.sel.parsers.logic;

import lang.sel.commons.constants.logic.FalseConstant;
import lang.sel.commons.constants.logic.TrueConstant;
import lang.sel.commons.operators.logic.AndOperator;
import lang.sel.commons.operators.logic.NotOperator;
import lang.sel.commons.operators.logic.OrOperator;
import lang.sel.core.EngineContext;
import lang.sel.core.ExpressionParser;
import lang.sel.core.wrappers.FunctionOptions;
import lang.sel.exceptions.ExpressionSemanticException;
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
  private EngineContext engineContext;

  @Before
  public void setup() {
    engineContext = new EngineContext();
    engineContext.addBinaryOperator("AND", AndOperator.class);
    engineContext.addBinaryOperator("OR", OrOperator.class);
    engineContext.addUnaryOperator("NOT", NotOperator.class);
    engineContext.addConstant("TRUE", TrueConstant.class);
    engineContext.addConstant("FALSE", FalseConstant.class);
    engineContext.addFunction("ERROR", ReturnNotCommonResultFunction.class, new FunctionOptions());
  }

  @Test
  public void shouldReturnTrue() {
    ExpressionParser parser = new ExpressionParser("TRUE AND TRUE", engineContext, new ExecutionData() {
    });
    Assert.assertTrue((Boolean) parser.evaluate().getResult());
  }

  @Test
  public void shouldReturnFalse() {
    ExpressionParser parser = new ExpressionParser("NOT TRUE", engineContext, new ExecutionData() {
    });
    Assert.assertFalse((Boolean) parser.evaluate().getResult());
  }

  @Test
  public void shouldReturnTrueWithOrOperation() {
    ExpressionParser parser = new ExpressionParser("TRUE OR FALSE", engineContext, new ExecutionData() {
    });
    Assert.assertTrue((Boolean) parser.evaluate().getResult());
  }

  @Test(expected = ExpressionSemanticException.class)
  public void shouldThrowSemanticExceptionIfWrongNotArgument() {
    ExpressionParser parser = new ExpressionParser("NOT 1", engineContext, new ExecutionData() {
    });
    parser.evaluate();
  }

  @Test(expected = ExpressionSemanticException.class)
  public void shouldThrowSemanticExceptionIfWrongDataTypeIAnd() {
    ExpressionParser parser = new ExpressionParser("TRUE AND 'test'", engineContext, new ExecutionData() {
    });
    parser.evaluate().getResult();
  }

  @Test(expected = ExpressionSemanticException.class)
  public void shouldThrowSemanticExceptionIfWrongDataTypeIOr() {
    ExpressionParser parser = new ExpressionParser("1 OR FALSE", engineContext, new ExecutionData() {
    });
    parser.evaluate().getResult();
  }

  @Test(expected = ExpressionSemanticException.class)
  public void shouldNotRespondToInAndOperator() {
    ExpressionParser parser = new ExpressionParser("TRUE AND ERROR()", engineContext, new ExecutionData() {
    });
    parser.evaluate();
  }

  @Test(expected = ExpressionSemanticException.class)
  public void shouldNotRespondToForNotOperator() {
    ExpressionParser parser = new ExpressionParser("NOT ERROR()", engineContext, new ExecutionData() {
    });
    parser.evaluate();
  }

}
