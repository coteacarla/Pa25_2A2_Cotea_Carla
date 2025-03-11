import java.util.ArrayList;
import java.util.List;

public class Airport {
    private String name;
    private int numberOfFlights;
    private int numberOfRunways;
    private List<Runway> runways = new ArrayList<>();

    public Airport(String name, int numberOfFlights, int numberOfRunways) {
        this.name = name;
        this.numberOfFlights = numberOfFlights;
        this.numberOfRunways = numberOfRunways;
    }
    public Airport(){}

    public String getName() {
        return name;
    }
    public int getNumberOfFlights() {
        return numberOfFlights;
    }
    public int getNumberOfRunways() {
        return numberOfRunways;
    }
    public List<Runway> getRunways() {
        return runways;
    }
    public void setRunways(List<Runway> runways) {
        this.runways = runways;
    }
    public void addRunway(Runway runway) {
        runways.add(runway);
    }
    public void removeRunway(Runway runway) {
        runways.remove(runway);
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setNumberOfFlights(int numberOfFlights) {
        this.numberOfFlights = numberOfFlights;
    }
    public void setNumberOfRunways(int numberOfRunways) {
        this.numberOfRunways = numberOfRunways;
    }
    @Override
    public String toString() {
        return name + " " + numberOfFlights + " " + numberOfRunways;
    }
}