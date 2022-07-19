package payroll.model.employee;

import payroll.model.payments.PayCheck;
import payroll.model.payments.PaymentData;
import payroll.model.services.SaleResult;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Commissioned extends Employee {

    private Double fixedSalary;

    private Double commission;

    private ArrayList<SaleResult> saleResults;

    public Commissioned(){

    }

    public Commissioned(Double fixedSalary, Double commission){
        this.fixedSalary = fixedSalary;
        this.commission = commission;
        this.saleResults = new ArrayList<SaleResult>();
    }

    public Commissioned(UUID id, String name, String address, Syndicate syndicate, PaymentData paymentData,
                        Double fixedSalary, Double commission) {
        super(id, name, address, syndicate, paymentData);
        this.fixedSalary = fixedSalary;
        this.commission = commission;
        this.saleResults = new ArrayList<SaleResult>();
    }

    public Double getFixedSalary() {
        return fixedSalary;
    }

    public void setFixedSalary(Double fixedSalary) {
        this.fixedSalary = fixedSalary;
    }

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public ArrayList<SaleResult> getSaleResults() {
        return saleResults;
    }

    public void setSaleResults(ArrayList<SaleResult> saleResults) {
        this.saleResults = saleResults;
    }

    @Override
    public String toString() {
        return super.toString() + "\nComissionado: {" +
                " Salário fixo: " + getFixedSalary() +
                ", Comissão: " + getCommission() +
                ", Resultado das vendas: " + getSaleResults() +
                '}';
    }

    @Override
    public Double getGrossPayment(LocalDate payDate) {
        ArrayList<SaleResult> saleResults;
        ArrayList<PayCheck> payChecks = this.getPaymentData().getPayChecks();
        double grossPayment = this.getFixedSalary() / this.getMethodDiv();

        if(payChecks.isEmpty()){
            Predicate<SaleResult> dateFilter = saleResult -> !saleResult.getDate().isAfter(payDate);

            saleResults = this.getSaleResults().stream().filter(dateFilter).
                    collect(Collectors.toCollection(ArrayList::new));
        }else{
            LocalDate lastPayCheckDate = payChecks.get(payChecks.size() - 1).getDate();

            Predicate<SaleResult> dateFilter = saleResult -> saleResult.getDate().isAfter(lastPayCheckDate)
                    && !saleResult.getDate().isAfter(payDate);

            saleResults = this.getSaleResults().stream().filter(dateFilter).
                    collect(Collectors.toCollection(ArrayList::new));
        }

        for(SaleResult sr : saleResults){
            double commission = (this.getCommission() / 100.0) * sr.getValue();
            grossPayment += commission;
        }

        return grossPayment;
    }

}
