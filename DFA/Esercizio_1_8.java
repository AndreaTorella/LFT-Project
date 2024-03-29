package DFA;


public class Esercizio_1_8 {
    public static boolean scan(String s)  //L`automa prende in input una stringa di caratteri
    {
	int state = 0;
	int i = 0; // Per indicare il prossimo carattere da analizzare della stringa

	while (state >= 0 && i < s.length()) {
	    final char ch = s.charAt(i++);

	    switch (state) {
        case 0:
        if (ch == 'u' || ch == 'U')
            state = 1;
        else
            state = 4;
		break;

        case 1:
        if (ch == 'g' || ch == 'G')
            state = 2;
        else
            state = 6;
		break;

        case 2:
            state = 3;
		break;

        case 3:
        state = -1; // caso pozzo 
        break;
        
        case 4:
        if(ch == 'g')
            state = 5;
        else
            state = -1;
        break;

        case 5:
        if(ch == 'o')
            state = 3;
        else
            state = -1;
        break;

        case 6:
        if(ch == 'o')
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