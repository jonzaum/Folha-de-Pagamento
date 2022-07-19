package payroll.model.services;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class TimeCard implements Serializable {

    public LocalDate date;

    public LocalTime timeEntry;

    public LocalTime timeOut;


    public TimeCard(LocalDate date, LocalTime timeEntry, LocalTime timeOut) {
        this.date = date;
        this.timeEntry = timeEntry;
        this.timeOut = timeOut;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTimeEntry() {
        return timeEntry;
    }

    public void setTimeEntry(LocalTime timeEntry) {
        this.timeEntry = timeEntry;
    }

    public LocalTime getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(LocalTime timeOut) {
        this.timeOut = timeOut;
    }

    @Override
    public String toString() {
        return " Cartão de ponto {" +
                "data: " + getDate() +
                ", entrada: " + getTimeEntry() +
                ", saída: " + getTimeOut() +
                '}' + "\n";
    }
}
