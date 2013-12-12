package mainWindow;

import java.io.*;
import java.util.*;

public class Settings {
  private static HashMap<String, String> settings = new HashMap<String, String>();
  private static String filename = ".\\src\\settings.ini";

  /**
   * Private construct to prevent creating of an object
   */
  private Settings() {
    throw new AssertionError();
  }

  /**
   * Get a value from the settings
   * @param  key Key
   * @return     Value
   */
  public static String get(String key) {
    String value = settings.get(key);
    return value == null
      ? null
      : value;
  }

  /**
   * Set a value
   * @param key   Key
   * @param value Value
   */
  public static void set(String key, String value) {
    settings.put(key, value);
  }

  /**
   * Writes the settings to file
   * @throws  FileNotFoundException When file not found
   */
  public static void save() {
    System.out.println("Saving settings");
    try {
      PrintStream ps = new PrintStream(filename);
      Set<String> settingKeys = settings.keySet();

      for(String key : settingKeys) {
        ps.println(key + '=' + settings.get(key));
        ps.flush();
      }

      ps.close();
    } catch(FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * Loads settings from file
   * @throws  IOException
   */
  public static void load() {
    try {
      BufferedReader br = new BufferedReader(new FileReader(filename));
      String line = "";

      while((line = br.readLine()) != null) {
        String[] keyValue = line.split("=", 2);
        settings.put(keyValue[0], keyValue[1]);
      }

      br.close();
    } catch(IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Print all settings
   */
  public static void printAll() {
    Set<String> settingKeys = settings.keySet();
    for(String key : settingKeys) {
      System.out.println(key + " => " + settings.get(key));
    }
  }
}
