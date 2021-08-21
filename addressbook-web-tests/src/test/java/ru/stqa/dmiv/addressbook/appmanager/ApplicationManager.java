package ru.stqa.dmiv.addressbook.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ApplicationManager {
  private ContactHelper contactHelper;
  private NavigationHelper navigationHelper;
  private GroupHelper groupHelper;
  private SessionHelper sessionHelper;
  protected WebDriver wd;
  private String browser;
  Properties localProperties;

  public ApplicationManager(String browser) {
    this.browser = browser;
  }

  public void init(){

//    localProperties = new Properties(new FileReader(new File(String.format("/src/test/resources/", System.getProperties().getProperty("target")))));

    if (browser.equals(BrowserType.FIREFOX)) {
      wd = new FirefoxDriver();
    } else if (browser.equals(BrowserType.CHROME)) {
      wd = new ChromeDriver();
    } else if (browser.equals(BrowserType.IE)) {
      wd = new InternetExplorerDriver();
    }
    wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
//    wd.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    wd.get("https://localhost/addressbook/");
    groupHelper = new GroupHelper(wd);
    contactHelper = new ContactHelper(wd);
    navigationHelper = new NavigationHelper(wd);
    sessionHelper = new SessionHelper(wd);
    sessionHelper.login("admin", "secret");
  }

  public void stop() {
    wd.quit();
  }


  public GroupHelper group() {
    return groupHelper;
  }

  public NavigationHelper goTo() {
    return navigationHelper;
  }

  public ContactHelper contact() {
    return contactHelper;
  }
}
