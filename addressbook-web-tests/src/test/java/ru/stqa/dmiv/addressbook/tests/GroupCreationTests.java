package ru.stqa.dmiv.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.dmiv.addressbook.model.GroupData;

import java.util.Set;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation(){
    app.goTo().groupPage();
    Set<GroupData> before = app.group().all();
    GroupData newGroup = new GroupData().withName("test1").withHeader("test2").withFooter("test3");
    app.group().create(newGroup);

    Set<GroupData> after = app.group().all();
    before.add(newGroup.withId(after.stream().mapToInt(g -> g.getId()).max().getAsInt()));
    Assert.assertEquals(after, before);
  }
}
