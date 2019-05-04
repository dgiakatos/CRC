import java.util.concurrent.ThreadLocalRandom;

public class BitErrorRate {

    private double possibility;
    private String data;

    public BitErrorRate() {}

    public void setData(String data) {this.data = data;}

    public void setPossibility(double possibility) {this.possibility = possibility;}

    public String start() {
        generateBitErrorRate();
        //System.err.println("error: "+data);
        return data;
    }

    private void generateBitErrorRate() {
        double randomError;
        //System.err.println("er-data: "+data);
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
