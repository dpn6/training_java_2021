package ru.stqa.dmiv.mantis.tests;

import org.testng.annotations.Test;
import ru.stqa.dmiv.mantis.appmanager.HttpSession;
import ru.stqa.dmiv.mantis.model.MailMessage;
import ru.stqa.dmiv.mantis.model.UserData;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class ChangePasswordTests extends TestBase {

  @Test
  public void testChangePassword() throws IOException, MessagingException {

    UserData user = app.db().users().stream().filter(u -> !(u.getUsername().equals("administrator"))).findAny().get();

    String password = "password";
    String email = user.getEmail();
    app.web().resetPassword(user);
    System.out.println("" + user.getUsername());
    List<MailMessage> mailMessages = app.james().waitForMail(user.getUsername(), password, 100000);
    String confirmationLink = app.james().findConfirmationLink(mailMessages, email);
    String newPassword = password + "new";
    app.web().changePassword(confirmationLink, newPassword);

//    assertTrue(app.newSession().login(user.getUsername(), newPassword));

    HttpSession sessionUser = app.newSession();
    assertTrue(sessionUser.login(user.getUsername(), newPassword));
    assertTrue(sessionUser.isLoggedIdAs(user.getUsername()));
  }
}
