package crypto.boomerang;

import boomerang.DefaultBoomerangOptions;
import boomerang.stats.CSVBoomerangStatsWriter;
import boomerang.stats.IBoomerangStats;
import crypto.analysis.CryptoScanner;

public class CogniCryptBoomerangOptions extends DefaultBoomerangOptions {
	private String queryReportFile;
	public CogniCryptBoomerangOptions(CryptoScanner scanner) {
		if(scanner != null)
			queryReportFile = scanner.getQueryReportFileName();
	}
	@Override
	public boolean onTheFlyCallGraph() {
		return false;
	}

	@Override
	public boolean arrayFlows() {
		return true;
	}
	
	@Override
	public int analysisTimeoutMS() {
		return 5000;
	}
	@Override
	public IBoomerangStats statsFactory() {
		if(queryReportFile == null)
			return super.statsFactory();
		return new CSVBoomerangStatsWriter(queryReportFile);
	}
}
