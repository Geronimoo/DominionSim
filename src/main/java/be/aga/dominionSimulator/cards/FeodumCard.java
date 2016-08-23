package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class FeodumCard extends DomCard {
    public FeodumCard() {
      super( DomCardName.Feodum);
    }

    @Override
    public void doWhenTrashed() {
        for (int i=0;i<3;i++)
            owner.gain(DomCardName.Silver);
    }

    @Override
    public int getTrashPriority() {
      if (owner!=null && owner.wantsToGainOrKeep(DomCardName.Feodum))
          return 55;

      return super.getTrashPriority();
    }
}