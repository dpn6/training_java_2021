package ru.stqa.dmiv.addressbook.model;

public class ContactData {

  private int id;
  private final String firstname;
  private final String lastname;
  private final String address;
  private final String mobile;
  private final String email;

  public ContactData(String lastname, String firstname, String address, String mobile, String email) {
    this.id = Integer.MAX_VALUE;
    this.firstname = firstname;
    this.lastname = lastname;
    this.address = address;
    this.mobile = mobile;
    this.email = email;
  }

  public ContactData(int id,  String lastname, String firstname, String address, String mobile, String email) {
    this.id = id;
    this.firstname = firstname;
    this.lastname = lastname;
    this.address = address;
    this.mobile = mobile;
    this.email = email;
  }

  public int getId() {
    return id;
  }

  public String getFirstname() {
    return firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public String getAddress() {
    return address;
  }

  public String getMobile() {
    return mobile;
  }

  public String getEmail() {
    return email;
  }
}
