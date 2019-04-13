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
        transceiver.setKey(binaryKey);
        BitErrorRate bitErrorRate = new BitErrorRate();
        Receiver receiver = new Receiver();
        receiver.setKey(binaryKey);
        boolean goOn = true;
        boolean hasAnswer;
        while (goOn) {
            bitErrorRate.setData(transceiver.start());
            receiver.setData(bitErrorRate.start());
            System.out.println(receiver.start());
            System.out.println("Do you want to go on with the transmission or to stop? Give (Y) to go on or (N) to stop:");
            String answer = keyboard.next();
            hasAnswer = false;
            while (!hasAnswer) {
                if (answer.equals("Y") || answer.equals("N")) {
                    hasAnswer = true;
                } else {
                    System.out.println("Wrong input. Please give again (Y) to go on or (N) to stop:");
                    answer = keyboard.next();
                }
            }
            if (answer.equals("Y")) {
                goOn = true;
            } else {
                goOn = false;
            }
        }
    }
}
