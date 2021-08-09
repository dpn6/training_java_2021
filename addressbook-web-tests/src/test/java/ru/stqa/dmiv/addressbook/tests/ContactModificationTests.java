package ru.stqa.dmiv.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.dmiv.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {


  @BeforeMethod
  void precondition(){
    app.goTo().contactPage();
    if (app.contact().list().size() == 0){
      app.contact().create(new ContactData().withLastname("test1").withFirstname("test2").withAddress("Novosibirsk"));
    }
  }

  @Test
  public void testContactModification(){
    List<ContactData> before =  app.contact().list();
    int lastIdx = before.size()-1;
    int id = before.get(lastIdx).getId();
    ContactData modifiedContact = new ContactData().withId(id).withLastname("lastname").withFirstname("name").withAddress("test3").withMobile("222").withEmail("polina@mail.ru");

    app.contact().modify(lastIdx, modifiedContact);

    List<ContactData> after =  app.contact().list();
    before.remove(lastIdx);
    before.add(modifiedContact);
    Comparator<? super ContactData> comparatorById = (c1, c2)-> Integer.compare(c1.getId(), c2.getId());
    before.sort(comparatorById);
    after.sort(comparatorById);
    Assert.assertEquals(after, before);
  }


}
