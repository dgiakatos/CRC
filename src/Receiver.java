public class Receiver {

    private String key;
    private String bitErrorData;
    private int lenOfKey;
    private int errorMessagesCrc;
    private int totalErrorMessages;
    private int errorMessageCrcCorrect;
    private boolean hasError;

    public Receiver() {
        this.errorMessagesCrc = 0;
        this.totalErrorMessages = 0;
        this.errorMessageCrcCorrect = 0;
    }

//    public Receiver(String key, String bitErrorData) {
//        this();
//        this.key = key;
//        this.bitErrorData = bitErrorData;
//        lenOfKey = key.length();
//    }

    public void setKey(String key) {
        this.key = key;
        lenOfKey = key.length();
    }

    public void setData(String bitErrorData, String data) {
        this.bitErrorData = bitErrorData;
        hasError = false;
        if (!data.equals(bitErrorData)) {
            totalErrorMessages++;
            hasError = true;
        }
    }

    public void start() {
        division();
        if (bitErrorData.contains("1")) {
            errorMessagesCrc++;
            //System.err.println("Wrong data!");
            //return;
        } else if (hasError){
            errorMessageCrcCorrect++;
            //System.err.println("Correct data with error!");
            //return;
        }
        //System.err.println("Correct data!");
    }

    public int getErrorMessagesCrc() {return errorMessagesCrc;}

    public int getTotalErrorMessages() {return  totalErrorMessages;}

    public int getErrorMessageCrcCorrect() {return  errorMessageCrcCorrect;}

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
