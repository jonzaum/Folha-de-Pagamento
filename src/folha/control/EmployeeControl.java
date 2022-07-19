package payroll.control;

import payroll.main.GeneralUtils;
import payroll.model.employee.*;
import payroll.model.payments.PaymentData;
import payroll.model.payments.PaymentSchedule;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class EmployeeControl {

    public static Employee register(Scanner input, ArrayList<PaymentSchedule> paymentSchedules){
        Employee employee;
        Syndicate syndicate = null;
        PaymentData paymentData;
        PaymentSchedule paySchedule;
        String schedule;
        int answer = 0;

        UUID id = UUID.randomUUID();

        System.out.println("\nDigite o nome do empregado:");
        String name = input.nextLine();

        System.out.println("\nDigite o endereço:");
        String address = input.nextLine();

        System.out.println("\nQual o tipo de empregado?");
        System.out.println("[1] - Horista, [2] - Salariado, [3] - Comissionado");
        answer = input.nextInt();

        if(answer == 1){
            System.out.println("Digite o salário por hora:");
            Double hourlySalary = input.nextDouble();

            employee = new Hourly(id, name, address, syndicate, null, hourlySalary);
            paySchedule = paymentSchedules.get(1);
        }
        else if(answer == 2){
            System.out.println("Digite o salário:");
            Double salary = input.nextDouble();

            employee = new Salaried(id, name, address, syndicate, null, salary);
            paySchedule = paymentSchedules.get(0);
        }
        else if(answer == 3){
            System.out.println("Digite o salário fixo:");
            Double fixedSalary = input.nextDouble();
            System.out.println("Digite a taxa de comissão:");
            Double commission = input.nextDouble();

            employee = new Commissioned(id, name, address, syndicate, null, fixedSalary, commission);
            paySchedule = paymentSchedules.get(2);
        }else{
            System.out.println("Digite o salário:");
            Double salary = input.nextDouble();

            employee = new Salaried(id, name, address, syndicate, null, salary);
            paySchedule = paymentSchedules.get(0);
        }

        System.out.println("\nO empregado é membro do sindicato? ([1] - Sim, [2] - Não): ");
        int aux = input.nextInt();

        if (aux == 1){
            UUID syndicateId = UUID.randomUUID();
            System.out.println("Digite a taxa sindical:");
            Double tax = input.nextDouble();
            syndicate = new Syndicate(syndicateId, id, true, tax);
            employee.setSyndicate(syndicate);
        }

        input.nextLine();

        System.out.println("\nVamos cadastrar os dados de pagamento!");
        System.out.println("Digite o número do banco:");
        int bank = input.nextInt();
        System.out.println("Digite o número da agência:");
        int agency = input.nextInt();
        System.out.println("Digite o número da conta:");
        int account = input.nextInt();

        String payMethod = GeneralUtils.readPayMethod(input);
        paymentData = new PaymentData(bank, agency, account, payMethod, paySchedule);
        employee.setPaymentData(paymentData);

        input.nextLine();
        System.out.println();
        System.out.println("Empregado cadastrado com sucesso!");
        System.out.println(employee.toString());

        return employee;
    }

    public static void removeEmployee(Scanner input, ArrayList<Employee> employees){
        System.out.println("Digite o ID do empregado que deve ser removido:");
        String id = input.nextLine();

        Employee employeeToRemove = null;
        for(Employee employee : employees){
            if(employee.getId().toString().equals(id)){
                employeeToRemove = employee;
                employees.remove(employee);
                break;
            }
        }

        if(employeeToRemove == null){
            System.out.println("Empregado não foi encontrado!");
        }else{
            System.out.println("Empregado removido com sucesso!");
        }
    }

    public static void listEmployees(ArrayList<Employee> employees) {
        int i = 1;
        System.out.println("\n\nListagem de empregados");
        for (Employee employee : employees) {
            System.out.println("\nEmpregado #" + i);
            System.out.println(employee.toString());
            System.out.println("\n");
            i++;
        }
    }

    public static void editEmployee(Scanner input, ArrayList<Employee> employees){

        System.out.println("\nDigite o ID do empregado:");
        String id = input.nextLine();

        Employee employeeToEdit = null;
        for(Employee employee : employees){
            if(employee.getId().toString().equals(id)){
                employeeToEdit = employee;
            }
        }

        if(employeeToEdit == null){
            System.out.println("\nEmpregado não foi encontrado!");
        }
        else{
            System.out.println("\nQual informação deseja editar?");
            System.out.println("[1] Nome");
            System.out.println("[2] Endereço");
            System.out.println("[3] Tipo de empregado");
            System.out.println("[4] Método de pagamento");
            System.out.println("[5] Vínculo ao sindicato");
            System.out.println("[6] Taxa do sindicato");
            int option = input.nextInt();
            input.nextLine();

            if(option == 1){
                System.out.println("Digite o novo nome: ");
                String name = input.nextLine();
                for(Employee employee : employees){
                    if(employee.getId().toString().equals(id)){
                        employee.setName(name);
                    }
                }
                System.out.println("Nome editado!");
            }
            else if(option == 2){
                System.out.println("Digite o novo endereço: ");
                String address = input.nextLine();
                for(Employee employee : employees){
                    if(employee.getId().toString().equals(id)){
                        employee.setAddress(address);
                    }
                }
                System.out.println("Endereço editado!");
            }
            else if(option == 3){
                System.out.println("\nEscolha o novo tipo");
                System.out.println("[1] - Horista, [2] - Salariado, [3] - Comissionado");
                int type = input.nextInt();

                for(Employee employee : employees){
                    if(employee.getId().toString().equals(id)){
                        Employee newEmployee = null;
                        if(type == 1){
                            System.out.println("Digite o salário por hora:");
                            Double hourlySalary = input.nextDouble();
                            System.out.println();

                            newEmployee = new Hourly(employee.getId(), employee.getName(),
                                    employee.getAddress(), employee.getSyndicate(),
                                    employee.getPaymentData(), hourlySalary);
                        }else if(type == 2){
                            System.out.println("Digite o salário:");
                            Double salary = input.nextDouble();
                            System.out.println();

                            newEmployee = new Salaried(employee.getId(), employee.getName(),
                                    employee.getAddress(), employee.getSyndicate(),
                                    employee.getPaymentData(), salary);
                        }else if(type == 3){
                            System.out.println("Digite o salário fixo:");
                            Double fixedSalary = input.nextDouble();
                            System.out.println();

                            System.out.println("Digite a taxa de comissão:");
                            Double commission = input.nextDouble();
                            System.out.println();

                            newEmployee = new Commissioned(employee.getId(), employee.getName(),
                                    employee.getAddress(), employee.getSyndicate(),
                                    employee.getPaymentData(), fixedSalary, commission);
                        }else{
                            System.out.println("Opção inválida!");
                        }

                        employees.remove(employee);
                        employees.add(newEmployee);
                        System.out.println("\nTipo de empregado editado com sucesso!");
                    }
                }
            }
            else if(option == 4){
                String payMethod = GeneralUtils.readPayMethod(input);

                for(Employee employee : employees){
                    if(employee.getId().toString().equals(id)){
                        employee.getPaymentData().setPaymentMethod(payMethod);
                    }
                }
                System.out.println("Método de pagamento atualizado!");
            }
            else if(option == 5){
                for(Employee employee : employees){
                    if(employee.getId().toString().equals(id)){
                        if(employee.getSyndicate() == null){
                            System.out.println("Empregado não pertence ao sindicato, deseja cadastrar?");
                            System.out.println("[1] Sim, [2] Não");
                            int choice = input.nextInt();
                            if(choice == 1){
                                System.out.println("Digite a taxa sindical:");
                                Double tax = input.nextDouble();
                                employee.setSyndicate(new Syndicate(UUID.randomUUID(),
                                        employee.getId(), true, tax));
                            }
                        }else{
                            if(employee.getSyndicate().getActive()){
                                System.out.println("Seu cadastro no sindicato está ativo, deseja desativar?");
                                System.out.println("[1] Sim, [2] Não");
                                int choice = input.nextInt();
                                if(choice == 1){
                                    employee.getSyndicate().setActive(false);
                                }
                            }else{
                                System.out.println("Seu cadastro no sindicato está desativado, deseja ativar?");
                                System.out.println("[1] Sim, [2] Não");
                                int choice = input.nextInt();
                                if(choice == 1){
                                    employee.getSyndicate().setActive(true);
                                }
                            }
                        }
                    }
                }
                System.out.println("Operação realizada com sucesso!");
            }
            else if(option == 6){
                for(Employee employee : employees){
                    if(employee.getId().toString().equals(id)){
                        if(employee.getSyndicate() == null){
                            System.out.println("Empregado não pertence ao sindicato");
                        }else{
                            System.out.println("Digite a nova taxa sindical:");
                            Double tax = input.nextDouble();
                            employee.getSyndicate().setTax(tax);
                        }
                    }
                }
                System.out.println("Operação realizada com sucesso!");
            }
            else{
                System.out.println("Opção inválida!");
            }
        }

    }

    public static void editEmployeeSchedule(Scanner input, ArrayList<Employee> employees,
                                            ArrayList<PaymentSchedule> paymentSchedules){
        System.out.println("\nDigite o ID do empregado:");
        String id = input.nextLine();

        Boolean foundEmp = null;
        for(Employee employee : employees){
            if(employee.getId().toString().equals(id)){
                foundEmp = true;
                int counter = 0;

                StringBuilder str = new StringBuilder("\n-Escolha uma das agendas para receber seu salário-\n");
                for(PaymentSchedule p : paymentSchedules){
                    str.append('[').append(counter).append(']').append(p.toString()).append('\n');
                    counter +=1;
                }
                System.out.println(str);

                int choice = input.nextInt();

                if(choice <= counter && choice >= 0){
                    employee.getPaymentData().setSchedule(paymentSchedules.get(choice));
                    System.out.println("Agenda atualizada!\n");
                }else{
                    System.out.println("Opção inválida");
                }

            }
        }

        if(foundEmp == null){
            System.out.println("\nEmpregado não foi encontrado!");
        }

    }
}
