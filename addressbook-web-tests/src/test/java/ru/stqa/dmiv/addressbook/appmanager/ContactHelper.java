package ru.stqa.dmiv.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.stqa.dmiv.addressbook.model.ContactData;

public class ContactHelper extends HelperBase{

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

  public void selectContact() {
    click(By.name("selected[]"));
  }

  public void initContactModification() {
    click(By.xpath("//img[@Title='Edit']"));
      }

  public void submitContactModification() {
    click(By.name("update"));
  }

  public boolean isThereAContact() {
    if(isElementPresent(By.name("selected[]"))){
      return true;
    }
    return false;
   }

  public void creationContact(ContactData contactData) {
    initContactCreation();
    fillContactForm(contactData);
    returnHomePage();
  }
}
