import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Set;

import org.joda.time.DateMidnight;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.util.Utils;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.fitness.Fitness;
import com.google.api.services.fitness.FitnessScopes;
import com.google.api.services.fitness.model.AggregateBucket;
import com.google.api.services.fitness.model.AggregateBy;
import com.google.api.services.fitness.model.AggregateRequest;
import com.google.api.services.fitness.model.AggregateResponse;
import com.google.api.services.fitness.model.DataPoint;
import com.google.api.services.fitness.model.Dataset;
import com.google.api.services.fitness.model.Value;
import java.io.FileOutputStream;
import java.util.List;


public class Main {

	/** Application name. */
	private static final String APPLICATION_NAME = "Fitness API Java Quickstart";
	
	/** File client_secret.json **/
	private static final String CLIENT_SECRET = "client_secret.json";

	/** Directory to store user credentials for this application. */
	private static final java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.home"),
			".credentials/drive-java-quickstart");

	/** Global instance of the {@link FileDataStoreFactory}. */
	private static FileDataStoreFactory DATA_STORE_FACTORY;

	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	/** Global instance of the HTTP transport. */
	private static HttpTransport HTTP_TRANSPORT;
        
        private static List<String> credentials;
	
	static {
		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(1);
		}
	}
	
	
	/**
	 * Global instance of the scopes required by this quickstart.
	 *
	 * If modifying these scopes, delete your previously saved credentials at
	 * ~/.credentials/drive-java-quickstart
	 */
	private static final Set<String> SCOPES = FitnessScopes.all();

	
	/**
	 * Creates an authorized Credential object.
	 * 
	 * @return an authorized Credential object.
	 * @throws IOException
	 */
	public static Credential authorize(String filename) throws IOException {
		// Load client secrets.
		InputStreamReader in = new InputStreamReader(new FileInputStream(filename)); 
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, in);

		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				clientSecrets, SCOPES).setDataStoreFactory(DATA_STORE_FACTORY).build();
		Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
		System.out.println("Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
		return credential;
	}
	
	
	
	
	public static void main(String[] args) throws IOException, GeneralSecurityException {
		System.out.println("Getting step count!"); 	
		int sum = 0;	
                Gamification games = new Gamification();
                String username;
                
                for(String cred: credentials){
                    Credential credential = authorize(cred);
                    Fitness fitness = new Fitness.Builder(
                            Utils.getDefaultTransport(),
                            Utils.getDefaultJsonFactory(),
                            credential //prerequisite
                    ).setApplicationName(APPLICATION_NAME).build();		
                    AggregateRequest aggregateRequest = new AggregateRequest();
                    aggregateRequest.setAggregateBy(Collections.singletonList(
                            new AggregateBy()
                                    .setDataSourceId("derived:com.google.step_count.delta:com.google.android.gms:estimated_steps")));
                    aggregateRequest.setStartTimeMillis(DateMidnight.now().getMillis());
                    aggregateRequest.setEndTimeMillis(System.currentTimeMillis());     
                    AggregateResponse response =  fitness.users().dataset().aggregate("me", aggregateRequest).execute();

                    for (AggregateBucket aggregateBucket : response.getBucket()) {
                        for (Dataset dataset : aggregateBucket.getDataset()) {
                            for (DataPoint dataPoint : dataset.getPoint()) {
                                for (Value value : dataPoint.getValue()) {
                                    if (value.getIntVal() != null) {
                                        sum += value.getIntVal(); //for steps you only receive int values
                                    }
                                }
                            }
                        }
                    }
                    
                    username = cred.split(".json")[0];
                   
                    if(!games.getUser(username)){
                        games.insertUsers(username,sum);
                    }
                }
	}

}
