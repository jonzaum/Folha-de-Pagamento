package payroll.model.services;

import java.io.Serializable;
import java.time.LocalDate;

public class ServiceTax implements Serializable {

    public Double value;

    public LocalDate date;

    public ServiceTax(Double value, LocalDate date) {
        this.value = value;
        this.date = date;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return " Taxa de serviço {" +
                " valor da taxa: " + getValue() +
                ", data: " + getDate() +
                '}';
    }
}
