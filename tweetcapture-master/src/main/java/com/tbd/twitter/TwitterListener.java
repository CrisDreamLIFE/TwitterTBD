package com.tbd.twitter;

import javax.annotation.PostConstruct;

import Utilities.ChileDetector;
import Utilities.SentimentSpanish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;


@Service
@Configurable
public class TwitterListener {
	@Autowired
	private TwitterStream twitterStream;
	@Autowired
	private MongoTemplate mongo;
	
	private SentimentSpanish sentimentSpanish;
		
	@PostConstruct
	public void run() {
		twitterStream.addListener(new StatusListener() {
			public void onStatus(Status status) {
				ChileDetector clDetect = ChileDetector.getInstance();
				if (clDetect.isChilean(status.getUser().getLocation())) {
					Tweet tweet = new Tweet();
					tweet.set_id(status.getId());
					tweet.setCreatedAt(status.getCreatedAt());
					tweet.setGeoLocation(status.getGeoLocation());
					tweet.setText(status.getText());
					tweet.setRetweetCount(status.getRetweetCount());
					tweet.setFavoriteCount(status.getFavoriteCount());
					tweet.setRetweet(status.isRetweet());
					tweet.setUserId(status.getUser().getId());
					tweet.setUserScreenName(status.getUser().getScreenName());
					tweet.setUserVerified(status.getUser().isVerified());
					tweet.setUserFollowersCount(status.getUser().getFollowersCount());
					tweet.setUserStatusesCount(status.getUser().getStatusesCount());
					tweet.setUserFriendsCount(status.getUser().getFriendsCount());
					sentimentSpanish.analyze(status.getText());
					tweet.setPositivePercent(sentimentSpanish.getPositivePercent()); //crear estos
					tweet.setNegativePercent(sentimentSpanish.getNegativePercent()); //crear estos
					tweet.setPositiveScore(sentimentSpanish.getPositiveScore()); //crear estos
					tweet.setNegativeScore(sentimentSpanish.getNegativeScore()); //crear estos
					tweet.setAnalysis(sentimentSpanish.getAnalysis()); //crear estos
					mongo.insert(tweet);
				}
	        }

			@Override
			public void onException(Exception arg0) {
								
			}

			@Override
			public void onDeletionNotice(StatusDeletionNotice arg0) {
							
			}

			@Override
			public void onScrubGeo(long arg0, long arg1) {
								
			}

			@Override
			public void onStallWarning(StallWarning arg0) {
								
			}

			@Override
			public void onTrackLimitationNotice(int arg0) {
								
			}			
		});
		
		this.sentimentSpanish = SentimentSpanish.getInstance();
		this.sentimentSpanish.loadWords();
		
		
		// se creara la lista con las palabras o frases claves
				String[ ] bolsa = new String[34];
				bolsa[0] = "presidente piñera";
				bolsa[1] = "presidente sebastian piñera";
				bolsa[2] = "presidente de chile";
				bolsa[3] = "político chileno";
				bolsa[4] = "política chilena";
				bolsa[5] = "politico chileno";
				bolsa[6] = "politica chilena";
				bolsa[7] = "ministro chileno";
				bolsa[8] = "piñericosas";
				bolsa[9] = "sebastian piñera";
				bolsa[10] = "Andrés Chadwick";
				bolsa[11] = "Roberto Ampuero";
				bolsa[12] = "Alberto Espina";
				bolsa[13] = "Felipe Larraín Bascuñán";
				bolsa[14] = "Gonzalo Blumel Mac-Iver";
				bolsa[15] = "Cecilia Pérez";
				bolsa[16] = "José Ramón Valente Vías";
				bolsa[17] = "Alfredo Moreno Charme";
				bolsa[18] = "Alfredo Moreno Charme";
				bolsa[19] = "María Carolina Schmidt Zaldívar";
				bolsa[20] = "Hernán Larraín Fernández";
				bolsa[21] = "Nicolás Monckeberg Díaz";
				bolsa[22] = "Juan Andrés Fontaine Talavera"; 
				bolsa[23] = "Emilio Santelices Cuevas";
				bolsa[24] = "Cristian Monckeberg Bruner";
				bolsa[25] = "Antonio Walker Prieto";
				bolsa[26] = "Baldo Prokurica Prokurica"; 
				bolsa[27] = "Gloria Hutt Hesse";
				bolsa[28] = "Felipe Ward Edwards";
				bolsa[29] = "Susana Jiménez Schuster";
				bolsa[30] = "Marcela Cubillos Sigall";
				bolsa[31] = "Pauline Kantor Pupkin"; 
				bolsa[32] = "Isabel Plá Jarufe";
				bolsa[33] = "Mauricio José Rojas Mullor";
				
				
	    FilterQuery filter = new FilterQuery();
	    //filter.track(new String[]{"presidente piñera"});
	    filter.track(bolsa);
	    filter.language(new String[]{"es"});
	    twitterStream.filter(filter);
	}

	public TwitterStream getTwitterStream() {
		return twitterStream;
	}

	public void setTwitterStream(TwitterStream twitterStream) {
		this.twitterStream = twitterStream;
	}

	public MongoTemplate getMongo() {
		return mongo;
	}

	public void setMongo(MongoTemplate mongo) {
		this.mongo = mongo;
	}
}
