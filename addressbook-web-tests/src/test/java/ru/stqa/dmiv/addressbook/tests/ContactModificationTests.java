package ru.stqa.dmiv.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.dmiv.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class ContactModificationTests extends TestBase {


  @BeforeMethod
  void precondition() throws InterruptedException {
    app.goTo().contactPage();
    if (app.contact().all().size() == 0) {
      app.contact().create(new ContactData().withLastname("test1").withFirstname("test2").withAddress("Novosibirsk"));
    }
  }

  @Test
  public void testContactModification() throws InterruptedException {
    Set<ContactData> before = app.contact().all();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData().withId(modifiedContact.getId()).withLastname("lastname").withFirstname("name").withAddress("test3").withMobile("222").withEmail("polina@mail.ru");
    app.contact().modify(contact);
    Set<ContactData> after = app.contact().all();

    before.remove(modifiedContact);
    before.add(contact);
    Assert.assertEquals(after, before);
  }


}
