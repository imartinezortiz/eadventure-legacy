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
package es.eucm.eadventure.editor.control.controllers.general;

import java.util.List;

import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.Searchable;
import es.eucm.eadventure.editor.control.controllers.globalstate.GlobalStateListDataControl;
import es.eucm.eadventure.editor.control.controllers.macro.MacroListDataControl;
import es.eucm.eadventure.editor.control.controllers.timer.TimersListDataControl;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class AdvancedFeaturesDataControl extends DataControl {

    private TimersListDataControl timersListDataControl;

    private GlobalStateListDataControl globalStatesListDataControl;

    private MacroListDataControl macrosListDataControl;

    /**
     * Constructor.
     * 
     * @param scene
     *            Contained scene data
     */
    public AdvancedFeaturesDataControl( ) {

    }

    @Override
    public Object getContent( ) {

        return null;
    }

    @Override
    public int[] getAddableElements( ) {

        return new int[] { /*Controller.RESOURCES*/};
    }

    @Override
    public boolean canAddElement( int type ) {

        return false;
    }

    @Override
    public boolean canBeDeleted( ) {

        return false;
    }

    @Override
    public boolean canBeMoved( ) {

        return false;
    }

    @Override
    public boolean canBeRenamed( ) {

        return false;
    }

    @Override
    public boolean addElement( int type, String id ) {

        return false;
    }

    @Override
    public boolean deleteElement( DataControl dataControl, boolean askConfirmation ) {

        return false;
    }

    @Override
    public boolean moveElementUp( DataControl dataControl ) {

        return false;
    }

    @Override
    public boolean moveElementDown( DataControl dataControl ) {

        return false;
    }

    @Override
    public String renameElement( String name ) {

        return null;
    }

    @Override
    public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {

        timersListDataControl.updateVarFlagSummary( varFlagSummary );
        globalStatesListDataControl.updateVarFlagSummary( varFlagSummary );
        macrosListDataControl.updateVarFlagSummary( varFlagSummary );
    }

    @Override
    public boolean isValid( String currentPath, List<String> incidences ) {

        boolean valid = true;

        valid &= timersListDataControl.isValid( currentPath, incidences );
        valid &= globalStatesListDataControl.isValid( currentPath, incidences );
        valid &= macrosListDataControl.isValid( currentPath, incidences );

        return valid;
    }

    @Override
    public int countAssetReferences( String assetPath ) {

        int count = 0;

        count += timersListDataControl.countAssetReferences( assetPath );
        count += globalStatesListDataControl.countAssetReferences( assetPath );
        count += macrosListDataControl.countAssetReferences( assetPath );

        return count;
    }

    @Override
    public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {

        timersListDataControl.getAssetReferences( assetPaths, assetTypes );
        globalStatesListDataControl.getAssetReferences( assetPaths, assetTypes );
        macrosListDataControl.getAssetReferences( assetPaths, assetTypes );
    }

    @Override
    public void deleteAssetReferences( String assetPath ) {

        timersListDataControl.deleteAssetReferences( assetPath );
        globalStatesListDataControl.deleteAssetReferences( assetPath );
        macrosListDataControl.deleteAssetReferences( assetPath );
    }

    @Override
    public int countIdentifierReferences( String id ) {

        int count = 0;

        count += timersListDataControl.countIdentifierReferences( id );
        count += globalStatesListDataControl.countIdentifierReferences( id );
        count += macrosListDataControl.countIdentifierReferences( id );

        return count;
    }

    @Override
    public void replaceIdentifierReferences( String oldId, String newId ) {

        timersListDataControl.replaceIdentifierReferences( oldId, newId );
        globalStatesListDataControl.replaceIdentifierReferences( oldId, newId );
        macrosListDataControl.replaceIdentifierReferences( oldId, newId );
    }

    @Override
    public void deleteIdentifierReferences( String id ) {

        timersListDataControl.deleteIdentifierReferences( id );
        globalStatesListDataControl.deleteIdentifierReferences( id );
        macrosListDataControl.deleteIdentifierReferences( id );

    }

    @Override
    public boolean canBeDuplicated( ) {

        return true;
    }

    @Override
    public void recursiveSearch( ) {

        this.getMacrosListDataControl( ).recursiveSearch( );
        this.getGlobalStatesListDataControl( ).recursiveSearch( );
        this.getTimersList( ).recursiveSearch( );
    }

    public void setTimerListDataControl( TimersListDataControl timersList ) {

        this.timersListDataControl = timersList;
    }

    public void setGlobalStatesListDataContorl( GlobalStateListDataControl globalStatesListDataControl ) {

        this.globalStatesListDataControl = globalStatesListDataControl;
    }

    public void setMacrosListDataControl( MacroListDataControl macrosListDataControl ) {

        this.macrosListDataControl = macrosListDataControl;
    }

    public TimersListDataControl getTimersList( ) {

        return timersListDataControl;
    }

    public GlobalStateListDataControl getGlobalStatesListDataControl( ) {

        return globalStatesListDataControl;
    }

    public MacroListDataControl getMacrosListDataControl( ) {

        return macrosListDataControl;
    }

    @Override
    public List<Searchable> getPathToDataControl( Searchable dataControl ) {

        List<Searchable> path = getPathFromChild( dataControl, globalStatesListDataControl );
        if( path != null )
            return path;
        path = getPathFromChild( dataControl, timersListDataControl );
        if( path != null )
            return path;
        return getPathFromChild( dataControl, macrosListDataControl );
    }

}
