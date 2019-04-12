public class Receiver {

    private String key;
    private String data;
    private int lenOfKey;

    public Receiver(String key, String data) {
        this.key = key;
        this.data = data;
        lenOfKey = key.length();
    }

    public String start() {
        division();
        if (data.contains("1")) {
            return "Wrong data!";
        }
        return "Correct data!";
    }

    private void division() {
        String value;
        String result;
        while (data.length()>=lenOfKey) {
            value = data.substring(0, lenOfKey);
            result = Long.toBinaryString(Long.parseLong(value, 2) ^ Long.parseLong(key, 2));
            data = result + data.substring(lenOfKey);
        }
    }
}
