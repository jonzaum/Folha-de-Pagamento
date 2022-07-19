package payroll.control;

import payroll.main.GeneralUtils;
import payroll.model.employee.Employee;
import payroll.model.payments.PayCheck;
import payroll.model.payments.PaymentList;
import payroll.model.payments.PaymentSchedule;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Scanner;

public class PaymentsControl {

    public static PaymentList payroll(Scanner input, ArrayList<Employee> employees){
        int i, week = -1;
        PayCheck payCheck;
        PaymentList paymentList = null;
        ArrayList<PayCheck> payCheckList = new ArrayList<>();

        System.out.println("\nDigite a data do primeiro dia do mês: ");
        LocalDate firstDate = GeneralUtils.readData(input);
        System.out.println("\nDigite a data do último dia do mês: ");
        LocalDate lastDate = GeneralUtils.readData(input);

        long size = ChronoUnit.DAYS.between(firstDate, lastDate.plusDays(1));

        for(i = 0; i < size; i++){
            LocalDate current = firstDate.plusDays(i);

            if(current.getDayOfWeek() == firstDate.getDayOfWeek()){
                week += 1;
            }

            for(Employee emp : employees){
                if(verifyPayDate(emp, week, current)){
                    payCheck = emp.makePayment(current);
                    System.out.println(payCheck.toString());
                    payCheckList.add(payCheck);
                }
            }
        }

        if(!payCheckList.isEmpty()) {
            paymentList = new PaymentList(payCheckList, lastDate);
        }

        return paymentList;
    }

    public static boolean verifyPayDate(Employee employee, int week, LocalDate current){
        boolean alreadyPay = false;
        boolean dateInSchedule = false;
        PaymentSchedule empSchedule = employee.getPaymentData().getSchedule();

        switch (empSchedule.getSchedule()) {
            case "Mensal":
                if (empSchedule.getMonthDay() == null) {
                    dateInSchedule = current.isEqual(GeneralUtils.
                            getLastJobDay(current.with(TemporalAdjusters.lastDayOfMonth())));
                } else {
                    dateInSchedule = (empSchedule.getMonthDay() == current.getDayOfMonth());
                }
                break;
            case "Semanal":
                dateInSchedule = (empSchedule.getWeekDay() == current.getDayOfWeek());
                break;
            case "Bisemanal":
                dateInSchedule = (empSchedule.getWeekDay() == current.getDayOfWeek() && week%2==0);
                break;
        }

        for(PayCheck pc : employee.getPaymentData().getPayChecks()){
            if (pc.getDate() == current) {
                alreadyPay = true;
                break;
            }
        }

        return (dateInSchedule && (!alreadyPay));
    }

    public static ArrayList<PaymentSchedule> startSchedules(){
        ArrayList<PaymentSchedule> paymentSchedules = new ArrayList<>();

        paymentSchedules.add(new PaymentSchedule(null, null, "Mensal"));
        paymentSchedules.add(new PaymentSchedule(null, DayOfWeek.FRIDAY, "Semanal"));
        paymentSchedules.add(new PaymentSchedule(null, DayOfWeek.FRIDAY, "Bisemanal"));

        return paymentSchedules;
    }

    public static PaymentSchedule createPaymentSchedule(Scanner input){
        System.out.println("Escolha um tipo de agenda para criar:");
        System.out.println("[1] - Mensal\n[2] - Semanal\n[3] - Bisemanal");
        int choice = input.nextInt();

        if(choice == 1){
            System.out.println("Escolha o dia do pagamento da agenda (de 1 a 28):");
            int day = input.nextInt();

            if(day>0 && day<29){
                return new PaymentSchedule(day, null, "Mensal");
            }else{
                return new PaymentSchedule(null, null, "Mensal");
            }
        }else{
            System.out.println("Escolha o dia da semana:");
            System.out.println("[1] - Segunda\n[2] - Terça\n[3] - Quarta\n[4] - Quinta\n[5] - Sexta");
            int week = input.nextInt();
            DayOfWeek weekDay = DayOfWeek.of(week);

            if(choice == 2){
                if(week>0 && week<6){
                    return new PaymentSchedule(null, weekDay, "Semanal");
                }else{
                    return new PaymentSchedule(null, DayOfWeek.FRIDAY, "Semanal");
                }
            }
            else if(choice == 3){
                if(week>0 && week<6){
                    return new PaymentSchedule(null, weekDay, "Bisemanal");
                }else{
                    return new PaymentSchedule(null, DayOfWeek.FRIDAY, "Bisemanal");
                }
            }else{
                System.out.println("Opção inválida, por padrão foi criada uma agenda mensal");
                return new PaymentSchedule(null, null, "Mensal");
            }
        }
    }

}
