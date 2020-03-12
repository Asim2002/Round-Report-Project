package IAPackage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Website {

    private String laptopAddress;
    private String username;
    private String password;
    private String response;
    private boolean shortReport;

    /**
     * Calls the methods that instantiate the object's properties and
     * automate tabroom.com, the website this program extracts data from, to create a round report
     * with judge comments and basic information about the round
     * 
     * @param username
     * @param password
     * @param laptopAddress
     * @param debaterID
     * @param tournamentID
     * @param extractionID
     * @param response
     * @param shortReport
     */
    public Website(String username, String password, String laptopAddress, int debaterID, int tournamentID, String extractionID, String response, Boolean shortReport) {
        setLaptopAddress(laptopAddress);
        setUsername(username);
        setPassword(password);
        setResponse(response);
        createRoundReport(debaterID, tournamentID, extractionID);
    }

    /**
     * Sets the laptop address where Chrome Driver, a download needed for the program to automate the debate website properly, is stored 
     * @param laptopAddress
     */
    public void setLaptopAddress(String laptopAddress) {
        this.laptopAddress = laptopAddress;
    }

    /**
     * Sets the username of my coach's account on tabroom.com, the debate website, in order to login
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the password of my coach's account on tabroom.com, the debate website, in order to login
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the laptop address where Chrome Driver, a download needed for the program to automate the debate website properly, is stored
     * @return
     */
    public String getLaptopAddress() {
        return laptopAddress;
    }

    /**
     * Gets the username of my coach's account on tabroom.com, the debate website, in order to login
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the password of my coach's account on tabroom.com, the debate website, in order to login
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the response, which refers to whether the user wants to see the process of automation or not, to yes/true or no/false
     * @param response
     */
    public void setResponse(String response) {
        this.response = response;
    }

    /**
     * Gets the response of yes/true or no/false to whether the user wants to see the process of automation or not
     * @return
     */
    public String getResponse() {
        return response;
    }

    /**
     * Gets shortReport, which refers to whether the report is only outputting basic information (true) or outputting judge
     * comments as well (false)
     * @return
     */
    public boolean getShortReport() {
        return shortReport;
    }

    /**
     * Sets shortReport, which refers to whether the report is only outputting basic information (true) or outputting judge
     * comments as well (false)
     * @param shortReport
     */
    public void setShortReport(boolean shortReport) {
        this.shortReport = shortReport;
    }

    /**
     * Automate tabroom.com, the website this program extracts data from, to create a round report
     * with judge comments and basic information about the round
     * 
     * @param debaterID
     * @param tournamentID
     * @param extractionID
     */
    public void createRoundReport(int debaterID, int tournamentID, String extractionID) {
        //This connects the Chrome Driver to the program
        System.setProperty("webdriver.chrome.driver", laptopAddress);

        ChromeOptions options = new ChromeOptions();

        //If the user says they do not want to see the process, the automation process is done in the background
        //and a tab does not pop up
        if (response.equals("No")) {
            options.addArguments("--headless");
        }

        WebDriver driver = new ChromeDriver(options);

        //URL to login into my coach's account
        String url = "https://www.tabroom.com/user/login/login.mhtml";

        //Access the url
        driver.get(url);

        //Waits for the page to load
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        //Types coach's email
        driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/form/div[1]/span[2]/input")).sendKeys(username);

        //Types coach's password
        driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/form/div[2]/span[2]/input")).sendKeys(password);

        //Clicks submit
        driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/form/div[3]/input")).click();

        //Logged in so waiting for page to load
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        //After logging in, the process goes straight to the URL with the table that has the information requested by the user
        url = "https://www.tabroom.com/user/chapter/history.mhtml?tourn_id=" + tournamentID + "&student_id=" + debaterID + ""; //can edit
        driver.navigate().to(url);
        
        //This finds the number of rounds by finding the number of rows with the tag "tr"
        //It subtracts 2 to only account for the rows in the desired table
        int numberOfRounds = driver.findElements(By.tagName("tr")).size() - 2;

        //1. If the extraction ID is null, that implies that the user-requested information does not exist, 
        //because there is no table with the requested information
        //2. However, the extraction ID is based on the table-specific files (tableDebaters, tableTournaments, tableIDs),
        //and the tableIDs file has to be updated manually.
        //3. If the user added a debater and tournament combination that does not have an extraction ID yet, then a debater and
        //tournament ID is created as well, because the user adds data to the NON-table-specific files (debaterNames, debaterIDs,
        //tournamentNames, tournamentIDs)
        //4. This would mean that information does exist about the debater and tournament, but just that the table ID has not been
        //added to the file. 
        //This is the purpose of shortReport. If the extractionID is null, shortReport is true. The program navigates to the correct
        //URL based on the debater and tournament ID. However, because there is no extraction ID, it only outputs the basic data
        //of the table (which means it is a short report), as accessing the URLs with the judge comments requires an extraction ID in this program.
        //However, the user will be able to manually access the ballots, as the shortReport process purposely leaves the tab open.
        //If it is just an incorrect combination, the exception will say "This debater did not go to this tournament"
        //or the round report with be blank.
        if (shortReport) {
            try {
                //outputs each table row, which provides the basic information of the round report
                List<WebElement> basicInformationPoints = driver.findElements(By.tagName("tr"));
                //It purposely doesn't close the tab, so that my coach can look at the ballots manually
                for (int i = 1; i <= numberOfRounds; i++) { 
                    System.out.println(basicInformationPoints.get(i).getText());
                    System.out.println("");
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "This debater did not go to this tournament.");
            }
            //ends the program
            return;
        }

        //An ArrayList that holds elements that are used to access each tab
        ArrayList<String> tabs;
        
        //The outer for loop is based on the number of rounds to extract information for each round of the user-requested tournament
        for (int i = 1; i <= numberOfRounds; i++) { 
            //This inner for loop outputs the round data where i is the round number, giving the row, and j is the column with basic information
            System.out.println("\nBasic Information for Round " + i + ": \n");
            for (int j = 1; j <= 6; j++) {
                //Creates a list with the information that should be extracted 
                List<WebElement> dataPoints = driver.findElements(By.xpath("//*[@id=\"" + extractionID
                        + "\"]/tbody/tr[" + i + "]/td[" + j + "]")); 
                //This inner for loop outputs the information in the list
                for (WebElement eachDataPoint : dataPoints) {
                    System.out.println(eachDataPoint.getText()); 
                }
            }
            
            //In tabroom, the seventh column, represented by "td[7]", holds links to the judge comments
            //This list holds the elements that are clicked on to access the links
            List<WebElement> webPages = driver.findElements(By.xpath("//*[@id=\"" + extractionID + "\"]/tbody/tr[" + i + "]/td[7]/a"));
            
            //If there are judge comments, the program extracts the information from each link one at a time.
            if (webPages.size() != 0) { 
            System.out.println("\nJudge Comments for Round " + i + ": \n");
            
            //Understand that the tab with the table with the user-requested information is the first tab
            //and thus index 0
            //This for loop clicks on the links one at a time
            //Each links contains the judge's comments (or ballot)
            //This is an inner for loop, so the process is round by round
            for (WebElement eachWebPage : webPages) { 
                //Clicks on the ballot link
                eachWebPage.click(); 
                //Tabs holds elements that are used to access each tab. Because the first tab is 0, and the second tab
                //is the link that was just clicked on, the driver switches to the second tab (or index 1)
                tabs = new ArrayList(driver.getWindowHandles());
                driver.switchTo().window(tabs.get(1));
                //Then, it extracts the ballot data in the link
                extractBallotData(driver);
                System.out.println("");
                //It closes the tab with the ballot
                driver.close();
                //It switches back to the original table
                driver.switchTo().window(tabs.get(0));
                }
            }
        }
        //close all the tabs
        driver.quit();
    }

    /**
     * Extracts ballot data once on the webpage that contains the judge's comments (also known as a ballot)
     * This method is called inside of createRoundReport to make the code easier to understand
     * 
     * @param driver
     */
    public void extractBallotData(WebDriver driver) {
        //This extracts ballot data based on the consistent formatting of tabroom
        for (int k = 3; k <= 4; k++) {
            List<WebElement> ballotData = driver.findElements(By.xpath("//*[@id=\"content\"]/div[3]/div[" + k + "]")); //create extraction array
            for (WebElement eachBallotDataPoint : ballotData) {
                System.out.println(eachBallotDataPoint.getText());
            }
        }
    }

}
