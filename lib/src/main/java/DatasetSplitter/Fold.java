package DatasetSplitter;

import java.util.ArrayList;
import java.util.HashSet;

public class Fold {
    private int foldHash = -1;
    private HashSet<String> sourceKey = new HashSet<>();
    private HashSet<String> classKey = new HashSet<>();

    public Fold(ArrayList<Pair> keys, int foldHash) {
        for(Pair p : keys){
            sourceKey.add(""+p.getSrc());
            classKey.add(""+p.getCla());
        }
        this.foldHash = foldHash;
    }

    public int getFoldHash() {
        return foldHash;
    }

    public HashSet<String> getSourceKey() {
        return sourceKey;
    }

    public HashSet<String> getClassKey() {
        return classKey;
    }
}
