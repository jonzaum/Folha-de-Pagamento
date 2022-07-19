package payroll.model.payments;

import payroll.model.employee.Employee;

import java.io.Serializable;
import java.time.LocalDate;

public class PayCheck implements Serializable {

    private Employee employee;

    private Double paymentValue;

    private Double taxes;

    private boolean haveTax;

    private LocalDate date;

    public PayCheck(){

    }

    public PayCheck(Employee employee, LocalDate date) {
        this.employee = employee;
        this.date = date;
    }

    public PayCheck(Employee employee, Double paymentValue, Double taxes, boolean haveTax, LocalDate date) {
        this.employee = employee;
        this.paymentValue = paymentValue;
        this.taxes = taxes;
        this.haveTax = haveTax;
        this.date = date;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Double getPaymentValue() {
        return paymentValue;
    }

    public void setPaymentValue(Double paymentValue) {
        this.paymentValue = paymentValue;
    }

    public Double getTaxes() {
        return taxes;
    }

    public void setTaxes(Double taxes) {
        this.taxes = taxes;
    }

    public boolean isHaveTax() {
        return haveTax;
    }

    public void setHaveTax(boolean haveTax) {
        this.haveTax = haveTax;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        String str = "\nEmpregado: " + this.getEmployee().getName();
        str += "\nMétodo de pagamento: " + this.getEmployee().getPaymentData().getPaymentMethod();
        str += "\nValor bruto do pagamento: " + this.getPaymentValue();
        if(isHaveTax()){
            str += "\nTaxas (sindicato + serviços): " + this.getTaxes();
            str += "\nSalário final (bruto - taxas): " + (this.getPaymentValue() - this.getTaxes());
        }else{
            str += "\nSalário final (não houve desconto de taxas): " + this.getPaymentValue();
        }
        str += "\nData: " + this.getDate() + "\n";

        return str;
    }
}
