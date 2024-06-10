import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) throws CalculatorException {
        Scanner scn = new Scanner(System.in);
        System.out.print("Введите выражение: ");
        String exp = scn.nextLine();

        System.out.print(calc(exp));
    }

    public static String calc(String input) throws CalculatorException {
        Converter converter = new Converter();

        String[] action = {"+", "-", "/", "*"};
        String[] regAction = {"\\+", "-", "/", "\\*"};

        int mycount = 0;
        for (int i = 0; i < regAction.length; i++) {
            String[] tmp = input.split(regAction[i]);

            if (tmp.length == 2) mycount++;
            else if (tmp.length > 2) mycount += (tmp.length - 1);
        }

        if (mycount > 1) {
            throw new CalculatorException("Используйте выражение только из двух чисел");
        }

        int actionIndex = -1;
        for (int i = 0; i < action.length; i++) {
            if (input.contains(action[i])) {
                actionIndex = i;
                break;
            }
        }

        if (actionIndex == -1) {
            throw new CalculatorException("Используйте арифметическое выражение");
        }

        String[] data = input.split(regAction[actionIndex]);

        if (converter.isRoman(data[0]) == converter.isRoman(data[1])) {
            int a, b;
            boolean isRoman = converter.isRoman(data[0]);

            if (isRoman) {
                a = converter.romanToInt(data[0]);
                b = converter.romanToInt(data[1]);
            } else {
                a = Integer.parseInt(data[0]);
                b = Integer.parseInt(data[1]);
            }

            if ((a > 10) || (b > 10)) {
                throw new CalculatorException("Используйте числа меньше 10");
            }

            int result = switch (action[actionIndex]) {
                case "+" -> a + b;
                case "-" -> a - b;
                case "*" -> a * b;
                default -> a / b;
            };

            if (isRoman) {
                return converter.intToRoman(result);
            } else {
                return String.valueOf(result);
            }
        } else {
            throw new CalculatorException("Используйте числа одного формата");
        }
    }
}

