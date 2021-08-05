package ru.stqa.dmiv.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.dmiv.addressbook.model.ContactData;

import java.util.List;

public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification(){
    app.getNavigationHelper().gotoContactPage();
    if (!app.getContactHelper().isThereAContact()){
      app.getContactHelper().creationContact(new ContactData("lastname", "name", "Novosibirsk", null, null));
    }
    List<ContactData> before =  app.getContactHelper().getContactList();
    int lastIdx = before.size()-1;
    app.getContactHelper().selectContact(lastIdx);
    app.getContactHelper().initContactModification();
    app.getContactHelper().fillContactForm(new ContactData("test1", "test2", "test3", "222", "polina@mail.ru"));
    app.getContactHelper().submitContactModification();
    app.getContactHelper().returnHomePage();
  }
}
