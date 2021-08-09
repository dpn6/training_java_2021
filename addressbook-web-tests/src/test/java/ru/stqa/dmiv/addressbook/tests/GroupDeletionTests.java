package ru.stqa.dmiv.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.dmiv.addressbook.model.GroupData;

import java.util.List;

public class GroupDeletionTests extends TestBase{

  @BeforeMethod
  void precondition(){
    app.getNavigationHelper().gotoGroupPage();
    if (!app.getGroupHelper().isThereAGroup()){
      app.getGroupHelper().createGroup(new GroupData("test1", null, null));
    }
  }

  @Test
  public void testGroupDeletionTests(){

    List<GroupData> before =  app.getGroupHelper().getGroupList();
    int lastIdx = before.size()-1;
    app.getGroupHelper().selectGroup(lastIdx);
    app.getGroupHelper().deleteSelectedGroups();
    app.getGroupHelper().returnToGroupPage();
    List<GroupData> after =  app.getGroupHelper().getGroupList();
    before.remove(lastIdx);
    Assert.assertEquals(before, after);
  }
}
