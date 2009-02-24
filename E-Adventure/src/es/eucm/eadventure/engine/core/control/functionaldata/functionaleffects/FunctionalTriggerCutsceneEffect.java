package es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects;

import es.eucm.eadventure.common.data.chapter.NextScene;
import es.eucm.eadventure.common.data.chapter.effects.TriggerCutsceneEffect;
import es.eucm.eadventure.engine.core.control.Game;

/**
 * An effect that triggers a cutscene
 */
public class FunctionalTriggerCutsceneEffect extends FunctionalEffect {

    /**
     * Creates a new TriggerCutsceneEffect
     * @param targetCutsceneId the id of the cutscene to be triggered
     */
    public FunctionalTriggerCutsceneEffect( TriggerCutsceneEffect effect ) {
    	super(effect);
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#triggerEffect()
     */
    public void triggerEffect( ) {
        if( Game.getInstance( ).getCurrentChapterData( ).isCutscene( ((TriggerCutsceneEffect)effect).getTargetId() ) ) {
            Game.getInstance( ).setNextScene( new NextScene( ((TriggerCutsceneEffect)effect).getTargetId() ) );
            Game.getInstance( ).setState( Game.STATE_NEXT_SCENE );
        }
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#isInstantaneous()
     */
    public boolean isInstantaneous( ) {
        return false;
    }
    
    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects.FunctionalEffect#isStillRunning()
     */
    public boolean isStillRunning( ) {
        return false;
    }
}