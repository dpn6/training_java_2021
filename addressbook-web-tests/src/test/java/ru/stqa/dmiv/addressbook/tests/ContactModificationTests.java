package ru.stqa.dmiv.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.dmiv.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification(){
    app.getNavigationHelper().gotoContactPage();
    if (!app.getContactHelper().isThereAContact()){
      app.getContactHelper().creationContact(new ContactData("test1", "test2", "Novosibirsk", null, null));
    }
    List<ContactData> before =  app.getContactHelper().getContactList();
    int lastIdx = before.size()-1;
    int id = before.get(lastIdx).getId();
    app.getContactHelper().selectContact(lastIdx);
    app.getContactHelper().initContactModification();
    ContactData modifiedContact = new ContactData(id,"lastname", "name", "test3", "222", "polina@mail.ru");
    app.getContactHelper().fillContactForm(modifiedContact);
    app.getContactHelper().submitContactModification();
    app.getContactHelper().returnHomePage();

    List<ContactData> after =  app.getContactHelper().getContactList();
    before.remove(lastIdx);
    before.add(modifiedContact);

    Comparator<? super ContactData> comparatorById = (c1, c2)-> Integer.compare(c1.getId(), c2.getId());
    before.sort(comparatorById);
    after.sort(comparatorById);

    Assert.assertEquals(after, before);
  }
}
