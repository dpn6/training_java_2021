package ru.stqa.dmiv.addressbook.tests;

import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import ru.stqa.dmiv.addressbook.appmanager.ApplicationManager;

public class TestBase {
  protected String browser = BrowserType.FIREFOX;

  protected final ApplicationManager app = new ApplicationManager(browser);

  @BeforeMethod(alwaysRun = true)
  public void setUp() {
    app.init();
  }

  @AfterMethod(alwaysRun = true)
  public void tearDown() {
    app.stop();
  }

}
