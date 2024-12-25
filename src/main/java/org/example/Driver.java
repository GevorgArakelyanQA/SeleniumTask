package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;


public class Driver {
    private static WebDriver driver;
    private static String projectName;

    private static String baseURL = "https://qa.podcastle.ai/";
    private static String projectsURL = "https://qa.podcastle.ai/editor/workspace/projects";

    private static String loginModalXpath = "//section[@class=\"pc-sign-up-wrapper\"]";
    private static String loginXpath = "//div[@class=\"pc-header-login\"]";
    private static String statusXpath = "//span[@class=\"cursor-pointer\"]";
    private static String submitButton = "//button[@type=\"submit\"]";
    private static String projectCreateButtonXpath = "//div[@class=\"_912c5b13cf-card\"]";
    private static String projectCreationModalXpath = "//div[@class=\"flex justify-center gap-16 p-32\"]";
    private static String audioProjectCreationButton = "//div[@id=\"audio-project\"]";
    private static String projectNameXpath = "//span[@class=\"_fadbfcda65-inline-input-text typo-base-medium block h-20\"]";
    private static String projectsDivXpath = "//span[@class=\"_fadbfcda65-inline-input-text typo-base-medium block\"]";


    private static WebElement searchXpathElement(String xpath) {
        return driver.findElement(By.xpath(xpath));
    }

    public static void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(baseURL);
    }

    public static void browserTurnDown() {
        driver.quit();
    }

    public static void loginButtonClick() {
        isElementDisplayed(loginXpath, 3000);
        searchXpathElement(loginXpath).click();

    }

    public static void singIn() {
        isElementDisplayed(loginModalXpath, 5000);
        WebElement email = searchXpathElement("//input[@type=\"text\"]");
        email.sendKeys("gevorg.arakelyan@podcastle.ai");

        WebElement password = searchXpathElement("//input[@type=\"password\"]");
        password.sendKeys("Djvarpass157504$");

        searchXpathElement(submitButton).click();
        System.out.println("Successfully login");

    }

    public static void createAudioProject() {
        isElementDisplayed(projectCreateButtonXpath, 8000);
        searchXpathElement(projectCreateButtonXpath).click();

        isElementDisplayed(projectCreationModalXpath, 3000);
        isElementDisplayed(audioProjectCreationButton, 3000);

        searchXpathElement(audioProjectCreationButton).click();
        try {
            isElementDisplayed(projectCreateButtonXpath, 800);
            Actions action = new Actions(driver);
            action.sendKeys(Keys.ESCAPE);
            System.out.println("Project creation modal appeared, pressed Escape button");
        } catch (NoSuchElementException e) {
            System.out.println("Project creation modal does not appeared");
        }
    }

    public static void projectSavingCheck() {
        isElementDisplayed(statusXpath, 3000);

        List<WebElement> saveStatusList = driver.findElements(By.xpath(statusXpath));
        System.out.println(saveStatusList.size());
        if (saveStatusList.size() == 1) {
            System.out.println("Project saved");
        } else {
            System.out.printf("Project status is %s", saveStatusList.getLast());
        }
    }

    public static void getProjectName() {
        isElementDisplayed(projectNameXpath, 3000);
        projectName = searchXpathElement(projectNameXpath).getText();
    }

    public static void projectsPageNavigation() {
        driver.navigate().to(projectsURL);
    }

    public static void checkCreatedProject() {
        isElementDisplayed(projectsDivXpath, 5000);

        List<WebElement> projectsNameList = driver.findElements(By.xpath(projectsDivXpath));
        for (WebElement item : projectsNameList) {
            if (item.getText().equals(projectName)) {
                System.out.printf("New made project is presented with name: %s", projectName);
                break;
            }
        }
    }


    private static void isElementDisplayed(String xpath, int duration) {
        try {
            Wait<WebDriver> wait = new WebDriverWait(Driver.driver, Duration.ofMillis(duration));
            wait.until(d -> searchXpathElement(xpath)).isDisplayed();

        } catch (Exception e) {
            System.out.printf("The element does not appeared with exception: %s", e);
            Driver.browserTurnDown();
        }

    }
}


