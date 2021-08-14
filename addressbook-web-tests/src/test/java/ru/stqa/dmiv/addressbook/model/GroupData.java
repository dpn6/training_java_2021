package ru.stqa.dmiv.addressbook.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.util.Objects;

@XStreamAlias("group")
public class GroupData {
  @XStreamOmitField
  private int id = Integer.MAX_VALUE;;
  private  String name;
  private  String header;
  private  String footer;

  public GroupData() {
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof GroupData)) return false;
    GroupData groupData = (GroupData) o;
    return id == groupData.id &&
            Objects.equals(name, groupData.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }

  private GroupData(int id, String name, String header, String footer) {
    this.id = id;
    this.name = name;
    this.header = header;
    this.footer = footer;
  }

  private GroupData(String name, String header, String footer) {
    this.name = name;
    this.header = header;
    this.footer = footer;
  }

  public GroupData withId(int id) {
    this.id = id;
    return this;
  }

  public GroupData withName(String name) {
    this.name = name;
    return this;
  }

  public GroupData withHeader(String header) {
    this.header = header;
    return this;
  }

  public GroupData withFooter(String footer) {
    this.footer = footer;
    return this;
  }


  @Override
  public String toString() {
    return "GroupData{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", header='" + header + '\'' +
            ", footer='" + footer + '\'' +
            '}';
  }

  public int getId() {
    return id;
  }
  public String getName() {
    return name;
  }

  public String getHeader() {
    return header;
  }

  public String getFooter() {
    return footer;
  }

}
