import java.util.concurrent.ThreadLocalRandom;

/**
 * Η κλάση υλοποιεί ένα μεταδότη, ο οποίος δημιουργεί ένα τυχαίο μήνυμα μήκους k και εκτελείται ο αλγόριθμος εντοπισμού
 * σφαλμάτων CRC.
 *
 * @author Δημήτριος Παντελεήμων Γιακάτος
 * @version 1.0.0
 */
public class Transceiver {

    private String key;
    private String data;
    private String dataForDivision;
    private String dataWithCrc;
    private int lenOfKey;
    private long size;

    /**
     * Η μέθοδος είναι ο constructor.
     */
    public Transceiver() {}

    /**
     * Η μέθοδος αποθηκεύει στη μεταβλητή key τον αριθμό P και αποθηκεύει στη μεταβλητή lenOf Key το μήλος του αριθμού P.
     * @param key Μία συμβολοσειρά που περιλαμβάνει τον αριθμό P.
     */
    public void setKey(String key) {
        this.key = key;
        lenOfKey = key.length();
    }

    /**
     * Η μέθοδος αποθηκεύει στη μεταβλητή size το μήκος k για το μήνυμα που θέλουμε να δημιουργηθεί.
     * @param size Ο αριθμός k δηλαδή το μήκος του μηνύματος.
     */
    public void setSize(long size) {this.size = size;}

    /**
     * Η μέθοδος είναι ο εκκινητής της διαδικασίας μετάδοσης.
     * @return Επιστρέφει μία συμβολοσειρά που περιλαμβάνει το μήνυμα T που θέλουμε να στείλουμε.
     */
    public String start() {
        generateData();
        generateDataForDivision();
        division();
        return dataWithCrc;
    }

    /**
     * Η μέθοδος δημιουργεί τυχαία ένα μήνυμα μήκους k.
     */
    private void generateData() {
        long randomData;
        data = "";
        for (long i=0; i<size; i++) {
            randomData = ThreadLocalRandom.current().nextLong(0, 2);
            data = data + Long.toBinaryString(randomData);
        }
    }

    /**
     * Η μέθοδος προσθέτει στο τέλος του μηνύματος Μ, n μηδενικά, όπου n είναι ο αριθμός των bits του P των n+1 bits,
     * και προκύπτει ο αριθμός (2^n)M.
     */
    private void generateDataForDivision() {
        dataForDivision = data;
        for (long i=0; i<lenOfKey-1; i++) {
            dataForDivision = dataForDivision + "0";
        }
    }

    /**
     * Η μέθοδος υλοποιεί τη διαίρεση του αριθμού 2^n M με τον αριθμό P και από το υπόλοιπο της διαίρεσης προκύπτει ο
     * αριθμός F.  Μετά, ο αριθμός F προστίθεται στο τέλος του μηνύματος (2^n)M και προκύπτει ο αριθμός T.
     */
    private void division() {
        String value;
        String result;
        while (dataForDivision.length()>=lenOfKey) {
            value = dataForDivision.substring(0, lenOfKey);
            result = Long.toBinaryString(Long.parseLong(value, 2) ^ Long.parseLong(key, 2));
            dataForDivision = result + dataForDivision.substring(lenOfKey);
        }
        if (dataForDivision.length()<lenOfKey-1) {
            for (int i=dataForDivision.length(); i<lenOfKey-1; i++) {
                dataForDivision = "0" + dataForDivision;
            }
        }
        dataWithCrc = data + dataForDivision;
    }
}