import java.io.*;
import java.util.*;
import java.nio.file.*;

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
import java.nio.file.Files;


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
        
        public static final long DAY_MILLIS = 86400000;
	
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
         * @param filename name the of the client secret to authorize
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
	
	public static List<String> getJSONS (String directory) {
            List<String> textFiles = new ArrayList<>();
            File dir = new File(directory);
            for (File file : dir.listFiles()) {
                if (file.getName().endsWith((".json"))) {
                  textFiles.add(file.getName());
                }
            }
            return textFiles;
	}
        
        public static List<String> getFiles(String directory){
            List<String> textFiles = new ArrayList<>();
            File dir = new File(directory);
            for(File file : dir.listFiles()) {
                if(file.getName().endsWith(".ups")){
                    textFiles.add(file.getName());
                }
            }
            return textFiles;
        }
	
        public static void generatePointsFile(String filename) throws Exception{
            int nDays;
            double[] steps;
            long starttime, endtime;
            String username;
            Date begin;
            StringBuilder sb;
            
            begin = new GregorianCalendar(2017, Calendar.DECEMBER, 6).getTime();
            starttime = begin.getTime();
            Date now = new Date();
            long diff = now.getTime() - starttime;
            endtime = starttime;
            nDays = ((diff % DAY_MILLIS)==0) ? (int)(diff / (DAY_MILLIS)) : (int)(diff / (DAY_MILLIS)) + 1;
            steps = new double[nDays];
            sb = new StringBuilder();
            
            Credential credential = authorize(filename);
            Fitness fitness = new Fitness.Builder(
                        Utils.getDefaultTransport(),
                        Utils.getDefaultJsonFactory(),
                        credential //prerequisite
            ).setApplicationName(APPLICATION_NAME).build();		
            AggregateRequest aggregateRequest = new AggregateRequest();
            aggregateRequest.setAggregateBy(Collections.singletonList(
                    new AggregateBy()
                            .setDataSourceId("derived:com.google.step_count.delta:com.google.android.gms:estimated_steps")));
            
            for(int i = 0; i < nDays; ++i){
                endtime = i == nDays ? System.currentTimeMillis() : endtime + DAY_MILLIS;
                aggregateRequest.setStartTimeMillis(starttime);
                aggregateRequest.setEndTimeMillis(endtime);     
                AggregateResponse response = fitness.users().dataset().aggregate("me", aggregateRequest).execute();

                for (AggregateBucket aggregateBucket : response.getBucket()) {
                    for (Dataset dataset : aggregateBucket.getDataset()) {
                        for (DataPoint dataPoint : dataset.getPoint()) {
                            for (Value value : dataPoint.getValue()) {
                                if (value.getIntVal() != null) {
                                    steps[i] += value.getIntVal(); //for steps you only receive int values
                                }
                            }
                        }
                    }
                }
                starttime = endtime;
                //System.out.printf("Total steps in day %d: %d\n", i, (int)steps[i]);
                sb.append((int)steps[i]).append("\n");
            }
            username = filename.split(".json")[0];
            try(PrintWriter out = new PrintWriter(username + ".ups")){
                out.print(sb.toString());
            }
        }
        
        public static void loadUsers(Gamification g, List<String> files) throws Exception{
            String username;
            List<Integer> points;
            Path path;
            for(String file : files){
                username = file.split(".ups")[0];
                path = Paths.get(file);
                points = new ArrayList<>();
                for(String line : Files.readAllLines(path)){
                    points.add(Integer.parseInt(line));
                }
                g.insertUser(username, points);
            }
        }
        
        private static void printRanking(List<Utilizador> utilizadores){
            int i = 1, pontos;
            for(Utilizador u : utilizadores){
                pontos = u.getPontos();
                System.out.printf("% 2d -> %s (%s lvl %d) : %d\n", i++, 
                        u.getUsername(), u.getLevel(), Gamification.getUserLevel(pontos), pontos);
            }
        }
        
	public static void main(String[] args) throws Exception {
            
            if(args.length > 0){
                if(args.length == 2){
                    if(args[0].equals("-g")){
                        generatePointsFile(args[1]);
                        return;
                    }
                } else { return; }
            }
            
            Gamification games = new Gamification();
            loadUsers(games, getFiles(System.getProperty("user.dir")));
            games.updateUserPoints();
            games.updateUserAchievements();
            games.updateUserLevel();
            printRanking(games.Ranking());
	}

}
