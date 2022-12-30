package DatasetSplitter;

import org.checkerframework.checker.units.qual.A;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TextFileReader {

    public ArrayList<String> readSourceList(String path) {
        ArrayList<String> sourcePaths = new ArrayList<>();
        BufferedReader r = null;
        try {
            String l;
            r = new BufferedReader(new FileReader(path));
            while ((l = r.readLine()) != null) {
                if (l.equals("")) continue;
                sourcePaths.add(l);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (r != null)
                    r.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return sourcePaths;
    }
}
