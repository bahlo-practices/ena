package mainWindow;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.io.FileNotFoundException;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

public class TV extends JFrame {
  // Stuff to set zp for UI
  private static final long serialVersionUID = 1L;
  private JPanel contentPane;
  private JPanel panel;
  public  JLabel tvImage;
  public  JLabel pipImage;
  public JSlider slider;
  protected JPanel pipDisplay;
  private Channel mainChannel;
  private Channel pipChannel;
  private PersistentData persistentData;

  // If is recording
  private boolean isRecording;
  // The time the recording started at
  private long recordingStartTime;

  private int volume = 20;
  private boolean pipActive = false;


  /**
   * Liefert den aktuellen Zeitpunkt.
   *
   * @return Current timestamp
   */
  public long now() {
    return Calendar.getInstance().getTimeInMillis() / 1000;
  }

  /**
   * Create the frame.
   */
  public TV() {
    super("TV");

    // Window is closing, save the settings!
    addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(java.awt.event.WindowEvent e) {
    	PersistentData.save(persistentData);
        System.exit(0);
      }
    });

    // Set up persistant data
    // Get data from file
    persistentData = PersistentData.load();
    Channel.setChannels(persistentData.getChannels());
    volume = persistentData.getVolume();

    // Set up pane
    setUpPane();
  }

  private void setUpPane() {
    // Window
    setResizable(false);
    setBounds(100, 100, 1000, 492);
    contentPane = new JPanel();
    contentPane.setLayout(new GridBagLayout());
    setContentPane(contentPane);
    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.HORIZONTAL;

    // Set panel for screen
    tvImage = new JLabel();
    panel = new JPanel();
    c.gridx = 0;
    c.gridy = 0;
    c.gridwidth = 7;
    c.gridheight = 12;
    panel.add(tvImage);
    contentPane.add(panel, c);
    mainChannel = new Channel(tvImage, persistentData.getSelectedChannel());

    // Set panel for pip
    pipImage = new JLabel();
    pipDisplay = new JPanel();
    c.gridx = 3;
    c.gridy = 9;
    c.gridwidth = 4;
    c.gridheight = 3;
    pipDisplay.add(pipImage);
    contentPane.add(pipDisplay, c);
    pipChannel = new Channel(pipImage, persistentData.getSelectedChannel());
    pipDisplay.setVisible(false);

    // Set buttons for remote
    JButton button1 = new JButton("1");
    button1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        switchActive(1);
      }
    });
    c.gridx = 8;
    c.gridy = 0;
    c.gridwidth = 1;
    c.gridheight = 1;
    contentPane.add(button1, c);

    JButton button2 = new JButton("2");
    button2.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        switchActive(2);
      }
    });
    c.gridx = 9;
    contentPane.add(button2, c);

    JButton button3 = new JButton("3");
    button3.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        switchActive(3);
      }
    });
    c.gridx = 10;
    contentPane.add(button3, c);

    JButton button4 = new JButton("4");
    button4.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        switchActive(4);
      }
    });
    c.gridx = 8;
    c.gridy = 1;
    contentPane.add(button4, c);

    JButton button5 = new JButton("5");
    button5.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        switchActive(5);
      }
    });
    c.gridx = 9;
    contentPane.add(button5, c);

    JButton button6 = new JButton("6");
    button6.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        switchActive(6);
      }
    });
    c.gridx = 10;
    contentPane.add(button6, c);

    JButton button7 = new JButton("7");
    button7.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        switchActive(7);
      }
    });
    c.gridx = 8;
    c.gridy = 2;
    contentPane.add(button7, c);

    JButton button8 = new JButton("8");
    button8.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        switchActive(8);
      }
    });
    c.gridx = 9;
    contentPane.add(button8, c);

    JButton button9 = new JButton("9");
    button9.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        switchActive(9);
      }
    });
    c.gridx = 10;
    contentPane.add(button9, c);

    JButton btnTimeshift = new JButton("TimeShift");
    btnTimeshift.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        timeShift();
      }
    });
    c.gridx = 8;
    c.gridy = 4;
    c.gridwidth = 2;
    contentPane.add(btnTimeshift, c);

    JButton btnPip = new JButton("PiP");
    btnPip.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
       setPictureInPicture(!pipActive);
      }
    });
    c.gridx = 10;
    contentPane.add(btnPip, c);

    JButton btnRendering = new JButton("Rendering");
    btnRendering.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        render();
      }
    });
    c.gridy = 5;
    c.gridx = 8;
    contentPane.add(btnRendering, c);

    JButton btnScanChannel = new JButton("Scan Channel");
    btnScanChannel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        persistentData.setChannels(Channel.scan());
      }
    });
    c.gridx = 10;
    contentPane.add(btnScanChannel, c);

    JButton buttonUp = new JButton(">");
    buttonUp.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if(pipActive) {
          switchActive(pipChannel.getCurrent() + 1);
        } else switchActive(mainChannel.getCurrent() + 1);
      }
    });
    c.gridy = 0;
    c.gridx = 11;
    contentPane.add(buttonUp, c);

    JButton buttonDown = new JButton("<");
    buttonDown.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if(pipActive) {
          switchActive(pipChannel.getCurrent() - 1);
        } else switchActive(mainChannel.getCurrent() - 1);
      }
    });
    c.gridy = 1;
    c.gridx = 11;
    contentPane.add(buttonDown, c);

    JButton buttonMinus = new JButton("-");
    buttonMinus.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        slider.setVisible(true);
        volume = slider.getValue();
        volume = volume-1;
        slider.setValue(volume);
        System.out.println("Volume: " + volume);
      }
    });
    c.gridy = 3;
    c.gridx = 11;
    contentPane.add(buttonMinus, c);

    JButton buttonPlus = new JButton("+");
    buttonPlus.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        slider.setVisible(true);
        volume=slider.getValue();
        slider.setValue(++volume);
        System.out.println("Volume: " + volume);
      }
    });
    c.gridy = 2;
    c.gridx = 11;
    contentPane.add(buttonPlus, c);

    JButton OKbutton = new JButton("OK");
    OKbutton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        slider.setVisible(false);
      }
    });
    c.gridy = 3;
    c.gridx = 8;
    c.gridwidth = 3;
    contentPane.add(OKbutton, c);

    slider = new JSlider(JSlider.VERTICAL, 0, 100, 10);
    slider.setOrientation(SwingConstants.VERTICAL);
    c.gridy = 0;
    c.gridheight = 12;
    c.gridwidth = 1;
    c.gridx = 7;
    slider.setVisible(false);
    slider.setMajorTickSpacing(10);
    slider.setMinorTickSpacing(1);
    slider.setPaintTicks(true);
    slider.setPaintLabels(true);
    slider.setValue(volume);
    contentPane.add(slider, c);
  }

  /**
   * Stops the film and continues later at this point.
   */
  public void timeShift(){

  }

  /**
   * Sets rendering.
   */
  public void render(){

  }


  /**
   * Vergrößert bei Aktivierung das aktuelle Bild des Main-Display auf 133% und stellt es zentriert dar,
   * d.h. die Ränder des vergrößerten Bildes werden abgeschnitten.
   * Dadurch verschwinden die schwarzen Balken rechts und links bei 4:3 Sendungen,
   * bzw. die schwarzen Balken oben und unten bei Cinemascope Filmen.
   *
   * @param on    true: Vergrößerung auf 133%; false: Normalgröße 100%
   */
  public void setZoom(boolean on) {
    System.out.println("Zoom = " + (on ? "133%" : "100%"));

    // TO DO (Aufgabe 4): Vergrößern Sie hier das aktuelle Bild des Main-Display, abhängig von "on"!
    if (on=true) {
      //tvImage= ... zoom 133 ? Wie zoomt man das?
    }
  }

  /**
   * Aktiviert bzw. deaktiviert die PictureInPicture-Darstellung.
   *
   * @param show    true: macht das kleine Bild sichtbar; false: macht das kleine Bild unsichtbar
   */
  public void setPictureInPicture(boolean show) {
    System.out.println("PiP = " + (show ? "visible" : "hidden"));

    // TO DO (Aufgabe 4): Machen Sie hier this.pipDisplay sichtbar bzw. unsichtbar!
    pipActive = show;
    pipDisplay.setVisible(show);
  }

  /**
   * Startet die Aufnahme auf den TimeShift-Recorder bzw. beendet sie wieder.
   * Das Beenden der Aufnahme beendet gleichzeitig eine eventuell laufende Wiedergabe.
   *
   * @param start         true: Start; false: Stopp
   * @throws Exception    wenn der Wert von "start" nicht zum aktuellen Zustand passt
   */
  public void recordTimeShift(boolean start) throws Exception {
    if (this.isRecording == start)
      throw new Exception("TimeShift is already " + (this.isRecording ? "recording" : "stopped"));
    if (!start)
      this.playTimeShift(false, 0);
    this.isRecording = start;
    this.recordingStartTime = now();
    System.out.println((start ? "Start" : "Stop") + " timeshift recording");
  }

  /**
   * Startet die Wiedergabe vom TimeShift-Recorder bzw. beendet sie wieder.
   *
   * @param start         true: Start; false: Stopp
   * @param offset        der Zeitversatz gegenüber der Aufnahme in Sekunden (nur relevant bei Start)
   * @throws Exception    wenn keine Aufzeichnung läuft oder noch nicht genug gepuffert ist
   */
  public void playTimeShift(boolean start, int offset) throws Exception {
    if (start && !this.isRecording)
      throw new Exception("TimeShift is not recording");
    if (start &&
      this.recordingStartTime + offset > now())
      throw new Exception("TimeShift has not yet buffered " + offset + " seconds");
    System.out.println((start ? "Start" : "Stop") + " timeshift playing" + (start ? " (offset " + offset + " seconds)" : ""));
  }

  private void switchActive(int to) {
    if(pipActive) {
        pipDisplay.removeAll();
        int channel = pipChannel.switchTo(to);
        repaint();
        pipDisplay.add(pipImage);
    } else {
        panel.removeAll();
        int channel = mainChannel.switchTo(to);
        persistentData.setSelectedChannel(channel);
        repaint();
        panel.add(tvImage);
    }
  }


  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          TV frame = new TV();
          frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }
}
