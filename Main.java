import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите арифметическое выражение:");
        String input = scanner.nextLine();
        try {
            String result = calc(input);
            System.out.println("Результат: " + result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static String calc(String input) throws Exception {
        String[] parts = input.split(" ");
        if (parts.length != 3) {
            throw new Exception("Неверный формат ввода. Введите два числа и арифметическую операцию между ними.");
        }

        String num1Str = parts[0];
        String num2Str = parts[2];
        char operation = parts[1].charAt(0);

        int num1 = 0;
        int num2 = 0;
        try {
            num1 = Integer.parseInt(num1Str);
            num2 = Integer.parseInt(num2Str);
        } catch (NumberFormatException e) {
            try {
                num1 = RomanNumerals.toArabic(num1Str);
                num2 = RomanNumerals.toArabic(num2Str);
            } catch (IllegalArgumentException e1) {
                throw new Exception("Неверный формат чисел. Допустимые числа: арабские (1,2,3,4,5...) или римские (I,II,III,IV,V...) от 1 до 10 включительно.");
            }
        }

        if (num1 < 1 || num1 > 10 || num2 < 1 || num2 > 10) {
            throw new Exception("Числа должны быть от 1 до 10 включительно.");
        }

        int result = 0;
        switch (operation) {
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num1 - num2;
                break;
            case '*':
                result = num1 * num2;
                break;
            case '/':
                if (num2 != 0) {
                    result = num1 / num2;
                } else {
                    throw new Exception("Ошибка: деление на ноль.");
                }
                break;
            default:
                throw new Exception("Неверная арифметическая операция. Допустимые операции: +, -, *, /.");
        }

        if (result < 1) {
            throw new Exception("Результат работы калькулятора с римскими числами должен быть не меньше единицы.");
        }

        return result < 11 ? String.valueOf(result) : RomanNumerals.toRoman(result);
    }
}

class RomanNumerals {
    private static final int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    private static final String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

    public static int toArabic(String roman) throws IllegalArgumentException {
        int result = 0;
        int i = 0;

        while (!roman.isEmpty() && i < values.length) {
            if (roman.startsWith(symbols[i])) {
                result += values[i];
                roman = roman.substring(symbols[i].length());
            } else {
                i++;
            }
        }

        if (!roman.isEmpty()) {
            throw new IllegalArgumentException("Неверный формат римских чисел.");
        }

        return result;
    }

    public static String toRoman(int arabic) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < values.length && arabic > 0; i++) {
            while (arabic >= values[i]) {
                arabic -= values[i];
                result.append(symbols[i]);
            }
        }

        return result.toString();
    }
}0+