package wolan;

import com.Utilities.AppConfig.Session;

/**
 * Session of configuration
 *
 * @author Vladimir Roncevic <vladimir.roncevic@frobas.com>
 */
public class WoLANSession extends Session  {

    /**
     * Saving APP configuration
     * @param CfgDir APP configuration directory
     * @param CfgFile APP configuration file
     */
    public WoLANSession(String CfgDir, String CfgFile) {
        super(CfgDir, CfgFile);
    }
}
