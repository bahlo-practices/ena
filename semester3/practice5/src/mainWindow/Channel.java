package mainWindow;

import java.io.*;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.util.List;
import java.util.Arrays;
import java.util.Vector;


public class Channel {
  private JLabel imageLabel;
  private int current;
  private static List<String> channels = new Vector<String>(Arrays.asList("error"));
  private static String channelPath = "\\\\vmware-host\\Shared Folders\\ena-svn\\ENA_P5_GoellnitzWeichelBahlo\\channels\\";
  private static String scanFile = "\\\\vmware-host\\Shared Folders\\ena-svn\\ENA_P5_GoellnitzWeichelBahlo\\Kanalscan.csv";

  /**
   * Construct
   * @param  imageLabel The imageLabel to set the ImageIcon to
   */
  public Channel(JLabel imageLabel, int initialChannel) {
    this.imageLabel = imageLabel;

    switchTo(initialChannel);
  }

  /**
   * Switches to a channel
   * @return  switchTo The channel # it switched to
   */
  public int switchTo(int to) {
	current = Channel.checkChannel(to); // Set current channel
    System.out.println("DEBUG: checkedChannel: #" + to + " to #" + current);
    String channel = channels.get(current);
    String path = channelPath + channel + ".jpg";

    // Check if image exists
    if(!(new File(path).exists())) {
      return switchTo(-2);
    }

    imageLabel.setIcon(new ImageIcon(path));
    // repaint();

    System.out.println("Switching to Channel #" + current + " (" + channel + ")");
    return current;
  }

  /**
   * Scans all channels
   */
  public static List<String> scan() {
    BufferedReader bufferedReader = null;
    String line = "";
    List<String> scannedChannels = new Vector<String>(Arrays.asList("error"));

    try {
      bufferedReader = new BufferedReader(new FileReader(scanFile));
      bufferedReader.readLine(); // Skip first line, headings

      while ((line = bufferedReader.readLine()) != null) {
        String[] splitLine = line.split(";");
        if(splitLine.length > 1) {
          String channelName = splitLine[1];
          scannedChannels.add(channelName);
        }
      }

      if(!scannedChannels.isEmpty()) {
        channels = scannedChannels;
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (bufferedReader != null) {
        try {
          bufferedReader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

    return scannedChannels;
  }

  /**
   * Returns current channel
   * @return Channel
   */
  public int getCurrent() {
    return current;
  }

  /**
   * Get the count of all channels
   * @return Channel count
   */
  public static int getChannelCount() {
    return channels.size();
  }

  /**
   * Checks a channel and changes it if not correct
   * @param  channel # to check
   * @return         Final channel
   */
  private static int checkChannel(int channel) {
    System.out.println("Channel # " + channels);
    if(channels.size() < 2) {
      // Just error!
      channel = 0;
    } else if(channel == -2) { // -2 because when you are at the error screen and press back, it's -1
      channel = 0; // Show error
    } else if(channel < 1) {
      channel = channels.size() - 1;
    } else if(channel >= channels.size()) {
      channel = 1;
    }

    return channel;
  }

  public static void setChannels(List<String> channels) {
    Channel.channels = channels;
  }
}
