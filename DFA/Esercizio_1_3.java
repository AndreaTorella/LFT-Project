package DFA;


public class Esercizio_1_3 {

    public static boolean scan(String s)
    {
        int state = 0;
        int i = 0;

        while (state >= 0 && i < s.length()) {
            final char ch = s.charAt(i++);

            switch (state) {

            case 0:
            if (((ch >= 65) && (ch <= 90)) || ((ch >= 97) && (ch <= 122))) // ch è una lettera, vado nello stato pozzo
                state = -1;
            else if (ch >= 48 && ch <= 57){// ch è un numero
                if (ch == '0' || ch == '2' || ch == '4' || ch == '6' || ch == '8') // ch è un numero pari
                    state = 1;
                else
                    state = 2; //ch è un numero dispari
            }
            else
                state = -1; 
            break;

            case 1:
            if (ch >= 48 && ch <= 57){// ch è un numero
                if (ch == '0' || ch == '2' || ch == '4' || ch == '6' || ch == '8') // ch è un numero pari
                    state = 1;
                else
                    state = 2; //ch è un numero dispari
            }
            else if ((ch >= 76 && ch <= 90) || (ch >= 108 && ch <= 122)) // ch è una lettera L-Z, vado nello stato pozzo 
                state = -1;
            else if ((ch >= 65 && ch <= 75) || (ch >= 97 && ch <= 107)) // ch è una lettera A-K, giusto, cambio stato
                state = 3;
            else
                state = -1;
            break;

            case 2:
            if (ch >= 48 && ch <= 57){// ch è un numero
                if (ch == '0' || ch == '2' || ch == '4' || ch == '6' || ch == '8') // ch è un numero pari
                    state = 1;
                else
                    state = 2; //ch è un numero dispari
            }
            else if ((ch >= 76 && ch <= 90) || (ch >= 108 && ch <= 122)) // ch è una lettera L-Z, giusto , cambio stato
                state = 4;
            else if ((ch >= 65 && ch <= 75) || (ch >= 97 && ch <= 107)) // ch è una lettera A-K, vado nello stato pozzo
                state = -1;
            else
                state = -1;
            break;

            case 3:
            if (((ch >= 65) && (ch <= 90)) || ((ch >= 97) && (ch <= 122))) // ch è una lettera, resto nello stato 3
                state = 3;
            else if (ch >= 48 && ch <= 57) // ch è un numero, vado nello stato pozzo
                state = -1; 
            else
                state = -1;
            break;

            case 4:
            if (((ch >= 65) && (ch <= 90)) || ((ch >= 97) && (ch <= 122))) // ch è una lettera, resto nello stato 4
                state = 4;
            else if (ch >= 48 && ch <= 57) // ch è un numero, vado nello stato pozzo
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
