package ru.stqa.dmiv.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.dmiv.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification(){
    app.getContactHelper().selectContact("9");
    app.getContactHelper().initContactModification();
    app.getContactHelper().fillContactForm(new ContactData("test1", "test2", "test3", "222", "polina@mail.ru"));
    app.getContactHelper().submitContactModification();
    app.getContactHelper().gotoHomePage();
  }
}
