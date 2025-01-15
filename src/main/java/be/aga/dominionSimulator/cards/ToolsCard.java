package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.*;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.HashSet;

public class ToolsCard extends DomCard {
    public ToolsCard() {
      super( DomCardName.Tools);
    }

    public void play() {
      ArrayList<DomCard> allCardsInPlay=new ArrayList<>();
      for (DomPlayer player:owner.getCurrentGame().getPlayers()) {
          allCardsInPlay.addAll(player.getCardsInPlay());
      }
      for (DomBuyRule rule:owner.getBuyRules()) {
          for (DomCard domCard : allCardsInPlay) {
              if (rule.getCardToBuy()==domCard.getName() && owner.checkBuyConditions(rule)) {
                  owner.gain(owner.getCurrentGame().takeFromSupply(domCard.getName()));
                  return;
              }
          }
      }
    }
}