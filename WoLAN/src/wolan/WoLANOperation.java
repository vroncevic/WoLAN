/**
 * WoLAN lets you turn on a computer via the network. If your network socket
 * still shows a green light after you've shut down computer, it probably
 * supports Wake-on-LAN. The motherboard uses a small amount of power to monitor
 * network traffic and look for special Wake-on-LAN packets. If it sees one, it
 * will power up the system as if you had just pressed the on switch.
 *
 * Creating a Wake-on-LAN packet
 *
 * UDP and MAC addresses A Wake-on-LAN packet is an ordinary UDP packet which
 * contains the MAC address of the target computer. For reasons unknown to me,
 * the UDP packet must be 16 times larger than the byte representation of the
 * MAC address, plus an extra 6 bytes for a header. A MAC address is usually
 * specified as a string of hexadecimal digits, for example 00:0D:61:08:22:4A,
 * so can be represented using just 6 bytes. This makes the total packet size 6
 * + 16*6 = 102 bytes.
 *
 * The first 6 bytes of the packet are filled with 0xff. I'm not sure why! The
 * next 6 bytes are the MAC address of the target computer. Each subsequent set
 * of 6 bytes is also filled with the MAC address of the target computer, until
 * the packet is full.
 *
 * Sending the Magic Packet
 *
 * The UDP packet is sent to a broadcast address, such as 192.168.4.255. This
 * will cause it to be received by all computers on your local LAN, but only
 * those with a matching MAC address will respond by powering on. MAC addresses
 * are associated with each network interface and are typically unique. The UDP
 * packet uses port 9. Note that the delivery of a UDP packet is not guaranteed.
 * You may need to send more than one Wake-on-LAN packet if you are using a busy
 * network.
 */
package wolan;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import org.apache.commons.codec.binary.Hex;

/**
 * WoLANOperation contain method for sending Magic Packet
 *
 * @author Vladimir Roncevic <vladimir.roncevic@frobas.com
 */
public class WoLANOperation {

    /**
     * Port number for sending the Magic Packet
     */
    protected static final int PORT = 9;

    /**
     * usageWoLAN parse arguments
     *
     * @param numarg number of arguments
     * @return status 0 for GUI mode or 1 for CLI mode
     */
    public static int usageWoLAN(int numarg) {
        int status = 0;
        if (numarg != 0) {
            if (numarg > 2 || numarg < 2) {
                printUsageCLI(-1, "");
                System.exit(-1);
            } else if (numarg == 2) {
                status = 1;
                return status;
            }
        } else if (numarg == 0) {
            status = 0;
        }
        return status;
    }

    /**
     * Print Usage for CLI mode
     *
     * @param status -1 => Standard Usage, 0 => notification
     * @param mac address
     */
    public static void printUsageCLI(int status, String mac) {
        if (status == -1) {
            System.out.println(
                    "\n"
                    + "\tUsage: wolan [Broadcas IP address] [mac address]"
                    + "\t       example  wolan 192.168.0.1 aa:bb:cc:dd:ee:ff"
                    + ""
            );
        } else if (status == 0) {
            System.out.println(
                    "\n"
                    + ""
                    + "\t WoLAN request sent [MAC: " + mac + "]."
                    + ""
                    + "\n"
            );
        }
    }

    /**
     * sendingMagicPacket generate Magic Packet and send to workstation
     *
     * @param broadcastAddress of LAN
     * @param macAddress of target workstation
     * @return value 0 for success else -1
     */
    public static int sendingMagicPacket(String broadcastAddress, String macAddress) {
        DatagramSocket socket = null;
        DatagramPacket packet = null;
        InetAddress address = null;
        String macadd = null;
        if (broadcastAddress == null || macAddress == null || broadcastAddress.equals("") || macAddress.equals("")) {
            return -1;
        }
        byte[] macBytes = getMacBytes(macAddress);
        byte[] bytes = new byte[6 + 16 * macBytes.length];
        for (int i = 0; i < 6; i++) {
            bytes[i] = (byte) 0xff;
        }
        for (int i = 6; i < bytes.length; i += macBytes.length) {
            System.arraycopy(macBytes, 0, bytes, i, macBytes.length);
        }
        try {
            address = InetAddress.getByName(broadcastAddress);
        } catch (UnknownHostException ex) {

        }
        packet = new DatagramPacket(bytes, bytes.length, address, PORT);
        try {
            socket = new DatagramSocket();
        } catch (SocketException ex) {

        }
        macadd = String.valueOf(Hex.encodeHex(macBytes));
        if (macadd == null) {
            return -1;
        }
        if (socket != null) {
            try {
                socket.send(packet);
            } catch (IllegalArgumentException | IOException ex) {

            }
            socket.close();
        } else {
            return -1;
        }
        return 0;
    }

    /**
     * getMacBytes
     *
     * @param mac address
     * @return number of bytes
     * @throws IllegalArgumentException
     */
    private static byte[] getMacBytes(String mac) throws IllegalArgumentException {
        byte[] bytes = new byte[6];
        String[] hex = mac.split("(\\:|\\-)");
        if (hex.length != 6) {
            throw new IllegalArgumentException("Invalid MAC address.");
        }
        try {
            for (int i = 0; i < 6; i++) {
                bytes[i] = (byte) Integer.parseInt(hex[i], 16);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid hex digit in MAC address.");
        }
        return bytes;
    }

    /**
     * pingWorkstation in LAN
     *
     * @param ipaddress target for pinging
     * @return status true for reachable else false
     */
    public static boolean pingWorkstation(String ipaddress) {
        boolean status = false;
        if(ipaddress == null || ipaddress.equals("")) {
            return status;
        }
        try {
            InetAddress address = InetAddress.getByName(ipaddress);
            status = address.isReachable(10000);
        } catch (Exception e) {

        }
        return status;
    }
}
