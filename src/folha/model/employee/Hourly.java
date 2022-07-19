package payroll.model.employee;

import payroll.model.payments.PayCheck;
import payroll.model.payments.PaymentData;
import payroll.model.services.TimeCard;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Hourly extends Employee {

    private Double hourlySalary;

    private ArrayList<TimeCard> timeCards;

    public Hourly(){

    }

    public Hourly(Double hourlySalary){
        this.hourlySalary = hourlySalary;
        this.timeCards = new ArrayList<TimeCard>();
    }

    public Hourly(UUID id, String name, String address, Syndicate syndicate,
                  PaymentData paymentData, Double hourlySalary) {
        super(id, name, address, syndicate, paymentData);
        this.hourlySalary = hourlySalary;
        this.timeCards = new ArrayList<TimeCard>();
    }

    public Double getHourlySalary() {
        return hourlySalary;
    }

    public void setHourlySalary(Double hourlySalary) {
        this.hourlySalary = hourlySalary;
    }

    public ArrayList<TimeCard> getTimeCards() {
        return timeCards;
    }

    public void setTimeCards(ArrayList<TimeCard> timeCards) {
        this.timeCards = timeCards;
    }

    @Override
    public String toString() {
        return super.toString() + "\nHorista: {" +
                " Salário por hora: " + getHourlySalary() +
                ", Cartões de ponto: " + getTimeCards() +
                '}';
    }

    @Override
    public Double getGrossPayment(LocalDate payDate) {
        ArrayList<TimeCard> timeCards;
        ArrayList<PayCheck> payChecks = this.getPaymentData().getPayChecks();
        double hours, extraHours, grossPayment = 0.0;

        if(payChecks.isEmpty()){
            Predicate<TimeCard> dateFilter = timeCard -> !timeCard.getDate().isAfter(payDate);

            timeCards = this.getTimeCards().stream().filter(dateFilter).collect(Collectors.toCollection(ArrayList::new));
        }else{
            LocalDate lastPayCheckDate = payChecks.get(payChecks.size() - 1).getDate();

            Predicate<TimeCard> dateFilter = timeCard -> timeCard.getDate().isAfter(lastPayCheckDate)
                    && !timeCard.getDate().isAfter(payDate);

            timeCards = this.getTimeCards().stream().filter(dateFilter).collect(Collectors.toCollection(ArrayList::new));
        }

        for(TimeCard tc : timeCards){
            LocalTime timeEntry = tc.getTimeEntry();
            LocalTime timeOut = tc.getTimeOut();
            Duration time = Duration.between(timeEntry, timeOut);
            hours = (double) time.getSeconds() / 3600;

            if(hours > 8.0){
                extraHours = hours - 8.0;
                grossPayment += 8.0 * this.getHourlySalary();
                grossPayment += extraHours * 1.5 * this.getHourlySalary();
            }
            else if(hours >= 0.0){
                grossPayment += hours * this.getHourlySalary();
            }
        }

        return grossPayment;
    }

}
