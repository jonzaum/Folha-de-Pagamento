package payroll.model.payments;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class PaymentList implements Serializable {

    private ArrayList<PayCheck> payChecks;

    private LocalDate date;

    public PaymentList(ArrayList<PayCheck> payChecks, LocalDate date) {
        this.payChecks = payChecks;
        this.date = date;
    }

    public ArrayList<PayCheck> getPayChecks() {
        return payChecks;
    }

    public void setPayChecks(ArrayList<PayCheck> payChecks) {
        this.payChecks = payChecks;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        String str = "\nLista de pagamentos";
        str += "\nData: " + this.getDate() + "\n";
        for(PayCheck paycheck : this.getPayChecks()){
            str += paycheck.toString();
        }
        str += "\n";
        return str;
    }
}
