package ru.stqa.dmiv.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.dmiv.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class ContactPhoneTests extends TestBase {

  @BeforeMethod
  void precondition() {
    app.goTo().contactPage();
    if (app.contact().all().size() == 0) {
      app.contact().create(new ContactData().withLastname("test1").withFirstname("test2")
              .withHomePhone("8(38341)5-10-12").withWorkPhone("+911"));
    }
  }

  @Test
  public void testContactPhone() {
    app.goTo().contactPage();
    ContactData contact = app.contact().all().iterator().next();

    ContactData contactInfoFormEdit = app.contact().infoFromEdit(contact);
    assertThat(contact.getAllPhones(), equalTo(merge(contactInfoFormEdit)));
  }

  private String merge(ContactData contact) {
    return Arrays.asList(contact.getHomePhone(), contact.getMobile(), contact.getWorkPhone()).stream().filter(s -> !s.isEmpty())
            .map(ContactPhoneTests::cleaned).collect(Collectors.joining("\n"));
//            .map(s ->cleaned(s)).collect(Collectors.joining("\n"));
  }

  public static String cleaned(String text) {
    return text.replaceAll("\\s", "").replaceAll("[()-]", "");
  }
}
