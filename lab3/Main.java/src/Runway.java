public class Runway {
    private int runwayID;
    public Runway(int runwayID) {
        this.runwayID = runwayID;
    }
    public Runway() {}
    public int getRunwayID() {
        return runwayID;
    }
    public void setRunwayID(int runwayID) {
        this.runwayID = runwayID;
    }
    public String toString()
    {
        return runwayID + " ";
    }
}
