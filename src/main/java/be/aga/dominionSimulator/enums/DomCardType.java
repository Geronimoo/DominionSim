package be.aga.dominionSimulator.enums;

/**
 * Enum with values representing various, non-exclusive card types.
 * <p>
 * Cards types are legal if the name of the type actually appears in the listing of types at the bottom of the card, or not legal otherwise.
 * ({@code Prize} is the only exception to the rule.)
 * <p>
 * Non-legal card types are useful for unofficial classification of cards, not rule implementation.
 */
public enum DomCardType {
    Kingdom(false),
    Event(true),
    Curse(true),
    Treasure(true),
    Victory(true),
    Prize(false),
    Action(true),
    Duration(true),
    Attack(true),
    Terminal(false),
    Cycler(false),
    Multiplier(false),
    Village(false),
    Trasher(false),
    Junk(false),
    Reaction(true),
    Card_Advantage(false),
    Looter(true),
    Ruins(true),
    Reserve(true),
    Traveller(true),
    Knight(true),
    Shelter(true),
    Base(false),
    DominionDevelopment(false),
    Gathering(true),
    Castle(true),
    Landmark(true),
    Split_Pile(false),
    Split_Pile_Bottom(false),
    TrashForBenefit(false),
    Night(true),
    Fate(true),
    Heirloom(true),
    Boon(true),
    Spirit(true),
    Hex(true),
    State(true),
    Doom(true),
    Project(true),
    Way(true),
    Ally(true),
    Liaison(true),
    NextTime(false),
    Loot(true),
    Prophecy(true),
    Omen(true),
    Command(true),
    Shadow(true);

    private final boolean isLegalCardType;

    /**
     * Sole constructor.
     * @param isLegalCardType boolean whether this is a legal type
     */
    DomCardType(boolean isLegalCardType) {
        this.isLegalCardType = isLegalCardType;
    }

    /**
     * Returns true iff this value is a card type that appears on the bottom of the card as part of the rules.
     */
    public boolean isLegal() {
        return isLegalCardType;
    }
}