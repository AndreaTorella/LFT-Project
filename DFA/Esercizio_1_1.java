package DFA;


// Automa che riconosce stringhe di 0 e 1 in cui ci sono tre 0 consecutivi

public class Esercizio_1_1
{
    public static boolean scan(String s)  //L`automa prende in input una stringa di caratteri
    {
	int state = 0;
	int i = 0; // Per indicare il prossimo carattere da analizzare della stringa

	while (state >= 0 && i < s.length()) {
	    final char ch = s.charAt(i++);

	    switch (state) {
	    case 0:
		if (ch == '0')
		    state = 1;
		else if (ch == '1')
		    state = 0;
		else
		    state = -1;
		break;

	    case 1:
		if (ch == '0')
		    state = 2;
		else if (ch == '1')
		    state = 0;
		else
		    state = -1;
		break;

	    case 2:
		if (ch == '0')
		    state = 3;
		else if (ch == '1')
		    state = 0;
		else
		    state = -1;
		break;

	    case 3:
		if (ch == '0' || ch == '1')
		    state = 3;
		else
		    state = -1;
		break;
	    }
	}
	return state == 3;
    }

    public static void main(String[] args)
    {
	System.out.println(scan(args[0]) ? "OK" : "NOPE"); // if scan(args0) == true return OK else NOPE
    }
}