package ru.stqa.dmiv.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.dmiv.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation(){
    app.getNavigationHelper().gotoGroupPage();
    List<GroupData> before = app.getGroupHelper().getGroupList();
    GroupData newGroup = new GroupData("test1", "test2", "test3");
    app.getGroupHelper().createGroup(newGroup);
    before.add(newGroup);
    Comparator<? super GroupData> comparatorById = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(comparatorById);
    List<GroupData> after = app.getGroupHelper().getGroupList();
    after.sort(comparatorById);

    Assert.assertEquals(after, before);
  }
}
