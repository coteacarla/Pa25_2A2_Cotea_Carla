public class Drone
        extends Aircraft
        implements PassengerCapable, CargoCapable {
    private int seatCount;
    private double maximumPayload;
    @Override
    public int getSeatCount() {
        return seatCount;
    }
    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }
    @Override
    public double getMaximumPayload() {
        return maximumPayload;
    }

    public void setMaximumPayload(double maximumPayload) {
        this.maximumPayload = maximumPayload;
    }


}