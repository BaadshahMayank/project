package model;

import java.io.File;
import java.io.IOException;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;
import it.sauronsoftware.jave.FFMPEGLocator;
import it.sauronsoftware.jave.InputFormatException;

public class VideoToAudio {

	static String path="C:\\Users\\RAMESH SONI\\Desktop\\target\\output.wav";
	static String pathpm3="C:\\Users\\RAMESH SONI\\Desktop\\target\\output3.mp3";
	public static void convertToAudio(File video) throws IllegalArgumentException, InputFormatException, EncoderException, IOException
	{   System.err.println("starting conversion");
		File target = new File(path);
		AudioAttributes audio = new AudioAttributes();
		audio.setCodec("libmp3lame");
		audio.setBitRate(new Integer(128000));
		audio.setChannels(new Integer(2));
		audio.setSamplingRate(new Integer(44100));
		EncodingAttributes attrs = new EncodingAttributes();
		attrs.setFormat("wav");
		attrs.setAudioAttributes(audio);
		Encoder encoder = new Encoder();
		encoder.encode(video, target, attrs);
		System.err.println("completed");
		
	}
	public static void convertToMP3(File video) throws IllegalArgumentException, InputFormatException, EncoderException
	{
		System.err.println("starting conversion");
		File target = new File(pathpm3);
		AudioAttributes audio = new AudioAttributes();
		audio.setCodec("libmp3lame");
		audio.setBitRate(new Integer(128000));
		audio.setChannels(new Integer(2));
		audio.setSamplingRate(new Integer(44100));
		EncodingAttributes attrs = new EncodingAttributes();
		attrs.setFormat("mp3");
		attrs.setAudioAttributes(audio);
		Encoder encoder = new Encoder();
		encoder.encode(video, target, attrs);
		System.err.println("completed");
	}
	
}

