package be.aga.dominionSimulator.gui;

import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import javax.swing.table.DefaultTableModel;

public class KingdomTableModel extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8672408093782296492L;
	private DomEngine anEngine;
    public KingdomTableModel(DomEngine anEngine) {
        super(25,2);
        this.anEngine = anEngine;
        update();
    }

	public void update() {
        int kingdomRowCount = 0;
        int nonKingdomRowCount=0;
        for (DomCardName theCard:anEngine.getCurrentGame().getBoard().keySet()) {
            if (theCard.hasCardType(DomCardType.Shelter) || theCard.hasCardType(DomCardType.Heirloom))
                continue;
            super.setValueAt(theCard,theCard.hasCardType(DomCardType.Kingdom)?kingdomRowCount++:nonKingdomRowCount++, theCard.hasCardType(DomCardType.Kingdom)?1:0);
        }
        kingdomRowCount++;
        for (DomCardName theCard:anEngine.getCurrentGame().getBoard().getSeperatePiles().keySet()) {
            if (theCard.hasCardType(DomCardType.Shelter))
                continue;
            super.setValueAt(theCard,kingdomRowCount++, 1);
        }
    }

    public boolean isCellEditable(int row, int cols)    {
        return false;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return DomCardName.class;
    }
}
