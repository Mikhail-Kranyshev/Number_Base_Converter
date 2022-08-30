package converter;

import java.math.BigInteger;
import java.util.Scanner;

public class Converter {
    private String number;
    private int sourceBase;
    private int targetBase;


    private final Scanner scanner = new Scanner(System.in);

    public Converter() {
        number = "";
        sourceBase = 10;
        targetBase = 10;
    }




    private void convertingNumberFromBase10() {
        BigInteger number = new BigInteger(this.number);
        BigInteger targetBase = new BigInteger(String.valueOf(this.targetBase));
        StringBuilder stringBuilder = new StringBuilder();
        while (!number.equals(BigInteger.ZERO)) {
            int i = Integer.parseInt(String.valueOf(number.remainder(targetBase)));
            if (i > 9) {
                stringBuilder.append(Character.toChars(i + 55));
            } else {
                stringBuilder.append(number.remainder(targetBase));
            }
            number = number.divide(targetBase);
        }
        this.number = stringBuilder.reverse().toString();
    }

    private void convertingNumberToBase10() {
        if (sourceBase != 10) {
            char[] number = this.number.toUpperCase().toCharArray();
            BigInteger bigInteger = BigInteger.ZERO;
            BigInteger pow = BigInteger.ONE;
            for (int i = number.length - 1; i >= 0; i--) {
                BigInteger tmp = BigInteger.valueOf(number[i]);
                if (number[i] > 64) {
                    tmp = tmp.subtract(BigInteger.valueOf(55));
                } else {
                    tmp = tmp.subtract(BigInteger.valueOf(48));
                }
                bigInteger = bigInteger.add(tmp.multiply(pow));
                pow = pow.multiply(BigInteger.valueOf(sourceBase));
            }
            this.number = bigInteger.toString();
        }
    }

    private void converting() {
        while (true) {
            System.out.printf("Enter number in base %d to convert to base %d (To go back type /back) ", sourceBase, targetBase);
            number = scanner.nextLine();
            if ("/back".equals(number)) {
                return;
            } else if (!"0".equals(number)) {
                if (sourceBase != 10) {
                    convertingNumberToBase10();
                }
                if (targetBase != 10) {
                    convertingNumberFromBase10();
                }
            }
            System.out.println("Conversion result: " + number + "\n");
        }
    }

    public void action() {
        while (true) {
            System.out.print("Enter two numbers in format: {source base} {target base} (To quit type /exit) ");
            String string = scanner.nextLine();
            if ("/exit".equals(string)) {
                return;
            } else {
                String[] strings = string.split("\\s");
                sourceBase = Integer.parseInt(strings[0]);
                targetBase = Integer.parseInt(strings[1]);
                converting();
            }
            System.out.println();
        }
    }

}
