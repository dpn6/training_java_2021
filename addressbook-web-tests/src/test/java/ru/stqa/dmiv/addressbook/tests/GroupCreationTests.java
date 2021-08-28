package ru.stqa.dmiv.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.dmiv.addressbook.model.GroupData;
import ru.stqa.dmiv.addressbook.model.Groups;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTests extends TestBase {
  Logger logger = LoggerFactory.getLogger(GroupCreationTests.class);

  @DataProvider
  public Iterator<Object[]> validGroupsFromJson() throws IOException {
    List<GroupData> groups = new ArrayList<>();
    Gson gson = new Gson();
    try (InputStreamReader r = new InputStreamReader(this.getClass().getResourceAsStream("/groups.json"));
         BufferedReader reader = new BufferedReader(r)) {
      if (reader.ready()) {
//        groups = gson.fromJson(reader, groups.getClass());
        groups = gson.fromJson(reader, new TypeToken<List<GroupData>>() {
        }.getType());
      }
    }
//    logger.info("Из файла загружены группы: " + groups);
    logger.info(String.format("Из файла загружены группы: %s для добавления", groups));
    return groups.stream().map(g -> new Object[]{g}).iterator();
  }

  @Test(dataProvider = "validGroupsFromJson")
  public void testGroupCreationFromJson(GroupData newGroup) {
    app.goTo().groupPage();
    Groups before = app.db().groups();
    app.group().create(newGroup);
    Groups after = app.db().groups();
    assertThat(after, equalTo(before.withAdded(newGroup.withId(after.stream().mapToInt(g -> g.getId()).max().getAsInt()))));
    verifyGroupListInUi();
  }

  @DataProvider
  public Iterator<Object[]> validGroupsFromXml() throws IOException {
    List<GroupData> groups = new ArrayList<>();
    XStream xStream = new XStream();
    xStream.processAnnotations(GroupData.class);
    try (InputStreamReader r = new InputStreamReader(this.getClass().getResourceAsStream("/groups.xml"));
         BufferedReader reader = new BufferedReader(r)) {
      if (reader.ready()) {
        groups = (List<GroupData>) xStream.fromXML(reader, groups.getClass());
      }
    }
    logger.info(String.format("Из файла groups.xml загружены группы: %s для добавления", groups));
    return groups.stream().map(g -> new Object[]{g}).iterator();
  }

  @Test(dataProvider = "validGroupsFromXml")
  public void testGroupCreationFromXml(GroupData newGroup) {
    app.goTo().groupPage();
    Groups before = app.db().groups();
    app.group().create(newGroup);
    Groups after = app.db().groups();
    assertThat(after, equalTo(before.withAdded(newGroup.withId(after.stream().mapToInt(g -> g.getId()).max().getAsInt()))));
    verifyGroupListInUi();
  }

  @DataProvider
  public Iterator<Object[]> validGroupsFromCsv() throws IOException {
    List<GroupData> groups = new ArrayList<>();

    try (InputStreamReader r = new InputStreamReader(this.getClass().getResourceAsStream("/groups.csv"));
         BufferedReader reader = new BufferedReader(r)) {
      while (reader.ready()) {
        String[] g = reader.readLine().split(";");
        groups.add(new GroupData().withName(g[0]).withHeader(g[1]).withFooter(g[2]));
      }
    }
    logger.info(String.format("Из файла groups.csv загружены группы: %s для добавления", groups));
    return groups.stream().map(g -> new Object[]{g}).iterator();
  }

  @Test(dataProvider = "validGroupsFromCsv")
  public void testGroupCreationFromCsv(GroupData newGroup) {
    app.goTo().groupPage();
    Groups before = app.db().groups();
    app.group().create(newGroup);
    Groups after = app.db().groups();
    assertThat(after, equalTo(before.withAdded(newGroup.withId(after.stream().mapToInt(g -> g.getId()).max().getAsInt()))));
    verifyGroupListInUi();
  }

  @Test
  public void testGroupCreation() {
    app.goTo().groupPage();
    Groups before = app.db().groups();
    GroupData newGroup = new GroupData().withName(String.format("name_group_%s", new Date().getTime())).withHeader("test2").withFooter("test3");
    app.group().create(newGroup);
    Groups after = app.db().groups();
    assertThat(after, equalTo(before.withAdded(newGroup.withId(after.stream().mapToInt(g -> g.getId()).max().getAsInt()))));
    verifyGroupListInUi();
  }
}
