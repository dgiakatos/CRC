import java.util.Scanner;

/**
 * Η κλάση Main είναι η πρώτη που θα εκτελεστεί από τον υπολογιστή.
 *
 * @author Δημήτριος Παντελεήμων Γιακάτος
 * @version 1.0.0
 */
public class Main {

    /**
     * Η μέθοδο Main δέχεται από το χρήστη τον αριθμό P, το μήκος του μηνύματος που θα δημιουργηθεί, τη πιθανότητα
     * σφάλματος και το πλήθος των μηνυμάτων που θα μεταδοθούν. Εκτελεί τη διαδικασία του CRC και εκτυπώνει στην οθόνη
     * τα αποτελέσματα των ποσοστών.
     * @param args Δεν χρησιμοποιείται στο πρόγραμμα, όμως είναι απαραίτητη για την εκτέλεση του προγράμματος από τον
     *             υπολογιστή.
     */
    public static void main(String[] args) {

        /*
        * Το πρόγραμμα ζητάει από το χρήστη τα δώσει το τον αριθμό P. Το πρώτο και το τελευταίο ψηφίο του P πρέπει να
        * είναι 1. Αν το πρώτο και το τελευταίο ψηφίο δεν είναι 1 τότε το πρόγραμμα ξαναζητάει από το χρήστη να δώσει
        * ξανά τον αριθμό P.
        * */
        Transceiver transceiver = new Transceiver();
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Give a binary key that the FIRST and the LAST element must be 1:");
        String binaryKey = keyboard.next();
        boolean isBinary = false;
        while (!isBinary) {
            if (!binaryKey.matches("^[1][0*1*]*[1]")) {
                System.out.println("Wrong type. The key must be binary and the FIRST and the LAST element of the key must be 1. Please give the key again:");
                binaryKey = keyboard.next();
            } else {
                isBinary = true;
            }
        }

        /*
        * Το πρόγραμμα ζητάει από το χρήστη τα δώσει το τον αριθμό k, δηλαδή το μήκος του μηνύματος που θέλει να
        * δημιουργήσει. Ο αριθμός k πρέπει να είναι μεγαλύτερος ή ίσος από τον μήκος του αριθμού P. Αν δεν είναι τότε
        * το πρόγραμμα ζητάει από το χρήστη να ξαναδώσει τον αριθμό k.
        * */
        System.out.println("Give the size of the data. The size must be positive integer number and equal or longer than the size of binary key:");
        long binarySize = keyboard.nextLong();
        boolean hasSize = false;
        while (!hasSize) {
            if (binarySize<binaryKey.length()) {
                System.out.println("Wrong type. The size must be positive integer number and equal or longer than the size of binary key. Please give the key again:");
                binarySize = keyboard.nextLong();
            } else {
                hasSize = true;
            }
        }
        transceiver.setSize(binarySize);

        /*
        * Το πρόγραμμα ζητάει από το χρήστη τα δώσει τον αριθμό E, δηλαδή τη πιθανότητα σφάλματος. Ο αριθμός E
        * πρέπει να είναι ανήκει στο διάστημα [0.0, 1.0]. Αν δεν ανήκει τότε το πρόγραμμα ζητάει από το χρήστη να
        * ξαναδώσει τον αριθμό Ε.
        * */
        transceiver.setKey(binaryKey);
        BitErrorRate bitErrorRate = new BitErrorRate();
        System.out.println("Give the possibility of error:");
        double possibilityError = keyboard.nextDouble();
        boolean isPossibility = false;
        while (!isPossibility) {
            if (possibilityError<0.0 || possibilityError>1.0) {
                System.out.println("Wrong type. The possibility error must be from 0 to 1 (exp. 0.003). Please give the key again:");
                possibilityError = keyboard.nextDouble();
            } else {
                isPossibility = true;
            }
        }
        bitErrorRate.setPossibility(possibilityError);

        /*
        * Το πρόγραμμα ζητάει από το χρήστη τα δώσει το πλήθος των μνημάτων που θέλει να δημιουργήσει το πρόγραμμα.
        * Αν ο αριθμός είναι αρνητικός τότε το πρόγραμμα ζητάει από το χρήστη να ξαναδώσει το πλήθος των μηνυμάτων.
        * */
        System.out.println("Give the number of messages you want to send:");
        int numberOfMessages = keyboard.nextInt();
        boolean hasAnswer = false;
        while (!hasAnswer) {
            if (numberOfMessages < 0) {
                System.out.println("Wrong type. The number of messages must be positive. Please give the key again:");
                numberOfMessages = keyboard.nextInt();
            } else {
                hasAnswer = true;
            }
        }

        /*
        * Το πρόγραμμα δημιουργεί τόσα μηνύματα όσο το πλήθος των μηνυμάτων που έχει οριστεί από το χρήστη.
        * Για κάθε μήνυμα ακολουθείται η εξής διαδικασία:
        * 1)    Αρχικά η μέθοδος transceiver.start δημιουργεί τυχαία ένα μήνυμα και αποθηκεύεται στη μεταβλητή data.
        * 2)	Το μήνυμα που είναι αποθηκευμένο στη μεταβλητή data ορίζεται ως όρισμα στη μέθοδο bitErrorRate.setData
        *       η οποία περνάει το μήνυμα από ένα κανάλι θορύβου και ανάλογα τη πιθανότητα σφάλματος αλλοιώνει τα bit του.
        * 3)	Καλείται η μέθοδο receiver.setData όπου δέχεται ως ορίσματα το πιθανό αλλοιωμένο μήνυμα που προέκυψε από
        *       το κανάλι θορύβου και το αναλλοίωτο μήνυμα που είναι αποθηκευμένο στη μεταβλητή data.
        * 4)	Καλείται η μέθοδο receiver.start όπου υλοποίει τη διαίρεση του πιθανού αλλοιωμένου μηνύματος με τον
        *       αριθμό P και το υπόλοιπο καθορίζει αν το σήμα έχει σφάλμα ή όχι.
        * */
        Receiver receiver = new Receiver();
        receiver.setKey(binaryKey);
        String data;
        for (int i=0; i<numberOfMessages; i++) {
            data = transceiver.start();
            bitErrorRate.setData(data);
            receiver.setData(bitErrorRate.start(), data);
            receiver.start();
        }

        /*
        * Εκτυπώνει τα ποσοστά στην οθόνη.
        * */
        System.out.println("Statistics data:");
        System.out.println("Rate of messages with errors on transmission: " +  (double) (receiver.getTotalErrorMessages()*100)/numberOfMessages + "%");
        System.out.println("Rate of messages with errors detective by CRC: " + (double) (receiver.getErrorMessagesCrc()*100)/numberOfMessages + "%");
        System.out.println("Rate of messages with errors that they were not detective by CRC: " + (double) (receiver.getErrorMessageCrcCorrect()*100)/numberOfMessages + "%");
    }
}
