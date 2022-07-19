package payroll.main;

import payroll.control.EmployeeControl;
import payroll.control.PaymentsControl;
import payroll.control.ServicesControl;
import payroll.model.Enterprise;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.DateTimeException;
import java.util.Base64;
import java.util.Scanner;
import java.util.Stack;

public class Menu {
    public static void menu(Enterprise enterprise){

        Scanner input = new Scanner(System.in);

        Stack<String> undo = new Stack<>();
        Stack<String> redo = new Stack<>();

        int option = 0;

        try{
            while(option != 14){
                System.out.println("\nFolha de pagamento");
                System.out.println("Escolha uma das opções: ");
                System.out.println("[1] - Cadastrar empregado");
                System.out.println("[2] - Remover empregado");
                System.out.println("[3] - Listar empregados");
                System.out.println("[4] - Lançar cartão de ponto");
                System.out.println("[5] - Lançar resultado de venda");
                System.out.println("[6] - Lançar taxa de serviço");
                System.out.println("[7] - Editar dados do empregado");
                System.out.println("[8] - Rodar folha de pagamento");
                System.out.println("[9] - Imprimir listas dos pagamentos");
                System.out.println("[10] - Mudar agenda de pagamentos");
                System.out.println("[11] - Registrar nova agenda de pagamentos");
                System.out.println("[12] - Undo (Desfazer última alteração)");
                System.out.println("[13] - Redo (Refazer última alteração)");
                System.out.println("[14] - Sair\n");

                assert enterprise != null;
                option = input.nextInt();
                input.nextLine();

                if(option == 1){
                    undo.push(storeState(enterprise));
                    enterprise.getEmployees().add(EmployeeControl.register(input, enterprise.getPaymentSchedules()));
                }
                else if(option == 2){
                    if(!enterprise.getEmployees().isEmpty()){
                        undo.push(storeState(enterprise));
                        EmployeeControl.removeEmployee(input, enterprise.getEmployees());
                    }else{
                        System.out.println("Não há empregados cadastrados!");
                    }
                }
                else if(option == 3){
                    if(!enterprise.getEmployees().isEmpty()){
                        EmployeeControl.listEmployees(enterprise.getEmployees());
                    }else{
                        System.out.println("Não há empregados cadastrados!");
                    }
                }
                else if(option == 4){
                    if(!enterprise.getEmployees().isEmpty()){
                        undo.push(storeState(enterprise));
                        ServicesControl.postTimeCard(input, enterprise.getEmployees());
                    }else{
                        System.out.println("Não há empregados cadastrados!");
                    }
                }
                else if(option == 5){
                    if(!enterprise.getEmployees().isEmpty()){
                        undo.push(storeState(enterprise));
                        ServicesControl.postSaleResult(input, enterprise.getEmployees());
                    }else{
                        System.out.println("Não há empregados cadastrados!");
                    }
                }
                else if(option == 6){
                    if(!enterprise.getEmployees().isEmpty()){
                        undo.push(storeState(enterprise));
                        ServicesControl.postServiceTax(input, enterprise.getEmployees());
                    }else{
                        System.out.println("Não há empregados cadastrados!");
                    }
                }
                else if(option == 7){
                    if(!enterprise.getEmployees().isEmpty()){
                        undo.push(storeState(enterprise));
                        EmployeeControl.editEmployee(input, enterprise.getEmployees());
                    }else{
                        System.out.println("Não há empregados cadastrados!");
                    }
                }
                else if(option == 8){
                    if(!enterprise.getEmployees().isEmpty()){
                        undo.push(storeState(enterprise));
                        enterprise.getPaymentsLists().add(PaymentsControl.payroll(input, enterprise.getEmployees()));
                    }else{
                        System.out.println("Não há empregados cadastrados!");
                    }
                }
                else if(option == 9){
                    System.out.println("\n----Relatório com as listas dos pagamentos----\n");
                    System.out.println(enterprise.getPaymentsLists());
                }
                else if(option == 10){
                    undo.push(storeState(enterprise));
                    EmployeeControl.editEmployeeSchedule(input, enterprise.getEmployees(), enterprise.getPaymentSchedules());
                    System.out.println("Operação realizada com sucesso!");
                }
                else if(option == 11){
                    undo.push(storeState(enterprise));
                    enterprise.getPaymentSchedules().add(PaymentsControl.createPaymentSchedule(input));
                    System.out.println("Operação realizada com sucesso!");
                }
                else if(option == 12){
                    if(!undo.isEmpty()){
                        redo.push(storeState(enterprise));
                        String state = undo.pop();
                        enterprise = restoreState(state);
                        System.out.println("Operação realizada com sucesso!");
                    }else{
                        System.out.println("Nenhuma alteração para desfazer.");
                    }
                }
                else if(option == 13){
                    if(!redo.isEmpty()){
                        undo.push(storeState(enterprise));
                        String state = redo.pop();
                        enterprise = restoreState(state);
                        System.out.println("Operação realizada com sucesso!");
                    }else{
                        System.out.println("Nenhuma alteração para refazer.");
                    }
                }
            }
        }catch (DateTimeException | IndexOutOfBoundsException err){
            System.out.println("\nData inválida!\n");
            menu(enterprise);
        } catch (NumberFormatException err){
            System.out.println("\nEntrada inválida!\n");
            menu(enterprise);
        }
    }

    private static String storeState(Enterprise enterprise) {
        try {
            ByteArrayOutputStream bass = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bass);
            oos.writeObject(enterprise);
            oos.close();
            return Base64.getEncoder().encodeToString(bass.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            return "Não foi possível salvar o estado.";
        }
    }

    private static Enterprise restoreState(String state) {
        try {
            byte[] data = Base64.getDecoder().decode(state);
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
            return (Enterprise) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Não foi possível restaurar o estado.");
            return null;
        }
    }
}
