package payroll.model.payments;

import java.io.Serializable;
import java.util.ArrayList;

public class PaymentData implements Serializable {

    private int bank;

    private int agency;

    private int account;

    private String paymentMethod;

    private PaymentSchedule schedule;

    private ArrayList<PayCheck> payChecks;

    public PaymentData(){

    }

    public PaymentData(int bank, int agency, int account, String paymentMethod, PaymentSchedule schedule) {
        this.bank = bank;
        this.agency = agency;
        this.account = account;
        this.paymentMethod = paymentMethod;
        this.schedule = schedule;
        this.payChecks = new ArrayList<PayCheck>();
    }

    public int getBank() {
        return bank;
    }

    public void setBank(int bank) {
        this.bank = bank;
    }

    public int getAgency() {
        return agency;
    }

    public void setAgency(int agency) {
        this.agency = agency;
    }

    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentSchedule getSchedule() {
        return schedule;
    }

    public void setSchedule(PaymentSchedule schedule) {
        this.schedule = schedule;
    }

    public ArrayList<PayCheck> getPayChecks() {
        return payChecks;
    }

    public void setPayChecks(ArrayList<PayCheck> payChecks) {
        this.payChecks = payChecks;
    }

    @Override
    public String toString() {
        return " {" +
                "Banco: " + getBank() +
                ", Agencia: " + getAgency() +
                ", Conta: " + getAccount() +
                ", Método de pagamento: '" + getPaymentMethod() + '\'' +
                ", \nAgenda: '" + getSchedule().toString() + '\'' +
                '}';
    }
}
