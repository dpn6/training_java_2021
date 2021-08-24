package ru.stqa.dmiv.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.dmiv.addressbook.model.GroupData;
import ru.stqa.dmiv.addressbook.model.Groups;

import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class GroupDeletionTests extends TestBase {

  @BeforeMethod
  void precondition() {
    app.goTo().groupPage();
//    if (app.group().all().size() == 0){
    if (app.db().groups().size() == 0) {
      app.group().create(new GroupData().withName("test1"));
    }
  }

  @Test
  public void testGroupDeletionTests() {
//    Groups before =  app.group().all();
      Groups before = app.db().groups();
      GroupData groupDeleted = before.iterator().next();
      app.group().delete(groupDeleted);
//    Groups after =  app.group().all();
      Groups after = app.db().groups();
      assertThat(after, equalTo(before.without(groupDeleted)));
      }
}
