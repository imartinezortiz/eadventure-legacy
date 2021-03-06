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
package es.eucm.eadventure.editor.gui.structurepanel.structureelements.Effects;

import es.eucm.eadventure.common.gui.TC;

public class MainStructureListElement extends EffectsStructureListElement {

    private static final String LIST_URL = "effects_short/Effects_General.html";

    public MainStructureListElement( ) {

        super( TC.get( "EffectsGroup.Main" ) );
        //icon = EffectsStructurePanel.getEffectIcon(name, EffectsStructurePanel.ICON_SIZE_MEDIUM);
        //new ImageIcon( "img/icons/adaptationProfiles.png" );
        groupEffects = new String[] { TC.get( "Effect.Activate" ), TC.get( "Effect.Deactivate" ), TC.get( "Effect.SetValue" ), TC.get( "Effect.IncrementVar" ), TC.get( "Effect.DecrementVar" ), TC.get( "Effect.PlaySound" ), TC.get( "Effect.PlayAnimation" ), TC.get( "Effect.SpeakPlayer" ), TC.get( "Effect.SpeakCharacter" ), TC.get( "Effect.ShowText" ), TC.get( "Effect.TriggerConversation" ), TC.get( "Effect.TriggerScene" ), TC.get( "Effect.TriggerLastScene" ), TC.get( "Effect.TriggerCutscene" ), TC.get( "Effect.TriggerBook" ), TC.get( "Effect.ConsumeObject" ), TC.get( "Effect.GenerateObject" ), TC.get( "Effect.HighlightItem" ), TC.get( "Effect.MovePlayer" ), TC.get( "Effect.MoveCharacter" ),TC.get( "Effect.MoveObject" ), TC.get( "Effect.MacroReference" ), TC.get( "Effect.CancelAction" ), TC.get( "Effect.RandomEffect" ), TC.get( "Effect.WaitTime" ) };
        path = LIST_URL;
    }

}
