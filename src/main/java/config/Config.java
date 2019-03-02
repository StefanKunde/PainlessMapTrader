package config;

public class Config {
	
	public static boolean useAutomatedTrading = false;

	public static String leagueSelection = "Betrayal";

    public static final String[] CURRENCY_TYPES = { "ANY", "chaos", "alchemy", "chisel", "vaal", "fuse" };
    public static final String[] AVAILABLE_LEAGUES = new String[]{"Standard", "Hardcore", "Betrayal", "Hardcore Betrayal"};

    public static String getEncodedLeagueSelection() {
		return leagueSelection.replace(" ", "%20");
	};
	
	public static boolean isUseAutomatedTrading() {
		return useAutomatedTrading;
	}

	public static void setUseAutomatedTrading(boolean useAutomatedTrading) {
		Config.useAutomatedTrading = useAutomatedTrading;
	}
}
