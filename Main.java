package model;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Port;

import edu.cmu.sphinx.api.*;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
public class Main{
	private Logger logger = Logger.getLogger(getClass().getName());
    private String result;
    Thread speechThread;
    Thread resourcesThread;
    private LiveSpeechRecognizer recognizer;
    public Main() 
    {
    	logger.log(Level.INFO,"Loading..\n");
    	Configuration configuration = new Configuration();
    	configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
    	configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
    	configuration.setGrammarPath("resource:/grammars");
    	configuration.setUseGrammar(true);
    	try 
    	{
    		recognizer =new LiveSpeechRecognizer(configuration);
    	}
    	catch(IOException e) 
    	{
    		logger.log(Level.SEVERE,null,e);
    	}
    	recognizer.startRecognition(true);
    	startSpeechThread();
    	startResourcesThread();
    }
	protected void startSpeechThread() {
		
		if(speechThread!=null && speechThread.isAlive())
			return;
		speechThread = new Thread(() -> {
			logger.log(Level.INFO,"You can start to speak...\n");
			try {
				while(true) {
				SpeechResult speechResult = recognizer.getResult();
				if(speechResult!=null) {
	        	result = speechResult.getHypothesis();
	        	System.out.println("You said: ["+result+"]\n");
	            }
				else
				{logger.log(Level.INFO,"I can't understand what you said.\n");}
				}
				}
			catch(Exception e) 
			{
				logger.log(Level.WARNING,null,e);
			}
			logger.log(Level.INFO,"SpeechThread has exited...");
			});
		speechThread.start();
		}
		
		
	
	private void startResourcesThread() {
	if(resourcesThread!=null && resourcesThread.isAlive())
		return;
	resourcesThread = new Thread(() -> {
		
		try {
			while(true) {
			if(AudioSystem.isLineSupported(Port.Info.MICROPHONE)) {
        	
            }
			else
			{			}
			Thread.sleep(350);
			}
			}
		catch(InterruptedException e) 
		{
			logger.log(Level.WARNING,null,e);
			resourcesThread.interrupt();
		}
		//logger.log(Level.INFO,"SpeechThread has exited...");
		});
	resourcesThread.start();
	}
	public static void main(String args[]) {
		new Main();
		/*if("SPEECH".equalsIgnoreCase(args[0])) 
		{
			//args.length==1)&& 
			System.out.print("yeah");
			//new Main();	
		}
		else
		{
			Logger.getLogger(Main.class.getName()).log(Level.WARNING,"Give me the correct entry string..");
		}*/
	}
}