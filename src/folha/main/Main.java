package payroll.main;

import payroll.model.Enterprise;

public class Main {

    public static void main(String[] args){

        Enterprise enterprise = new Enterprise();

        Menu.menu(enterprise);

    }

}
