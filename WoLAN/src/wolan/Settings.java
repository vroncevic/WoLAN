package wolan;

/**
 * Global settings for tool WoLAN
 *
 * @author Vladimir Roncevic <vladimir.roncevic@frobas.com>
 */
public class Settings {

    private String BroadcasAddress;
    private String MacAddress;
    private String Version;

    public Settings(String version) {
        this.BroadcasAddress = "192.168.0.1";
        this.MacAddress = "ex: aa:bb:cc:dd:ee:ff";
        this.Version = version;
    }

    public Settings(String BroadcasAddress, String MacAddress, String Version) {
        this.BroadcasAddress = BroadcasAddress;
        this.MacAddress = MacAddress;
        this.Version = Version;
    }

    public String getBroadcasAddress() {
        return BroadcasAddress;
    }

    public void setBroadcasAddress(String BroadcasAddress) {
        this.BroadcasAddress = BroadcasAddress;
    }

    public String getMacAddress() {
        return MacAddress;
    }

    public void setMacAddress(String MacAddress) {
        this.MacAddress = MacAddress;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String Version) {
        this.Version = Version;
    }
}
