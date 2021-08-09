package ru.stqa.dmiv.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.dmiv.addressbook.model.ContactData;

import java.util.List;
import java.util.Set;

public class ContactDeletionTests extends TestBase {

  @BeforeMethod
  void precondition() throws InterruptedException {
    app.goTo().contactPage();
    if (app.contact().all().size() == 0){
      app.contact().create(new ContactData().withLastname("test1").withFirstname("test2").withAddress("Novosibirsk"));
    }
  }

  @Test
  public void testContactDeletionTests() throws InterruptedException {
    Set<ContactData> before =  app.contact().all();
    ContactData deletedContact = before.iterator().next();
    app.contact().delete(deletedContact);
    app.goTo().contactPage();

    Thread.sleep(60);
    Set<ContactData> after =  app.contact().all();
    before.remove(deletedContact);
    Assert.assertEquals(after, before);
  }


}
