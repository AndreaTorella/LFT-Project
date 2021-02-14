package DFA;


/*
Modificare l’automa dell’esercizio precedente in modo che riconosca le combinazioni di matricola e cognome di studenti del turno 2 o del turno 3 del laboratorio,
dove il numero di matricola e il cognome possono essere separati da una sequenza di spazi, e possono essere
precedute e/o seguite da sequenze eventualmente vuote di spazi.
Per esempio, l’automa deve accettare la stringa “654321 Rossi” e “ 123456 Bianchi ” (dove, nel secondo esempio, cisono spazi prima del primo carattere e dopo l’ultimo carattere),
ma non “1234 56Bianchi” e “123456Bia nchi”. 
Per questo esercizio, i cognomi composti (con un numero arbitrario di parti) possono essere accettati: per esempio, la stringa “123456De Gasperi” 
deve essere accettato. Modificare l’implementazione Java dell’automa di conseguenza.
*/
public class Esercizio_1_4 {
    public static boolean scan(String s)  //L`automa prende in input una stringa di caratteri
    {
        int state = 0;
        int i = 0; // Per indicare il prossimo carattere da analizzare della stringa

        while (state >= 0 && i < s.length()) {
            final char ch = s.charAt(i++);

            switch (state) {
            case 0:
            if (ch == ' ') //Leggo uno spazio, resto in 0
                state = 0; 
            else if (((ch >= 65) && (ch <= 90)) || ((ch >= 97) && (ch <= 122))) // Leggo una lettera, vado nello stato pozzo
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
            else if((ch >= 76 && ch <= 90) || (ch >= 108 && ch <= 122)) // ch è una lettera L-Z, vado nello sato pozzo
                state = -1;
            else if (ch == ' ') // Leggo uno spazio, vado in qs1 (state 7)
                state = 5; 
            else if ((ch >= 65 && ch <= 75) || (ch >= 97 && ch <= 107)) // Leggo una lettera A-K, giusto, vado in stato 3
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
            else if ((ch >= 65 && ch <= 75) || (ch >= 97 && ch <= 107)) // ch è A-K, vado nello stato pozzo
                state = -1;
            else if (ch == ' ') // Leggo uno spazio, vado in q2s (state = 6)
                state = 6;
            else if ((ch >= 76 && ch <= 90) || (ch >= 108 && ch <= 122)) // Leggo una lettera L-Z, giusto, vado in stato 3
                state = 3;
            else
                state = -1;
            break;

            case 3:
            if (ch >= 48 && ch <= 57) // ch è un numero, vado in stato pozzo
                state = -1;
            else if (((ch >= 65) && (ch <= 90)) || ((ch >= 97) && (ch <= 122))) // ch è una lettera vado in stato finale 4
                state = 4;
            else if (ch == ' ') // ch è uno spazio, vado in stato intermedio qs3 (state 7)
                state = 7;
            else
                state = -1;
            break; 

            case 4:
            if (ch == ' ') // ch è spazio vado in stato 8
                state = 8; 
            else if (ch >= 48 && ch <= 57) // ch è numero, vado in stato pozzo
                state = -1;
            else if (((ch >= 65) && (ch <= 90)) || ((ch >= 97) && (ch <= 122))) // ch è una lettera, resto in stato 4
                state = 4;
            else
                state = -1;
            break;

            case 5:
            if(ch == ' ') // ch è spazio, resto in stato 5
                state = 5; 
            else if ((ch >= 48 && ch <= 57) || ((ch >= 76 && ch <= 90) || (ch >= 108 && ch <= 122))) // ch è numero o lettera L-Z, vado in sttao pozzo
                state = -1; 
            else if ((ch >= 65 && ch <= 75) || (ch >= 97 && ch <= 107)) // ch è A-K vado in stato 3
                state = 3;
            else
                state = -1;
            break;

            case 6:
            if(ch == ' ') // ch è spazio, resto in stato 6
                state = 6;
            else if ((ch >= 48 && ch <= 57) || ((ch >= 65 && ch <= 75) || (ch >= 97 && ch <= 107))) // ch è num o lett A-K, vado in pozzo
                state = -1;
            else if ((ch >= 76 && ch <= 90) || (ch >= 108 && ch <= 122)) // ch è lettera L-Z, vado in state 3
                state = 3;
            else
                state = -1;
            break;

            case 7:
            if ((ch == ' ') || (ch >= 97 && ch <= 122)) // ch è spazio or lett MINUSCOLA, vado in stato pozzo
                state = -1;
            else if (ch >= 65 && ch <= 90) // ch è una lettera MAIUSCOLA, vado in stato finale 4
                state = 4;
            else
                state = -1; 
            break;

            case 8:
            if (ch == ' ')
                state = 8;
            else if ((ch >= 97 && ch <= 122)) // ch lett min
                state = -1; 
            else
                state = -1;
            break;

           
        }
        }
        return state == 4 || state == 7 || state == 8;
        }

        public static void main(String[] args) {
            for (int i = 0; i < args.length; i++) {
              System.out.print(args[i] + "\t");
              System.out.println(scan(args[i]) ? "OK" : "NOPE");
            }
          }
}
