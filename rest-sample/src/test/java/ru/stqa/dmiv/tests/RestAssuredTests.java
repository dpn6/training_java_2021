package ru.stqa.dmiv.tests;

import com.jayway.restassured.RestAssured;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.stqa.dmiv.rest.Issue;

import java.io.IOException;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class RestAssuredTests extends TestBase{

  @BeforeClass
  public void init(){
    RestAssured.authentication = RestAssured.basic("288f44776e7bec4bf44fdfeb1e646490", "");
  }

  @Test
  public void testCreateIssue() throws IOException {
    Set<Issue> oldIssues = rest.getIssues();
    Issue newIssue = new Issue().withSubject("My test Issue").withDescription("New test Issue description");
    int issueId = rest.createIssue(newIssue);
    Set<Issue> newIssues = rest.getIssues();
    oldIssues.add(newIssue.withId(issueId));
    assertEquals(newIssues, oldIssues);
  }

  //тест должен быть выполнен, т.к этот баг-репорт был предварительно закрыт
  @Test
  public void testNotNeedSkip() throws IOException {
    Issue newIssue = new Issue().withSubject("My closed test Issue").withDescription("My issue will be closed");
    int issueId = rest.createIssue(newIssue);
    rest.closeIssue(newIssue.withId(issueId));
    skipIfNotFixed(issueId);
    System.out.println(String.format("Test was executing, because issue %s is closed", issueId));
  }

  //тест должен быть пропущен, т.к баг-репорт был предварительно открыт
  @Test
  public void testNeedSkip() throws Exception {
    Issue newIssue = new Issue().withSubject("My open test Issue").withDescription("My issue will not be closed");
    int issueId = rest.createIssue(newIssue);

    try {
      skipIfNotFixed(issueId);
      throw new Exception("Test was executing, but issue is open");
    } catch (SkipException e) {
      System.out.println(e.getMessage());
    }
  }
}
