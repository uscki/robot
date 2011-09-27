package bots;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

public class Twitterbot implements Bot {
	
	private Twitter twitter;
	public Twitterbot()
	{
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey("PeOeRoCdRMXu0jerzN5g")
		  .setOAuthConsumerSecret("Y4kvHtBPcjlVQ2WRjmGWMRkXpeFt6ICeqcmUTUGCk")
		  .setOAuthAccessToken("361885401-XHUW5aC1Zss9yk8tX8mwox62O7Lgq1bBlUR6luxB")
		  .setOAuthAccessTokenSecret("4s0uwOcGnqgGmxZImF9L926J9ECxD8tLqMbAPi0ZzI");
		  
		TwitterFactory tf = new TwitterFactory(cb.build());
		twitter = tf.getInstance();
	}
	
	@Override
	public String ask(String input, String user) {

		try {
			Status status = twitter.updateStatus("\""+input+"\"");
			System.out.println("Successfully updated the status to [" + status.getText() + "].");
		} catch (TwitterException e) {
			
		}
		
		return null;
	}

}
