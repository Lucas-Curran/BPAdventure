package com.mygdx.game.ui;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.CrashWriter;
import com.mygdx.game.Utilities;
import com.mygdx.game.entities.EntityHandler;

public class ReportBugWindow {
	
	static Logger logger = LogManager.getLogger(ReportBugWindow.class.getName());
	
	private Table inputTable;
	private Stage stage;
	private TextButton confirmButton, leaveButton, okButton;
	private Table bottomTable;
	private TextField subjectField;
	private TextArea messageField;
	private Table confirmationTable;

	/**
	 * Instance of bug report window to use in main menu
	 */
	public ReportBugWindow() {	
		try {
			stage = new Stage();

			inputTable = new Table();
			bottomTable = new Table();
			confirmationTable = new Table();

			inputTable.setWidth(400);
			inputTable.setHeight(400);

			inputTable.setPosition(200, 50);
			bottomTable.setPosition(350, 100);

			inputTable.background(Utilities.ACTUAL_UI_SKIN.getDrawable("default-pane"));
			Label titleLabel = new Label("Report Bug", Utilities.ACTUAL_UI_SKIN);
			subjectField = new TextField("", Utilities.ACTUAL_UI_SKIN);
			messageField = new TextArea("", Utilities.ACTUAL_UI_SKIN);
			//text area is multi line text field
			subjectField.setMessageText("Subject");
			messageField.setMessageText("Describe your bug...");
			messageField.setAlignment(Align.topLeft);

			titleLabel.setFontScale(2);

			confirmButton = new TextButton("Confirm", Utilities.buttonStyles("default-rect", "default-rect-down"));
			leaveButton = new TextButton("Leave", Utilities.buttonStyles("default-rect", "default-rect-down"));
			okButton = new TextButton("OK", Utilities.buttonStyles("default-rect", "default-rect-down"));

			inputTable.add(titleLabel).top();
			inputTable.row();
			inputTable.add(subjectField);
			inputTable.row();
			inputTable.add(messageField).width(300).height(200);

			bottomTable.add(confirmButton).width(100);
			bottomTable.add(leaveButton).width(100);

			Label doneLabel = new Label("Your bug report was sent!", Utilities.ACTUAL_UI_SKIN);

			confirmationTable.background(Utilities.ACTUAL_UI_SKIN.getDrawable("default-pane"));
			confirmationTable.setWidth(200);
			confirmationTable.setHeight(150);
			confirmationTable.add(doneLabel);
			confirmationTable.row();
			confirmationTable.add(okButton).width(100);
			confirmationTable.setPosition(300, 200);
			confirmationTable.setVisible(false);


			stage.addActor(inputTable);
			stage.addActor(bottomTable);
			stage.addActor(confirmationTable);

			setBugWindowVisible(false);
			
			logger.info("Report Bug Window instanced.");
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			try {
				CrashWriter cw = new CrashWriter(e);
				cw.writeCrash();
			} catch (IOException e1) {
				logger.error(e1.getMessage());
			}
		}
	}
	
	public void setBugWindowVisible(boolean isVisible) {
		inputTable.setVisible(isVisible);
		bottomTable.setVisible(isVisible);
	}
	
	public boolean isBugWindowVisible() {
		return inputTable.isVisible() || confirmationTable.isVisible();
	}
	
	public void render(float delta) {
		
		//When confirm button is clicked, if the message isnt empty, send an email to the provided address and show the confirmation table
		if (confirmButton.isPressed()) {
			if (!messageField.getText().equals("")) {
				sendMail();
				messageField.setText("");
				subjectField.setText("");
				setBugWindowVisible(false);
				confirmationTable.setVisible(true);
			}
		}
		
		//Leave button on report window pressed, set visibilit to false
		if (leaveButton.isPressed()) {
			logger.info("Left report bug window.");
			setBugWindowVisible(false);
		}
		
		//Ok button on confirmation windoe pressed, set visibility to false
		if (okButton.isPressed()) {
			logger.info("Confirm bug report window left.");
			confirmationTable.setVisible(false);
		}
		
		stage.draw();
		stage.act(delta);
	}
	
	public Stage getStage() {
		return stage;
	}
	
	public void resize(int width, int height) {
		stage.getViewport().update(width, height);
	}
	
	/**
	 * Sends an email to the given address given message text and subject text from users input
	 */
	public void sendMail() {
		
		//insecure email
		final String username = "bpagame54@gmail.com";
		final String password = "SuperPassword5";
		
		//Most of following code is from https://www.tutorialspoint.com/javamail_api/javamail_api_gmail_smtp_server.htm
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
	      props.put("mail.smtp.starttls.enable", "true");
	      props.put("mail.smtp.host", "smtp.gmail.com");
	      props.put("mail.smtp.port", "587");
	      props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
	      props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
	     
	      Session session = Session.getInstance(props, new Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(username, password);
	            }
	        });
	      
	      try {
	    	  Message message = new MimeMessage(session);
	    	  message.setFrom(new InternetAddress("bpagame54@gmail.com"));
	    	  message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("bpagame54@gmail.com"));
	    	  message.setSubject(subjectField.getText());
	    	  message.setText("Bug report from user: \n" + messageField.getText());
	    	  
	    	  Transport.send(message);
	    	  
	    	  logger.info("Bug report sent to email.");
	    	  
	      } catch (MessagingException e) {
	  			logger.error(e.getMessage());
	  			try {
	  				CrashWriter cw = new CrashWriter(e);
	  				cw.writeCrash();
	  				throw new RuntimeException(e);
	  			} catch (IOException e1) {
	  				logger.error(e1.getMessage());
	  			}	  			    	  
	      }
	}
	
}
