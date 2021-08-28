package ru.stqa.dmiv.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import ru.stqa.dmiv.addressbook.appmanager.ApplicationManager;
import ru.stqa.dmiv.addressbook.model.GroupData;
import ru.stqa.dmiv.addressbook.model.Groups;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class TestBase {
  Logger logger = LoggerFactory.getLogger(TestBase.class);

  protected static final ApplicationManager app
//          = new ApplicationManager(System.getProperty("browser", BrowserType.FIREFOX));
          = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));

  @BeforeSuite
  public void setUp() throws IOException {
    app.init();
  }

  @AfterSuite(alwaysRun = true)
  public void tearDown() {
    app.stop();
  }

  @BeforeMethod
  public void logTestStart(Method m, Object[] p) {
    logger.info("start test " + m.getName() + " with parameters " + Arrays.asList(p));
  }

  @AfterMethod(alwaysRun = true)
  public void logTestStop(Method m) {
    logger.info("stop test " + m.getName());
  }


  public void verifyGroupListInUi() {
    if (Boolean.getBoolean("verifyUI")) {
      Groups uiGroups = app.group().all();
      Groups dbGroups = app.db().groups();
//    Set<GroupData> groupDataSet = app.db().groups().stream().map(g -> new GroupData().withId(g.getId()).withName(g.getName())).collect(Collectors.toSet());
//    Groups dbGroups = new Groups(groupDataSet);
//    assertThat(uiGroups, equalTo(dbGroups));
      assertThat(uiGroups, equalTo(dbGroups.stream()
              .map(g -> new GroupData().withId(g.getId()).withName(g.getName()))
              .collect(Collectors.toSet())));
    }
  }
}
