package ru.stqa.dmiv.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.dmiv.addressbook.model.ContactData;
import ru.stqa.dmiv.addressbook.model.Contacts;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() {
    app.goTo().contactPage();
    Contacts before =  app.contact().all();
    ContactData newContact = new ContactData().withLastname("test1").withFirstname("test2").withAddress("test3")
            .withMobile("222").withEmail("polina@mail.ru").withPhoto(new File("./src/test/resources/Small2.png"));
    app.contact().create(newContact);
    Contacts after =  app.contact().all();
    before.add(newContact.withId(after.stream().mapToInt(c -> c.getId()).max().getAsInt()));
    assertThat(after, equalTo(before.
                    withAdded(newContact.withId(after.stream().mapToInt(c -> c.getId()).max().getAsInt()))));
  }


  @Test
  public void getPileTest(){
    System.out.println((new File(".").getAbsolutePath()));
    File photo = new File("/src/test/resources/Small.png");
    File photo1 = new File("./src/test/resources/Small.png");
    System.out.println(photo.getAbsolutePath());
    System.out.println(photo1.getAbsolutePath());




  }

}
