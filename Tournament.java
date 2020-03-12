package IAPackage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Tournament implements CommonInterface {

    private String tournamentName;
    private int tournamentID;
    private ArrayList<String> tournamentNames;
    private ArrayList<Integer> tournamentIDs;
    File file1;
    File file2;

    /**
     * Instantiates variables and calls a method that adds a new tournament to the list of tournaments available
     * 
     * @param newTournamentName
     * @param newTournamentID
     * @throws IOException
     */
    public Tournament(String newTournamentName, int newTournamentID) throws IOException {
        file1 = new File("tournamentNames.in");
        Scanner stores = new Scanner(file1);

        //Stores the information from the file into an ArrayList
        tournamentNames = new ArrayList<String>();
        while (stores.hasNext()) {
            tournamentNames.add(stores.nextLine());
        }

        file2 = new File("tournamentIDs.in");
        stores = new Scanner(file2);

        //Stores the information from the file into an ArrayList
        tournamentIDs = new ArrayList<Integer>();
        while (stores.hasNext()) {
            tournamentIDs.add(stores.nextInt());
        }

        //Calls a method that adds a new tournament name and new tournament ID to the corresponding files
        addNew(newTournamentName, file1, newTournamentID, file2);
    }

    /**
     * Instantiates variables and calls method that set the tournament's name and ID based on the available list of tournaments
     * 
     * @param name
     * @throws FileNotFoundException
     */
    public Tournament(String name) throws FileNotFoundException {
        File file1 = new File("tournamentNames.in");
        Scanner stores = new Scanner(file1);

        //Stores the information from the file into an ArrayList
        tournamentNames = new ArrayList<String>();
        while (stores.hasNext()) {
            tournamentNames.add(stores.nextLine());
        }

        File file2 = new File("tournamentIDs.in");
        stores = new Scanner(file2);

        //Stores the information from the file into an ArrayList
        tournamentIDs = new ArrayList<Integer>();
        while (stores.hasNext()) {
            tournamentIDs.add(stores.nextInt());
        }

        //sets the name of the tournament requested by the user
        setName(name);
        
        //sets the ID of the tournament requested by the user
        setID();
    }

    /**
     * Gets the name of the debater
     * 
     * @return
     */
    @Override
    public String getName() {
        return this.tournamentName;
    }

    /**
     * Sets the name of the tournament if it exists in the available list 
     * @param name
     */
    @Override
    public void setName(String name) {
        //Checks if the user-requested tournament exists in the file of tournament names
        if (tournamentNames.contains(name)) {
            this.tournamentName = name;
        } else {
            JOptionPane.showMessageDialog(null, "Error: rerun the program and spell the tournament's name differently.");
            System.exit(0);
        }
    }

    /**
     * Gets the tournament's ID
     * 
     * @return
     */
    @Override
    public int getID() {
        return this.tournamentID;
    }

    /**
     * Sets the tournament's ID if it exists in the list of IDs
     */
    @Override
    public void setID() {
        //Checks if the index of the tournament name is less than the size of the list that contains the tournament IDs
        //in order to avoid any program errors with trying to get the index
        if (this.tournamentNames.indexOf(tournamentName) < tournamentIDs.size()) {
            this.tournamentID = tournamentIDs.get(this.tournamentNames.indexOf(tournamentName));
        } else {
            JOptionPane.showMessageDialog(null, "Error: rerun the program and correct the tournament's ID");
            System.exit(0);
        }
    }

    /**
     * Adds a new tournament to the available list of tournaments 
    * 
     * @param newName
     * @param file1
     * @param newID
     * @param file2
     * @throws IOException
     */
    @Override
    public void addNew(String newName, File file1, int newID, File file2) throws IOException {
        //Checks the tournament or ID the user tries to add already exists
        if ((tournamentNames.contains(newName)) || tournamentIDs.contains(newID)) {
            JOptionPane.showMessageDialog(null, "This tournament already exists.");
            return;
        }
        
        //If the tournament and ID do not already exist, they are added to the corresponding files
        FileWriter fw = new FileWriter(file1, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("\n" + newName);
        bw.close();
        fw.close();

        FileWriter fw2 = new FileWriter(file2, true);
        BufferedWriter bw2 = new BufferedWriter(fw2);
        bw2.write(" " + newID);
        bw2.close();
        fw2.close();
        JOptionPane.showMessageDialog(null, "Tournament added.");
    }
}
