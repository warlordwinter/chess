package model;

import java.util.Objects;

public class UserData {
  final String username;
  final String password;
  final String email;

  public UserData(String username, String password, String email) {
    this.username=username;
    this.password=password;
    this.email=email;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserData userData=(UserData) o;
    return Objects.equals(getUsername(), userData.getUsername()) && Objects.equals(getPassword(), userData.getPassword()) && Objects.equals(getEmail(), userData.getEmail());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getUsername(), getPassword(), getEmail());
  }
}
