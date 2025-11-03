import java.io.*;
import java.util.*;
// used trim String that i had to look up due to not knowing how it works
// https://www.geeksforgeeks.org/java/java-string-trim-method-example/

public class DataSet {
    private ArrayList<DataRow> data;
    private int numIndepVariables;

    public DataSet(String fileName) {
        data = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(fileName))) {
            if (!sc.hasNextLine()) return;
            String header = sc.nextLine();
            String[] headers = header.split(",");
            numIndepVariables = headers.length - 1;

            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(",");
                if (parts.length < headers.length) continue;

                double y = Double.parseDouble(parts[0].trim());
                double[] x = new double[numIndepVariables];
                for (int i = 0; i < numIndepVariables; i++) {
                    x[i] = Double.parseDouble(parts[i + 1].trim());
                }
                data.add(new DataRow(y, x));
            }
        } catch (Exception e) {
            System.out.println("Could not read file: " + e.getMessage());
        }
    }

    public ArrayList<DataRow> getRows() {
        return data;
    }

    public int getNumIndependantVariables() {
        return numIndepVariables;
    }
}
