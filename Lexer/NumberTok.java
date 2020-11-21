package Lexer;

public class NumberTok extends Token {
	public int NumLexeme;
	public NumberTok(int tag, int n) { super(tag); NumLexeme = n;}
	public String toString() { return "<" + tag + ", " + NumLexeme + ">"; }
}