package payroll.model.employee;

import payroll.model.payments.PayCheck;
import payroll.model.payments.PaymentData;
import payroll.model.services.ServiceTax;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toCollection;

public abstract class Employee implements Serializable {

    private UUID id;

    private String name;

    private String address;

    private Syndicate syndicate;

    private PaymentData paymentData;

    public Employee(){

    }

    public Employee(UUID id, String name, String address, Syndicate syndicate, PaymentData paymentData) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.syndicate = syndicate;
        this.paymentData = paymentData;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Syndicate getSyndicate() {
        return syndicate;
    }

    public void setSyndicate(Syndicate syndicate) {
        this.syndicate = syndicate;
    }

    public PaymentData getPaymentData() {
        return paymentData;
    }

    public void setPaymentData(PaymentData paymentData) {
        this.paymentData = paymentData;
    }

    @Override
    public String toString() {
        String str = "Id no sistema: " + getId();
        str += "\nNome: " + getName();
        str += "\nEndereço: " + getAddress();
        if(getSyndicate() != null){
            str += getSyndicate().toString();
        }else{
            str += "\nNão é membro do sindicato";
        }
        str += "\nDados de pagamento:" + getPaymentData().toString();
        return str;
    }

    public Double getSyndicateTax() {
        Double tax = 0.0;

        if (this.getSyndicate() != null) {
            if (this.getSyndicate().isActive()) {
                tax += this.getSyndicate().getTax();
            }
        }

        return tax;
    }

    public Double calcServicesTaxes(){
        ArrayList<ServiceTax> serviceTaxes;
        ArrayList<PayCheck> payChecks = this.getPaymentData().getPayChecks();
        Double taxes = 0.0;

        if(this.getSyndicate() != null){

            if (payChecks.isEmpty()) {
                serviceTaxes = this.getSyndicate().getServiceTaxes();
            } else {
                LocalDate lastDate = payChecks.get(payChecks.size() - 1).getDate();
                Predicate<ServiceTax> dateFilter = tax -> tax.getDate().isAfter(lastDate);

                serviceTaxes = this.getSyndicate().getServiceTaxes().stream().filter(dateFilter).
                        collect(toCollection(ArrayList::new));
            }

            for(ServiceTax stax : serviceTaxes){
                taxes += stax.getValue();
            }

        }

        return taxes;
    }

    public int getMethodDiv(){
        int div;
        String method = this.getPaymentData().getSchedule().getSchedule();
        if(method.equals("Semanal")){
            div = 4;
        }else if(method.equals("Bisemanal")){
            div = 2;
        }else{
            div = 1;
        }
        return div;
    }

    public abstract Double getGrossPayment(LocalDate payDate);

    public PayCheck makePayment(LocalDate payDate){
        PayCheck payCheck;
        PayCheck lastPayCheck = null;
        Double taxSyndicate = this.getSyndicateTax();
        Double taxes = this.calcServicesTaxes();
        Double paymentValue = this.getGrossPayment(payDate);
        ArrayList<PayCheck> payChecks = this.getPaymentData().getPayChecks();
        boolean haveTax = false;

        if(taxSyndicate > 0.0){
            if(!payChecks.isEmpty()){
                lastPayCheck = payChecks.get(payChecks.size()-1);
            }

            if(lastPayCheck != null){
                LocalDate lastPayDate = lastPayCheck.getDate();

                if(payDate.getMonthValue() != lastPayDate.getMonthValue()){
                    taxes += taxSyndicate;
                    haveTax = true;
                }
            }else{
                taxes += taxSyndicate;
                haveTax = true;
            }
        }

        payCheck = new PayCheck(this, paymentValue, taxes, haveTax, payDate);
        this.getPaymentData().getPayChecks().add(payCheck);
        return payCheck;
    }

}
