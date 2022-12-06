package com.splitscale.mockie.user;

public class UserDisplayable {
  long id;
  String username;

  public UserDisplayable() {
    // default
  }

  public UserDisplayable(long id, String username) {
    this.id = id;
    this.username = username;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
