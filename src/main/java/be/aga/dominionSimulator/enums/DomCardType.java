package be.aga.dominionSimulator.enums;

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
    TrashForBenefit(false);

    private final boolean isLegalCardType;

    DomCardType(boolean isLegalCardType) {
        this.isLegalCardType = isLegalCardType;
    }

    public boolean isLegal() {
        return isLegalCardType;
    }
}