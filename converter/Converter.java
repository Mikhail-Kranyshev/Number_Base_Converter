package converter;

import java.util.Scanner;

public class Converter {
    private String number;
    private int base;

    private final Scanner scanner = new Scanner(System.in);

    public Converter() {
        number = "";
        base = 10;
    }

    public void inputNumber(String msg) {
        System.out.print(msg);
        this.number = scanner.next();
    }

    public void inputBase(String msg) {
        System.out.print(msg);
        base = scanner.nextInt();
    }

    public void convertingNumberFromBase() {
        inputNumber("Enter a number in decimal system: ");
        inputBase("Enter the target base: ");
        System.out.println("Conversion result: " +
                switch (base) {
                    case 2 -> Long.toBinaryString(Long.parseLong(number));
                    case 8 -> Long.toOctalString(Long.parseLong(number));
                    case 16 -> Long.toHexString(Long.parseLong(number)).toUpperCase();
                    default -> number;
                });
    }

    private void convertingNumberToBase() {
        inputNumber("Enter source number: ");
        inputBase("Enter source base: ");
        System.out.println("Conversion to decimal result: " +
                switch (base) {
                    case 2 -> Long.parseLong(number, 2);
                    case 8 -> Long.parseLong(number, 8);
                    case 16 -> Long.parseLong(number, 16);
                    default -> Long.parseLong(number);
                });
    }

    public void action() {
        while (true) {
            System.out.print("Do you want to convert /from decimal or /to decimal? (To quit type /exit) ");
            switch (scanner.next()) {
                case "/from" -> convertingNumberFromBase();
                case "/to" -> convertingNumberToBase();
                case "/exit" -> {
                    return;
                }
            }
            System.out.println();
        }
    }

}
