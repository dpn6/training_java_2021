package ru.stqa.dmiv.addressbook.tests;

import org.testng.annotations.Test;

public class ContactDeletionTests extends TestBase {

  @Test
  public void testContactDeletionTests() {
    app.selectContact();
    app.deleteSelectedContact();
  }
}
