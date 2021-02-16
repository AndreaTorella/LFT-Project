package DFA;


public class Esercizio_1_5 {

    public static boolean scan(String s)
    {
        int state = 0;
        int i = 0;

        while (state >= 0 && i < s.length()) {
            final char ch = s.charAt(i++);

            switch (state) {
            case 0:
            if (ch >= 48 && ch <= 57) // ch è un numero
                state = -1; 
            else if ((ch >= 65 && ch <= 75) || (ch >= 97 && ch <= 107)) // ch è una lettera A-K
                state = 1;
            else if ((ch >= 76 && ch <= 90) || (ch >= 108 && ch <= 122)) // ch è una lettera L-Z
                state = 2; 
            else
                state = -1;
            break;

            case 1:
            if (((ch >= 65) && (ch <= 90)) || ((ch >= 97) && (ch <= 122))) // ch è una lettera
                state = 1;
            else if (ch == '1' || ch == '3' || ch == '5' || ch == '7' || ch == '9') // ch è num dispari
                state = 1;
            else if (ch == '0' || ch == '2' || ch == '4' || ch == '6' || ch == '8') // ch è pari
                state = 3;
            else
                state = -1;
            break;

            case 2:
            if (((ch >= 65) && (ch <= 90)) || ((ch >= 97) && (ch <= 122))) // ch è una lettera
                state = 2;
            else if (ch == '0' || ch == '2' || ch == '4' || ch == '6' || ch == '8') // ch è pari
                state = 2;
            else if (ch == '1' || ch == '3' || ch == '5' || ch == '7' || ch == '9') // ch è num dispari
                state = 4;
            else
                state = -1; 
            break;

            case 3:
            if (ch == '0' || ch == '2' || ch == '4' || ch == '6' || ch == '8') // num pari
                state = 3;
            else if (ch == '1' || ch == '3' || ch == '5' || ch == '7' || ch == '9') // num disp
                state = 1;
            else if (((ch >= 65) && (ch <= 90)) || ((ch >= 97) && (ch <= 122))) // lett
                state = -1; 
            else
                state = -1;
            break;

            case 4:
            if (ch == '0' || ch == '2' || ch == '4' || ch == '6' || ch == '8') // num pari
                state = 2;
            else if (ch == '1' || ch == '3' || ch == '5' || ch == '7' || ch == '9') // ch è num dispari
                state = 4;
            else if (((ch >= 65) && (ch <= 90)) || ((ch >= 97) && (ch <= 122))) // lett
                state = -1; 
            else
                state = -1; 
            break;
            }
        }
        return (state == 3) || (state == 4);
    }

    public static void main(String[] args)
    {
	System.out.println(scan(args[0]) ? "OK" : "NOPE");
    }
    
}
