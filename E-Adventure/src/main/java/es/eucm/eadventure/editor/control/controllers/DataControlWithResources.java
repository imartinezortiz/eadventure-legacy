/*******************************************************************************
 * eAdventure (formerly <e-Adventure> and <e-Game>) is a research project of the e-UCM
 *          research group.
 *   
 *    Copyright 2005-2012 e-UCM research group.
 *  
 *     e-UCM is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *  
 *          C Profesor Jose Garcia Santesmases sn,
 *          28040 Madrid (Madrid), Spain.
 *  
 *          For more info please visit:  <http://e-adventure.e-ucm.es> or
 *          <http://www.e-ucm.es>
 *  
 *  ****************************************************************************
 * This file is part of eAdventure, version 1.5.
 * 
 *   You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with Adventure.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.control.controllers;

import java.util.List;

import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.editor.control.controllers.general.ResourcesDataControl;
import es.eucm.eadventure.editor.control.tools.general.assets.DeleteResourcesBlockTool;
import es.eucm.eadventure.editor.control.tools.general.assets.DuplicateResourcesBlockTool;

public abstract class DataControlWithResources extends DataControl {

    /**
     * List of resources.
     */
    protected List<Resources> resourcesList;

    /**
     * List of resources controllers.
     */
    protected List<ResourcesDataControl> resourcesDataControlList;

    /**
     * The resources that must be used in the previews.
     */
    protected int selectedResources;

    public List<ResourcesDataControl> getResources( ) {

        return resourcesDataControlList;
    }

    public int getResourcesCount( ) {

        return resourcesDataControlList.size( );
    }

    /**
     * Returns the last resources controller of the list.
     * 
     * @return Last resources controller
     */
    public ResourcesDataControl getLastResources( ) {

        return resourcesDataControlList.get( resourcesDataControlList.size( ) - 1 );
    }

    /**
     * Returns the selected resources block of the list.
     * 
     * @return Selected block of resources
     */
    public int getSelectedResources( ) {

        return selectedResources;
    }

    /**
     * Sets the new selected resources block of the list.
     * 
     * @param selectedResources
     *            New selected block of resources
     */
    public void setSelectedResources( int selectedResources ) {

        this.selectedResources = selectedResources;
    }

    @Override
    // This method only caters for deleting RESOURCES. Subclasses should override this method
    // to implement removal of other element types
    public boolean deleteElement( DataControl dataControl, boolean askConfirmation ) {

        return controller.addTool( new DeleteResourcesBlockTool( resourcesList, resourcesDataControlList, dataControl, this ) );
    }

    public boolean duplicateResources( DataControl dataControl ) {

        return controller.addTool( new DuplicateResourcesBlockTool( dataControl, resourcesList, resourcesDataControlList, this ) );
    }

}
