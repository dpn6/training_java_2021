package ru.stqa.dmiv.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.dmiv.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ContactEmailTests extends TestBase {

  @BeforeMethod
  void precondition() {
    app.goTo().contactPage();
    if (app.contact().all().size() == 0) {
      app.contact().create(new ContactData().withLastname("test1").withFirstname("test2")
              .withEmail("email@main.ru").withEmail3("email3@main.ru"));
    }
  }

  @Test
  void testContactEmail(){
    app.goTo().contactPage();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFormEdit = app.contact().infoFromEdit(contact);

    MatcherAssert.assertThat(contact.getAllEmails(), CoreMatchers.equalTo(merge(contactInfoFormEdit)));
  }

  private String merge(ContactData contact) {
    return Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3()).stream().filter(s -> !s.isEmpty())
            .collect(Collectors.joining("\n"));
  }
}
