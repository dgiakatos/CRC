import java.util.concurrent.ThreadLocalRandom;

/**
 * Η κλάση υλοποιεί το κανάλι θορύβου.
 *
 * @author Δημήτριος Παντελεήμων Γιακάτος
 * @version 1.0.0
 */
public class BitErrorRate {

    private double possibility;
    private String data;

    /**
     * Η μέθοδος είναι ο constructor.
     */
    public BitErrorRate() {}

    /**
     * H μέθοδος αποθηκεύει το μήνυμα T στη μεταβλητή data.
     * @param data Μία συμβολοσειρά που περιλαμβάνει το μήνυμα T.
     */
    public void setData(String data) {this.data = data;}

    /**
     * Η μέθοδος αποθηκεύει τη πιθανότητα σφάλματος στη μεταβλητή possibility.
     * @param possibility Η πιθανότητα σφάλματος.
     */
    public void setPossibility(double possibility) {this.possibility = possibility;}

    /**
     * Η μέθοδος είναι ο εκκινητής της διαδικασίας για το κανάλι θορύβου.
     * @return Επιστρέφει το μήνυμα T το οποίο πιθανό να έχει αλλοιωθεί από το κανάλι θορύβου.
     */
    public String start() {
        generateBitErrorRate();
        return data;
    }

    /**
     * Η μέθοδος ανάλογα με την πιθανότητα σφάλματος που έχει δώσει ο χρήστης αλλοιώνει κάθε bit του μηνύματος T που
     * είναι στη μεταβλητή data. Με λίγα λόγια η κλάση δημιουργεί για κάθε ένα bit του T ένα αριθμό από το 0.0 μέχρι
     * και το 1.0 και αν αυτός ο αριθμός είναι μικρότερος ή ίσος με τη πιθανότητα σφάλματος τότε αλλάζει το bit του
     * μηνύματος από 0 σε 1 και από 1 σε 0.
     */
    private void generateBitErrorRate() {
        double randomError;
        for (int i=0; i<data.length(); i++) {
            randomError = ThreadLocalRandom.current().nextDouble(0.0, 1.0);
            if (possibility>=randomError) {
                if (data.charAt(i)=='0') {
                    data = data.substring(0, i) + "1" + data.substring(i+1);
                } else {
                    data = data.substring(0, i) + "0" + data.substring(i+1);
                }
            }
        }
    }
}
