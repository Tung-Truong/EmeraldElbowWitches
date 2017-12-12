package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class CSVtoArrayList {

    /*
     * readFile takes a generic file to read, number of expected columns, and the destination table, and reads all data from the file into the database
     */
    public static void readCSVToArray(String fileToRead2, int numColExpected, ArrayList<String> arrayList) throws ClassNotFoundException, SQLException, FileNotFoundException, NullPointerException {
        String[] newRow = new String[numColExpected];
        File fileToRead = new File(fileToRead2);

        try {
            Scanner inputStream = new Scanner(fileToRead);
            inputStream.useDelimiter(",|\\n");
            for (int p = 0; p < newRow.length; p++) {
                inputStream.next();
            }

            while (inputStream.hasNext()) {  //while there's still a row
                for (int i = 0; ((inputStream.hasNext()) && (i < newRow.length)); i++) { // get the row
                    String data = inputStream.next();
                    if (data.contains("\r")) {
                        data = data.replace("\r", "");
                    }
                    data = data.trim();
                    arrayList.add(data);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
