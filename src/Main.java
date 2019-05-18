import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Transceiver transceiver = new Transceiver();
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Give a binary key that the FIRST and the LAST element must be 1:");
        String binaryKey = keyboard.next();
        boolean isBinary = false;
        while (!isBinary) {
            if (!binaryKey.matches("^[1][0*1*]*[1]")) {
                System.out.println("Wrong type. The key must be binary and the FIRST and the LAST element of the key must be 1. Please give the key again:");
                binaryKey = keyboard.next();
            } else {
                isBinary = true;
            }
        }

        System.out.println("Give the size of the data. The size must be positive integer number and equal or longer than the size of binary key:");
        long binarySize = keyboard.nextLong();
        boolean hasSize = false;
        while (!hasSize) {
            if (binarySize<binaryKey.length()) {
                System.out.println("Wrong type. The size must be positive integer number and equal or longer than the size of binary key. Please give the key again:");
                binarySize = keyboard.nextLong();
            } else {
                hasSize = true;
            }
        }
        transceiver.setSize(binarySize);

        transceiver.setKey(binaryKey);
        BitErrorRate bitErrorRate = new BitErrorRate();
        System.out.println("Give the possibility of error:");
        double possibilityError = keyboard.nextDouble();
        boolean isPossibility = false;
        while (!isPossibility) {
            if (possibilityError<0.0 || possibilityError>1.0) {
                System.out.println("Wrong type. The possibility error must be from 0 to 1 (exp. 0.003). Please give the key again:");
                possibilityError = keyboard.nextDouble();
            } else {
                isPossibility = true;
            }
        }
        bitErrorRate.setPossibility(possibilityError);

        System.out.println("Give the number of messages you want to send:");
        int numberOfMessages = keyboard.nextInt();
        boolean hasAnswer = false;
        while (!hasAnswer) {
            if (numberOfMessages < 0) {
                System.out.println("Wrong type. The number of messages must be positive. Please give the key again:");
                numberOfMessages = keyboard.nextInt();
            } else {
                hasAnswer = true;
            }
        }

        Receiver receiver = new Receiver();
        receiver.setKey(binaryKey);
        String data;
        for (int i=0; i<numberOfMessages; i++) {
            data = transceiver.start();
            //System.err.println(data);
            bitErrorRate.setData(data);
            receiver.setData(bitErrorRate.start(), data);
            receiver.start();
        }

        System.out.println("Statistics data:");
        System.out.println("Rate of messages with errors on transmission: " +  (float) (receiver.getTotalErrorMessages()*100)/numberOfMessages + "%");
        System.out.println("Rate of messages with errors detective by CRC: " + (float) (receiver.getErrorMessagesCrc()*100)/numberOfMessages + "%");
        System.out.println("Rate of messages with errors that they were not detective by CRC: " + (float) (receiver.getErrorMessageCrcCorrect()*100)/numberOfMessages + "%");
    }
}
