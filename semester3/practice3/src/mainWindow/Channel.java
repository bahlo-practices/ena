package mainWindow;

import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class Channel {
  private JLabel imageLabel;
  private int current;
  private static String[] channels = {"error", "ard", "zdf", "mdr", "rtl", "rtl2", "superrtl", "pro7", "sat1", "kabel1", "vox", "kika", "eurosport", "dmax"};

  /**
   * Construct
   * @param  imageLabel The imageLabel to set the ImageIcon to
   */
  public Channel(JLabel imageLabel) {
    this.imageLabel = imageLabel;

    String currentChannel = Settings.get("currentChannel");
    if(currentChannel != null) {
      switchTo(Integer.parseInt(currentChannel));
    } else switchTo(-2); // Error
  }

  /**
   * Switches to a channel
   */
  public void switchTo(int to) {
    if(to == -2) { // -2 because when you are at the error screen and press back, it's -1
      to = 0; // Show error
    } else if(to < 1) {
      to = channels.length - 1;
    } else if(to >= channels.length) {
      to = 1;
    }

    current = to; // Set current channel
    String channel = channels[to];
    String path = Settings.get("imagePath") + channel + ".jpg";
    imageLabel.setIcon(new ImageIcon(path));
    // repaint();
    Settings.set("currentChannel", to + "");

    System.out.println("Switching to Channel #" + to + " (" + channel + ")");
  }

  /**
   * Switches to the next channel
   */
  public void next() {
    switchTo(++current);
  }

  /**
   * Switches to the previous channel
   */
  public void prev() {
    switchTo(--current);
  }

  /**
   * Scans all channels
   */
  public static void scan() {
    // Scan channels
  }

  /**
   * Returns current channel
   * @return Channel
   */
  public int getCurrent() {
    return current;
  }
}
