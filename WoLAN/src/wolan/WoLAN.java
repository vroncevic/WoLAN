package wolan;

import com.Utilities.AboutContainer;
import com.Utilities.HelpContainer;
import com.Utilities.SplashScreen.SplashScreen;
import com.Utilities.Version;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * WoLAN tool - wake on lan tool. It can be used as GUI and CLI tool.
 *
 * @author Vladimir Roncevic <vladimir.roncevic@frobas.com>
 */
public class WoLAN {

    protected static SplashScreen splashScreen;
    protected static AboutContainer about;
    protected static HelpContainer help;
    protected static Version ver;

    /**
     * main - application entry point
     *
     * @param args GUI or CLI mode
     */
    public static void main(String[] args) {
        if (WoLANOperation.usageWoLAN(args.length) == 0) {
            splashScreen = new SplashScreen(3000, WoLAN.class);
            ver = new Version();
            about = new AboutContainer(WoLAN.class);
            about.setTitle("About WoLAN");
            about.setAppName("WoLAN ver." + ver.fullAppVersion());
            about.setCompanyName("Frobas IT Department 2015");
            about.setAppInfo(" Frobas, www.frobas.com");
            help = new HelpContainer(WoLAN.class);
            help.setTitle("Help Topics WoLAN");
            help.setAppName("WoLAN ver." + ver.fullAppVersion());
            help.setCompanyName("Frobas IT Department 2015");
            try {
                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {

            }
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new WoLANGUI(about, help).setVisible(true);
                }
            });
        } else if (WoLANOperation.usageWoLAN(args.length) == 1) {
            String broadcastaddress = args[0];
            String macaddress = args[1];
            int status = WoLANOperation.sendingMagicPacket(broadcastaddress, macaddress);
            WoLANOperation.printUsageCLI(status, macaddress);
        }
    }
}
