package ru.stqa.dmiv.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.dmiv.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation(){
    app.goTo().groupPage();
    List<GroupData> before = app.group().list();
    GroupData newGroup = new GroupData("test1", "test2", "test3");
    app.group().create(newGroup);

    before.add(newGroup);
    Comparator<? super GroupData> comparatorById = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(comparatorById);
    List<GroupData> after = app.group().list();
    after.sort(comparatorById);
    Assert.assertEquals(after, before);
  }
}
