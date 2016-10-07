package lang.sel.parsers.math;

import lang.sel.commons.operators.logic.NotOperator;
import lang.sel.commons.operators.math.*;
import lang.sel.core.EngineContext;
import lang.sel.core.ExpressionParser;
import lang.sel.exceptions.ExpressionSemanticException;
import lang.sel.interfaces.ExecutionData;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author diegorubin
 */
@RunWith(MockitoJUnitRunner.class)
public class MathParserTest {

  private EngineContext engineContext;

  @Before
  public void setup() {
    engineContext = new EngineContext();
    engineContext.addBinaryOperator("GTE", GreaterThanEqualsOperator.class);
    engineContext.addBinaryOperator("GT", GreaterThanOperator.class);
    engineContext.addBinaryOperator("LTE", LessThanEqualsOperator.class);
    engineContext.addBinaryOperator("LT", LessThanOperator.class);
    engineContext.addBinaryOperator("EQ", EqualsOperator.class);
    engineContext.addBinaryOperator("PLUS", PlusOperator.class);
    engineContext.addUnaryOperator("NOT", NotOperator.class);
  }

  @Test
  public void shouldGetTrueIfGreater() {
    ExpressionParser parser = new ExpressionParser("4 GTE 2.0", engineContext, new ExecutionData() {
    });
    Assert.assertTrue((Boolean) parser.evaluate().getResult());
  }

  @Test
  public void shouldGetTrueIfLess() {
    ExpressionParser parser = new ExpressionParser("2 LTE 4.0", engineContext, new ExecutionData() {
    });
    Assert.assertTrue((Boolean) parser.evaluate().getResult());
  }

  @Test
  public void shouldGetFalseIfNotGreater() {
    ExpressionParser parser = new ExpressionParser("1.0 GTE 5.0", engineContext, new ExecutionData() {
    });
    Assert.assertFalse((Boolean) parser.evaluate().getResult());
  }

  @Test
  public void shouldGetFalseIfLess() {
    ExpressionParser parser = new ExpressionParser("4 LTE 2.0", engineContext, new ExecutionData() {
    });
    Assert.assertFalse((Boolean) parser.evaluate().getResult());
  }

  @Test
  public void shouldGetFalseIfEqualsInGreater() {
    ExpressionParser parser = new ExpressionParser("1.0 GT 1", engineContext, new ExecutionData() {
    });
    Assert.assertFalse((Boolean) parser.evaluate().getResult());
  }

  @Test
  public void shouldGetTrueIfIsGreater() {
    ExpressionParser parser = new ExpressionParser("2.0 GT 1", engineContext, new ExecutionData() {
    });
    Assert.assertTrue((Boolean) parser.evaluate().getResult());
  }

  @Test
  public void shouldGetFalseIfEqualsInLess() {
    ExpressionParser parser = new ExpressionParser("1.0 LT 1", engineContext, new ExecutionData() {
    });
    Assert.assertFalse((Boolean) parser.evaluate().getResult());
  }

  @Test
  public void shouldGetTrueIfIsLess() {
    ExpressionParser parser = new ExpressionParser("1.0 LT 2", engineContext, new ExecutionData() {
    });
    Assert.assertTrue((Boolean) parser.evaluate().getResult());
  }

  @Test
  public void shouldGetTrueIfEquals() {
    ExpressionParser parser = new ExpressionParser("1.0 EQ 1", engineContext, new ExecutionData() {
    });
    Assert.assertTrue((Boolean) parser.evaluate().getResult());
  }

  @Test
  public void shouldGetTrueIfNotEquals() {
    ExpressionParser parser = new ExpressionParser("NOT(1.0 EQ 1)", engineContext, new ExecutionData() {
    });
    Assert.assertFalse((Boolean) parser.evaluate().getResult());
  }

  @Test
  public void shouldSum2Plus2() {
    ExpressionParser parser = new ExpressionParser("2 PLUS 2", engineContext, new ExecutionData() {
    });
    Assert.assertEquals(parser.evaluate().getResult().toString(), "4");
  }

  @Test
  public void shouldCheckIf2Plus2Equals4() {
    ExpressionParser parser = new ExpressionParser("2 PLUS 2 EQ 4.0", engineContext, new ExecutionData() {
    });
    Assert.assertTrue((Boolean) parser.evaluate().getResult());
  }

  @Test(expected = ExpressionSemanticException.class)
  public void shouldThrowSemanticExceptionIfWrongDataType() {
    ExpressionParser parser = new ExpressionParser("1.0 GTE 'test'", engineContext, new ExecutionData() {
    });
    parser.evaluate().getResult();
  }

}
