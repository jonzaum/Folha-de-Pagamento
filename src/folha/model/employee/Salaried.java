package payroll.model.employee;

import payroll.model.payments.PaymentData;

import java.time.LocalDate;
import java.util.UUID;

public class Salaried extends Employee {

    private Double salary;

    public Salaried(){

    }

    public Salaried(Double salary){
        this.salary = salary;
    }

    public Salaried(UUID id, String name, String address, Syndicate syndicate,
                    PaymentData paymentData, Double salary) {
        super(id, name, address, syndicate, paymentData);
        this.salary = salary;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return super.toString() + "\nAssalariado: {" +
                "Salário=" + getSalary() +
                '}';
    }

    @Override
    public Double getGrossPayment(LocalDate payDate) {
        return (this.getSalary() / this.getMethodDiv());
    }
}
