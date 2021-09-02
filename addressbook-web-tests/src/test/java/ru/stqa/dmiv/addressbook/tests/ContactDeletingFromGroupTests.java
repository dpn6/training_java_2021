package ru.stqa.dmiv.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.stqa.dmiv.addressbook.model.ContactData;
import ru.stqa.dmiv.addressbook.model.GroupData;
import ru.stqa.dmiv.addressbook.model.Groups;

import java.util.Date;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class ContactDeletingFromGroupTests extends TestBase {

  @BeforeTest
  public void precondition() {
    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName(String.format("gr_name_%s", new Date().getTime())));
    }
    app.goTo().contactPage();
    if (app.db().contacts().size() == 0) {
      app.contact().create(new ContactData().withLastname("test1").withFirstname("test2").withAddress("Novosibirsk"));
    }
  }

  @Test
  void testContactDeletingFromGroup() {
    ContactData contact = app.db().contacts().iterator().next();
    Groups before = contact.getGroups();
    if (contact.getGroups().size() == 0) {
      app.contact().includeInGroup(contact);
    }
    GroupData group = app.contact().deleteFromGroup(contact);
    Groups after =
            app.db().contacts().stream().filter(c -> c.getId() == contact.getId()).collect(Collectors.toList()).get(0)
            .getGroups();
    assertThat(after.withAdded(group), equalTo(before));
  }
}
