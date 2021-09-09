package ru.stqa.dmiv.mantis.model;

import com.google.common.collect.ForwardingSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Users extends ForwardingSet<UserData> {

  private Set<UserData> delegate;

  public Users() {
    delegate = new HashSet<>();
  }

  public Users(Users users) {
    this.delegate = users.delegate;
  }

  public Users(Collection<UserData> users) {
    this.delegate =  new HashSet<>(users);
  }

  public Users withAdded(UserData user) {
    Users users = new Users(this);
    users.add(user);
    return users;
  }

  @Override
  protected Set<UserData> delegate() {
    return delegate;
  }
}
