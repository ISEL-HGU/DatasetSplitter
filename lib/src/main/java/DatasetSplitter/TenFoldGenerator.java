package DatasetSplitter;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class TenFoldGenerator {
    public ArrayList<Fold> readMathcedList(String path){
        ArrayList<Pair> allKeyPairs = new ArrayList<>();
        try (
                Reader reader = Files.newBufferedReader(Paths.get(path));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                        .withFirstRecordAsHeader()
                        .withIgnoreHeaderCase()
                        .withTrim());
        ) {
            for (CSVRecord csvRecord : csvParser) {
                //"file path", "method signature", "source hash", "class hash", "new hash"
                String srcHash = csvRecord.get("source hash");
                String classHash = csvRecord.get("class hash");
                Pair keyPair = new Pair(Integer.parseInt(srcHash.trim()), Integer.parseInt(classHash.trim()));
                allKeyPairs.add(keyPair);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Fold> tenFold = new ArrayList<>();
        ArrayList<Pair> tmpPairs = new ArrayList<>();
        int cnt = 0;
        for(int i =1; i <= 10; i++){
            if(i != 10) {
                tmpPairs.addAll(allKeyPairs.subList((i-1) * allKeyPairs.size() / 10, (i * allKeyPairs.size()) / 10));
            } else {
                tmpPairs.addAll(allKeyPairs.subList((i-1) * allKeyPairs.size() / 10, allKeyPairs.size()));
            }
            tenFold.add(new Fold(tmpPairs, i));
            cnt += tmpPairs.size();
            tmpPairs = new ArrayList<>();
        }
        System.out.println(cnt);
        return tenFold;
    }
}
