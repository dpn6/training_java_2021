package ru.stqa.dmiv.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.dmiv.addressbook.model.ContactData;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

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
    assertThat(contact.getHomePhone(), equalTo(cleaned(contactInfoFormEdit.getHomePhone())));
    assertThat(contact.getMobile(), equalTo(cleaned(contactInfoFormEdit.getMobile())));
    assertThat(contact.getWorkPhone(), equalTo(cleaned(contactInfoFormEdit.getWorkPhone())));
  }

  private String cleaned(String text){
    return text.replaceAll("\\s", "").replaceAll("[()-]", "");
  }
}
