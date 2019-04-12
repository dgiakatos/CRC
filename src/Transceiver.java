import java.util.concurrent.ThreadLocalRandom;

public class Transceiver {

    private String key;
    private String data;
    private String dataWithKey;
    private String dataWithCrc;
    private int lenOfKey;

    public Transceiver(String key) {
        this.key = key;
        lenOfKey = (int) key.length();
        generateData();
        generateDataWithKey();
        //division();
    }

    private void generateData() {
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        //long randomData = ThreadLocalRandom.current().nextLong(Long.parseLong(key, 2), 100000 + 1);
        //data = Long.toBinaryString(randomData);
        data = "11100101";
        System.out.println(data + "\n" + key);
    }

    private void generateDataWithKey() {
        dataWithKey = data;
        for (long i=0; i<lenOfKey-1; i++) {
            dataWithKey = dataWithKey + "0";
        }
        System.out.println(dataWithKey);
    }

    private void division() {
        String value;
        String result;
        while (dataWithKey.length()>0) {
            value = dataWithKey.substring(0,lenOfKey);

        }
    }
}