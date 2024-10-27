package DnDGen.UI;

import DnDGen.DB.DBMS;


public class UIStarter {
    public static void main(final String[] args) {
    	DBMS.main(args);

    	UI.launchUI(args);
    }
}
