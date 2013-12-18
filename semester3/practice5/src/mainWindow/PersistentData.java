package mainWindow;

import java.io.*;
import java.util.*;

public class PersistentData implements Serializable {
  private static String filename = ".\\src\\persistentData.ser";
  private int volume = 20;
  private List<String> channels = new Vector<String>(Arrays.asList("error"));
  private int selectedChannel = -2;
  private int ratio = 0;

  public PersistentData() {}

  /**
   * Setter
   */
  public void setVolume(int volume) { this.volume = volume; }
  public void setChannels(List<String> channels) { this.channels = channels; }
  public void setSelectedChannel(int selectedChannel) { this.selectedChannel = selectedChannel; }
  public void setRatio(int ratio) { this.ratio = ratio; }

  /**
   * Getter
   */
  public int getVolume() { return volume; }
  public List<String> getChannels() { return channels; }
  public int getSelectedChannel() { return selectedChannel; }
  public int getRatio() { return ratio; }

  /**
   * Saves a PersistentData object to file
   * @param dataObj PersistentData object
   */
  public static void save(PersistentData dataObj) {
	  try {
	      ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream(filename));
	      file.writeObject(dataObj);
	      file.close();
	  } catch(IOException e) {
        e.printStackTrace();
    }
  }

  /**
   * Load persistentData from file
   * @return persistentData
   */
  public static PersistentData load() {
    try {
      ObjectInputStream file = new ObjectInputStream(new FileInputStream(filename));
      Object obj = file.readObject();
      if (obj instanceof PersistentData) {
        PersistentData persistentData = (PersistentData) obj;
        file.close();
        return persistentData;
      } else file.close();
    } catch (FileNotFoundException e) {
    	// No settings saved .. yet!
    	return new PersistentData();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
	  return new PersistentData();
  }
}
