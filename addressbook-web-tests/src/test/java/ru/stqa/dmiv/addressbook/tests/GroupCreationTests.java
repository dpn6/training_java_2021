package ru.stqa.dmiv.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.dmiv.addressbook.model.GroupData;
import ru.stqa.dmiv.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation(){
    app.goTo().groupPage();
    Groups before = app.group().all();
    GroupData newGroup = new GroupData().withName("test1").withHeader("test2").withFooter("test3");
    app.group().create(newGroup);
    Groups after = app.group().all();
    assertThat(after, equalTo(before.withAdded(newGroup.withId(after.stream().mapToInt(g -> g.getId()).max().getAsInt()))));
  }
}
