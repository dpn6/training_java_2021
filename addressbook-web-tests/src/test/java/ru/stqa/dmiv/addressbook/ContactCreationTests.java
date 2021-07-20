package ru.stqa.dmiv.addressbook;

import org.testng.annotations.Test;

public class ContactCreationTests extends TestBase{
   @Test
  public void testContactCreation() {
    initContactCreation();
    fillContactForm(new ContactData("test1", "test2", "test3", "222", "polina@mail.ru"));
    gotoHomePage();
  }
}
