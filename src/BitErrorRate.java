import java.util.Random;

public class BitErrorRate {

    private String data;
    private boolean hasBitErrorRate;

    public BitErrorRate() {
        Random random = new Random();
        hasBitErrorRate = random.nextBoolean();
    }

    public void setData(String data) {this.data = data;}

    public String start() {
        if (hasBitErrorRate) {
            generateBitErrorRate();
        }
        return data;
    }

    private void generateBitErrorRate() {
        Random random = new Random();
        Random errorBit = new Random();
        int numberOfErrorBit = random.nextInt(data.length()+1);
        int indexOfError;
        for (int i=0; i<numberOfErrorBit; i++) {
            indexOfError = random.nextInt(data.length());
            data = data.substring(0, indexOfError) + String.valueOf(errorBit.nextInt(2)) +
                    data.substring(indexOfError+1);
        }
    }
}
