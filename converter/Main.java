package converter;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String input = scanner.nextLine();
        if (!isRadix(input)) {
            System.out.println("error");
            scanner.close();
            return;
        }
        int sradix = Integer.parseInt(input);

        input = scanner.nextLine();
        if (!isNumber(input, sradix)) {
            System.out.println("error");
            scanner.close();
            return;
        }
        Converter con = new Converter(input, sradix);

        input = scanner.nextLine();
        if (!isRadix(input)) {
            System.out.println("error");
            scanner.close();
            return;
        }
        int dradix = Integer.parseInt(input);

        String output = con.toString(dradix);

        System.out.println(output);

        scanner.close();
    }

    static boolean isRadix(String str) {
        if (!str.matches("[0-9]+")){
            return false;
        }
        int number = Integer.parseInt(str);
        if (number < 1) {
            return false;
        }
        if (number > 36) {
            return false;
        }
        return true;
    }

    static boolean isNumber(String input, int radix) {
        if (radix == 1) {
            if (input.matches("[1]+")) {
                return true;
            } else {
                return false;
            }
        }
        if (!input.matches("[0-9a-zA-Z]*[.]?[0-9a-zA-Z]*")){
            return false;
        }
        String str = input.toLowerCase(); 
        for (int i = 0; i < str.length(); i++) {
            int digit = str.charAt(i);
            if (digit == '.') {
                continue;
            }
            if (digit <= '9') {
                digit -= '0';
            } else {
                digit -= 'a';
                digit += 10;
            }
            if (digit >= radix) {
                return false;
            }
        }
        return true;
    }
}

class Converter {
    double number;

    Converter(String source, int radix) {
        if (radix == 1) {
            number = source.length();
            return;
        }

        String[] parts = source.split("[.]");
        if (parts.length == 1) {
            number = Integer.parseInt(parts[0], radix);
            return;   
        }

        number = Integer.parseInt(parts[0], radix);
        String fraction = parts[1].toLowerCase();
        double pow = radix;
        for (int i = 0; i < fraction.length(); i++) {
            int digit = fraction.charAt(i);
            if (digit <= '9') {
                digit -= '0';
            } else {
                digit -= 'a';
                digit += 10;
            }
            number += (double)digit / pow;
            pow *= (double)radix;
        }
    }

    String toString(int radix) {
        String output = "";

        if (radix == 1) {
            for (int i = 0; i < number; i++) {
                output += "1";
            }
            return output;
        }

        output = Integer.toString((int)number, radix);

        if (number == (int)number) {
            return output;
        }
        
        output += ".";
        double fraction = number - (int)number;
        for (int i = 0; i < 5; i++) {
            fraction *= radix;
            int digit = (int)fraction;
            char ch;
            if (digit < 9) {
                ch = '0';
                ch += digit;
            } else {
                digit -= 10;
                ch = 'a';
                ch += digit;
            }
            output += ch;
            fraction = fraction - (int)fraction;
            if (fraction == 0) {
                break;
            }
        }

        return output;
    }
}