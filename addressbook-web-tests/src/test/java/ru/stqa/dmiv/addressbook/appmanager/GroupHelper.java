package ru.stqa.dmiv.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stqa.dmiv.addressbook.model.GroupData;

import java.util.ArrayList;
import java.util.List;

public class GroupHelper extends HelperBase {

  public GroupHelper(WebDriver wd) {
    super(wd);
  }

  public void returnToGroupPage() {
    click(By.linkText("group page"));
  }

  public void submitGroupCreation() {
    click(By.name("submit"));
  }

  public void fillGroupForm(GroupData groupData) {
    type(By.name("group_name"), groupData.getName());
    type(By.name("group_header"), groupData.getHeader());
    type(By.name("group_footer"), groupData.getFooter());
  }

  public void initGroupCreation() {
    click(By.name("new"));
  }

  public void deleteSelectedGroups() {
    click(By.name("delete"));
  }

  public void selectGroup(int idx) {
    wd.findElements(By.name("selected[]")).get(idx).click();
  }

  public void initGroupModification() {
    click(By.name("edit"));
  }

  public void submitGroupModification() {
    click(By.name("update"));
  }

  public boolean isThereAGroup() {
    if (isElementPresent(By.name("selected[]"))) {
      return true;
    }
    return false;
  }

  public void create(GroupData groupData) {
    initGroupCreation();
    fillGroupForm(groupData);
    submitGroupCreation();
    returnToGroupPage();
  }

  public void delete(int lastIdx) {
    selectGroup(lastIdx);
    deleteSelectedGroups();
    returnToGroupPage();
  }

  public void modify(int lastIdx, GroupData groupData) {
    selectGroup(lastIdx);
    initGroupModification();
    fillGroupForm(groupData);
    submitGroupModification();
    returnToGroupPage();
  }

  public List<GroupData> list() {
    List<GroupData> groups = new ArrayList<>();
    for (WebElement element : wd.findElements(By.cssSelector("span.group"))) {
      String value = element.findElement(By.tagName("input")).getAttribute("value");
      GroupData groupData = new GroupData().withId(Integer.parseInt(value)).withName(element.getText());
      groups.add(groupData);
    }
    return groups;
  }

}
