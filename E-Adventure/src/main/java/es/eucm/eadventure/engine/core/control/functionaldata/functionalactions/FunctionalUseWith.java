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
package es.eucm.eadventure.engine.core.control.functionaldata.functionalactions;

import es.eucm.eadventure.common.auxiliar.SpecialAssetPaths;
import es.eucm.eadventure.common.data.chapter.Action;
import es.eucm.eadventure.common.data.chapter.elements.NPC;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.engine.core.control.ActionManager;
import es.eucm.eadventure.engine.core.control.animations.Animation;
import es.eucm.eadventure.engine.core.control.animations.AnimationState;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalElement;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalItem;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalPlayer;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;

/**
 * The action to use an element with another
 */
public class FunctionalUseWith extends FunctionalAction {

    /**
     * The first element
     */
    private FunctionalElement element;

    /**
     * The second element
     */
    private FunctionalElement anotherElement;

    /**
     * The total elapsed time of the action
     */
    private long totalTime;

    /**
     * True if the element can be used with the other
     */
    private boolean canUseWith = false;

    /**
     * Constructor using the original action and the first element
     * 
     * @param action
     *            The original action
     * @param element
     *            The first element
     */
    public FunctionalUseWith( Action action, FunctionalElement element ) {

        super( );
        this.element = element;
        this.type = ActionManager.ACTION_USE_WITH;
        this.originalAction = element.getFirstValidAction( Action.USE_WITH );
        this.requiersAnotherElement = true;
        this.needsGoTo = false;
    }

    @Override
    public void drawAditionalElements( ) {

    }

    @Override
    public void setAnotherElement( FunctionalElement element ) {

        anotherElement = element;
        if( !anotherElement.isInInventory( ) && originalAction != null ) {
            this.needsGoTo = originalAction.isNeedsGoTo( );
            this.keepDistance = originalAction.getKeepDistance( );
        }
    }

    @Override
    public void start( FunctionalPlayer functionalPlayer ) {

        this.functionalPlayer = functionalPlayer;
        this.totalTime = 0;
        this.finished = false;

        Resources resources = functionalPlayer.getResources( );
        MultimediaManager multimedia = MultimediaManager.getInstance( );

        Animation[] animations = new Animation[ 4 ];
      
        if( resources.getAssetPath( NPC.RESOURCE_TYPE_USE_RIGHT ) != null && !resources.getAssetPath( NPC.RESOURCE_TYPE_USE_RIGHT ).equals( SpecialAssetPaths.ASSET_EMPTY_ANIMATION )
                && !resources.getAssetPath( NPC.RESOURCE_TYPE_USE_RIGHT ).equals( SpecialAssetPaths.ASSET_EMPTY_ANIMATION + ".eaa") )
            animations[AnimationState.EAST] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_USE_RIGHT ), false, MultimediaManager.IMAGE_PLAYER );
        else
            animations[AnimationState.EAST] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_USE_LEFT ), true, MultimediaManager.IMAGE_PLAYER );
         
        if( resources.getAssetPath( NPC.RESOURCE_TYPE_USE_LEFT ) != null && !resources.getAssetPath( NPC.RESOURCE_TYPE_USE_LEFT ).equals( SpecialAssetPaths.ASSET_EMPTY_ANIMATION )
                && !resources.getAssetPath( NPC.RESOURCE_TYPE_USE_LEFT ).equals( SpecialAssetPaths.ASSET_EMPTY_ANIMATION + ".eaa" ) )
            animations[AnimationState.WEST] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_USE_LEFT ), false, MultimediaManager.IMAGE_PLAYER );
        else
            animations[AnimationState.WEST] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_USE_RIGHT ), true, MultimediaManager.IMAGE_PLAYER );
        animations[AnimationState.NORTH] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_USE_RIGHT ), false, MultimediaManager.IMAGE_PLAYER );
        animations[AnimationState.SOUTH] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_USE_RIGHT ), true, MultimediaManager.IMAGE_PLAYER );

        if( functionalPlayer.getX( ) > element.getX( ) )
            functionalPlayer.setDirection( AnimationState.EAST );
        else
            functionalPlayer.setDirection( AnimationState.WEST );

        functionalPlayer.setAnimation( animations, -1 );
    }

    @Override
    public void stop( ) {

        finished = true;
    }

    @Override
    public void update( long elapsedTime ) {

        if( anotherElement != null ) {
            totalTime += elapsedTime;
            if( element instanceof FunctionalItem && anotherElement instanceof FunctionalItem ) {

                FunctionalItem item1 = (FunctionalItem) element;
                FunctionalItem item2 = (FunctionalItem) anotherElement;

                if( !finished && !canUseWith ) {
                    canUseWith = item1.useWith( item2 ) || item2.useWith( item1 );
                    if( !canUseWith ) {
                        finished = true;
                        functionalPlayer.popAnimation( );
                    }
                }
                else if( !finished && totalTime > 1000 ) {
                    functionalPlayer.popAnimation( );
                    finished = true;
                }
            }
            else
                finished = true;
        }
    }

    @Override
    public FunctionalElement getAnotherElement( ) {

        return anotherElement;
    }

    public FunctionalElement getElement( ) {
        return element;
    }

}
