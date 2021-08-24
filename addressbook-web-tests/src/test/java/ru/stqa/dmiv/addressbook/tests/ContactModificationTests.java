package ru.stqa.dmiv.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.dmiv.addressbook.model.ContactData;
import ru.stqa.dmiv.addressbook.model.Contacts;

public class ContactModificationTests extends TestBase {


  @BeforeMethod
  void precondition(){
    app.goTo().contactPage();

    if (app.db().contacts().size() == 0) {
//    if (app.contact().all().size() == 0) {
      app.contact().create(new ContactData().withLastname("test1").withFirstname("test2").withAddress("Novosibirsk"));
    }
  }

  @Test
  public void testContactModification(){
//    Contacts before = app.contact().all();
    Contacts before = app.db().contacts();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData().withId(modifiedContact.getId()).withLastname("lastname").withFirstname("name").withAddress("test3").withMobile("222").withEmail("polina@mail.ru");
    app.contact().modify(contact);
//    Contacts after = app.contact().all();
    Contacts after = app.db().contacts();
    MatcherAssert.assertThat(after, CoreMatchers.equalTo(before.without(modifiedContact).withAdded(contact)));
  }


}
