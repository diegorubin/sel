package lang.sel.core;

import lang.sel.exceptions.ExpressionParserException;

import static lang.sel.core.Keyword.END_EXPRESSION_CHAR;

/**
 * Expression Lexer
 * <p>
 * In this class are implemented all regular grammars used in the parser
 *
 * @author diegorubin
 */
class ExpressionLexer {

  private String buffer;
  private StringBuilder lexeme;

  private Keyword lookahead;

  private int position = 0;

  /**
   * Instantiates a new Expression lexer.
   *
   * @param expression promotion expression that will be parsed by the lexer.
   */
  ExpressionLexer(String expression) {
    buffer = expression + END_EXPRESSION_CHAR + END_EXPRESSION_CHAR;
  }

  /**
   * Returns lexeme
   *
   * @return the lexeme
   */
  String getLexeme() {
    return lexeme.toString();
  }

  /**
   * Get current position of buffer
   *
   * @return position
   */
  Integer getPosition() {
    return position;
  }

  Keyword getLookahead() {
    return lookahead;
  }

  /**
   * Check if expected is the current token.
   * <p>
   * If the expected token is different of the current token a exception is throw.
   *
   * @param expected the expected token
   */
  void match(final Keyword expected) {
    if (lookahead == expected) {
      lookahead = getToken();
    } else {
      throw new ExpressionParserException(
          "expected \"" + expected + "\", but found \"" + lookahead + "\" in position " + position);
    }
  }

  /**
   * Get the next token from expression
   *
   * @return the token
   */
  Keyword getToken() {
    Keyword token;

    lexeme = new StringBuilder();

    skipSpaces();

    if ((token = isSpecialCharacter()) != null) {
      return token;
    }

    if ((token = isNumber()) != null) {
      return token;
    }

    if (isString()) {
      return Keyword.STRING;
    }

    if (isID() != null) {
      if ((token = isStmt()) != null) {
        return token;
      }
      return Keyword.ID;
    }

    return isEndExpression();
  }

  /**
   * Check if current char is special character, like '(', ')', ';', '\n' or ','
   *
   * @return the token
   */
  private Keyword isSpecialCharacter() {
    if (isStartParentheses()) {
      return Keyword.SP;
    }

    if (isEndParentheses()) {
      return Keyword.EP;
    }

    if (isFactorSeparator()) {
      return Keyword.FACTOR_SEPARATOR;
    }

    if (isAssigment()) {
      return Keyword.ASSIGNMENT;
    }

    if (isEndStmt(getChar())) {
      return Keyword.END_STMT;
    }
    ungetChar();

    return null;
  }

  /**
   * Check if current token is a float or an integer
   *
   * @return the token
   */
  private Keyword isNumber() {
    char head = getChar();

    if (isFloat(head)) {
      return Keyword.FLOAT;
    }

    if (Character.isDigit(head)) {
      lexeme.append(head);
      while (Character.isDigit(head = getChar())) {
        lexeme.append(head);
      }

      if (isFloat(head)) {
        return Keyword.FLOAT;
      } else {
        ungetChar();
        return Keyword.INTEGER;
      }
    }
    ungetChar();
    return null;
  }


  /**
   * Check if current number is a float.
   *
   * @param head the current char
   *
   * @return true if number is a float
   */
  private boolean isFloat(char head) {
    if (head == '.') {
      lexeme.append(head);

      char c;
      while (Character.isDigit(c = getChar())) {
        lexeme.append(c);
      }
      ungetChar();
      return true;
    }

    return false;
  }

  /**
   * Check if current char is a end of expression representation
   *
   * @return the end of expression token
   */
  private Keyword isEndExpression() {
    if (getChar() == END_EXPRESSION_CHAR) {
      return Keyword.END_EXPRESSION;
    }
    ungetChar();
    return null;
  }

  /**
   * Check if current token is a ID
   *
   * @return the ID token
   */
  private Keyword isID() {
    char head = getChar();
    if (Character.isLetter(head)) {
      lexeme.append(head);

      while (Character.isLetterOrDigit(head = getChar()) || head == '_') {
        lexeme.append(head);
      }

      ungetChar();

      return Keyword.ID;
    }
    ungetChar();
    return null;
  }

  private Keyword isStmt() {
    if (lexeme.toString().equals(Keyword.IF.toString())) {
      return Keyword.IF;
    }

    if (lexeme.toString().equals(Keyword.ELSE.toString())) {
      return Keyword.ELSE;
    }

    if (lexeme.toString().equals(Keyword.RETURN.toString())) {
      return Keyword.RETURN;
    }

    if (lexeme.toString().equals(Keyword.END.toString())) {
      return Keyword.END;
    }

    return null;
  }

  private boolean isString() {
    char head = getChar();
    if (head == '\'') {
      while ((head = getChar()) != '\'') {
        lexeme.append(head);
      }
      return true;
    }
    ungetChar();
    return false;
  }

  private boolean isFactorSeparator() {
    if (getChar() == Keyword.FACTOR_SEPARATOR_CHAR) {
      return true;
    }
    ungetChar();
    return false;
  }

  private boolean isStartParentheses() {
    if (getChar() == '(') {
      return true;
    }
    ungetChar();
    return false;
  }

  private boolean isAssigment() {
    if (getChar() == '=') {
      return true;
    }
    ungetChar();
    return false;
  }

  private boolean isEndStmt(char head) {
    return head == ';';
  }

  private boolean isEndParentheses() {
    if ((getChar()) == ')') {
      return true;
    }
    ungetChar();
    return false;
  }

  private void skipSpaces() {
    while (isSpace(getChar())) {
      // this while remove spaces
    }
    ungetChar();
  }

  private boolean isSpace(final char c) {
    return c == ' ' || c == '\n' || c == '\t';
  }

  private char getChar() {
    try {
      return buffer.charAt(position++);
    } catch (StringIndexOutOfBoundsException e) {
      throw new ExpressionParserException("error on get char, lookahead \"" + lookahead + "\" in position " + position);
    }

  }

  private void ungetChar() {
    position--;
  }

}

