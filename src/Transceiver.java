import java.util.concurrent.ThreadLocalRandom;

public class Transceiver {

    private String key;
    private String data;
    private String dataForDivision;
    private String dataWithCrc;
    private int lenOfKey;
    private boolean userHasGivenData;
    private long size;

    public Transceiver() {userHasGivenData = false;}

    public void setKey(String key) {
        this.key = key;
        lenOfKey = key.length();
    }

    public void setSize(long size) {this.size = size;}

    public String start() {
        if (!userHasGivenData) {
            generateData();
        }
        generateDataForDivision();
        division();
        return dataWithCrc;
    }

    private void generateData() {
        long randomData;
        data = "";
        for (long i=0; i<size; i++) {
            randomData = ThreadLocalRandom.current().nextLong(0, 2);
            data = data + Long.toBinaryString(randomData);
        }
    }

    private void generateDataForDivision() {
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