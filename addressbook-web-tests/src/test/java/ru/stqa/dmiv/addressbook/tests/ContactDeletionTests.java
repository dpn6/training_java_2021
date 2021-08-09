package ru.stqa.dmiv.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.dmiv.addressbook.model.ContactData;

import java.util.List;

public class ContactDeletionTests extends TestBase {

  @BeforeMethod
  void precondition(){
    app.goTo().contactPage();
    if (app.contact().list().size() == 0){
      app.contact().create(new ContactData().withLastname("test1").withFirstname("test2").withAddress("Novosibirsk"));
    }
  }

  @Test
  public void testContactDeletionTests() throws InterruptedException {
    List<ContactData> before =  app.contact().list();
    int lastIdx = before.size()-1;
    app.contact().delete(lastIdx);
    app.goTo().contactPage();

    List<ContactData> after =  app.contact().list();
    before.remove(lastIdx);
    Assert.assertEquals(after, before);
  }


}
