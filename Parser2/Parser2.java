package Parser2;

import java.io.*;
import Lexer.*;

public class Parser2 {
    private Lexer lex;
    private BufferedReader pbr;
    private Token look;

    public Parser2(Lexer l, BufferedReader br) { // avvia il parsing
        lex = l;
        pbr = br;
        move();
    }

    void move() {
        look = lex.lexical_scan(pbr); // lexical scan restituisce un token alla volta. Il primo token viene messo in
                                      // look con la chiamata di move() del costruttore
        System.out.println("token = " + look);
    }

    void error(String s) {
        throw new Error("near line " + Lexer.line + ": " + s + "token: "+ look.tag );
    }

    void match(int t) {
        if (look.tag == t) {
            if (look.tag != Tag.EOF)
                move();
        } else
            error("syntax error : instead of "+t+" founfd");
    }

    // Parte l'analisi sintattica. Il primo token che viene analizzato è quello che
    // è sttao messo in look dalla prima move()
    // chiamata dal costruttore
    public void prog() {
        switch (look.tag) {
            // <prog> → <statlist> EOF
            case '=':
            case Tag.PRINT:
            case Tag.READ:
            case Tag.COND:
            case Tag.WHILE:
            case '{':
                statlist();
                match(Tag.EOF);
                break;

            default:
                error("prog");
        }
    }

    private void statlist() {
        switch (look.tag) {
            // <statlist> → <stat> <statlistp>
            case '=':
            case Tag.PRINT:
            case Tag.READ:
            case Tag.COND:
            case Tag.WHILE:
            case '{':
                stat();
                statlistsp();
                break;

            default:
                error("statlist");
        }
    }

    private void statlistsp() {
        switch (look.tag) {
             // <statlistp> → <stat> <statlistp>
            case ';':
                match(';');
                stat();
                statlistsp();
                break;
            
            // <statlistp> → ε
            case Tag.EOF:
            case '}':
                break;

            default:
                error("statlistsp");
        }
    }

    private void stat() {
        switch (look.tag) {
            // <stat> → = ID <expr>
            case '=':
                match('=');
                match(Tag.ID);
                expr();
                break;

            // <stat> → print(<expr>)
            case Tag.PRINT:
                match(Tag.PRINT);
                match('(');
                exprlist();
                match(')');
                break;

            // <stat> → read(ID)
            case Tag.READ:
                match(Tag.READ);
                match('(');
                match(Tag.ID);
                match(')');
                break;

            // <stat> → cond <whenlist> else <stat>
            case Tag.COND:
                match(Tag.COND);
                whenlist();
                match(Tag.ELSE);
                stat();
                break;

            // <stat> → while(<bexpr>) <stat>
            case Tag.WHILE:
                match(Tag.WHILE);
                match('(');
                bexpr();
                match(')');
                stat();
                break;

            // <stat> → {<statlist>}
            case '{':
                match('{');
                statlist();
                match('}');
                break;

            default:
                error("stat");
        }
    }

    private void whenlist() {
        switch (look.tag) {
            // <whenlist> → <whenitem> <whenlistp>
            case Tag.WHEN:
                whenitem();
                whenlistsp();
                break;

            default:
                error("whenlist");
        }
    }

    private void whenlistsp() {
        switch (look.tag) {
            // <whenlistp> → <whenitem> <whenlistp>
            case Tag.WHEN:
                whenitem();
                whenlistsp();
                break;
            
            // <whenlistp> → ε
            case Tag.ELSE:
                break;

            default:
                error("whenlistsp");
        }
    }

    private void whenitem() {
        switch (look.tag) {
            // <whenitem> → when(<bexpr>) do <stat>
            case Tag.WHEN:
                match(Tag.WHEN);
                match('(');
                bexpr();
                match(')');
                match(Tag.DO);
                stat();
                break;

            default:
                error("whenitem");
        }
    }

    private void bexpr() {
        switch (look.tag) {
            // <bexpr> → RELOP <expr> <expr>
            case Tag.RELOP:
                match(Tag.RELOP);
                expr();
                expr();
                break;

            default:
                error("bexpr");
        }
    }

    private void expr() {
        switch (look.tag) {
            // <expr> → + <exprlist>
            case '+':
                match('+');
                match('(');
                exprlist();
                match(')');
                break;
            
            // <expr> → - <expr> <expr>
            case '-':
                match('-');
                expr();
                expr();
                break;

            // <expr> → * <exprlist>
            case '*':
                match('*');
                match('(');
                exprlist();
                match(')');
                break;

            // <expr> → / <expr> <expr>
            case '/':
                match('/');
                expr();
                expr();
                break;

            // <expr> → NUM
            case Tag.NUM:
                match(Tag.NUM);
                break;

            // <expr> → ID
            case Tag.ID:
                match(Tag.ID);
                break;

            default:
                error("expr");

        }
    }

    private void exprlist() {
        switch (look.tag) {
            // <exprlist> → <expr> <exprlistp>
            case '+':
            case '-':
            case '*':
            case '/':
            case Tag.NUM:
            case Tag.ID:
                expr();
                exprlistsp();
                break;
            
            default:
                error("exprlist");
        }
    }

    private void exprlistsp() {
        switch (look.tag) {
            // <exprlistp> → <expr> <exprlistp>
            case '+':
            case '-':
            case '*':
            case '/':
            case Tag.NUM:
            case Tag.ID:
                expr();
                exprlistsp();
                break;

             // <exprlistp> → ε
            case ')':
                break;
            
            default:
                error("exprlistsp");
        }
    }

    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "D:/Università/SECONDO ANNO/LINGUAGGI FORMALI E TRADUTTORI/Progetto/Parser2/analisi.txt";
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Parser2 parser = new Parser2(lex, br);
            parser.prog();
            System.out.println("Input OK");
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
