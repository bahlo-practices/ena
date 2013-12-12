package mainWindow;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        Settings.save();
        System.exit(0);
      }
    });

    // Set default settings
    Settings.set("imagePath", ".\\channels\\");

    // Load settings
    Settings.load();

    // Set up pane
    setUpPane();
  }

  private void setUpPane() {
    // Window
    setResizable(false);
    setBounds(100, 100, 1000, 492);
    contentPane = new JPanel();
    setContentPane(contentPane);
    contentPane.setLayout(null);

    // Set panel for pip
    pipImage = new JLabel();
    pipDisplay = new JPanel();
    pipDisplay.setBounds(408, 326, 206, 128);
    pipDisplay.add(pipImage);
    contentPane.add(pipDisplay);
    pipChannel = new Channel(pipImage);
    pipDisplay.setVisible(false);

    // Set panel for screen
    tvImage = new JLabel();
    panel = new JPanel();
    panel.setBounds(0, 0, 734, 454);
    panel.add(tvImage);
    contentPane.add(panel);
    mainChannel = new Channel(tvImage);

    // Set buttons for remote
    JButton button1 = new JButton("1");
    button1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        switchActive(1);
      }
    });
    button1.setBounds(809, 77, 44, 23);
    contentPane.add(button1);

    JButton button2 = new JButton("2");
    button2.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        switchActive(2);
      }
    });
    button2.setBounds(863, 77, 44, 23);
    contentPane.add(button2);

    JButton button3 = new JButton("3");
    button3.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        switchActive(3);
      }
    });
    button3.setBounds(917, 77, 44, 23);
    contentPane.add(button3);

    JButton button4 = new JButton("4");
    button4.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        switchActive(4);
      }
    });
    button4.setBounds(809, 111, 44, 23);
    contentPane.add(button4);

    JButton button5 = new JButton("5");
    button5.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        switchActive(5);
      }
    });
    button5.setBounds(863, 111, 44, 23);
    contentPane.add(button5);

    JButton button6 = new JButton("6");
    button6.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        switchActive(6);
      }
    });
    button6.setBounds(917, 111, 44, 23);
    contentPane.add(button6);

    JButton button7 = new JButton("7");
    button7.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        switchActive(7);
      }
    });
    button7.setBounds(809, 145, 44, 23);
    contentPane.add(button7);

    JButton button8 = new JButton("8");
    button8.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        switchActive(8);
      }
    });
    button8.setBounds(863, 145, 44, 23);
    contentPane.add(button8);

    JButton button9 = new JButton("9");
    button9.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        switchActive(9);
      }
    });
    button9.setBounds(917, 145, 44, 23);
    contentPane.add(button9);

    JButton btnTimeshift = new JButton("TimeShift");
    btnTimeshift.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        timeShift();
      }
    });
    btnTimeshift.setBounds(833, 374, 102, 23);
    contentPane.add(btnTimeshift);

    JButton btnPip = new JButton("PiP");
    btnPip.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
       setPictureInPicture(!pipActive);
      }
    });
    btnPip.setBounds(833, 340, 102, 23);
    contentPane.add(btnPip);

    JButton btnRendering = new JButton("Rendering");
    btnRendering.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        render();
      }
    });
    btnRendering.setBounds(833, 408, 102, 23);
    contentPane.add(btnRendering);

    JButton btnScanChannel = new JButton("Scan Channel");
    btnScanChannel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        Channel.scan();
      }
    });
    btnScanChannel.setBounds(821, 11, 125, 23);
    contentPane.add(btnScanChannel);

    JButton buttonDown = new JButton("<");
    buttonDown.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        switchPrev();
      }
    });
    buttonDown.setBounds(833, 209, 44, 23);
    contentPane.add(buttonDown);

    JButton buttonUp = new JButton(">");
    buttonUp.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        switchNext();
      }
    });
    buttonUp.setBounds(891, 209, 44, 23);
    contentPane.add(buttonUp);

    slider = new JSlider(JSlider.VERTICAL, 0, 100, 10);
    slider.setOrientation(SwingConstants.VERTICAL);
    slider.setBounds(744, 13, 55, 418);
    slider.setVisible(false);
    slider.setMajorTickSpacing(10);
    slider.setMinorTickSpacing(1);
    slider.setPaintTicks(true);
    slider.setPaintLabels(true);
    slider.setValue(volume);
    contentPane.add(slider);

    JButton buttonMinus = new JButton("-");
    buttonMinus.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        slider.setVisible(true);
        volume=slider.getValue();
        volume=volume-1;
        slider.setValue(volume);
        System.out.println("Volume: " + volume);

      }
    });
    buttonMinus.setBounds(833, 243, 44, 23);
    contentPane.add(buttonMinus);

    JButton buttonPlus = new JButton("+");
    buttonPlus.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        slider.setVisible(true);
        volume=slider.getValue();
        slider.setValue(++volume);
        System.out.println("Volume: " + volume);
      }
    });
    buttonPlus.setBounds(891, 243, 44, 23);
    contentPane.add(buttonPlus);

    JButton OKbutton = new JButton("OK");
    OKbutton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        slider.setVisible(false);
      }
    });
    OKbutton.setBounds(848, 294, 72, 23);
    contentPane.add(OKbutton);

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

  public void switchActive(int to) {
    if(pipActive) {
        pipDisplay.removeAll();
        pipChannel.switchTo(to);
        repaint();
        pipDisplay.add(pipImage);
    } else {
        panel.removeAll();
        mainChannel.switchTo(to);
        repaint();
        panel.add(tvImage);
    }
  }

  public void switchNext() {
    if(pipActive) {
        pipDisplay.removeAll();
        pipChannel.next();
        repaint();
        pipDisplay.add(pipImage);
    } else {
        panel.removeAll();
        mainChannel.next();
        repaint();
        panel.add(tvImage);
    }
  }

  public void switchPrev() {
    if(pipActive) {
        pipDisplay.removeAll();
        pipChannel.prev();
        repaint();
        pipDisplay.add(pipImage);
    } else {
        panel.removeAll();
        mainChannel.prev();
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
