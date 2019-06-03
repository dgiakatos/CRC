/**
 * Η κλάση υλοποιεί τον αποδέκτης του μηνύματος από το κανάλι θορύβου και εξετάζει αν το μήνυμα περιέχει σφάλμα
 * χρησιμοποιώντας τον αλγόριθμο εντοπισμού σφαλμάτων CRC.
 *
 * @author Δημήτριος Παντελεήμων Γιακάτος
 * @version 1.0.0
 */
public class Receiver {

    private String key;
    private String bitErrorData;
    private int lenOfKey;
    private int errorMessagesCrc;
    private int totalErrorMessages;
    private int errorMessageCrcCorrect;
    private boolean hasError;

    /**
     * Η μέθοδος είναι ο constructor που αρχικοποιεί κάποιες μεταβλητές που θα χρειαστούν για τον υπολογισμό των
     * ζητούμενων ποσοστών.
     */
    public Receiver() {
        this.errorMessagesCrc = 0;
        this.totalErrorMessages = 0;
        this.errorMessageCrcCorrect = 0;
    }

    /**
     * Η μέθοδος αποθηκεύει στη μεταβλητή key τον αριθμό P και αποθηκεύει στη μεταβλητή lenOf Key το μήλος του αριθμού P.
     * @param key Μία συμβολοσειρά που περιλαμβάνει τον αριθμό P.
     */
    public void setKey(String key) {
        this.key = key;
        lenOfKey = key.length();
    }

    /**
     * H μέθοδος αποθηκεύει το μήνυμα T που έχει ληφθεί από το κανάλι θορύβου στη μεταβλητή bitErrorData αλλά αποθηκεύει
     * και το μήνυμα Τ αναλλοίωτο στη μεταβλητή data. Ύστερα ελέγχει αν τα δύο μηνύματα είναι ίδια. Αν είναι δεν ίδια
     * τότε αυξάνει τη μεταβλητή totalErrorMessages κατά ένα, που θα χρησιμοποιηθεί για τον υπολογισμό του ποσοστού των
     * συνολικών μηνυμάτων με σφάλμα.
     * @param bitErrorData Το μήνυμα T που έχει ληφθεί από το κανάλι θορύβου
     * @param data Το μήνυμα Τ αναλλοίωτο
     */
    public void setData(String bitErrorData, String data) {
        this.bitErrorData = bitErrorData;
        hasError = false;
        if (!data.equals(bitErrorData)) {
            totalErrorMessages++;
            hasError = true;
        }
    }

    /**
     * Η μέθοδος είναι ο εκκινητής της διαδικασίας του αλγορίθμου για τον εντοπισμό σφαλμάτων CRC. Αν το υπόλοιπο της
     * διαίρεσης που έχει προηγηθεί με τη μέθοδο division είναι 1 τότε αυξάνεται κατά ένα η μεταβλητή errorMessagesCrc
     * που δείχνει το συνολικό αριθμό των μηνυμάτων που ανιχνεύτηκαν με σφάλμα από το CRC. Διαφορετικά αν το υπόλοιπο
     * είναι 0 τότε αυξάνεται κατά ένα η μεταβλητή errorMessageCrcCorrect που δείχνει το συνολικό αριθμό των μηνυμάτων
     * που δεν ανιχνεύτηκαν με σφάλμα από το CRC αλλά έχουν σφάλμα.
     */
    public void start() {
        division();
        if (bitErrorData.contains("1")) {
            errorMessagesCrc++;
        } else if (hasError){
            errorMessageCrcCorrect++;
        }
    }

    /**
     * @return Ο συνολικός αριθμός των μηνυμάτων που ανιχνεύτηκαν με σφάλμα από το CRC
     */
    public int getErrorMessagesCrc() {return errorMessagesCrc;}

    /**
     * @return Ο συνολικός αριθμός των μηνυμάτων με σφάλμα.
     */
    public int getTotalErrorMessages() {return  totalErrorMessages;}

    /**
     * @return Ο συνολικός αριθμός των μηνυμάτων που δεν ανιχνεύτηκαν με σφάλμα από το CRC αλλά έχουν σφάλμα.
     */
    public int getErrorMessageCrcCorrect() {return  errorMessageCrcCorrect;}

    /**
     * Η μέθοδος διαιρεί τον αριθμό του μηνύματος T με τον αριθμό P από όπου θα προκύψει το υπόλοιπο το οποίο θα
     * εξετάσουμε αν το μήνυμα έχει σφάλμα.
     */
    private void division() {
        String value;
        String result;
        while (bitErrorData.length()>=lenOfKey) {
            value = bitErrorData.substring(0, lenOfKey);
            result = Long.toBinaryString(Long.parseLong(value, 2) ^ Long.parseLong(key, 2));
            bitErrorData = result + bitErrorData.substring(lenOfKey);
        }
    }
}
