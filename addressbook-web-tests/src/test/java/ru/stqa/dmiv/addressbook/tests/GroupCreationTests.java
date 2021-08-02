package ru.stqa.dmiv.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.dmiv.addressbook.model.GroupData;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation(){
    app.getNavigationHelper().gotoGroupPage();
    app.getGroupHelper().createGroup(new GroupData("test1", "test2", "test3"));
  }
}
