public class Main {

    public static void main(String[] args) {

        Transceiver transceiver = new Transceiver("11011", "11100101");
        BitErrorRate bitErrorRate = new BitErrorRate(transceiver.start());
        Receiver receiver = new Receiver("11011", bitErrorRate.start());
        System.out.println(receiver.start());
    }
}
