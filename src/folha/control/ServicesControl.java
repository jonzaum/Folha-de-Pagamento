package payroll.control;

import payroll.main.GeneralUtils;
import payroll.model.employee.Commissioned;
import payroll.model.employee.Employee;
import payroll.model.employee.Hourly;
import payroll.model.services.SaleResult;
import payroll.model.services.ServiceTax;
import payroll.model.services.TimeCard;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ServicesControl {

    public static void postTimeCard(Scanner input, ArrayList<Employee> Employees){
        System.out.println("Digite o ID do empregado:");
        String id = input.nextLine();

        Predicate<Employee> empFilter = employee -> employee instanceof Hourly;
        ArrayList<Employee> hourlyList = Employees.stream().filter(empFilter).
                collect(Collectors.toCollection(ArrayList::new));

        Hourly employeeToPost = null;
        for(Employee hourly : hourlyList){
            if(hourly.getId().toString().equals(id)){
                employeeToPost = (Hourly) hourly;
            }
        }

        if(employeeToPost == null){
            System.out.println("Empregado não encontrado na lista de Horistas!");
        }else{
            LocalDate date = GeneralUtils.readData(input);

            System.out.println("Horário de entrada:");
            LocalTime timeEntry = GeneralUtils.readTime(input);
            System.out.println("Horário de saída:");
            LocalTime timeOut = GeneralUtils.readTime(input);

            TimeCard timeCard = new TimeCard(date, timeEntry, timeOut);
            employeeToPost.getTimeCards().add(timeCard);
            System.out.println("Cartão de ponto registrado!");
        }

    }

    public static void postSaleResult(Scanner input, ArrayList<Employee> Employees){
        System.out.println("Digite o ID do empregado:");
        String id = input.nextLine();

        Predicate<Employee> empFilter = employee -> employee instanceof Commissioned;
        ArrayList<Employee> commissionedList = Employees.stream().filter(empFilter).
                collect(Collectors.toCollection(ArrayList::new));

        Commissioned employeeToPost = null;
        for(Employee commissioned : commissionedList){
            if(commissioned.getId().toString().equals(id)){
                employeeToPost = (Commissioned) commissioned;
            }
        }

        if(employeeToPost == null){
            System.out.println("Empregado não encontrado na lista de Comissionados!");
        }else{
            System.out.println("Digite o valor da venda:");
            Double value = input.nextDouble();

            LocalDate date = GeneralUtils.readData(input);

            SaleResult saleResult =  new SaleResult(value, date);
            employeeToPost.getSaleResults().add(saleResult);
            System.out.println("Resultado da venda registrado!");
        }
    }

    public static void postServiceTax(Scanner input, ArrayList<Employee> Employees){
        System.out.println("Digite o ID do empregado:");
        String id = input.nextLine();

        Predicate<Employee> empFilter = employee -> employee.getSyndicate() != null && employee.getSyndicate().isActive();
        ArrayList<Employee> syndicateList = Employees.stream().filter(empFilter).
                collect(Collectors.toCollection(ArrayList::new));

        Employee employeeToPost = null;
        for(Employee employee : syndicateList){
            if(employee.getId().toString().equals(id)){
                employeeToPost = employee;
            }
        }

        if(employeeToPost == null){
            System.out.println("Empregado não encontrado na lista de membros do sindicato!");
        }else{
            System.out.println("Digite o valor da taxa de serviço:");
            Double value = input.nextDouble();

            LocalDate date = GeneralUtils.readData(input);

            ServiceTax serviceTax = new ServiceTax(value, date);
            employeeToPost.getSyndicate().getServiceTaxes().add(serviceTax);
        }
    }
}
