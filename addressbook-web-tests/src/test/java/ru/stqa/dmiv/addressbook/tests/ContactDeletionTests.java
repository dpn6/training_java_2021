package ru.stqa.dmiv.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.dmiv.addressbook.model.ContactData;

import java.util.List;

public class ContactDeletionTests extends TestBase {

  @Test
  public void testContactDeletionTests() throws InterruptedException {

    app.getNavigationHelper().gotoContactPage();
    if (!app.getContactHelper().isThereAContact()){
      app.getContactHelper().creationContact(new ContactData("test1", "test2", "Novosibirsk", null, null));
    }
    List<ContactData> before =  app.getContactHelper().getContactList();
    int lastIdx = before.size()-1;
    app.getContactHelper().selectContact(lastIdx);
    app.getContactHelper().deleteSelectedContact();
    app.getContactHelper().closeAlertAccept();
    app.getNavigationHelper().gotoContactPage();
    List<ContactData> after =  app.getContactHelper().getContactList();
    before.remove(lastIdx);

    Assert.assertEquals(after, before);
  }
}
