/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IAPackage;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author casey
 */
public interface CommonInterface {

    /**
     *
     * @return
     */
    String getName();

    /**
     *
     * @param name
     */
    void setName(String name);

    /**
     *
     * @return
     */
    int getID();

    /**
     *
     */
    void setID();

    /**
     *
     * @param newName
     * @param file1
     * @param newID
     * @param file2
     * @throws IOException
     */
    void addNew(String newName, File file1, int newID, File file2) throws IOException;
}
