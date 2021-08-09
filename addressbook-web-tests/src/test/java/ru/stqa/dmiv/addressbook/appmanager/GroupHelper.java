package ru.stqa.dmiv.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stqa.dmiv.addressbook.model.GroupData;
import ru.stqa.dmiv.addressbook.model.Groups;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

  public void selectGroup(int id) {
    wd.findElement(By.cssSelector("input[value = '" + id +  "']")).click();
  }

  public void initGroupModification() {
    click(By.name("edit"));
  }

  public void submitGroupModification() {
    click(By.name("update"));
  }

  public void create(GroupData groupData) {
    initGroupCreation();
    fillGroupForm(groupData);
    submitGroupCreation();
    returnToGroupPage();
  }

  public void delete(GroupData group) {
    selectGroup(group.getId());
    deleteSelectedGroups();
    returnToGroupPage();
  }

  public void modify(GroupData group) {
    selectGroup(group.getId());
    initGroupModification();
    fillGroupForm(group);
    submitGroupModification();
    returnToGroupPage();
  }

  public Groups all() {
    Groups groups = new Groups();
    for (WebElement element : wd.findElements(By.cssSelector("span.group"))) {
      String value = element.findElement(By.tagName("input")).getAttribute("value");
      GroupData groupData = new GroupData().withId(Integer.parseInt(value)).withName(element.getText());
      groups.add(groupData);
    }
    return groups;
  }
}
