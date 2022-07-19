package payroll.model.employee;

import payroll.model.services.ServiceTax;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class Syndicate implements Serializable {

    private UUID id;

    private UUID EmployeeId;

    private boolean active;

    private Double tax;

    private ArrayList<ServiceTax> serviceTaxes;

    public Syndicate(){

    }

    public Syndicate(UUID id, UUID EmployeeId, boolean active, Double tax) {
        this.id = id;
        this.EmployeeId = EmployeeId;
        this.active = active;
        this.tax = tax;
        this.serviceTaxes = new ArrayList<ServiceTax>();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getEmployeeId() {
        return EmployeeId;
    }

    public void setEmployeeId(UUID employeeId) {
        EmployeeId = employeeId;
    }

    public boolean isActive() {
        return active;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public ArrayList<ServiceTax> getServiceTaxes() {
        return serviceTaxes;
    }

    public void setServiceTaxes(ArrayList<ServiceTax> serviceTaxes) {
        this.serviceTaxes = serviceTaxes;
    }

    @Override
    public String toString() {
        String str = "\nId no sindicato: " + getId();
        str += "\nMembro ativo do sindicato: " + getActive();
        str += "\nTaxa do sindicato: " + getTax();
        str += "\nTaxas de serviço: {" + getServiceTaxes() + "}";
        return str;
    }

}
