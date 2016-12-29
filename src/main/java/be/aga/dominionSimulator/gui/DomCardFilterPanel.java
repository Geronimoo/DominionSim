package be.aga.dominionSimulator.gui;

import be.aga.dominionSimulator.enums.DomCardName;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

/**
 * A Panel that manages a selection of Kingdom cards.
 */
public class DomCardFilterPanel extends JPanel {

    private HashSet<DomCardName> mSelectedCards = new HashSet<DomCardName>();
    private JPanel mCardsPane = new JPanel(new WrapLayout(FlowLayout.LEFT));
    private CardsChangedListener mListener = null;

    public DomCardFilterPanel() {
        super(new GridBagLayout());

        setBorder(new TitledBorder("Card Filter"));
        final JComboBox cardsCB = new JComboBox(DomCardName.getKingdomCards());
        cardsCB.setBorder(new EmptyBorder(6, 0, 7, 0));
        cardsCB.setSelectedIndex(-1);

        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.gridx = 0;
        c.gridy = 0;
        add(cardsCB, c);

        c.gridx++;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(mCardsPane, c);

        cardsCB.addPopupMenuListener(new PopupMenuListener() {
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            }

            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                DomCardName cardName = (DomCardName) cardsCB.getSelectedItem();
                if (cardName != null) {
                    addCard(cardName);
                }
            }

            public void popupMenuCanceled(PopupMenuEvent e) {
            }
        });
    }

    public DomCardName[] getSelectedCards() {
        return mSelectedCards.toArray(new DomCardName[mSelectedCards.size()]);
    }

    public void addListener(CardsChangedListener l) {
        mListener = l;
    }

    private void cardsChanged() {
        revalidate();
        repaint();
        if (mListener != null) {
            mListener.cardsChanged(getSelectedCards());
        }
    }

    private void addCard(final DomCardName card) {
        if (mSelectedCards.contains(card)) {
            return;
        }
        mSelectedCards.add(card);
        final JButton cardButton = new JButton(card.toString());
        cardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mSelectedCards.remove(card);
                mCardsPane.remove(cardButton);
                cardsChanged();
            }
        });
        cardButton.setMargin(new Insets(-15, -15, -15, -15));
        mCardsPane.add(cardButton);
        cardsChanged();
    }

    public interface CardsChangedListener {
        void cardsChanged(DomCardName[] cards);
    }

}
