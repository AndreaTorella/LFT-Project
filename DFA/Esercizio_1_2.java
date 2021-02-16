package DFA;


public class Esercizio_1_2 {
    public static boolean scan(String s)
    {
        int state = 0;
        int i = 0;

        while (state >= 0 && i < s.length()) {
            final char ch = s.charAt(i++);

            switch (state) {
            case 0:
            if (ch >= 48 && ch <= 57) // Se è un numero
                state = -1;
            else if (ch == '_') 
                state = 1;
            else if ((ch >= 48 && ch <= 57) || (ch >= 65 && ch <= 90) || (ch >= 97 && ch <= 122) || (ch == '_')) // Se è numero, lettera maiscuola o min, underscore
                state = 2;
            else
                state = -1; 
            break;

            case 1:
            if (ch == '_')
                state = 1;
            else if ((ch >= 48 && ch <= 57) || (ch >= 65 && ch <= 90) || (ch >= 97 && ch <= 122) || (ch == '_'))
                state = 2;
            else
                state = -1;
            break;

            case 2:
            if ((ch >= 48 && ch <= 57) || (ch >= 65 && ch <= 90) || (ch >= 97 && ch <= 122) || (ch == '_'))
                state = 2;
            else
                state = -1;
            break;
            }
        }
        return state == 2;
    }

    public static void main(String[] args)
    {
	System.out.println(scan(args[0]) ? "OK" : "NOPE");
    }
}
