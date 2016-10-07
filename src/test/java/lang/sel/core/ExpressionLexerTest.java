package lang.sel.core;

import lang.sel.exceptions.ExpressionParserException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author diegorubin
 */
@RunWith(MockitoJUnitRunner.class)
public class ExpressionLexerTest {

  @Test
  public void shouldGetId() {
    final String expr = "lista";

    final ExpressionLexer lexer = new ExpressionLexer(expr);
    Assert.assertEquals(lexer.getToken(), Keyword.ID);
    Assert.assertEquals(lexer.getLexeme(), expr);
  }

  @Test
  public void shouldGetAnd() {
    final String expr = "AND";

    final ExpressionLexer lexer = new ExpressionLexer(expr);
    Assert.assertEquals(lexer.getToken(), Keyword.ID);
    Assert.assertEquals(lexer.getLexeme(), expr);
  }

  @Test
  public void shouldGetNot() {
    final String expr = "NOT";

    final ExpressionLexer lexer = new ExpressionLexer(expr);
    Assert.assertEquals(lexer.getToken(), Keyword.ID);
    Assert.assertEquals(lexer.getLexeme(), expr);
  }

  @Test
  public void shouldGetKeywordsSkippingWhitespace() {
    final String expr = "AND TESTE";

    final ExpressionLexer lexer = new ExpressionLexer(expr);
    Assert.assertEquals(lexer.getToken(), Keyword.ID);
    Assert.assertEquals(lexer.getToken(), Keyword.ID);
  }

  @Test
  public void shouldGetStartParentheses() {
    final String expr = "(";

    final ExpressionLexer lexer = new ExpressionLexer(expr);
    Assert.assertEquals(lexer.getToken(), Keyword.SP);
  }

  @Test
  public void shouldGetEndParentheses() {
    final String expr = ")";

    final ExpressionLexer lexer = new ExpressionLexer(expr);
    Assert.assertEquals(lexer.getToken(), Keyword.EP);
  }

  @Test
  public void shouldGetString() {
    final String expr = "TESTE('nike')";

    final ExpressionLexer lexer = new ExpressionLexer(expr);
    Assert.assertEquals(lexer.getToken(), Keyword.ID);
    Assert.assertEquals(lexer.getToken(), Keyword.SP);
    Assert.assertEquals(lexer.getToken(), Keyword.STRING);
    Assert.assertEquals(lexer.getLexeme(), "nike");
    Assert.assertEquals(lexer.getToken(), Keyword.EP);
  }

  @Test
  public void shouldGetExpression() {
    final String expr = "TESTE(list) AND TESTE('nike')";

    final ExpressionLexer lexer = new ExpressionLexer(expr);
    Assert.assertEquals(lexer.getToken(), Keyword.ID);
    Assert.assertEquals(lexer.getToken(), Keyword.SP);
    Assert.assertEquals(lexer.getToken(), Keyword.ID);
    Assert.assertEquals(lexer.getLexeme(), "list");
    Assert.assertEquals(lexer.getToken(), Keyword.EP);
    Assert.assertEquals(lexer.getToken(), Keyword.ID);
    Assert.assertEquals(lexer.getToken(), Keyword.ID);
    Assert.assertEquals(lexer.getToken(), Keyword.SP);
    Assert.assertEquals(lexer.getToken(), Keyword.STRING);
    Assert.assertEquals(lexer.getLexeme(), "nike");
    Assert.assertEquals(lexer.getToken(), Keyword.EP);
  }

  @Test
  public void shouldGetTokensForExpressionList() {
    final String expr = "TESTE(list, 'nike')";

    final ExpressionLexer lexer = new ExpressionLexer(expr);
    Assert.assertEquals(lexer.getToken(), Keyword.ID);
    Assert.assertEquals(lexer.getToken(), Keyword.SP);
    Assert.assertEquals(lexer.getToken(), Keyword.ID);
    Assert.assertEquals(lexer.getToken(), Keyword.FACTOR_SEPARATOR);
    Assert.assertEquals(lexer.getToken(), Keyword.STRING);
    Assert.assertEquals(lexer.getToken(), Keyword.EP);
  }

  @Test
  public void shouldGetNumber() {
    final String expr = "123, 1.123, .1234";

    final ExpressionLexer lexer = new ExpressionLexer(expr);
    Assert.assertEquals(lexer.getToken(), Keyword.INTEGER);
    Assert.assertEquals(lexer.getToken(), Keyword.FACTOR_SEPARATOR);
    Assert.assertEquals(lexer.getToken(), Keyword.FLOAT);
    Assert.assertEquals(lexer.getToken(), Keyword.FACTOR_SEPARATOR);
    Assert.assertEquals(lexer.getToken(), Keyword.FLOAT);
  }

  @Test(expected = ExpressionParserException.class)
  public void shouldThrowExceptionForWrongExpression() {
    final String expr = "AND";

    final ExpressionLexer lexer = new ExpressionLexer(expr);
    lexer.match(null);
    lexer.match(Keyword.SP);
  }

  @Test(expected = ExpressionParserException.class)
  public void shouldThrowExceptionForWrongString() {
    final String expr = "'wrong string";

    final ExpressionLexer lexer = new ExpressionLexer(expr);
    lexer.match(null);
    lexer.match(Keyword.STRING);
  }
}
