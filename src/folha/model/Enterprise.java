package payroll.model;

import payroll.control.PaymentsControl;
import payroll.model.employee.Employee;
import payroll.model.payments.PaymentList;
import payroll.model.payments.PaymentSchedule;

import java.io.Serializable;
import java.util.ArrayList;

public class Enterprise implements Serializable {

    private ArrayList<Employee> employees;

    private final ArrayList<PaymentList> paymentsLists;

    private ArrayList<PaymentSchedule> paymentSchedules;


    public Enterprise(){
        this.employees = new ArrayList<>();
        this.paymentsLists = new ArrayList<>();
        this.paymentSchedules = PaymentsControl.startSchedules();
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(ArrayList<Employee> employees) {
        this.employees = employees;
    }

    public ArrayList<PaymentList> getPaymentsLists() {
        return paymentsLists;
    }

    public ArrayList<PaymentSchedule> getPaymentSchedules() {
        return paymentSchedules;
    }

    public void setPaymentSchedules(ArrayList<PaymentSchedule> paymentSchedules) {
        this.paymentSchedules = paymentSchedules;
    }

}
