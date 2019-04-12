public class Main {

    public static void main(String[] args) {

        Transceiver transceiver = new Transceiver("11011", "11100101");
        Receiver receiver = new Receiver("11011", transceiver.start());
        System.out.println(transceiver.start());
        System.out.println(receiver.start());
    }
}
