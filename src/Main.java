public class Main {

    public static void main(String[] args) {

        Transceiver transceiver = new Transceiver("11011");
        System.out.println(transceiver.getDataWithCrc());
    }
}
