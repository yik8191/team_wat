package edu.teamWat.rhythmKnights.alpha.music;

/** 
 * Custom class for our own game music, which is generated from 
 * internal classes. 
 * 
 * Heavily based off the Music interface of LibGDX, but some methods 
 * are not relevant to us and we need some additional features. */
public class GameMusic {
	
	private Thread mthread; // thread to play music
	private GameTrackRunnable track; // runnable object for thread
	
	public GameMusic (){
		track = new GameTrackRunnable();
		mthread = new Thread(track);
	}
	
	/** Stops the track */
	public void stop(){
		
	}
	
	/** Starts the track */
	public void start(){

	}
	
	/** Pauses the track */
	public void pause(){
		
	}
	
	/** Set whether the track is looping 
	 * 
	 * @param isLooping - whether to loop the track*/
	public void setLooping (){
		
	}

	/** Sets the volume of this music stream. The volume must be given in the range [0,1] with 0 being silent and 1 being the
	 * maximum volume.
	 * 
	 * @param volume */
	public void setVolume (float volume){
		
	}

	/** @return the volume of this music stream. */
	public float getVolume (){
		return 0;
	}
	
	/** Set the playback position in seconds. */ 
	public void setPosition (float position){
		
	}
	
	/** Returns the playback position in seconds. */
	public float getPosition (){
		return 0;
	}

	
	
}
