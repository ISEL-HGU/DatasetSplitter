/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package DatasetSplitter;

import java.util.ArrayList;

public class Main {
    // input: 1) a matched method list, 2) a list path of tokenized source method by six tokenizers, 3) tokenized class methods, 4) output path
    // (example) ./RxJava_matched.csv ./RxJava/sourceTokenizedPaths.txt ./RXJava_OPCodes.txt ./RxJava
    // output: a split dataset
    // (example) RxJava/UTFwithoutComment/test1.txt, RxJava/UTFwithoutComment/training1.txt RxJava/opcode/test1.txt, RxJava/opcode/training1.txt, …
    public static void main(String[] args){
        long start = System.currentTimeMillis();
        if(args.length != 4){
            System.err.println("// input: 1) a matched method list, 2) a list path of tokenized source method by six tokenizers, 3) tokenized class methods, 4) output path\n" +
                    "    // (example) ./RxJava_matched.csv ./RxJava/sourceTokenizedPaths.txt ./RXJava_OPCodes.txt ./RxJava\n" +
                    "    // output: a split dataset\n" +
                    "    // (example) RxJava/UTFwithoutComment/test1.txt, RxJava/UTFwithoutComment/training1.txt RxJava/opcode/test1.txt, RxJava/opcode/training1.txt, …\n");
            System.exit(-1);
        }

        String matchedListPath = args[0];
        String sourcePath = args[1];
        String classPath = args[2];
        String outputPath = args[3];

        TextFileReader tfr = new TextFileReader();
        ArrayList<String> sourcePaths = tfr.readSourceList(sourcePath);

        DataGenerator dg = new DataGenerator();
        dg.generateData(matchedListPath, sourcePaths, classPath, outputPath);

        System.out.println("Running Time (sec): " + ((System.currentTimeMillis() - start) / 1000));
    }
}
