package es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects;

import es.eucm.eadventure.common.data.chapter.effects.DeactivateEffect;
import es.eucm.eadventure.engine.core.control.Game;

/**
 * An effect that deactivates a flag.
 */
public class FunctionalDeactivateEffect extends FunctionalEffect {

    /**
     * Creates a new FunctionalDeactivateEffect.
     */
    public FunctionalDeactivateEffect( DeactivateEffect effect ) {
    	super(effect);
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#triggerEffect()
     */
    public void triggerEffect( ) {
        Game.getInstance( ).getFlags( ).deactivateFlag( ((DeactivateEffect)effect).getTargetId() );
        Game.getInstance( ).updateDataPendingFromState( false );
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#isInstantaneous()
     */
    public boolean isInstantaneous( ) {
        return true;
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects.FunctionalEffect#isStillRunning()
     */
    public boolean isStillRunning( ) {
        return false;
    }
}