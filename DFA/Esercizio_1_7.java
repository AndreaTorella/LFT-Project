package DFA;

/*
Progettare e implementare un DFA con alfabeto {a, b} che riconosca il linguaggio
delle stringhe tali che a occorre almeno una volta in una delle ultime tre posizioni della stringa.
Come nell’esercizio 1.6, il DFA deve accettare anche stringhe che contengono meno di tre simboli
(ma almeno uno dei simboli deve essere a).
Esempi di stringhe accettate: “abb”, “bbaba”, “baaaaaaa”, “aaaaaaa”, “a”, “ba”, “bba”,
“aa”, “bbbababab”
Esempi di stringhe non accettate: “abbbbbb”, “bbabbbbbbbb”, “b”
*/

public class Esercizio_1_7 {

    public static boolean scan(String s)  //L`automa prende in input una stringa di caratteri
    {
	int state = 0;
	int i = 0; // Per indicare il prossimo carattere da analizzare della stringa

	while (state >= 0 && i < s.length()) {
	    final char ch = s.charAt(i++);

	    switch (state) {
        case 0:
        if (ch == 'b')
            state = 4;
        else if (ch == 'a')
            state = 1;
        else
            state = -1;
		break;

	    case 1:
        if (ch == 'b')
            state = 2;
        else if (ch == 'a')
            state = 1;
        else
            state = -1;
		break;

	    case 2:
		if (ch == 'b')
            state = 3;
        else if (ch == 'a')
            state = 1;
        else
            state = -1;
		break;

	    case 3: 
		if (ch == 'b')
            state = 4;
        else if (ch == 'a')
            state = 1;
        else
            state = -1;
        break;
        
        case 4:
        if (ch == 'b')
            state = 4;
        else if (ch == 'a')
            state = 1;
        else
            state = -1;
        break;
	    }
	}
	return state == 1 || state == 2 || state == 3;
    }

    public static void main(String[] args)
    {
	System.out.println(scan(args[0]) ? "OK" : "NOPE"); // if scan(args0) == true return OK else NOPE
    }
    
}
