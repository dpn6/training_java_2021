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
    app.goTo().groupPage();
    if (app.group().list().size() == 0) {
      app.group().create(new GroupData().withName("test1").withHeader("test2"));
    }
  }

  @Test
  public void testGroupModification() {
    List<GroupData> before = app.group().list();
    int lastIdx = before.size() - 1;
    int id = before.get(lastIdx).getId();
    GroupData groupData = new GroupData().withId(id).withName("name_group").withHeader("header_group").withFooter("footer_group");
    app.group().modify(lastIdx, groupData);
    List<GroupData> after = app.group().list();
    before.remove(lastIdx);
    before.add(groupData);

    Comparator<? super GroupData> comparatorById = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(comparatorById);
    after.sort(comparatorById);
    Assert.assertEquals(before, after);
  }


}
