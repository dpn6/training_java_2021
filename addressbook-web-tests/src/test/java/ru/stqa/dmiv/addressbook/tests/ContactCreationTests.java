package ru.stqa.dmiv.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.dmiv.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() {
    app.goTo().contactPage();
    List<ContactData> before =  app.contact().list();
    ContactData newContact = new ContactData().withLastname("test1").withFirstname("test2").withAddress("test3").withMobile("222").withEmail("polina@mail.ru");
    app.contact().create(newContact);
    List<ContactData> after =  app.contact().list();
    before.add(newContact);
    Comparator<? super ContactData> comparatorById = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
    before.sort(comparatorById);
    after.sort(comparatorById);
    Assert.assertEquals(after, before);
  }
}
