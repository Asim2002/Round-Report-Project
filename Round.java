package IAPackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Round {
    
    private String extractionID;
    private String tables[][];
    File tableRow;

    /**
     * Instantiates 2-D array that holds table IDs and calls a method that searches for the correct ID
     * 
     * @param debaterName
     * @param tournamentName
     * @throws FileNotFoundException
     */
    public Round(String debaterName, String tournamentName) throws FileNotFoundException {
        tables = new String[3][130];
        
        //Creates a 2D array where the first part corresponds to the debater's name, the second corresponds to the tournament's name,
        //and the third corresponds to the table or extraction ID that is based on a debater and tournament combination
        //Each file's index corresponds to each other (e.g. the first line of the tableDebaters file corresponds to the first line of
        //the tableTournaments file and tableIDs file)
        for (int i = 0; i < 3; i++) {
            if (i == 0) {
                tableRow = new File("tableDebaters.in");
            } else if (i == 1) {
                tableRow = new File("tableTournaments.in");
            } else {
                tableRow = new File("tableIDs.in");
            }
            Scanner storesInformation = new Scanner(tableRow);
            
            for (int j = 0; j < 130; j++) {
               tables[i][j] = storesInformation.nextLine(); //stores debater names in the first row
           }
        }
        
        //sets the table's ID
        setExtractionID(debaterName, tournamentName);
    }

    /**
     * Searches for and sets the ID of the table with information about the correct debater AND the tournament the debater went to
     * 
     * @param debaterName
     * @param tournamentName
     * @return
     */
    public String setExtractionID(String debaterName, String tournamentName) {
            //Because each file corresponds to each other, the same index as the user given debater and tournament name can be used
            //to find the table extraction ID
           for (int i = 0; i < 130; i++) {
                if ((tables[0][i].equals(debaterName)) && (tables[1][i].equals(tournamentName))) {
                 extractionID = tables[2][i];
                }
             }
        return extractionID;
    }

    /**
     * Gets the ID of the table with information about the correct debater AND the tournament the debater went to
     * 
     * @return
     */
    public String getExtractionID() {
        return this.extractionID;
    }
}
