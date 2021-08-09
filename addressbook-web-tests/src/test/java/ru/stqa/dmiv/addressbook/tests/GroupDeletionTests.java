package ru.stqa.dmiv.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.dmiv.addressbook.model.GroupData;

import java.util.List;

public class GroupDeletionTests extends TestBase{

  @BeforeMethod
  void precondition(){
    app.goTo().groupPage();
    if (app.group().list().size() == 0){
      app.group().create(new GroupData().withName("test1"));
    }
  }

  @Test
  public void testGroupDeletionTests(){
    List<GroupData> before =  app.group().list();
    int lastIdx = before.size()-1;
    app.group().delete(lastIdx);
    List<GroupData> after =  app.group().list();

    before.remove(lastIdx);
    Assert.assertEquals(before, after);
  }


}
