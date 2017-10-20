package Judge;

import IO.OutputWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by teodor donchev on 10/17/2017.
 */
public class Tester {

    public static void compareContent(String actualOutput, String expectedOutput) {
        OutputWriter.writeMessageOnNewLine("Reading files...");
        String mismatchPath = getMismatchPath(expectedOutput);

        List<String> actualOutputString = readFiles(actualOutput);
        List<String> expectedOutputString = readFiles(expectedOutput);

        boolean mismatch = compareStrings(actualOutputString,
                expectedOutputString, mismatchPath);

        if (mismatch) {
            List<String> mismatchSting = readFiles(mismatchPath);

            mismatchSting.forEach(OutputWriter::writeMessageOnNewLine);
        } else {
            OutputWriter.writeMessageOnNewLine("Files are identical. There are no mismatches.");
        }
    }

    private static boolean compareStrings(List<String> actualOutputString,
                                          List<String> expectedOutputString,
                                          String mismatchPath) {
        OutputWriter.writeMessageOnNewLine("Comparing files...");

        String output = "";
        boolean isMismatch = false;

        try {
            FileWriter fileWriter = new FileWriter(mismatchPath);
            BufferedWriter writer = new BufferedWriter(fileWriter);

            for (int i = 0; i < expectedOutputString.size(); i++) {
                String actualLine = actualOutputString.get(i);
                String expectedLine = expectedOutputString.get(i);

                if (!actualLine.equals(expectedLine)) {
                    output = String.format("mismatch -> expected{%s}, actual{%s}\n",
                            expectedLine, actualLine);
                    isMismatch = true;
                } else {
                    output = String.format("line match -> %s\n", actualLine);
                }
                writer.write(output);
            }
            writer.close();

        } catch (Exception e) {
            OutputWriter.displayException("Error.");
        }
        return isMismatch;
    }


    private static List<String> readFiles(String filePath) {
        List<String> text = new ArrayList<>();


        File file = new File(filePath);

        try (FileReader fileReader = new FileReader(file);
             BufferedReader reader = new BufferedReader(fileReader)) {

            String line = reader.readLine();
            while (line != null) {
                text.add(line);

                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            OutputWriter.writeMessageOnNewLine("File not found.");
        }
        return text;
    }

    private static String getMismatchPath(String expectedOutput) {
        int index = expectedOutput.lastIndexOf("\\");
        String directoryPath = expectedOutput.substring(0, index);

        return directoryPath + "\\mismatch.txt";
    }
}
