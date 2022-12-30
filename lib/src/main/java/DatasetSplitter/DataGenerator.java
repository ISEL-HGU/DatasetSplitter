package DatasetSplitter;

import java.io.*;
import java.util.ArrayList;

public class DataGenerator {
    private enum State {NUM, BODY}
    public void generateData(String matchedListPath, ArrayList<String> sourcePaths, String classPath, String outputPath){
        TenFoldGenerator tg = new TenFoldGenerator();
        ArrayList<Fold> tenFold = tg.readMathcedList(matchedListPath);

        if(!outputPath.endsWith("/")){
            outputPath = outputPath + File.separator;
        }

        writeData(tenFold, sourcePaths, classPath, outputPath);
    }

    private void writeData(ArrayList<Fold> tenFold, ArrayList<String> sourcePaths, String classPath, String outputPath){
        System.out.println("INFO: Generating Class Tokens in Ten-Fold");
        writeClassData(tenFold, classPath, outputPath);
        System.out.println("INFO: Generating Source Tokens in Ten-Fold");
        for(String sourcePath : sourcePaths){
            int len = sourcePath.split("/").length;
            String tokenizerType = sourcePath.split("/")[len-1].split("\\.")[0];
            System.out.println("INFO: Tokenizer Type: " + tokenizerType);
            writeSourceData(tenFold, sourcePath, outputPath);
        }
    }

    private void writeSourceData(ArrayList<Fold> tenFold, String sourcePath, String outputPath){
        int len = sourcePath.split("/").length;
        String tokenizerType = sourcePath.split("/")[len-1].split("\\.")[0];
        String tmpOutputPath = outputPath + tokenizerType + File.separator;
        File f = new File(tmpOutputPath);
        if(!f.exists()){
            f.mkdirs();
        }
        BufferedReader r = null;
        int tmpFoldHash = -1;
        State s = State.NUM;
//        int testCnt = 0;
        try {
            String l;
            r = new BufferedReader(new FileReader(sourcePath));
            while ((l = r.readLine()) != null) {
                if (l.equals("") && s != State.BODY) continue;
                for(Fold fold : tenFold){
                    if(l.contains("$$METHOD#") && fold.getSourceKey().contains(l.split("#")[1])){
                        if(l.split("#")[1].equals("1883")){
                            System.out.println();
                        }
                        tmpFoldHash = fold.getFoldHash();
                        s = State.BODY;
                        break;
                    } else if(l.contains("$$METHOD#") && !fold.getSourceKey().contains(l.split("#")[1])){
                        tmpFoldHash = -1;
                        s = State.BODY;
                    }
                    else if(!l.contains("$$METHOD#") && !l.contains("$$METHOD_SIGNATURE") && tmpFoldHash == fold.getFoldHash()) {
//                        System.out.println(++testCnt);
                        if(!l.equals(""))
                            writeTestFile(l, tmpOutputPath, fold.getFoldHash());
                        else
                            writeTestFile("$EMPTY$", tmpOutputPath, fold.getFoldHash());
                        s = State.NUM;
                    }
                    else if(!l.contains("$$METHOD#") && !l.contains("$$METHOD_SIGNATURE")){
                        if(!l.equals(""))
                            writeTrainingFile(l, tmpOutputPath, fold.getFoldHash());
                        else
                            writeTrainingFile("$EMPTY$", tmpOutputPath, fold.getFoldHash());
                        s = State.NUM;
                    }
                }
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
    }

    private void writeClassData(ArrayList<Fold> tenFold, String classPath, String outputPath){
//        String tmpOutputPath = "./";
        String tmpOutputPath = outputPath + "opcode" + File.separator;
        File f = new File(tmpOutputPath);
        if(!f.exists()){
            f.mkdirs();
        }
//        int testCnt = 0;
        BufferedReader r = null;
        int tmpFoldHash = -1;
        try {
            String l;
            r = new BufferedReader(new FileReader(classPath));
            while ((l = r.readLine()) != null) {
                if (l.equals("")) continue;
                for(Fold fold : tenFold){
                    if(l.contains("$$METHOD#") && fold.getClassKey().contains(l.split("#")[1])){
                        tmpFoldHash = fold.getFoldHash();
                        break;
                    } else if(l.contains("$$METHOD#") && !fold.getClassKey().contains(l.split("#")[1])){
                        tmpFoldHash = -1;
                    }
                    else if(!l.contains("$$METHOD#") && !l.contains("$$METHOD_SIGNATURE") && tmpFoldHash == fold.getFoldHash()) {
//                        System.out.println(++testCnt);
                        writeTestFile(l, tmpOutputPath, fold.getFoldHash());
                    }
                    else if(!l.contains("$$METHOD#") && !l.contains("$$METHOD_SIGNATURE")){
                        writeTrainingFile(l, tmpOutputPath, fold.getFoldHash());
                    }
                }
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
    }

    private void writeTestFile(String tokens, String outputPath, int hash){
        BufferedWriter bw = null;
        try {
            File file = new File(outputPath + "test_" + hash + ".txt");
            FileWriter fw = new FileWriter(file, true);
            bw = new BufferedWriter(fw);
            if(tokens.contains("\n")){
                bw.write(tokens);
            } else{
                bw.write(tokens + "\n");
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try{
                if(bw!=null)
                    bw.close();
            }catch(Exception ex) {
                System.out.println("Error in closing the BufferedWriter"+ex);
            }
        }
    }

    private void writeTrainingFile(String tokens, String outputPath, int hash){
        BufferedWriter bw = null;
        try {
            File file = new File(outputPath + "training_" + hash + ".txt");
            FileWriter fw = new FileWriter(file, true);
            bw = new BufferedWriter(fw);
            if(tokens.contains("\n")){
                bw.write(tokens);
            } else{
                bw.write(tokens + "\n");
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try{
                if(bw!=null)
                    bw.close();
            }catch(Exception ex) {
                System.out.println("Error in closing the BufferedWriter"+ex);
            }
        }
    }
}
