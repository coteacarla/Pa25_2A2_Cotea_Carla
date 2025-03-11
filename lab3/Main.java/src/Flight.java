import java.time.LocalTime;

public class Flight {
    int flightNo;
    private Pair<LocalTime, LocalTime> interval;
    public Flight() {}
    Flight(int flightNo, Pair<LocalTime, LocalTime> interval) {
        this.flightNo = flightNo;
        this.interval = interval;
    }
    public Pair<LocalTime, LocalTime> getInterval() {
        return interval;
    }
    public void setInterval(Pair<LocalTime, LocalTime> interval) {
        this.interval = interval;
    }
    public int getFlightNo() {
        return flightNo;
    }
    public void setFlightNo(int flightNo) {
        this.flightNo = flightNo;
    }
    public Flight getFlight() {
        return this;
    }

    public String toString() {
        return flightNo + " ";
    }

    public boolean checkOverlapping(Pair<LocalTime, LocalTime> pair1) {

        if(pair1.getFirst().isBefore(interval.getFirst()) && pair1.getSecond().isBefore(interval.getSecond()))
            return false;
        if(pair1.getFirst().isAfter(interval.getFirst()) && pair1.getSecond().isAfter(interval.getSecond()))
            return false;
            return true;
        }

}
