package com.example.springbootdemo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.By;

public class UITest {

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\yerla\\Downloads\\chromedriver-win64\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Run in headless mode

        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get("http://localhost:8080/users");

            // Check if "No users found!" message is displayed
            WebElement noUsersMessage = driver.findElement(By.xpath("//h2[text()='No users found!']"));
            assert noUsersMessage.isDisplayed() : "No users message is not displayed";

            // If there are users, check if the user table is displayed
            if (!noUsersMessage.isDisplayed()) {
                WebElement userTable = driver.findElement(By.tagName("table"));
                assert userTable.isDisplayed() : "User table is not displayed";
            }


        } finally {
            // Close the browser
            driver.quit();
        }
    }
}


