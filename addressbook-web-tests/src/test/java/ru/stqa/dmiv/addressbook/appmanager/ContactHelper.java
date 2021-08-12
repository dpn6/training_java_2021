package ru.stqa.dmiv.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stqa.dmiv.addressbook.model.ContactData;
import ru.stqa.dmiv.addressbook.model.Contacts;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContactHelper extends HelperBase {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void returnHomePage() {
    click(By.linkText("home page"));
  }

  public void fillContactForm(ContactData contactData) {
    type(By.name("firstname"), contactData.getFirstname());
    type(By.name("lastname"), contactData.getLastname());
    type(By.name("address"), contactData.getAddress());
    type(By.name("mobile"), contactData.getMobile());
    type(By.name("email"), contactData.getEmail());
    click(By.xpath("//div[@id='content']/form/input[21]"));
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
    fillContactForm(contactData);
    returnHomePage();
  }

  public void modify(ContactData contact) {
    initContactModification(contact.getId());
    fillContactForm(contact);
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
      String email = cells.get(4).findElement(By.tagName("a")).getText();
      String phones = cells.get(5).getText();

      ContactData contactData = new ContactData().withId(id).withLastname(lastName).withFirstname(firstName)
              .withAddress(address).withAllPhones(phones).withEmail(email);
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
    ContactData info = new ContactData()
            .withId(id).withLastname(lastName).withFirstname(firstName).withAddress(address)
            .withHomePhone(homePhone).withMobile(mobilePhone).withWorkPhone(workPhone).withEmail(email);

    wd.navigate().back();
    return info;
  }
}
