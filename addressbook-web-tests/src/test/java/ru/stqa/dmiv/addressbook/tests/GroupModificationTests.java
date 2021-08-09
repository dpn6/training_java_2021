package ru.stqa.dmiv.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.dmiv.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class GroupModificationTests extends TestBase {

  @BeforeMethod
  void precondition() {
    app.getNavigationHelper().gotoGroupPage();
    if (!app.getGroupHelper().isThereAGroup()) {
      app.getGroupHelper().createGroup(new GroupData("test1", "test2", null));
    }
  }

  @Test
  public void testGroupModification() {
    List<GroupData> before = app.getGroupHelper().getGroupList();
    int lastIdx = before.size() - 1;
    int id = before.get(lastIdx).getId();
    GroupData groupData = new GroupData(id, "name_group", "header_group", "footer_group");
    app.getGroupHelper().selectGroup(lastIdx);
    app.getGroupHelper().initGroupModification();
    app.getGroupHelper().fillGroupForm(groupData);
    app.getGroupHelper().submitGroupModification();
    app.getGroupHelper().returnToGroupPage();
    List<GroupData> after = app.getGroupHelper().getGroupList();
    before.remove(lastIdx);
    before.add(groupData);

    Comparator<? super GroupData> comparatorById = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(comparatorById);
    after.sort(comparatorById);
    Assert.assertEquals(before, after);
  }
}
