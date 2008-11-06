package es.eucm.eadventure.engine.adaptation;

import es.eucm.eadventure.engine.core.control.Game;

/**
 * Clock for the adaptation engine. 
 */
public class AdaptationClock extends Thread {
	
	/**
	 * Time elapsed for the tick.
	 */
	private static final int TICK_TIME = 20000;
	
	/**
	 * Adaptation engine to notify.
	 */
	private AdaptationEngine adaptationEngine;
	
	/**
	 * True if the thread is still running.
	 */
	private boolean run;
	
	/**
	 * Constructor.
	 * @param adaptationEngine Dialog to notify
	 */
	public AdaptationClock( AdaptationEngine adaptationEngine ) {
		this.adaptationEngine = adaptationEngine;
		run = true;
	}
	
	/**
	 * Stops the clock.
	 */
	public void stopClock( ) {
		run = false;
	}
	
	/*
	 * Explanation: The first state is requested in the adaptation engine 
     * while still disconnected. If by the first time that the thread awakes
     * there has been no response from the server, the API will be disconnected
     * and the thread dies.
     * 
	 */
	public void run( ) {
		while( run ) {
			try {
                sleep( TICK_TIME );
                if(Game.getInstance().isConnected()){
                    adaptationEngine.requestNewState( );
                } else {
                    run=false;
                }
			} catch( InterruptedException e ) {
				e.printStackTrace();
			}
		}
    }
}
