package DatasetSplitter;

public class Pair {
    private int src;
    private int cla;

    Pair(int src, int cla) {
        this.src = src;
        this.cla = cla;
    }

    public int getSrc(){
        return src;
    }

    public int getCla(){
        return cla;
    }
}
