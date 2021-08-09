package ru.stqa.dmiv.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stqa.dmiv.addressbook.model.ContactData;

import java.util.ArrayList;
import java.util.List;

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

  public void selectContact(int idx) {
    wd.findElements(By.name("selected[]")).get(idx).click();
  }

  public void initContactModification() {
    click(By.xpath("//img[@Title='Edit']"));
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

  public void modify(int lastIdx, ContactData modifiedContact) {
    selectContact(lastIdx);
    initContactModification();
    fillContactForm(modifiedContact);
    submitContactModification();
    returnHomePage();
  }

  public void delete(int lastIdx) {
    selectContact(lastIdx);
    deleteSelectedContact();
    closeAlertAccept();
  }

  public List<ContactData> list() {
    List<ContactData> contactDataList = new ArrayList<>();
    List<WebElement> elements = wd.findElements(By.name("entry"));
    for (WebElement el : elements) {
      int id = Integer.parseInt(el.findElement(By.name("selected[]")).getAttribute("value"));
      String lastName = el.findElement(By.xpath("td[2]")).getText();
      String firstName = el.findElement(By.xpath("td[3]")).getText();
      ContactData contactData = new ContactData().withId(id).withLastname(lastName).withFirstname(firstName);
      contactDataList.add(contactData);
    }
    return contactDataList;
  }
}
