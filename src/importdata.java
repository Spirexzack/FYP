import java.net.URL;
import java.util.List;
import com.google.gdata.client.spreadsheet.ListQuery;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.CustomElementCollection;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetEntry;

public class importdata {
    public static void main(String[] args) throws Exception{
        String applicationName = "AppName";
        String user = args[0];
        String pass = args[1];
        String key = args[2];
        String query = args[3];

        SpreadsheetService service = new SpreadsheetService(applicationName);
        service.setUserCredentials(user, pass); //set client auth 

        URL entryUrl = new URL("http://spreadsheets.google.com/feeds/spreadsheets/" + key);
        SpreadsheetEntry spreadsheetEntry = service.getEntry(entryUrl, SpreadsheetEntry.class);

        WorksheetEntry worksheetEntry = spreadsheetEntry.getDefaultWorksheet();

        ListQuery listQuery = new ListQuery(worksheetEntry.getListFeedUrl());
        listQuery.setSpreadsheetQuery( query );

        ListFeed listFeed = service.query(listQuery, ListFeed.class);
        List<ListEntry> list = listFeed.getEntries();
        for( ListEntry listEntry : list )
        {
            System.out.println( "content=[" + listEntry.getPlainTextContent() + "]");
            CustomElementCollection elements = listEntry.getCustomElements();
            System.out.println(
                    " name=" + elements.getValue("name") + 
                    " age="  + elements.getValue("age") );
        }
    }
}