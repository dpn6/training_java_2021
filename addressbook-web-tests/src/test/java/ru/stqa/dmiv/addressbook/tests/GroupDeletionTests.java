package ru.stqa.dmiv.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.dmiv.addressbook.model.GroupData;
import ru.stqa.dmiv.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupDeletionTests extends TestBase {

  @BeforeMethod
  void precondition() {
    app.goTo().groupPage();
    if (app.db().groups().size() == 0) {
      app.group().create(new GroupData().withName("test1"));
    }
  }

  @Test
  public void testGroupDeletionTests() {
    for (int i = 0; i < 4; i++) {
      Groups before = app.db().groups();
      GroupData groupDeleted = before.iterator().next();
      app.group().delete(groupDeleted);
      Groups after = app.db().groups();
      assertThat(after, equalTo(before.without(groupDeleted)));
    }
  }
}
