package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomPhase;

public class TunnelCard extends DomCard {
    public TunnelCard () {
      super( DomCardName.Tunnel);
    }

    @Override
    public void doWhenDiscarded() {
    	if (owner.getCurrentGame().getActivePlayer().getPhase()!=DomPhase.CleanUp) {
    	    if (owner.isHumanOrPossessedByHuman()) {
                if (owner.getEngine().getGameFrame().askPlayer("<html>Gain " + DomCardName.Gold.toHTML() +" ?</html>", "Resolving " + this.getName().toString()))
                    if (owner.getCurrentGame().countInSupply(DomCardName.Gold)>0)
                        owner.gain(DomCardName.Gold);
            } else {
                if (owner.getCurrentGame().countInSupply(DomCardName.Gold) > 0)
                    owner.gain(DomCardName.Gold);
            }
        }
    }
    
    @Override
    public int getTrashPriority() {
      if (owner!=null && owner.wantsToGainOrKeep(DomCardName.Tunnel))
          return 39;
      return super.getTrashPriority();
    }
}