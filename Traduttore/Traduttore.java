package Traduttore;

import java.io.*;
import Lexer.*;

public class Traduttore {
    private Lexer lex;
    private BufferedReader pbr;
    private Token look;

    SymbolTable st = new SymbolTable();
    CodeGenerator code = new CodeGenerator();
    int count = 0;

    public Traduttore(Lexer l, BufferedReader br) { // avvia il parsing
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
        throw new Error("near line " + Lexer.line + ": " + s + "token: " + look.tag);
    }

    void match(int t) {
        if (look.tag == t) {
            if (look.tag != Tag.EOF)
                move();
        } else
            error("syntax error : instead of " + t + " found");
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
                try {
                    code.toJasmin();
                } catch (java.io.IOException e) {
                    System.out.println("IO error\n");
                }

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
                statlistp();
                break;

            default:
                error("statlist");
        }
    }

    private void statlistp() {
        switch (look.tag) {
            // <statlistp> → ; <stat> <statlistp>
            case ';':
                match(';');
                stat();
                statlistp();
                break;

            // <statlistp> → ε
            case Tag.EOF:
            case '}':
                break;

            default:
                error("statlistp");
        }
    }

    private void stat() {
        switch (look.tag) {
            // <stat> → = ID <expr>
            case '=':
                match('=');
                if (look.tag == Tag.ID) {
                    int id_addr = st.lookupAddress(((Word) look).lexeme); // Cerca identificatore nella simble table
                    if (id_addr == -1) { // non trovato
                        id_addr = count;
                        st.insert(((Word) look).lexeme, count++);
                    }
                    match(Tag.ID);
                    expr();
                    code.emit(OpCode.istore, id_addr); // Richiamo istore
                } else
                    error("Error in grammar (stat) after read( with " + look);
                break;

            // <stat> → print(<exprlist>)
            case Tag.PRINT:
                match(Tag.PRINT);
                match('(');
                exprlist(1); // Devo comunicare a exprlist di stampare ogni valore che mette sulla pila
                match(')');
                break;

            // <stat> → read(ID)
            case Tag.READ:
                match(Tag.READ);
                match('(');
                if (look.tag == Tag.ID) {
                    int id_addr = st.lookupAddress(((Word) look).lexeme); // Cerca identificatore nella simble table
                    if (id_addr == -1) { // non trovato
                        id_addr = count;
                        st.insert(((Word) look).lexeme, count++);
                    }
                    match(Tag.ID);
                    match(')');
                    code.emit(OpCode.invokestatic, 0); // Metto valore sulla pila
                    code.emit(OpCode.istore, id_addr); // Richiamo istore
                } else
                    error("Error in grammar (stat) after read( with " + look);
                break;

            // <stat> → cond <whenlist> else <stat>
            case Tag.COND:
                int stat_next = code.newLabel();
                match(Tag.COND);
                whenlist(stat_next);
                match(Tag.ELSE);
                stat();
                code.emitLabel(stat_next);
                break;

            // <stat> → while(<bexpr>) <stat>
            case Tag.WHILE:
                int B_true = code.newLabel();
                int B_false = code.newLabel();
                int S1_next = code.newLabel();
                match(Tag.WHILE);
                match('(');
                code.emitLabel(S1_next);
                bexpr(B_true, B_false); // ha 2 valori sulla pila e fa un controllo
                code.emitLabel(B_true);
                match(')');
                stat();
                code.emit(OpCode.GOto, S1_next);
                code.emitLabel(B_false);
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

    private void whenlist(int whenlist_next) {
        switch (look.tag) {
            // <whenlist> → <whenitem> <whenlistp>
            case Tag.WHEN:
                whenitem(whenlist_next);
                whenlistsp(whenlist_next);
                break;

            default:
                error("whenlist");
        }
    }

    private void whenlistsp(int whenlistp_next) {
        switch (look.tag) {
            // <whenlistp> → <whenitem> <whenlistp>
            case Tag.WHEN:
                whenitem(whenlistp_next);
                whenlistsp(whenlistp_next);
                break;

            // <whenlistp> → ε
            case Tag.ELSE:
                break;

            default:
                error("whenlistsp");
        }
    }

    private void whenitem(int whenitem_next) {
        switch (look.tag) {
            // <whenitem> → when(<bexpr>) do <stat>
            case Tag.WHEN: {
                int B_true = code.newLabel();
                int B_false = code.newLabel();
                match(Tag.WHEN);
                match('(');
                bexpr(B_true, B_false);
                match(')');
                match(Tag.DO);
                code.emitLabel(B_true);
                stat();
                code.emit(OpCode.GOto, whenitem_next);
                code.emitLabel(B_false);
                break;
            }

            default:
                error("whenitem");
        }
    }

    private void bexpr(int B_true, int B_false) {
        switch (look.tag) {
            // <bexpr> → RELOP <expr> <expr>
            case Tag.RELOP: {
                String rel = ((Word) look).lexeme;
                match(Tag.RELOP);
                expr();
                expr();
                switch (rel) {
                    case ">":
                        code.emit(OpCode.if_icmpgt, B_true);
                        break;

                    case "<":
                        code.emit(OpCode.if_icmplt, B_true);
                        break;

                    case "=":
                        code.emit(OpCode.if_icmpeq, B_true);
                        break;

                    case ">=":
                        code.emit(OpCode.if_icmpge, B_true);
                        break;

                    case "<=":
                        code.emit(OpCode.if_icmple, B_true);
                        break;

                    case "<>":
                        code.emit(OpCode.if_icmpne, B_true);
                        break;
                }
                code.emit(OpCode.GOto, B_false);
                break;
            }
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
                exprlist(2);
                match(')');
                break;

            // <expr> → - <expr> <expr>
            case '-':
                match('-');
                expr();
                expr();
                code.emit(OpCode.isub);
                break;

            // <expr> → * <exprlist>
            case '*':
                match('*');
                match('(');
                exprlist(3);
                match(')');
                break;

            // <expr> → / <expr> <expr>
            case '/':
                match('/');
                expr();
                expr();
                code.emit(OpCode.idiv);
                break;

            // <expr> → NUM
            case Tag.NUM:
                code.emit(OpCode.ldc, ((NumberTok) look).NumLexeme);
                match(Tag.NUM);
                break;

            // <expr> → ID
            case Tag.ID:
                code.emit(OpCode.iload, st.lookupAddress(((Word) look).lexeme)); // Prendi dalla simble table la
                                                                                 // variabile di nome lexeme e
                                                                                 // restituisci indirizzo
                match(Tag.ID);
                break;

            default:
                error("expr");

        }
    }

    private void exprlist(int flag) {
        switch (look.tag) {
            // <exprlist> → <expr> <exprlistp>
            case '+':
            case '-':
            case '*':
            case '/':
            case Tag.NUM:
            case Tag.ID:
                expr();
                if (flag == 1) {
                    code.emit(OpCode.invokestatic, 1);
                }
                exprlistsp(flag);
                break;

            default:
                error("exprlist");
        }
    }

    private void exprlistsp(int flag) {
        switch (look.tag) {
            // <exprlistp> → <expr> <exprlistp>
            case '+':
            case '-':
            case '*':
            case '/':
            case Tag.NUM:
            case Tag.ID:
                expr();
                switch (flag) {
                    case 1:
                        code.emit(OpCode.invokestatic, 1);
                        break;
                    case 2:
                        code.emit(OpCode.iadd);
                        break;
                    case 3:
                        code.emit(OpCode.imul);
                        break;
                }
                exprlistsp(flag);
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
        String path = "prova.txt";
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Traduttore translator = new Traduttore(lex, br);
            translator.prog();
            System.out.println("Input OK");
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
