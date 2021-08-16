package ru.stqa.dmiv.addressbook.tests;

import com.google.gson.Gson;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.dmiv.addressbook.model.GroupData;
import ru.stqa.dmiv.addressbook.model.Groups;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validGroupsFromJson() throws IOException {
    List<GroupData> groups = new ArrayList<>();
    System.out.println(this.getClass().getName());
    System.out.println(this.getClass().getResource("/groups.json"));
    InputStreamReader r = new InputStreamReader(this.getClass().getResourceAsStream("/groups.json"));
    BufferedReader reader = new BufferedReader(r);

    Gson gson = new Gson();
    if (reader.ready()) {
//    gson.fromJson(reader, GroupData.class);
      groups = gson.fromJson(reader, groups.getClass());
    }
    reader.close();
    return groups.stream().map(g -> new Object[]{g}).collect(Collectors.toList()).iterator();
  }

  @DataProvider
  public Iterator<Object[]> validGroupsFromCsv() throws IOException {
    List<GroupData> groups = new ArrayList<>();
//    System.out.println(this.getClass().getName());
//    System.out.println(this.getClass().getResource("/groups.csv"));
    InputStreamReader r = new InputStreamReader(this.getClass().getResourceAsStream("/groups.csv"));
    BufferedReader reader = new BufferedReader(r);

    while (reader.ready()) {
      String[] g = reader.readLine().split(";");
      groups.add(new GroupData().withName(g[0]).withHeader(g[1]).withFooter(g[2]));
    }
    reader.close();
    return groups.stream().map(g -> new Object[]{g}).collect(Collectors.toList()).iterator();

  }

  @Test
  public void myTest() throws IOException {
    List<GroupData> groups = new ArrayList<>();
    System.out.println(this.getClass().getName());
    System.out.println(this.getClass().getResource("/groups.json"));
    InputStreamReader r = new InputStreamReader(this.getClass().getResourceAsStream("/groups.json"));
    BufferedReader reader = new BufferedReader(r);

    Gson gson = new Gson();
    if (reader.ready()) {
//    gson.fromJson(reader, GroupData.class);
      groups = gson.fromJson(reader, groups.getClass());
    }
    groups.stream().map(g -> new Object[]{g}).collect(Collectors.toList()).iterator();

    System.out.println(groups);
  }


  @Test (dataProvider = "validGroupsFromCsv")
  public void testGroupCreationFromCsv(GroupData newGroup) {
    app.goTo().groupPage();
    Groups before = app.group().all();
    app.group().create(newGroup);
    Groups after = app.group().all();
    assertThat(after, equalTo(before.withAdded(newGroup.withId(after.stream().mapToInt(g -> g.getId()).max().getAsInt()))));
  }

  @Test
  public void testGroupCreation() {
    app.goTo().groupPage();
    Groups before = app.group().all();
    GroupData newGroup = new GroupData().withName("test1").withHeader("test2").withFooter("test3");
    app.group().create(newGroup);
    Groups after = app.group().all();
    assertThat(after, equalTo(before.withAdded(newGroup.withId(after.stream().mapToInt(g -> g.getId()).max().getAsInt()))));
  }
}
