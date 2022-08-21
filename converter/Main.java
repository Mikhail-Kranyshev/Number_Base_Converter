package converter;

public class Main {

    public static void main(String[] args) {
        Converter converter = new Converter();
        converter.inputNumber();
        converter.inputBase();
        System.out.println("Conversion result: " + converter.convertingNumberToBase());
    }
}
