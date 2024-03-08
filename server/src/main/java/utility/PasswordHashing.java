package utility;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashing {

  private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

  public static String hashPassword(String password) {
    return encoder.encode(password);
  }

  public static boolean verifyPassword(String password, String passwordFromDatabase) {
    return encoder.matches(password, passwordFromDatabase);
  }
}
