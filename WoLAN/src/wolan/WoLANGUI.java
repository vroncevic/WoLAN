package wolan;

import com.Utilities.About.About;
import com.Utilities.AboutContainer;
import com.Utilities.Dialog;
import com.Utilities.Help.Help;
import com.Utilities.HelpContainer;
import com.Utilities.Logging;
import com.Utilities.OSValidator;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.text.DefaultEditorKit;

/**
 * WoLAN UI for sending Magic Packet for calling method wake on lan
 *
 * @author Vladimir Roncevic <vladimir.roncevic@frobas.com>
 */
public class WoLANGUI extends javax.swing.JFrame {

    private static final long serialVersionUID = -7265262820532440619L;
    protected About aboutui;
    protected Help helpui;
    protected Dialog dialog;
    protected WoLANSession config;
    protected Settings setting;

    /**
     * Create form WoLANGUI
     *
     * @param about
     * @param help
     */
    public WoLANGUI(AboutContainer about, HelpContainer help) {
        initComponents();
        this.aboutui = new About(about);
        this.helpui = new Help(help);
        this.dialog = new Dialog(this);
        this.setting = new Settings(about.getAppName());
        this.config = new WoLANSession(".wolan", "wolan.cfg");
        setting = (Settings) config.read(setting);
        if (setting != null) {
            if (setting.getBroadcasAddress() != null) {
                broadcastipeditfield.setText(setting.getBroadcasAddress());
            }
            if (setting.getMacAddress() != null) {
                macaddresseditfield.setText(setting.getMacAddress());
            }
        } else {
            broadcastipeditfield.setText("192.168.0.1");
            macaddresseditfield.setText("ex: aa:bb:cc:dd:ee:ff");
        }
        setIconImage(about.getAppIcon().getImage());
        setTitle(setting.getVersion());
        PingWorkStation.setEnabled(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 4 - this.getSize().width / 4, dim.height / 4 - this.getSize().height / 4);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        if (OSValidator.isUnix()) {
            Logging.logging("Info", "Started WoLAN" + " " + about.getAppName(),
                    "/data/apps/logs/WoLAN/", "WoLAN");
        } else if (OSValidator.isWindows()) {
            Logging.logging("Info", "Started WoLAN" + " " + about.getAppName(),
                    System.getProperty("user.home") + "/", "WoLAN");
        } else {
            Logging.logging("Error", "Unsupported OS" + " " + about.getAppName(),
                    System.getProperty("user.home") + "/", "WoLAN");
            ApplicationExit();
        }
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ApplicationExit();
            }
        });
    }

    /**
     * Exit from Application
     */
    private void ApplicationExit() {
        int status = dialog.ShowDialog("Exit WoLAN ?", "Confirm Exit!", JOptionPane.YES_NO_OPTION);
        if (status == JOptionPane.YES_OPTION) {
            config.write(setting);
            System.exit(0);
        }
    }

    /**
     * WOL: sending magic packet
     *
     * @return integer status
     */
    private int wol() {
        if (((macaddresseditfield.getText()).equals(""))
                || ((macaddresseditfield.getText()).equals("ex: aa:bb:cc:dd:ee:ff"))) {
            return -2;
        }
        return (WoLANOperation.sendingMagicPacket(broadcastipeditfield.getText(), macaddresseditfield.getText()));
    }

    /**
     * Save last operation
     *
     * @return status
     */
    private int WakeOnLan() {
        int status = wol();
        switch (status) {
            case 0:
                if (OSValidator.isUnix()) {
                Logging.logging("Info", "Sent WoLAN Magic Packet",
                        "/data/apps/logs/WoLAN/", "WoLAN");
                } else if (OSValidator.isWindows()) {
                    Logging.logging("Info", "Sent WoLAN Magic Packet",
                    System.getProperty("user.home") + "/", "WoLAN");
                }
                break;
            case -1:
                dialog.Dismiss("Please provide Broadcast address and MAC address !", "Warning", JOptionPane.CLOSED_OPTION);
                break;
            case -2:
                dialog.Dismiss("Please provide MAC address !", "Warning", JOptionPane.CLOSED_OPTION);
                break;
        }
        setting.setBroadcasAddress(broadcastipeditfield.getText());
        setting.setMacAddress(macaddresseditfield.getText());
        return status;
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        broadcastiptxt = new javax.swing.JLabel();
        broadcastipeditfield = new javax.swing.JFormattedTextField();
        macaddresstxt = new javax.swing.JLabel();
        macaddresseditfield = new javax.swing.JFormattedTextField();
        cancel = new javax.swing.JButton();
        wolansend = new javax.swing.JButton();
        WoLANMenu = new javax.swing.JMenuBar();
        FileMenu = new javax.swing.JMenu();
        Exit = new javax.swing.JMenuItem();
        EditMenu = new javax.swing.JMenu();
        Copy = new javax.swing.JMenuItem();
        Paste = new javax.swing.JMenuItem();
        OptionMenu = new javax.swing.JMenu();
        WoLAN = new javax.swing.JMenuItem();
        PingWorkStation = new javax.swing.JMenuItem();
        HelpMenu = new javax.swing.JMenu();
        HelpContents = new javax.swing.JMenuItem();
        About = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Frobas WoLAN ver.1.0");
        setMaximumSize(new java.awt.Dimension(300, 180));
        setMinimumSize(new java.awt.Dimension(300, 180));
        setResizable(false);

        broadcastiptxt.setText("Broadcast IP");

        broadcastipeditfield.setColumns(8);
        broadcastipeditfield.setText("192.168.0.1");
        broadcastipeditfield.setAutoscrolls(false);
        broadcastipeditfield.setMaximumSize(new java.awt.Dimension(119, 19));
        broadcastipeditfield.setMinimumSize(new java.awt.Dimension(119, 19));

        macaddresstxt.setText("MAC Address");

        macaddresseditfield.setColumns(8);
        macaddresseditfield.setText("ex: aa:bb:cc:dd:ee:ff");
        macaddresseditfield.setMaximumSize(new java.awt.Dimension(4, 19));

        cancel.setText("Cancel");
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });

        wolansend.setText("WoLAN");
        wolansend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wolansendActionPerformed(evt);
            }
        });

        FileMenu.setText("File");

        Exit.setText("Close and Exit");
        Exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitActionPerformed(evt);
            }
        });
        FileMenu.add(Exit);

        WoLANMenu.add(FileMenu);

        EditMenu.setText("Edit");

        Copy.setText("Copy");
        Copy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CopyActionPerformed(evt);
            }
        });
        EditMenu.add(Copy);

        Paste.setText("Paste");
        Paste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PasteActionPerformed(evt);
            }
        });
        EditMenu.add(Paste);

        WoLANMenu.add(EditMenu);

        OptionMenu.setText("Option");

        WoLAN.setText("WoLAN");
        WoLAN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                WoLANActionPerformed(evt);
            }
        });
        OptionMenu.add(WoLAN);

        PingWorkStation.setText("Ping Test");
        PingWorkStation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PingWorkStationActionPerformed(evt);
            }
        });
        OptionMenu.add(PingWorkStation);

        WoLANMenu.add(OptionMenu);

        HelpMenu.setText("Help");

        HelpContents.setText("HelpContents");
        HelpContents.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HelpContentsActionPerformed(evt);
            }
        });
        HelpMenu.add(HelpContents);

        About.setText("About");
        About.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AboutActionPerformed(evt);
            }
        });
        HelpMenu.add(About);

        WoLANMenu.add(HelpMenu);

        setJMenuBar(WoLANMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(macaddresstxt)
                    .addComponent(broadcastiptxt)
                    .addComponent(cancel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(macaddresseditfield, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(broadcastipeditfield, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(wolansend, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(broadcastiptxt)
                    .addComponent(broadcastipeditfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(macaddresstxt)
                    .addComponent(macaddresseditfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancel)
                    .addComponent(wolansend))
                .addContainerGap())
        );

        getAccessibleContext().setAccessibleName("");

        setBounds(0, 0, 310, 210);
    }// </editor-fold>//GEN-END:initComponents

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        ApplicationExit();
    }//GEN-LAST:event_cancelActionPerformed

    private void wolansendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wolansendActionPerformed
        int res = WakeOnLan();
        if (res == 0) {
            dialog.Dismiss("Sent WoLAN Magic Packet!", "Info", JOptionPane.CLOSED_OPTION);
            System.exit(0);
        }
    }//GEN-LAST:event_wolansendActionPerformed

    private void AboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AboutActionPerformed
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                aboutui.setVisible(true);
            }
        });
    }//GEN-LAST:event_AboutActionPerformed

    private void HelpContentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HelpContentsActionPerformed
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                helpui.setVisible(true);
            }
        });
    }//GEN-LAST:event_HelpContentsActionPerformed

    private void WoLANActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_WoLANActionPerformed
        WakeOnLan();
    }//GEN-LAST:event_WoLANActionPerformed

    private void CopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CopyActionPerformed
        Copy.addActionListener(new DefaultEditorKit.CopyAction());
        Copy.setMnemonic(KeyEvent.VK_C);
    }//GEN-LAST:event_CopyActionPerformed

    private void PasteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PasteActionPerformed
        Paste.addActionListener(new DefaultEditorKit.PasteAction());
        Paste.setMnemonic(KeyEvent.VK_V);
    }//GEN-LAST:event_PasteActionPerformed

    private void ExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitActionPerformed
        ApplicationExit();
    }//GEN-LAST:event_ExitActionPerformed

    private void PingWorkStationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PingWorkStationActionPerformed
        boolean isreachable;
        isreachable = WoLANOperation.pingWorkstation("192.168.4.1");
        if (isreachable) {
            dialog.Dismiss("Workstation is reachable !", "Ping Test", JOptionPane.CLOSED_OPTION);
        } else {
            dialog.Dismiss("Workstation is not reachable !", "Ping Test", JOptionPane.CLOSED_OPTION);
        }
    }//GEN-LAST:event_PingWorkStationActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem About;
    private javax.swing.JMenuItem Copy;
    private javax.swing.JMenu EditMenu;
    private javax.swing.JMenuItem Exit;
    private javax.swing.JMenu FileMenu;
    private javax.swing.JMenuItem HelpContents;
    private javax.swing.JMenu HelpMenu;
    private javax.swing.JMenu OptionMenu;
    private javax.swing.JMenuItem Paste;
    private javax.swing.JMenuItem PingWorkStation;
    private javax.swing.JMenuItem WoLAN;
    private javax.swing.JMenuBar WoLANMenu;
    private javax.swing.JFormattedTextField broadcastipeditfield;
    private javax.swing.JLabel broadcastiptxt;
    private javax.swing.JButton cancel;
    private javax.swing.JFormattedTextField macaddresseditfield;
    private javax.swing.JLabel macaddresstxt;
    private javax.swing.JButton wolansend;
    // End of variables declaration//GEN-END:variables
}
