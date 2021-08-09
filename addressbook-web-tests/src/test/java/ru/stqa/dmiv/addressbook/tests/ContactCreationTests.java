package ru.stqa.dmiv.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.dmiv.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() {
    app.goTo().gotoContactPage();
    List<ContactData> before =  app.getContactHelper().getContactList();
    ContactData newContact = new ContactData("test1", "test2", "test3", "222", "polina@mail.ru");
    app.getContactHelper().creationContact(newContact);
    List<ContactData> after =  app.getContactHelper().getContactList();
    before.add(newContact);
    Comparator<? super ContactData> comparatorById = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
    before.sort(comparatorById);
    after.sort(comparatorById);
    Assert.assertEquals(after, before);
  }
}
