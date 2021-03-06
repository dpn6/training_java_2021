package ru.stqa.dmiv.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.dmiv.addressbook.model.ContactData;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class ContactAddressTests extends TestBase {

  @BeforeMethod
  void precondition() {
    app.goTo().contactPage();
    if (app.contact().all().size() == 0) {
      app.contact().create(new ContactData().withLastname("test1").withFirstname("test2")
              .withAddress("(333333) Novosibirsk\nLenina 8/1"));
    }
  }

  @Test
  void testContactAddress(){
    app.goTo().contactPage();
    ContactData contact = app.contact().all().iterator().next();

    ContactData contactInfoFormEdit = app.contact().infoFromEdit(contact);
    assertThat(contact.getAddress(), equalTo(contactInfoFormEdit.getAddress()));
  }
}
