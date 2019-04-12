import java.util.concurrent.ThreadLocalRandom;

public class Transceiver {

    private String key;
    private String data;
    private String dataForDivision;
    private String dataWithCrc;
    private int lenOfKey;
    private boolean userHasGivenData;

    public Transceiver(String key) {
        this.key = key;
        lenOfKey = key.length();
        userHasGivenData = false;
    }

    public Transceiver(String key, String data) {
        this(key);
        this.data = data;
        userHasGivenData = true;
    }

    public String start() {
        if (!userHasGivenData) {
            generateData();
        }
        generatedataForDivision();
        division();
        return dataWithCrc;
    }

    private void generateData() {
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        long randomData = ThreadLocalRandom.current().nextLong(Long.parseLong(key, 2), 100000 + 1);
        data = Long.toBinaryString(randomData);
    }

    private void generatedataForDivision() {
        dataForDivision = data;
        for (long i=0; i<lenOfKey-1; i++) {
            dataForDivision = dataForDivision + "0";
        }
    }

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