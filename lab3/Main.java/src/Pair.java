public class Pair<T, U> {
    private T first;
    private U second;
    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }
    public Pair() {}

    public T getFirst() {
        return first;
    }
    public void setFirst(T first) {
        this.first = first;
    }
    public U getSecond() {
        return second;
    }
    public void setSecond(U second) {
        this.second = second;
    }
    public String toString() {
        return first+" "+second;
    }
    public boolean equals(Pair<?, ?> p1, Pair<?, ?> p2) {
        if (p1.getFirst().equals(p2.getFirst()) && p1.getSecond().equals(p2.getSecond())) {
        return true;
        }
        return false;
    }
}