/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J. (alphabetical order) *
 * @author L�pez Ma�as, E., P�rez Padilla, F., Sollet, E., Torijano, B. (former
 *         developers by alphabetical order)
 * @author Moreno-Ger, P. & Fern�ndez-Manj�n, B. (directors)
 * @year 2009 Web-site: http://e-adventure.e-ucm.es
 */

/*
 * Copyright (C) 2004-2009 <e-UCM> research group
 * 
 * This file is part of <e-Adventure> project, an educational game & game-like
 * simulation authoring tool, available at http://e-adventure.e-ucm.es.
 * 
 * <e-Adventure> is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * <e-Adventure>; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 */
package es.eucm.eadventure.engine.adaptation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import es.eucm.eadventure.comm.manager.commManager.CommManagerApi;
import es.eucm.eadventure.common.data.adaptation.AdaptationProfile;
import es.eucm.eadventure.common.data.adaptation.AdaptationRule;
import es.eucm.eadventure.common.data.adaptation.AdaptedState;
import es.eucm.eadventure.engine.core.control.FlagSummary;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.VarSummary;

/**
 * This class manages the adaptation engine, responsible of initializing the
 * flags in the game
 */
public class AdaptationEngine {

    public static final String INITIAL_STATE = "Initial";

    public static final String ADAPTATION_RULES = "Adaptation";

    /**
     * Adaptation data.
     */
    private AdaptedState initialAdaptedState;

    private List<AdaptationRule> externalAdaptationRules;

    private Set<String> externalPropertyNames;

    /**
     * Adaptation clock.
     */
    private AdaptationClock adaptationClock;

    /**
     * Loads the adaptation data.
     * 
     * @param adaptationPath
     *            Path of the file containing the adaptation data
     * @return the initial adapted state
     */
    @SuppressWarnings ( "unchecked")
    public AdaptedState init( AdaptationProfile adaptationProfile ) {

        //boolean inited = false;
        //if (adaptationPath!=null && !adaptationPath.equals("")){
        loadAdaptationProfile( adaptationProfile );
        //  inited = true;
        //} else {
        //if (initialAdaptedState==null ){

        //initialAdaptedState = new AdaptedState();

        //}

        if( externalAdaptationRules == null ) {
            externalAdaptationRules = new ArrayList<AdaptationRule>( );
        }
        //}

        // if(inited) {

        //Game.getInstance().setAdaptedStateToExecute(initialAdaptedState);
        // the inital state is returned at the end of method

        //}
        //System.out.println("antes de comprobar si esta en modo applet");
        //If we are an applet...
        if( Game.getInstance( ).isAppletMode( ) ) {
            //System.out.println("antes de comprobar si es Scorm");
            if( Game.getInstance( ).getComm( ).getCommType( ) == CommManagerApi.LD_ENVIROMENT_TYPE ) {
                //Process rules
                processLDRules( );
            }
            else if( ( Game.getInstance( ).getComm( ).getCommType( ) == CommManagerApi.SCORMV12_TYPE ) || Game.getInstance( ).getComm( ).getCommType( ) == CommManagerApi.SCORMV2004_TYPE ) {
                //Process rules
                //  System.out.println("Entramos a procesar reglas scorm");
                processSCORMRules( );

            }

        }
        return initialAdaptedState;

    }

    /**
     * Load the adaptation profile filling the initial adapted state and
     * external adaptation rules
     * 
     * @param adaptationProfile
     *            the adaptation profile
     */
    private void loadAdaptationProfile( AdaptationProfile adaptationProfile ) {

        //AdaptationProfile profile = Loader.loadAdaptationProfile( ResourceHandler.getInstance(), adaptationPath, new ArrayList<Incidence>() );  
        if( adaptationProfile != null ) {
            // add profile vars and flags to game vars and flags
            FlagSummary flags = Game.getInstance( ).getFlags( );
            VarSummary vars = Game.getInstance( ).getVars( );
            for( String flag : adaptationProfile.getFlags( ) ) {
                flags.addFlag( flag );
            }
            for( String var : adaptationProfile.getVars( ) ) {
                vars.addVar( var );
            }

            initialAdaptedState = adaptationProfile.getAdaptedState( );
            externalAdaptationRules = adaptationProfile.getRules( );
        }
    }

    /**
     * Process the adaptation rules for LD communication type.
     */
    private void processLDRules( ) {

        Game.getInstance( ).getComm( ).setAdaptationEngine( this );

        //Create a Set with all the properties that should be requested from the server
        externalPropertyNames = new HashSet<String>( );
        for( AdaptationRule rule : externalAdaptationRules ) {
            for( String name : rule.getPropertyNames( ) ) {
                externalPropertyNames.add( name );
            }
        }

        //Request an initial state and set the clock to ask again late
        requestNewState( );
        adaptationClock = new AdaptationClock( this );
        adaptationClock.start( );
    }

    /**
     * Process the adaptation rules for SCORM communication type.
     */
    private void processSCORMRules( ) {

        //System.out.println("Entramos en el sitio correcto en AssesmentEngine.init()");
        Set<String> properties = new HashSet<String>( );
        for( AdaptationRule rule : externalAdaptationRules ) {
            // get all property names, to search in LMS
            Set<String> propertyNames = rule.getPropertyNames( );
            for( String propertyName : propertyNames )
                properties.add( propertyName );
            //Search in LMS to get associated values
            Game.getInstance( ).getComm( ).getAdaptedState( properties );
            // Get the values
            HashMap<String, String> lmsInitialStates = Game.getInstance( ).getComm( ).getInitialStates( );
            Set<String> keys = lmsInitialStates.keySet( );
            // Comprobar que todas las propiedades se cumplen
            boolean runRule = true;
            Iterator<String> it = propertyNames.iterator( );
            while( runRule && it.hasNext( ) ) {
                //System.out.println("entramos en el bucle");
                String propertyName = it.next( );
                runRule = checkOperation( keys, lmsInitialStates, propertyName, rule );
            }
            if( runRule ) {
                // merge the adapted state, beca
                Game.getInstance( ).setAdaptedStateToExecute( rule.getAdaptedState( ) );
                //System.out.println("Se tendria que ejecutar la regla");
            }
        }
    }

    private boolean checkOperation( Set<String> keys, HashMap<String, String> lmsInitialStates, String propertyName, AdaptationRule rule ) {

        boolean runRule = true;
        try {

            if( keys.contains( propertyName ) ) {
                String op = rule.getPropertyOp( propertyName );
                if( op.equals( AdaptationProfile.EQUALS ) ) {
                    //System.out.println("Entramos a compara equals");
                    if( !lmsInitialStates.get( propertyName ).equals( rule.getPropertyValue( propertyName ) ) ) {
                        runRule = false;
                    }
                }
                else if( op.equals( AdaptationProfile.GRATER ) ) {
                    // the data get from LMS must be grater than the value 
                    //System.out.println("Entramos a compara grater");
                    if( Integer.parseInt( lmsInitialStates.get( propertyName ) ) <= Integer.parseInt( rule.getPropertyValue( propertyName ) ) ) {
                        runRule = false;
                    }
                }
                else if( op.equals( AdaptationProfile.GRATER_EQ ) ) {
                    // the data get from LMS must be grater or equals than the value 
                    //System.out.println("Entramos a compara grater equals");
                    if( Integer.parseInt( lmsInitialStates.get( propertyName ) ) < Integer.parseInt( rule.getPropertyValue( propertyName ) ) ) {
                        runRule = false;
                    }
                }
                else if( op.equals( AdaptationProfile.LESS ) ) {
                    // the data get from LMS must be less than the value 
                    //System.out.println("Entramos a compara less");
                    if( Integer.parseInt( lmsInitialStates.get( propertyName ) ) >= Integer.parseInt( rule.getPropertyValue( propertyName ) ) ) {
                        runRule = false;
                    }
                }
                else if( op.equals( AdaptationProfile.LESS_EQ ) ) {
                    // the data get from LMS must be less or equals than the value 
                    //System.out.println("Entramos a compara less equals");
                    if( Integer.parseInt( lmsInitialStates.get( propertyName ) ) > Integer.parseInt( rule.getPropertyValue( propertyName ) ) ) {
                        runRule = false;
                    }
                }
            }
        }
        catch( NumberFormatException e ) {
            System.out.println( "Error:try to use numeric comparator with a non numeric field " );
            runRule = false;
        }

        return runRule;
    }

    /**
     * Requests a new state from the server.
     */
    void requestNewState( ) {

        Game.getInstance( ).getComm( ).getAdaptedState( externalPropertyNames );
    }

    /**
     * Receives a new uol state as and checks whether it triggers an adaptation
     * rule. If so, the new state is applied inmediately.
     * 
     * @param uolState
     *            A Map containing the state of the external properties in the
     *            UoL
     */
    public synchronized void processExternalState( Map<String, String> uolState ) {

        for( AdaptationRule r : externalAdaptationRules ) {
            if( evaluate( r, uolState ) ) {
                //System.out.println("Rule triggered");
                Game.getInstance( ).setAdaptedStateToExecute( r.getAdaptedState( ) );

                //The UoL states should be defined to be mutually exclusive
                return;
            }
        }
    }

    private static boolean evaluate( AdaptationRule rule, Map<String, String> currentState ) {

        boolean activated = true;

        Iterator<String> keysIt = rule.getPropertyNames( ).iterator( );
        while( activated && keysIt.hasNext( ) ) {
            String key = keysIt.next( );
            try {
                //System.out.print("Comparing " + propertyInUoL + " with "+ propertyInRule);
                activated = currentState.get( key ).equals( rule.getPropertyValue( key ) );
                if( activated ) {
                    //System.out.println(" TRUE");
                }
                else {
                    //System.out.println(" FALSE");
                }
            }
            catch( NullPointerException e ) {
                throw new IllegalArgumentException( "The external state does not reflect all relevant properties: Property " + key + " not found." );
            }
        }
        if( activated ) {
            // System.out.println("Rule returns with TRUE");
        }
        else {
            // System.out.println("Rule returns with FALSE");
        }
        return activated;
    }

    /**
     * Stops the adaptation clock.
     */
    public void stopAdaptationClock( ) {

        // If there is a clock, stop it
        if( adaptationClock != null )
            adaptationClock.stopClock( );
    }

}
