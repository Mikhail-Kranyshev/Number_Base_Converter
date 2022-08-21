package converter;

import java.util.Scanner;

public class Converter {
    private long number;
    private int base;

    private Scanner scanner = new Scanner(System.in);

    public Converter() {
        number = 0;
        base = 10;
    }

    public void inputNumber() {
        System.out.print("Enter number in decimal system: ");
        this.number = scanner.nextLong();
    }

    public void inputBase() {
        System.out.print("Enter target base: ");
        base = scanner.nextInt();
    }

    public String convertingNumberToBase() {
        return switch (base) {
            case 2 -> Long.toBinaryString(number);
            case 8 -> Long.toOctalString(number);
            case 16 -> Long.toHexString(number).toUpperCase();
            default -> Long.toString(number);
        };
    }
}
