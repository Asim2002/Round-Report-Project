package IAPackage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Debater implements CommonInterface {

    private String debaterName;
    private int debaterID;
    private ArrayList<String> debaterNames;
    private ArrayList<Integer> debaterIDs;
    private File file1;
    private File file2;

    /**
     * Instantiates variables and calls a method that adds a new debater to the list of debaters available
     * 
     * @param newDebaterName
     * @param newDebaterID
     * @throws IOException
     */
    public Debater(String newDebaterName, int newDebaterID) throws IOException {
        file1 = new File("debaterNames.in");
        Scanner stores = new Scanner(file1);

        //Stores the information from the file into an ArrayList
        this.debaterNames = new ArrayList<String>();
        while (stores.hasNext()) {
            debaterNames.add(stores.next());
        }

        file2 = new File("debaterIDs.in");
        stores = new Scanner(file2);

        //Stores the information from the file into an ArrayList
        this.debaterIDs = new ArrayList<Integer>();
        while (stores.hasNext()) {
            debaterIDs.add(stores.nextInt());
        }
        
        //Calls a method that adds a new debater to the file of debater names and the file of debater IDs
        addNew(newDebaterName, file1, newDebaterID, file2);
    }

    /**
     * Instantiates variables and calls method that set the debater's name and ID based on the available list of debaters
     * 
     * @param name
     * @throws FileNotFoundException
     */
    public Debater(String name) throws FileNotFoundException {
        File file = new File("debaterNames.in");
        Scanner stores = new Scanner(file);

        //Stores the information from the file into an ArrayList
        this.debaterNames = new ArrayList<String>();
        while (stores.hasNext()) {
            debaterNames.add(stores.next());
        }

        file = new File("debaterIDs.in");
        stores = new Scanner(file);

        //Stores the information from the file into an ArrayList
        this.debaterIDs = new ArrayList<Integer>();
        while (stores.hasNext()) {
            debaterIDs.add(stores.nextInt());
        }

        //sets the name of the debater requested by the user
        setName(name);
        
        //sets the ID of the debater requested by the user
        setID();
    }

    /**
     * Gets the name of the debater
     * 
     * @return
     */
    @Override
    public String getName() {
        return this.debaterName;
    }

    /**
     * Sets the name of the debater if it exists in the available list 
     * 
     * @param name
     */
    @Override
    public void setName(String name) {
        //Checks if the user-requested name exists in the file of debater names
        if (debaterNames.contains(name)) {
            this.debaterName = name;
        } else {
            JOptionPane.showMessageDialog(null, "Error: rerun the program and spell the debater's name differently.");
            System.exit(0);
        }
    }

    /**
     * Gets the debater's ID
     * 
     * @return
     */
    @Override
    public int getID() {
        return this.debaterID;
    }

    /**
     * Sets the debater's ID if it exists in the list of IDs
     */
    @Override
    public void setID() {
        
        //Checks if the index of the debater name is less than the size of the list that contains the debater IDs
        //in order to avoid any program errors with trying to get the index
        if (this.debaterNames.indexOf(debaterName) < debaterIDs.size()) {
            this.debaterID = debaterIDs.get(this.debaterNames.indexOf(debaterName));
        } else {
            JOptionPane.showMessageDialog(null, "Error: rerun the program and correct the debater's ID");
            System.exit(0);
        }
    }

    /**
     *
     * Adds a new debater to the available list of debaters
     * 
     * @param newName
     * @param file1
     * @param newID
     * @param file2
     * @throws IOException
     */
    @Override
    public void addNew(String newName, File file1, int newID, File file2) throws IOException {
        //Checks if the debater or ID the user tries to add already exists
        if ((debaterNames.contains(newName)) || debaterIDs.contains(newID)) {
            JOptionPane.showMessageDialog(null, "This debater or ID already exists.");
            return;
        }
        
        //If the debater and ID do not already exist, they are added to the corresponding files
        FileWriter fw = new FileWriter(file1, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(" " + newName);
        bw.close();
        fw.close();

        FileWriter fw2 = new FileWriter(file2, true);
        BufferedWriter bw2 = new BufferedWriter(fw2);
        bw2.write(" " + newID);
        bw2.close();
        fw2.close();
        JOptionPane.showMessageDialog(null, "Debater added.");
    }
}
