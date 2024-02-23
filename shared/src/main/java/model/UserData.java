package model;

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


}
