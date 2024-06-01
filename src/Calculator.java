import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        System.out.print("Введите выражение: ");
        String exp = scn.nextLine();

        System.out.print(calc(exp));
    }

    public static String calc (String input) {
        Converter converter = new Converter();

        String[] action = {"+", "-", "/", "*"};
        String[] regAction = {"\\+", "-", "/", "\\*"};

        int actionIndex = -1;
        for (int i = 0; i < action.length; i++) {
            if (input.contains(action[i])) {
                actionIndex = i;
                break;
            }
        }
        if (actionIndex == -1) {
            System.out.println("Используйте арифметическое выражение");
            return "error";
        }
        String[] data = input.split(regAction[actionIndex]);
        if (converter.isRoman(data[0]) == converter.isRoman(data[1])){
            int a,b;
            boolean isRoman = converter.isRoman(data[0]);
            if(isRoman){
                a = converter.romanToInt(data[0]);
                b = converter.romanToInt(data[1]);
            }else {
                a = Integer.parseInt(data[0]);
                b = Integer.parseInt(data[1]);
            }

            int result = switch (action[actionIndex]) {
                case "+" -> a + b;
                case "-" -> a - b;
                case "*" -> a * b;
                default -> a / b;
            };

            if (isRoman){
                return converter.intToRoman(result);
            }else{
                return String.valueOf(result);
            }
        }else {
            System.out.println("Используйте числа одного вида");
        }

        return "error";
    }
}
