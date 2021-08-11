package ru.stqa.dmiv.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.dmiv.addressbook.model.ContactData;

public class ContactPhoneTests extends TestBase{

  @BeforeMethod
  void precondition(){
    app.goTo().contactPage();
    if (app.contact().all().size() == 0) {
      app.contact().create(new ContactData().withLastname("test1").withFirstname("test2").withAddress("Novosibirsk"));
    }
  }

  @Test
  public void testContactPhone(){
    app.goTo().contactPage();
    ContactData contact = app.contact().all().iterator().next();

    ContactData contactInfoFormEdit = app.contact().infoFromEdit(contact);
  }
}
