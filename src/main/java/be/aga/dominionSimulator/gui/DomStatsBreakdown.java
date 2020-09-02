package be.aga.dominionSimulator.gui;

import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import be.aga.dominionSimulator.stats.SeriesStats;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Map;

public class DomStatsBreakdown {

    private static final DecimalFormat FORMATTER = new DecimalFormat("#.0");

    private final JPanel statsPanel = new JPanel();

    public DomStatsBreakdown() {
        update(SeriesStats.EMPTY_SERIES_STATS);
    }

    public Component getStatsPanel() {
        return statsPanel;
    }

    public void update(SeriesStats seriesStats) {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout());
        topPanel.add(generateCardsTable(generatePilesTableModel(seriesStats), "Average cards in piles"));
        topPanel.add(generateCardsTable(generateTrashPileTableModel(seriesStats), "Average cards in the trash"));
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topPanel, generatePlayersPanel(seriesStats));
        split.setResizeWeight(0.5);

        statsPanel.removeAll();
        statsPanel.setLayout(new BorderLayout());
        statsPanel.add(split, BorderLayout.CENTER);
    }

    private Component generatePlayersPanel(SeriesStats seriesStats) {
        JPanel parent = new JPanel();
        parent.setLayout(new GridLayout());
        seriesStats.getAverageStatsForPlayers().forEach(player -> parent.add(generatePlayerPanel(player)));
        return parent;
    }

    private Component generatePlayerPanel(SeriesStats.AverageStatsForPlayer averageStatsForPlayer) {
        String playerText = averageStatsForPlayer.getName() + ": " + FORMATTER.format(averageStatsForPlayer.getAverageVictoryPoints()) + " VP average";
        return generateCardsTable(generatePlayerTableModel(averageStatsForPlayer), playerText);
    }

    private TableModel generatePlayerTableModel(SeriesStats.AverageStatsForPlayer averageStatsForPlayer) {
        return generateTableModel(averageStatsForPlayer.getAverageCardsInTheDeck(), "Card", "In the deck");
    }

    private TableModel generatePilesTableModel(SeriesStats seriesStats) {
        return generateTableModel(seriesStats.getAveragePiles(), "Pile", "Cards left");
    }

    private TableModel generateTrashPileTableModel(SeriesStats seriesStats) {
        return generateTableModel(seriesStats.getAverageTrashPile(), "Card", "In trash");
    }

    private TableModel generateTableModel(Map<DomCardName, Double> data, String firstColumnName, String secondColumnName) {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public Class<?> getColumnClass(int column) {
                return column == 0 ? DomCardName.class : Double.class;
            }
        };
        model.addColumn(firstColumnName);
        model.addColumn(secondColumnName);
        data.forEach((name, averageCards) -> model.addRow(new Object[] {name, averageCards}));
        return model;
    }

    private Component generateCardsTable(TableModel model, String caption) {
        JPanel parent = new JPanel();
        parent.setLayout(new BorderLayout(5, 5));
        parent.add(new JLabel(caption), BorderLayout.NORTH);
        JTable table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setDefaultRenderer(DomCardName.class, new DominionCardTableRowRenderer());
        table.setDefaultRenderer(Double.class, new DominionCardTableRowRenderer());
        table.getTableHeader().setReorderingAllowed(false);

        TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
        sorter.setSortKeys(Arrays.asList(
            new RowSorter.SortKey(1, SortOrder.DESCENDING),
            new RowSorter.SortKey(0, SortOrder.ASCENDING)
            ));
        table.setRowSorter(sorter);

        parent.add(new JScrollPane(table), BorderLayout.CENTER);
        return parent;
    }

    private static class DominionCardTableRowRenderer extends DefaultTableCellRenderer {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component component = super
                .getTableCellRendererComponent(table, value instanceof Double ? FORMATTER.format(value) : value, isSelected, hasFocus, row, column);
            component.setBackground(calculateRowColor((DomCardName) table.getValueAt(row, 0)));
            return component;
        }

        private Color calculateRowColor(DomCardName card) {
            if (card.hasCardType(DomCardType.Victory)) {
                return new Color(0xC1E4A5);
            } else if (card.hasCardType(DomCardType.Treasure)) {
                return new Color(0xFFDE2E);
            } else if (card.hasCardType(DomCardType.Night)) {
                return new Color(0x777777);
            } else if (card.hasCardType(DomCardType.Curse)) {
                return new Color(0xCCC4FC);
            } else {
                return Color.white;
            }
        }
    }
}
