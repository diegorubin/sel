package lang.sel.core;

/**
 * Keywords used on promotion expressions
 *
 * @author diegorubin
 */
public enum Keyword {

  /** ID OF AN ENTITY */
  ID,

  /** CHARACTER TO FINAL EXPRESSION */
  END_EXPRESSION,

  /** Start parentheses*/
  SP,
  /** End parentheses*/
  EP,

  IF,
  ELSE,
  END,
  RETURN,
  ASSIGNMENT,

  END_STMT,
  FACTOR_SEPARATOR,

  BOOLEAN,
  INTEGER,
  FLOAT,
  STRING;

  public static final char FACTOR_SEPARATOR_CHAR = ',';
  public static final char END_EXPRESSION_CHAR = '\0';

}
