package ru.stqa.dmiv.mantis.tests;

import org.testng.annotations.Test;
import ru.stqa.dmiv.mantis.appmanager.HttpSession;
import ru.stqa.dmiv.mantis.model.UserData;

import java.io.IOException;

public class ChangePasswordTests extends TestBase {

  @Test
  public void testChangePassword() throws IOException {
    UserData user = app.db().users().iterator().next();
    app.web().resetPassword(user);
    System.out.println("" + user.getUsername());
  }
}
