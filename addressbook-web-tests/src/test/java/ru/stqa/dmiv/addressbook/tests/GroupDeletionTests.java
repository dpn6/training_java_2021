package ru.stqa.dmiv.addressbook.tests;

import org.testng.annotations.Test;

public class GroupDeletionTests extends TestBase{

  @Test
  public void testGroupDeletionTests(){
    app.gotoGroupPage();
    app.selectGroup();
    app.deleteSelectedGroups();
    app.returnToGroupPage();
  }
}
