import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;


public class Schedule {
  private Airport airport;
  private ArrayList<Flight> flights= new ArrayList<>();
  private Map<Flight, Runway> flightMap = new HashMap<>();

    public Map<Flight, Runway> getFlightMap() {
        return flightMap;
    }

    public void setFlightMap(Map<Flight, Runway> flightMap) {
        this.flightMap = flightMap;
    }
    public Airport getAirport() {
      return airport;
    }
    public void setAirport(Airport airport) {
      this.airport = airport;
    }
    public ArrayList<Flight> getFlights() {
      return flights;
    }
    public void setFlights(ArrayList<Flight> flights) {
      this.flights = flights;
    }
    public Schedule(Airport airport) {
      this.airport = airport;
    }
    public Schedule(){}
    public void add(Flight flight) {
      flights.add(flight);
    }
    public void remove(Flight flight) {
      flights.remove(flight);
    }


  public void scheduleFlights() {
    for (Flight flight : flights)
    {
      if (!flightMap.containsKey(flight))
      {
        if (flightMap.isEmpty())
        {
          flightMap.put(flight, airport.getRunways().get(0));
        }
        else
        {
          Runway availableRunway = null;
          for (Runway runway : airport.getRunways())
          {
            boolean overlap = false;
            for (Map.Entry<Flight, Runway> entry : flightMap.entrySet())
            {
              if (entry.getValue().equals(runway))
              {
                Flight scheduledFlight = entry.getKey();
                if (scheduledFlight.checkOverlapping(flight.getInterval()))
                {
                  overlap = true;
                  break;
                }
              }
            }

            if (!overlap) {
              availableRunway = runway;
              break;
            }
          }

          if (availableRunway != null) {
            flightMap.put(flight, availableRunway);
          }
        }
      }
    }
  }
  public void bonus() {
    Map<Runway, Integer> runwayUsage = new HashMap<>();
    for (Runway runway : airport.getRunways()) {
      runwayUsage.put(runway, 0);
    }

    int totalRunways = airport.getRunways().size();
    int maxFlightsPerRunway = (int) Math.ceil((double) flights.size() / totalRunways);

    for (Flight flight : flights) {
      if (!flightMap.containsKey(flight)) {
        Runway bestRunway = null;

        for (Runway runway : airport.getRunways()) {
          boolean overlap = false;

          for (Map.Entry<Flight, Runway> entry : flightMap.entrySet()) {
            if (entry.getValue().equals(runway)) {
              Flight scheduledFlight = entry.getKey();
              if (scheduledFlight.checkOverlapping(flight.getInterval())) {
                overlap = true;
                break;
              }
            }
          }

          if (!overlap && runwayUsage.get(runway) < maxFlightsPerRunway) {
            bestRunway = runway;
          }
        }

        if (bestRunway != null) {
          flightMap.put(flight, bestRunway);
          runwayUsage.put(bestRunway, runwayUsage.get(bestRunway) + 1);
        }
        else {
          Pair<LocalTime, LocalTime> currentinterval=flight.getInterval();
          currentinterval.setFirst(currentinterval.getFirst().plusMinutes(10));
          currentinterval.setSecond(currentinterval.getSecond().plusMinutes(10));
          flight.setInterval(currentinterval);
          bonus();
        }
        }
      }
    }


  public void printSchedule() {
      for (Map.Entry<Flight, Runway> entry : flightMap.entrySet()){
        System.out.println(entry.getKey()+" "+entry.getValue());
      }
  }
  }


