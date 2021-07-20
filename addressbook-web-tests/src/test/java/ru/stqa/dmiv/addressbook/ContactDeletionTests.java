package ru.stqa.dmiv.addressbook;

import org.testng.annotations.Test;

public class ContactDeletionTests extends TestBase {

  @Test
  public void testContactDeletionTests() {
    selectContact();
    deleteSelectedContact();
  }
}
