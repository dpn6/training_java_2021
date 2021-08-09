package ru.stqa.dmiv.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.dmiv.addressbook.model.ContactData;

import java.util.Set;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws InterruptedException {
    app.goTo().contactPage();
    Set<ContactData> before =  app.contact().all();
    ContactData newContact = new ContactData().withLastname("test1").withFirstname("test2").withAddress("test3").withMobile("222").withEmail("polina@mail.ru");
    app.contact().create(newContact);
    Set<ContactData> after =  app.contact().all();
    before.add(newContact.withId(after.stream().mapToInt(c -> c.getId()).max().getAsInt()));

    Assert.assertEquals(after, before);
  }
}
