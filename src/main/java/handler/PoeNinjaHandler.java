package handler;

import com.google.gson.Gson;
import connector.PoeNinjaFetcher;
import items.TradeableBulk;
import jsonNinjaResult.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class PoeNinjaHandler {

	private static final Logger LOG = LoggerFactory.getLogger(PoeNinjaHandler.class);

	private final int MAX_REQUESTS = 10;
	private PoeNinjaFetcher poeConnector;
	private JsonNinjaSearchData searchData;
	private TradeableBulk tradeables;
	private String jsonSearchString;
	private int minBulk;
	
	public PoeNinjaHandler(int minBulk) {
		this.poeConnector = new PoeNinjaFetcher();
		searchData = new JsonNinjaSearchData();
		tradeables = new TradeableBulk();
		jsonSearchString = "";
		this.minBulk = minBulk;
	}
	
	public void generateJsonSearchString() {
		String jsonSearchString = "";
		
		jsonSearchString += "{\"exchange\":{\"status\":{\"option\":\"online\"},\"have\":[],\"want\":[\"";
		jsonSearchString += searchData.getMap();
		jsonSearchString += "\"]";
		jsonSearchString += ",\"minimum\":";
		jsonSearchString += searchData.getBulkAmount();
		jsonSearchString += "}}";
		
		this.jsonSearchString = jsonSearchString;
	}
	
	public void handleBulkRequests() {
		String responseFromPost;
		String searchLink;
		try {
			responseFromPost = poeConnector.sendPost(this.jsonSearchString);
			poeConnector.storeResultsFromResponseAsList(responseFromPost);
			
			Gson gson = new Gson();
			int i = 0;
			while(this.poeConnector.getResultList().size() > 0 && i < MAX_REQUESTS) {
				searchLink = poeConnector.generateSearchString(responseFromPost);
				String response = poeConnector.sendGet(searchLink);
				Result result = gson.fromJson(response, Result.class);
				this.tradeables.addResults(result.getResult());
				TimeUnit.MILLISECONDS.sleep(300);
				i++;
			}
			
		} catch (Exception e) {
			LOG.error("e", e);
		}
		
		this.tradeables.generateTradebleItems(this.minBulk);
	}

	public JsonNinjaSearchData getSearchData() {
		return searchData;
	}

	public void setSearchData(JsonNinjaSearchData searchData) {
		this.searchData = searchData;
	}

	public TradeableBulk getTradeableBulks() {
		return this.tradeables;
	}
	
	
	
	
	
}
