public abstract class Aircraft
    implements PassengerCapable, CargoCapable
 {
    private String name;
    private String model;
    private String wingSpan;
    private int batteryCapacity;
    private int seatCount;
    private double maximumPayload;

    public String getName() {
        return name;
    }
    public String getModel() {
        return model;
    }
    public String getWingSpan() {
        return wingSpan;
    }
    public int getBatteryCapacity() {
        return batteryCapacity;
    }
    public void setBatteryCapacity(int batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public void setWingSpan(String wingSpan) {
        this.wingSpan = wingSpan;
    }

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

    public int compareTo(Aircraft other) {
        if (other == null || other.getName()==null)
            return -1;

        return this.name.compareTo(other.name);
    }

    public String toString() {
        return name;
    }
}