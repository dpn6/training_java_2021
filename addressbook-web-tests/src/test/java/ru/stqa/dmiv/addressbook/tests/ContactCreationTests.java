package ru.stqa.dmiv.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.dmiv.addressbook.model.ContactData;
import ru.stqa.dmiv.addressbook.model.Contacts;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

  @DataProvider
  Iterator<Object[]> validProviderFromJson() throws IOException {
    Gson gson = new Gson();
    InputStreamReader reader = new InputStreamReader(this.getClass().getResourceAsStream("/contacts.json"));
    List<ContactData> contacts = new ArrayList<>();
    if (reader.ready()) {
      contacts = gson.fromJson(reader, new TypeToken<List<ContactData>>() {
      }.getType());
    }
    reader.close();
    return contacts.stream().map(c -> new Object[]{c}).iterator();
  }

  @Test(dataProvider = "validProviderFromJson")
  public void testContactCreationFromJson(ContactData newContact) {
    app.goTo().contactPage();
    Contacts before =  app.contact().all();
    app.contact().create(newContact);
    Contacts after =  app.contact().all();
    before.add(newContact.withId(after.stream().mapToInt(c -> c.getId()).max().getAsInt()));
    assertThat(after, equalTo(before.
            withAdded(newContact.withId(after.stream().mapToInt(c -> c.getId()).max().getAsInt()))));
  }

  @DataProvider
  Iterator<Object[]> validProviderFromXml() throws IOException {
    List<ContactData> contacts = new ArrayList<>();
    InputStreamReader reader = new InputStreamReader(this.getClass().getResourceAsStream("/contacts.xml"));
    XStream xStream = new XStream();
    xStream.processAnnotations(ContactData.class);
    contacts = (List<ContactData>)xStream.fromXML(reader, contacts.getClass());
    reader.close();
    return contacts.stream().map(c -> new Object[]{c}).iterator();
  }

  @Test(dataProvider = "validProviderFromXml")
  public void testContactCreationFromXml(ContactData newContact) {
    app.goTo().contactPage();
    Contacts before =  app.contact().all();
    app.contact().create(newContact);
    Contacts after =  app.contact().all();
    before.add(newContact.withId(after.stream().mapToInt(c -> c.getId()).max().getAsInt()));
    assertThat(after, equalTo(before.
            withAdded(newContact.withId(after.stream().mapToInt(c -> c.getId()).max().getAsInt()))));
  }

  @DataProvider
  Iterator<Object[]> validProviderFromCsv() throws IOException {
    List<ContactData> contacts = new ArrayList<>();
    InputStreamReader reader = new InputStreamReader(this.getClass().getResourceAsStream("/contacts.csv"));
    BufferedReader br = new BufferedReader((reader));
    while (br.ready()){
      String[] lines = br.readLine().split(";");
      ContactData contact = new ContactData().withLastname(lines[0]).withFirstname(lines[1]).withAddress(lines[2])
              .withHomePhone(lines[3]).withWorkPhone(lines[4]).withEmail(lines[5]).withEmail3(lines[6]);
      contacts.add(contact);
    }
    reader.close();
    return contacts.stream().map(c -> new Object[]{c}).iterator();
  }

  @Test(dataProvider = "validProviderFromCsv")
  public void testContactCreationFromCsv(ContactData newContact) {
    app.goTo().contactPage();
    Contacts before =  app.contact().all();
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
