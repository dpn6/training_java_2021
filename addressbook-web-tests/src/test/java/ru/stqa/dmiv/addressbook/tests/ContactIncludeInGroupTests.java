package ru.stqa.dmiv.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.stqa.dmiv.addressbook.model.ContactData;
import ru.stqa.dmiv.addressbook.model.Contacts;
import ru.stqa.dmiv.addressbook.model.GroupData;
import ru.stqa.dmiv.addressbook.model.Groups;

import java.util.Date;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class ContactIncludeInGroupTests extends TestBase {

//  @BeforeMethod
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
  void testIncludeInGroup() {
    Contacts before = app.db().contacts();
    Groups groups = app.db().groups();
    ContactData contact = before.iterator().next();
    Groups beforeGroupsUseContact = contact.getGroups();
    if (beforeGroupsUseContact.size() == groups.size()) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName(String.format("gr_name_%s", new Date().getTime())));
      app.goTo().contactPage();
    }

    //пробегаемся по всем группам и выбираем ту, в которую не входит контакт
    while (groups.iterator().hasNext()) {
      GroupData groupData = groups.iterator().next();
      if (!beforeGroupsUseContact.contains(groupData)) {
        contact.inGroup(groupData);
        app.contact().includeInGroup(contact);
        break;
      }
    }
    Groups afterGroupsUseContact = contact.getGroups();
    assertThat(afterGroupsUseContact, equalTo(app.db().contacts().stream()
            .filter(c -> c.getId() == contact.getId()).map(c -> c.getGroups()).collect(Collectors.toList()).get(0)));

  }
}
