package ru.stqa.dmiv.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.dmiv.addressbook.model.GroupData;

import java.util.Set;

public class GroupModificationTests extends TestBase {

  @BeforeMethod
  void precondition() {
    app.goTo().groupPage();
    if (app.group().all().size() == 0) {
      app.group().create(new GroupData().withName("test1").withHeader("test2"));
    }
  }

  @Test
  public void testGroupModification() {
    Set<GroupData> before = app.group().all();
    GroupData modifiedGroup = before.iterator().next();
    GroupData afterModifyGroup = new GroupData().withId(modifiedGroup.getId()).withName("name_group").withHeader("header_group").withFooter("footer_group");
    app.group().modify(afterModifyGroup);

    before.remove(modifiedGroup);
    before.add(afterModifyGroup);
    Set<GroupData> after = app.group().all();
    Assert.assertEquals(before, after);
  }


}
