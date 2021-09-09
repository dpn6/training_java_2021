package ru.stqa.dmiv.mantis.appmanager;

import org.openqa.selenium.By;
import ru.stqa.dmiv.mantis.model.UserData;

public class WebHelper extends HelperBase {

  public WebHelper(ApplicationManager app) {
    super(app);
  }

  public void resetPassword(UserData user) {
    adminLogin();
    wd.get(app.getProperty("web.baseUrl") + "manage_user_edit_page.php?user_id=" + user.getId());
    click(By.cssSelector("input[value='Сбросить пароль']"));
  }

  private void adminLogin() {
    wd.get(app.getProperty("web.baseUrl") + "login.php");
    type(By.name("username"), app.getProperty("web.adminLogin"));
    click(By.cssSelector("input[type='submit']"));
    type(By.name("password"), app.getProperty("web.adminPassword"));
    click(By.cssSelector("input[type='submit']"));
  }
}
