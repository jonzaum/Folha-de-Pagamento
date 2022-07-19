package payroll.model.payments;

import java.io.Serializable;
import java.time.DayOfWeek;

public class PaymentSchedule implements Serializable {

    private Integer monthDay;

    private DayOfWeek weekDay;

    private String schedule;

    public PaymentSchedule(Integer monthDay, DayOfWeek weekDay, String schedule) {
        this.monthDay = monthDay;
        this.weekDay = weekDay;
        this.schedule = schedule;
    }

    public Integer getMonthDay() {
        return monthDay;
    }

    public void setMonthDay(Integer monthDay) {
        this.monthDay = monthDay;
    }

    public DayOfWeek getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(DayOfWeek weekDay) {
        this.weekDay = weekDay;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    @Override
    public String toString() {
        return " Agenda de pagamento {" +
                "Dia do mês: " + getMonthDay() +
                ", Dia da semana: " + getWeekDay() +
                ", Tipo de agenda: '" + getSchedule() + '\'' +
                '}';
    }
}
