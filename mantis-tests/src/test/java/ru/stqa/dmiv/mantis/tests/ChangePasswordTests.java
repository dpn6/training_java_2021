package ru.stqa.dmiv.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.dmiv.mantis.appmanager.HttpSession;
import ru.stqa.dmiv.mantis.model.MailMessage;
import ru.stqa.dmiv.mantis.model.UserData;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class ChangePasswordTests extends TestBase {

  private UserData user = null;
  private String password = "password";

  @BeforeMethod(alwaysRun = true)
  void registrationUser() throws MessagingException {
    if (app.db().users().size() == 1) {
      long now = System.currentTimeMillis();
      String username = String.format("user%s", now);
      app.james().createUser(username, password);
      app.registration().start(username, String.format("user%s@localhost", now));
      app.james().drainEmail(username, password);
    }
  }

  @Test
  public void testChangePassword() throws IOException, MessagingException {

    user = app.db().users().stream().filter(u -> !(u.getUsername().equals("administrator"))).findAny().get();
    String email = user.getEmail();
    app.adm().resetPassword(user);
    List<MailMessage> mailMessages = app.james().waitForMail(user.getUsername(), password, 100000);
    String confirmationLink = app.james().findConfirmationLink(mailMessages, email);
    String newPassword = password + "new";
    app.adm().changePassword(confirmationLink, newPassword);
    //Проверка, что можно залогиниться под пользователем после смены пароля
    HttpSession sessionUser = app.newSession();
    assertTrue(sessionUser.login(user.getUsername(), newPassword));
    assertTrue(sessionUser.isLoggedIdAs(user.getUsername()));
  }

  @AfterMethod(alwaysRun = true)
  //чтобы при повторном запуске тестов на смену пароля не было предыдущих писем с подтверждающей ссылкой
  void deleteUserEmail() throws MessagingException {
    if (user != null) {
      app.james().drainEmail(user.getUsername(), password);
    }
  }
}
