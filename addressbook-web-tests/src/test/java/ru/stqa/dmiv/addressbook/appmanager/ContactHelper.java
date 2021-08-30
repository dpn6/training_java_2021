package ru.stqa.dmiv.addressbook.appmanager;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.dmiv.addressbook.model.ContactData;
import ru.stqa.dmiv.addressbook.model.Contacts;
import ru.stqa.dmiv.addressbook.model.GroupData;
import ru.stqa.dmiv.addressbook.model.Groups;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ContactHelper extends HelperBase {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void returnHomePage() {
    click(By.linkText("home page"));
  }

  public void fillContactForm(ContactData contactData, boolean isCreate) {
    type(By.name("firstname"), contactData.getFirstname());
    type(By.name("lastname"), contactData.getLastname());
//    attach(By.name("photo"), contactData.getPhoto());
    if (isCreate) {
      if (contactData.getGroups().size() > 0) {
        Assert.assertTrue(contactData.getGroups().size() == 1);
        Select dropdown = new Select(wd.findElement(By.name("new_group")));
        dropdown.selectByVisibleText(contactData.getGroups().iterator().next().getName());
      }
    }
    type(By.name("address"), contactData.getAddress());
    type(By.name("home"), contactData.getHomePhone());
    type(By.name("mobile"), contactData.getMobile());
    type(By.name("work"), contactData.getWorkPhone());
    type(By.name("email"), contactData.getEmail());
    type(By.name("email2"), contactData.getEmail2());
    type(By.name("email3"), contactData.getEmail3());
  }

  public void initContactCreation() {
    click(By.linkText("add new"));
  }

  public void deleteSelectedContact() {
    click(By.xpath("//input[@value='Delete']"));
  }

  public void selectContact(int id) {
    wd.findElement(By.cssSelector("input[value = '" + id + "']")).click();
  }

  public void initContactModification(int id) {
    WebElement checkbox = wd.findElement(By.cssSelector(String.format("input[value = '%s']", id)));
    WebElement row = checkbox.findElement(By.xpath("./../.."));
    row.findElement(By.cssSelector("img[title = 'Edit']")).click();

//    альтернатива для поиска
//    wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", id))).click();
  }

  public void submitContactModification() {
    click(By.name("update"));
  }

  public boolean isThereAContact() {
    if (isElementPresent(By.name("selected[]"))) {
      return true;
    }
    return false;
  }

  public void create(ContactData contactData) {
    initContactCreation();
    fillContactForm(contactData, true);
    submitContactCreation();
    returnHomePage();
  }

  private void submitContactCreation() {
    click(By.name("submit"));
  }

  public void modify(ContactData contact) {
    initContactModification(contact.getId());
    fillContactForm(contact, false);
    submitContactModification();
    returnHomePage();
  }

  public void delete(ContactData contact) {
    selectContact(contact.getId());
    deleteSelectedContact();
    closeAlertAccept();
    waitMessage();
  }

  private void waitMessage() {
    try {
      while (!isElementPresent(By.cssSelector(".msgbox"))) {
        System.out.println("Ждемс...");
        Thread.sleep(10);
      }
    } catch (InterruptedException e) {
      System.out.println("InterruptedException");
    }
  }

  public Contacts all() {
    Contacts contactDataList = new Contacts();
    List<WebElement> elements = wd.findElements(By.name("entry"));
    for (WebElement el : elements) {
      List<WebElement> cells = el.findElements(By.tagName("td"));
      int id = Integer.parseInt(cells.get(0).findElement(By.tagName("input")).getAttribute("value"));
      String lastName = cells.get(1).getText();
      String firstName = cells.get(2).getText();
      String address = cells.get(3).getText();
//      String allEmails = cells.get(4).findElement(By.tagName("a")).getText();
      String allEmails = cells.get(4).findElements(By.tagName("a")).stream().map(p -> p.getText()).filter(p -> !p.isEmpty()).collect(Collectors.joining("\n"));
      String phones = cells.get(5).getText();

      ContactData contactData = new ContactData().withId(id).withLastname(lastName).withFirstname(firstName)
              .withAddress(address).withAllPhones(phones).withAllEmails(allEmails);
      contactDataList.add(contactData);
    }
    return contactDataList;
  }

  public ContactData infoFromEdit(ContactData contact) {
    int id = contact.getId();
    initContactModification(id);
    String lastName = wd.findElement(By.cssSelector("input[name = 'lastname']")).getAttribute("value");
    String firstName = wd.findElement(By.cssSelector("input[name = 'firstname']")).getAttribute("value");
    String address = wd.findElement(By.cssSelector("textarea[name = 'address']")).getText();
    String homePhone = wd.findElement(By.cssSelector("input[name = 'home']")).getAttribute("value");
    String mobilePhone = wd.findElement(By.cssSelector("input[name = 'mobile']")).getAttribute("value");
    String workPhone = wd.findElement(By.cssSelector("input[name = 'work']")).getAttribute("value");
    String email = wd.findElement(By.cssSelector("input[name = 'email']")).getAttribute("value");
    String email2 = wd.findElement(By.cssSelector("input[name = 'email2']")).getAttribute("value");
    String email3 = wd.findElement(By.cssSelector("input[name = 'email3']")).getAttribute("value");
    ContactData info = new ContactData()
            .withId(id).withLastname(lastName).withFirstname(firstName).withAddress(address)
            .withHomePhone(homePhone).withMobile(mobilePhone).withWorkPhone(workPhone).withEmail(email)
            .withEmail2(email2).withEmail3(email3);

    wd.navigate().back();
    return info;
  }

  public void includeInGroup(ContactData contact) {
    initContactIncludingInGroup(contact);
    selectGroup(contact);
    submitContactIncludingInGroup(contact);
  }

  private void submitContactIncludingInGroup(ContactData contact) {
    click(By.name("add"));
  }

  private void selectGroup(ContactData contact) {
    if (contact.getGroups().size() > 0) {
      //достаем информацию об изменяемом контакте из бд
      DbHelper db = new DbHelper();
      Groups groupsInDb = db.contacts().stream().filter(c -> c.getId() == contact.getId()).map(c -> c.getGroups()).collect(Collectors.toList()).get(0);
      while (groupsInDb.iterator().hasNext()) {
        if ( !groupsInDb.contains(contact.getGroups().iterator().next())) {
          //надо добавить контакт в группу
          Select dropdown = new Select(wd.findElement(By.name("to_group")));
          dropdown.selectByVisibleText(contact.getGroups().iterator().next().getName());
          break;
        }
      }
    }
  }

  private void initContactIncludingInGroup(ContactData contact) {
    click(By.cssSelector(String.format("input[type = 'checkbox'][id = '%s']", contact.getId())));
  }
}
