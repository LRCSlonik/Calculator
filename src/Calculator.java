import java.util.Objects;
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
        String[] rome = {"I","II","III","IV","V","VI","VII","VIII","IX","X",
                "1","2","3","4","5","6","7","8","9","10"};


        int mycount = 0;
        for (int i = 0; i < regAction.length; i++) {
            String[] tmp = input.split(regAction[i]);

            if (tmp.length == 2) mycount++;
            else if (tmp.length > 2) mycount += (tmp.length - 1);
        }

        if (mycount > 1) {
            throw new CalculatorException("формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }

        int actionIndex = -1;
        for (int i = 0; i < action.length; i++) {
            if (input.contains(action[i])) {
                actionIndex = i;
                break;
            }
        }

        if (actionIndex == -1) {
            throw new CalculatorException("строка не является математической операцией");
        }

        String[] data = input.split(regAction[actionIndex]);

       for (int j = 0; j < data.length; j++) {
           boolean myFlag = false;

           for (int i = 0; i < rome.length; i++) {
               if (Objects.equals(data[j], rome[i])) {
                   myFlag = true;
                   break;
               }
           }
           if (!myFlag) {
               throw new CalculatorException("Используйте только Римские или Арабские числа от 1 до 10");
           }
       }

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
                throw new CalculatorException("Используйте числа от 1 до 10");
            }
            if ((a < 1) || (b < 1)) {
                throw new CalculatorException("Используйте числа от 1 до 10");
            }

            int result = switch (action[actionIndex]) {
                case "+" -> a + b;
                case "-" -> a - b;
                case "*" -> a * b;
                default -> a / b;
            };
            
            if (isRoman) {
                if (result < 1) {
                    throw new CalculatorException("В римской системе нет отрицательных чисел");
                }
                else return converter.intToRoman(result);
            } else {
                return String.valueOf(result);
            }

        } else {
            throw new CalculatorException("используются одновременно разные системы счисления");
        }
    }
}

