package ru.stqa.dmiv.tests;

import org.testng.SkipException;
import ru.stqa.dmiv.rest.RestHelper;

public class TestBase {

  protected static final RestHelper rest = new RestHelper();

  private boolean isIssueOpen(int issueId){
    return rest.isIssueOpen(issueId);
  }

  public void skipIfNotFixed(int issueId){
    if (isIssueOpen(issueId)) {
      throw new SkipException("Ignored because of issue " + issueId);
    }
  }
}
