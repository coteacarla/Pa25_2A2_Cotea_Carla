import java.time.LocalTime;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
       Aircraft plane1=new Airliner();
       plane1.setName("Aircraft 1");
       Aircraft plane2=new Airliner();
       plane2.setName("Aircraft 2");
       Aircraft[] planes=new Aircraft[2];
       planes[0]=plane1;
       planes[1]=plane2;
        for (Aircraft plane : planes)
            System.out.println(plane.getName());

        Airport airports=new Airport("Henri-Coanda",4,3);
        Runway runway1=new Runway(1);
        Runway runway2=new Runway(2);
        Runway runway3=new Runway(3);
        airports.addRunway(runway1);
        airports.addRunway(runway2);
        airports.addRunway(runway3);
        Pair<LocalTime, LocalTime> slot1=new Pair<>(LocalTime.of(10,0),LocalTime.of(11,0));
        Pair<LocalTime, LocalTime> slot2=new Pair<>(LocalTime.of(12,0),LocalTime.of(12,20));
        Pair<LocalTime, LocalTime> slot3=new Pair<>(LocalTime.of(9,0),LocalTime.of(9,30));
        Flight flight1=new Flight(11,slot1);
        Flight flight2=new Flight(12,slot2);
        Flight flight3=new Flight(13,slot3);
        Flight flight4=new Flight(14,slot2);
        Schedule schedule1=new Schedule(airports);
        schedule1.add(flight1);
        schedule1.add(flight2);
        schedule1.add(flight3);
        schedule1.add(flight4);
        schedule1.scheduleFlights();
        schedule1.printSchedule();



    }
}