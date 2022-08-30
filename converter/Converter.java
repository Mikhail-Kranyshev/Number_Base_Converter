package converter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
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




    private String convertingIntegerPartFromBase10() {
        BigInteger number = new BigInteger(this.number.split("\\.")[0]);
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
        return stringBuilder.reverse().toString();
    }

    private String convertingFractionalPartFromBase10() {
        BigDecimal number = new BigDecimal("0." + this.number.split("\\.")[1]);
        BigDecimal targetBase = new BigDecimal(String.valueOf(this.targetBase));
        StringBuilder stringBuilder = new StringBuilder();
        while (!number.equals(BigDecimal.ZERO)) {
            int i = Integer.parseInt(String.valueOf(number.multiply(targetBase).setScale(0, RoundingMode.DOWN)));
            number = number.multiply(targetBase).subtract(BigDecimal.valueOf(i));
            if (i > 9) {
                stringBuilder.append(Character.toChars(i + 55));
            } else {
                stringBuilder.append(i);
            }
            if (stringBuilder.length() == 5) {
                break;
            }
        }
        return stringBuilder.toString();
    }

    private String convertingIntegerPartToBase10() {
        char[] number = this.number.toUpperCase().split("\\.")[0].toCharArray();
        BigInteger bigInteger = BigInteger.ZERO;
        BigInteger base = BigInteger.ONE;
        for (int i = number.length - 1; i >= 0; i--) {
            BigInteger tmp = BigInteger.valueOf(number[i]);
            if (number[i] > 64) {
                tmp = tmp.subtract(BigInteger.valueOf(55));
            } else {
                tmp = tmp.subtract(BigInteger.valueOf(48));
            }
            bigInteger = bigInteger.add(tmp.multiply(base));
            base = base.multiply(BigInteger.valueOf(sourceBase));
        }
        return bigInteger.toString();
    }

    private String convertingFractionalPartToBase10() {
        String string = this.number.toUpperCase().split("\\.")[1];
        char[] number = string.toCharArray();
        if (isEmpty(number)) {
            return "00000";
        }
        BigDecimal bigDecimal = BigDecimal.ZERO;
        BigDecimal base = new BigDecimal(String.valueOf(sourceBase));
        for (char c : number) {
            BigDecimal tmp = BigDecimal.valueOf(c);
            if (c > 64) {
                tmp = tmp.subtract(BigDecimal.valueOf(55));
            } else {
                tmp = tmp.subtract(BigDecimal.valueOf(48));
            }
            tmp = tmp.multiply(BigDecimal.ONE.divide(base, 10, RoundingMode.HALF_EVEN));
            bigDecimal = bigDecimal.add(tmp);
            base = base.multiply(BigDecimal.valueOf(sourceBase));
        }
        return (bigDecimal + "0000").substring(2, 9);
    }

    private boolean isEmpty(char[] chars) {
        for (char ch: chars) {
            if (ch != '0') {
                return false;
            }
        }
        return true;
    }

    private void convertingNumberToBase10() {
        if (sourceBase != 10) {
            if (number.contains(".")) {
                this.number = convertingIntegerPartToBase10() + "." + convertingFractionalPartToBase10();
            } else {
                this.number = convertingIntegerPartToBase10();
            }
        }
    }

    private void convertingNumberFromBase10() {
        if (number.contains(".")) {
            this.number = convertingIntegerPartFromBase10() + "." + convertingFractionalPartFromBase10();
        } else {
            this.number = convertingIntegerPartFromBase10();
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
            if (number.contains(".")) {
                number = (number + "00000").substring(0, number.indexOf(".") + 6);
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
