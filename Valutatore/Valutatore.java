package Valutatore;

import java.io.*;
import Lexer.*;

public class Valutatore {
    private Lexer lex;
    private BufferedReader pbr;
    private Token look;

    public Valutatore(Lexer l, BufferedReader br) {
        lex = l;
        pbr = br;
        move();
    }

    void move() {
        look = lex.lexical_scan(pbr);
        System.out.println("token = " + look);
    }

    void error(String s) {
        throw new Error("near line " + Lexer.line + ": " + s);
    }

    void match(int t) {
        if (look.tag == t) {
            if (look.tag != Tag.EOF)
                move();
        } else
            error("syntax error");
    }

    public void start() {
        int expr_val;
        switch (look.tag) {
            // <start> --> <exprp> EOF {print(exprp.val)}
            case '(', Tag.NUM:
                expr_val = expr();
                match(Tag.EOF);
                System.out.println(expr_val);
                break;
            default:
                error("Start");
        }
    }

    private int expr() {
        int term_val, exprp_val = 0;
        switch (look.tag) {
            // <expr> --> ⟨term⟩ {exprp.i=term.val} ⟨exprp⟩ {expr.val=exprp.val}
            case '(', Tag.NUM:
                term_val = term();
                exprp_val = exprp(term_val);
                break;
            default:
                error("Expr");
        }
        return exprp_val;
    }

    private int exprp(int exprp_i) {
        int term_val, exprp_val = 0;
        switch (look.tag) {

            // <exprp> --> + ⟨term⟩ {exprp1.i=exprp.i+term.val} ⟨exprp1⟩
            // {exprp.val=exprp1.val}
            case '+':
                match('+');
                term_val = term();
                exprp_val = exprp(exprp_i + term_val);
                break;

            // - ⟨term⟩ {exprp1.i=exprp.i−term.val} ⟨exprp1⟩ {exprp.val=exprp1.val}
            case '-':
                match('-');
                term_val = term();
                exprp_val = exprp(exprp_i - term_val);
                break;

            // <exprp> → ε {exprp.val = exprp.val}
            case Tag.EOF, ')':
                exprp_val = exprp_i;
                break;
            
            default:
                error("Exprp");
        }
        return exprp_val;
    }

    private int term() {
        int fact_val, termp_val=0;
        switch(look.tag){

            // <term> --> ⟨fact⟩ {termp.i=fact.val} ⟨termp⟩ {term.val=termp.val}
            case '(', Tag.NUM:
                fact_val = fact();
                termp_val = termp(fact_val);
                break;

            default:
                error("Term");
        }
        return termp_val;
    }

    private int termp(int termp_i) {
        int fact_val, termp_val=0;
        switch(look.tag){

            // <termp> --> * ⟨fact⟩ {termp1.i=termp.i∗fact.val} ⟨termp1⟩ {termp.val=termp1.val}
            case '*':
                match('*');
                fact_val = fact();
                termp_val = termp(termp_i * fact_val);
                break;
            
            // <termp> --> / ⟨fact⟩ {termp1.i=termp.i/fact.val} ⟨termp1⟩ {termp.val=termp1.val}
            case '/':
                match('/');
                fact_val = fact();
                termp_val = termp(termp_i / fact_val);
                break;
            
            // <termp> --> ε{termp.val=termp.i}
            case Tag.EOF, '+', '-', ')':
                termp_val = termp_i;
                break;

            default:
                error("Termp");
        }
        return termp_val;
    }

    private int fact() {
        int fact_val=0;
        switch(look.tag){

            // <fact> --> (⟨expr⟩) {fact.val=expr.val}
            case '(':
                match('(');
                fact_val = expr(); 
                match(')');
                break;
            
            // <fact> --> NUM {fact.val=NUM.value}
            case Tag.NUM:
                NumberTok NUM_value = (NumberTok) look;
                fact_val = NUM_value.NumLexeme; //valore numerico del terminale, fornito `dall’analizzatore lessicale.
                match(Tag.NUM);
                break;

            default:
                error("Fact");
        }
        return fact_val;
    }

    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "D:/Università/SECONDO ANNO/LINGUAGGI FORMALI E TRADUTTORI/LFT_Project/Valutatore/test.txt"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Valutatore valutatore = new Valutatore(lex, br);
            valutatore.start();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
