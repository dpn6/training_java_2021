package ru.stqa.dmiv.addressbook.tests;

import org.testng.annotations.Test;

public class ContactDeletionTests extends TestBase {

  @Test
  public void testContactDeletionTests() {
    app.getContactHelper().selectContact("11");
    app.getContactHelper().deleteSelectedContact();
    app.getContactHelper().closeAlertAccept();
  }
}
