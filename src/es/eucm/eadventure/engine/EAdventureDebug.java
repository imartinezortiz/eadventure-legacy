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
package es.eucm.eadventure.engine;

import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.data.GameText;
import es.eucm.eadventure.engine.resourcehandler.ResourceHandler;
import es.eucm.eadventure.common.data.adventure.AdventureData;
import es.eucm.eadventure.common.loader.InputStreamCreator;
import es.eucm.eadventure.common.loader.Loader;

/**
 * This is the main class, when run standalone. Creates a new game and runs it.
 * 
 */
/**
 * @updated by Javier Torrente. New functionalities added - Support for .ead
 *          files. Therefore <e-Adventure> files are no longer .zip but .ead
 * @updated by Enrique L�pez. Functionalities added (10/2008) - Multilanguage
 *          support. Two new classes added
 */
public class EAdventureDebug {

    /**
     * Launchs a new e-Adventure game
     * 
     * @param args
     *            Arguments
     */
    public static void debug( AdventureData data, InputStreamCreator isCreator ) {

        Loader.setAdventureData( data );
        ResourceHandler.setExternalMode( isCreator );
        GameText.reloadStrings( );
        Game.create( true, true );
        Game.getInstance( ).setAdventureName( data.getTitle( ) );
        Game.getInstance( ).run( );
        Game.delete( );
        Loader.setAdventureData( null );
    }

    public static void normalRun( AdventureData data, InputStreamCreator isCreator ) {

        Loader.setAdventureData( data );
        ResourceHandler.setExternalMode( isCreator );
        GameText.reloadStrings( );
        Game.create( true, false );
        Game.getInstance( ).setAdventureName( data.getTitle( ) );
        Game.getInstance( ).run( );
        Game.delete( );
        Loader.setAdventureData( null );
    }
}
