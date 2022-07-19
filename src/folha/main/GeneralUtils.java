package payroll.main;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class GeneralUtils {

    public static LocalDate readData(Scanner input){
        System.out.println("Digite o dia (número):");
        int day = input.nextInt();
        System.out.println("Digite o mês (número):");
        int month = input.nextInt();
        System.out.println("Digite o ano:");
        int year = input.nextInt();
        input.nextLine();

        return LocalDate.of(year, month, day);
    }

    public static LocalTime readTime(Scanner input){
        System.out.println("Digite a hora:");
        int hour = input.nextInt();
        System.out.println("Digite o minuto:");
        int minute= input.nextInt();

        return LocalTime.of(hour, minute);
    }

    public static LocalDate getLastJobDay(LocalDate lastDayOfMonth){
        LocalDate lastJobDay;

        if(lastDayOfMonth.getDayOfWeek() == DayOfWeek.SATURDAY){
            lastJobDay = lastDayOfMonth.minusDays(1);
        }else if(lastDayOfMonth.getDayOfWeek() == DayOfWeek.SUNDAY){
            lastJobDay = lastDayOfMonth.minusDays(2);
        }else{
            lastJobDay = lastDayOfMonth;
        }

        return lastJobDay;
    }

    public static String readPayMethod(Scanner input){
        System.out.println("Qual o método de pagamento?");
        System.out.println("[1] - Cheque nos correios, [2] - Depósito bancário, [3] - Em mãos\n");
        int methodChoice = input.nextInt();

        String payMethod;
        if(methodChoice == 1){
            payMethod = "Cheque nos correios";
        }else if(methodChoice == 2){
            payMethod = "Depósito bancário";
        }else if(methodChoice == 3){
            payMethod = "Em mãos";
        }else{
            payMethod = "Depósito bancário";
        }

        return payMethod;
    }
}
