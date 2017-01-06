package be.aga.dominionSimulator.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

import javax.swing.*;

import org.jfree.ui.RefineryUtilities;
import org.xml.sax.InputSource;

import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomBotType;

public class DomGui extends JFrame implements ActionListener {
  private DomEngine myEngine;
  private ArrayList< JButton > myBotSelectors= new ArrayList< JButton>();
  private DomBarChart myBarChart;
  private DomLineChart myVPLineChart;
  private DomLineChart myMoneyLineChart;
  private HashMap< JButton, ButtonGroup > myStartStateButtonGroups = new HashMap< JButton, ButtonGroup >();
  private HashMap< JButton, JButton > myEditCreateButtons = new HashMap<JButton, JButton>();
  private HashMap< JButton, JButton > myCopyPasteButtons = new HashMap<JButton, JButton>();
  private HashMap< JButton, JButton > myColonizeButtons = new HashMap<JButton, JButton>();
  private JCheckBox myOrderBox;
  private JButton myEditedSelector;
  private HashMap< JComponent, JComponent> myWinPercentageLBLs=new HashMap<JComponent, JComponent>();
  private JLabel myTiesLBL=new JLabel("");
  private JLabel myAverageTurnsLBL=new JLabel("");
  private JLabel myTimeLBL=new JLabel("");
  private JLabel my3EmptyPilesLBL=new JLabel("");
  private JSplitPane myTopSplit;
  private JSplitPane myBigSplit;
  private JSplitPane myBottomSplit;
  private HashMap<DomPlayer, JButton> myPlayers = new HashMap<DomPlayer, JButton>();

	public DomGui(DomEngine anEngine)	{
      myEngine=anEngine;
//      new DomGameSetup(myEngine, null);
//      new DomGameFrame(myEngine);
//      if (1==1)return;
	  initializeComponent();
      setGlassPane(new DomGlassPane());
      setTitle("Geronimoo's Dominion Simulator");
      setPreferredSize(RefineryUtilities.getMaximumWindowBounds().getSize());
      pack();
      addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
            shutDown();
        }
      });
      if (DomEngine.hideGraphs)
        hideGraphs();
	}

	private void shutDown() {
        myEngine.saveCurrentUserBots();
        System.exit( 0 );
    }

	private void hideGraphs() {
	  Runnable setMinSize = new Runnable() {
	    public void run() {
	      myTopSplit.remove(1);
	      myBigSplit.remove(1);
	  	  setPreferredSize(new Dimension(650,550));
	  	  pack();
	    }
	  };
	  SwingUtilities.invokeLater( setMinSize );
	}

	private void initializeComponent()	{
	  setJMenuBar( createMenu() );
	  
      myVPLineChart = new DomLineChart(myEngine.getPlayers(), "VP");
	  myBarChart = new DomBarChart(myEngine.getPlayers());
      myMoneyLineChart = new DomLineChart(myEngine.getPlayers(), "Money");
 
      myTopSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, new JScrollPane(getControlPanel()), myVPLineChart.getChartPanel());
      myBottomSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, myBarChart.getChartPanel(), myMoneyLineChart.getChartPanel());
	  myBigSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, myTopSplit, myBottomSplit);
      myBigSplit.setResizeWeight(0.54);
      myTopSplit.setResizeWeight(0.50);
      myBottomSplit.setResizeWeight(0.5);
      myBottomSplit.setDividerSize(5);
      myBigSplit.setDividerSize(5);
      myTopSplit.setDividerSize(5);

	  add(myBigSplit);
	}

	private JButton getSampleGameButton() {
       JButton theBTN = new JButton("Sample game");
       theBTN.setMnemonic('S');
       theBTN.setActionCommand("Start sample game");
       theBTN.addActionListener( this );
       return theBTN;
	}

    private JPanel getControlPanel() {
        final JPanel thePanel = new JPanel();
        thePanel.setLayout( new GridBagLayout() );
	    GridBagConstraints theCons = getGridBagConstraints( 3 );
	    JButton theBTN;
	
	    for (int i=1;i<5;i++) {
	      //most components will be linked to the select-button
	      JButton theBotSelector = getNewBotSelector();
	      //the win percentage label
	      theCons.gridx=0;
	      theCons.gridy+=2;
	      theCons.gridheight=1;
	      thePanel.add( new JLabel("Player " + i) , theCons);
	      theCons.gridy++;
	      thePanel.add( getWinPercentageLBL(theBotSelector) , theCons);
	      //the bot selector
	      theCons.gridx++;
	      theCons.gridy--;
	      theCons.gridheight=2;
	      theCons.weightx=10;
	      thePanel.add( theBotSelector , theCons);
	      theCons.weightx=1;
	      //the start state radio buttons (4/3 start, 5/2 start)
	      theCons.gridheight=1;
	      ButtonGroup theBTNGroup = getRadioBTNsForStartState();
	      myStartStateButtonGroups.put(theBotSelector, theBTNGroup);
	      for (Enumeration< AbstractButton > theEnum = theBTNGroup.getElements();theEnum.hasMoreElements();) {
	        theCons.gridx++;
	        thePanel.add( theEnum.nextElement(), theCons);
	      }
          //the edit/create button
	      theCons.gridx=2;
	      theCons.gridy++;
	      thePanel.add( getEditCreateButton(theBotSelector), theCons);
          //the copy/paste button
	      theCons.gridx++;
	      thePanel.add(getCopyPasteButton(theBotSelector), theCons);
          //the colonize button
	      theCons.gridx++;
	      thePanel.add(getColonizeButton(theBotSelector), theCons);
	    }
  	    myBotSelectors.get(0).requestFocus();
        //check box to determine player order 	
	    myOrderBox = new JCheckBox("Keep this player order for all games");
	    myOrderBox.setToolTipText("If unchecked, the players will be moved to a new random position for each game");
	    myOrderBox.setMnemonic('K');
	    theCons.gridx=0;
	    theCons.gridy++;
	    theCons.gridwidth=2;
	    thePanel.add(myOrderBox, theCons);
        //more buttons to play with
	    theCons.gridx=0;
	    theCons.gridy++;
	    theCons.gridwidth=2;
	    thePanel.add(getSampleGameButton(), theCons);
        //a label to indicate the 3 empty piles endings
	    theCons.gridx=2;
	    theCons.gridy--;
	    theCons.gridwidth=6;
	    my3EmptyPilesLBL.setForeground(Color.red);
	    thePanel.add(my3EmptyPilesLBL, theCons);
        //a label to indicate the time spent simulating
	    theCons.gridy++;
	    thePanel.add(myTimeLBL, theCons);
        //a label to indicate the number of ties
	    theCons.gridx=0;
	    theCons.gridy++;
	    myTiesLBL.setForeground(Color.red);
	    thePanel.add(myTiesLBL, theCons);
        //a button for quick simulation
	    theBTN = new JButton("Quick Simulation (1000 games)");
	    theBTN.setMnemonic('Q');
	    theBTN.setActionCommand("Start Fast Simulation");
	    theBTN.addActionListener( this );
	    theCons.gridx=2;
	    theCons.gridwidth=6;
	    thePanel.add(theBTN, theCons);
	    //a label to indicate average number of turns
	    theCons.gridx=0;
	    theCons.gridy++;
	    theCons.gridwidth=2;
	    myAverageTurnsLBL.setForeground(Color.red);
	    thePanel.add(myAverageTurnsLBL, theCons);
        //a button for accurate simulation
	    theBTN = new JButton("Accurate Simulation (10000 games)");
	    theBTN.setMnemonic('A');
	    theBTN.setActionCommand("Start Accurate Simulations");
	    theBTN.addActionListener( this );
	    theCons.gridx=2;
	    theCons.gridwidth=6;
	    thePanel.add(theBTN, theCons);
        //a button to play against the sim
        theCons.gridx=0;
        theCons.gridy++;
        theCons.gridwidth=2;
        theBTN = new JButton("Play against the bot(s)");
        theBTN.setMnemonic('P');
        theBTN.setActionCommand("Play against bots");
        theBTN.addActionListener( this );
//        thePanel.add(theBTN, theCons);
        //a button for ultimate simulation
	    theBTN = new JButton("Ultimate Simulation (100000 games)");
	    theBTN.setMnemonic('U');
	    theBTN.setActionCommand("Start Ultimate Simulations");
	    theBTN.addActionListener( this );
        theCons.gridx=2;
        theCons.gridwidth=6;
	    thePanel.add(theBTN, theCons);
	    //allow "tab"-key to travel between the bot selectors
	    for (Component component : thePanel.getComponents()){
	      if (myBotSelectors.indexOf(component)>=0)
	        component.setFocusable(true);
	      else
	    	component.setFocusable(false);
	    }

	    return thePanel;
    }

	private JButton getNewBotSelector() {
		JButton theBotSelector = new JButton() {
			@Override
			public String getToolTipText() {
				if (getSelectedPlayer(this)!=null) {
			      String theDescription = getSelectedPlayer(this).getDescription().replaceAll("XXXX", "<br>");
			      return "<html>"+theDescription+"</html>";
				}
 		        return super.getToolTipText();
			}
		};
		clearSelectionButton(theBotSelector);
	    theBotSelector.addActionListener(this);
	    theBotSelector.setActionCommand("Select");
	    theBotSelector.setToolTipText("Select a strategy based on buy rules");
	    myBotSelectors.add(theBotSelector);
		return theBotSelector;
	}

	private Component getCopyPasteButton(JButton theBotSelector) {
	  JButton theBTN=myCopyPasteButtons.get(theBotSelector);
	  if (theBTN==null) {
        theBTN = new JButton("Copy/Paste");
        theBTN.setToolTipText("Exchange this strategy with others, by copying/pasting the strategy in XML form");
        theBTN.addActionListener(this);
        theBTN.setActionCommand("CopyPaste");
        myCopyPasteButtons.put(theBotSelector, theBTN);
        myCopyPasteButtons.put(theBTN, theBotSelector);
	  }
	  return theBTN;
	}

	private Component getColonizeButton(JButton theBotSelector) {
	  JButton theBTN=myColonizeButtons.get(theBotSelector);
	  if (theBTN==null) {
        theBTN = new JButton("Colonize");
        theBTN.setToolTipText("Colony and Platinum will be added to the bot's buy rules");
        theBTN.addActionListener(this);
        theBTN.setActionCommand("Colonize");
        myColonizeButtons.put(theBotSelector, theBTN);
        myColonizeButtons.put(theBTN, theBotSelector);
	  }
	  return theBTN;
	}

	private JButton getEditCreateButton(JButton theBotSelector) {
	  JButton theBTN=myEditCreateButtons.get(theBotSelector);
	  if (theBTN==null) {
		  theBTN=new JButton("Edit/Create");
	      theBTN.setToolTipText("Edit or create a strategy from an existing strategy, or if nothing selected, from scratch");
	      theBTN.setMnemonic(theBTN.getText().charAt(myBotSelectors.indexOf(theBotSelector)));
	      theBTN.setActionCommand( "EditCreate");
	      theBTN.addActionListener( this );
	      myEditCreateButtons.put(theBTN, theBotSelector);
	      myEditCreateButtons.put(theBotSelector, theBTN);
	  }
      return theBTN;
	}

	private JLabel getWinPercentageLBL(JButton theBotSelector) {
		JLabel theLBL = (JLabel) myWinPercentageLBLs.get(theBotSelector);
		if (theLBL==null) {
	      theLBL=new JLabel();
	      theLBL.setForeground(Color.red);
	      myWinPercentageLBLs.put(theBotSelector,theLBL);
	      myWinPercentageLBLs.put(theLBL, theBotSelector);
	    }
		return theLBL;
	}

    private ButtonGroup getRadioBTNsForStartState() {
      ButtonGroup theStartStateRBTNGroup = new ButtonGroup();
      JRadioButton theBTN = new JRadioButton("random start");
      theBTN.setSelected( true );
      theStartStateRBTNGroup.add(theBTN);
      theStartStateRBTNGroup.add(new JRadioButton("4/3 start"));
      theStartStateRBTNGroup.add(new JRadioButton("5/2 start"));
      return theStartStateRBTNGroup;
    }

    @Override
    public void actionPerformed( ActionEvent aE ) {
      if (aE.getActionCommand().equals( "Select" )) {
    	myEditedSelector=(JButton) aE.getSource();
    	new DomBotSelector(myEngine, getSelectedPlayer((JButton) aE.getSource()));
      }
      if (aE.getActionCommand().startsWith( "Start" )) {
        if (!aE.getActionCommand().startsWith( "Start sample" )) 
    	  for (JButton button : myBotSelectors)
    		((JLabel) myWinPercentageLBLs.get(button)).setText("");
        startSimulation( aE );
        if (aE.getActionCommand().startsWith( "Start sample" )) {
          showSampleGame();
        }
      }
      if (aE.getActionCommand().startsWith("Play against bots")) {
          new PlayPreparationDialog(myEngine);
      }
      if (aE.getActionCommand().equals( "About" )) {
	    JOptionPane.showMessageDialog(this, getAboutPanel(), "About", JOptionPane.PLAIN_MESSAGE);
      }
      if (aE.getActionCommand().equals("DevMode")) {
          AbstractButton aButton = (AbstractButton) aE.getSource();
          boolean selected = aButton.getModel().isSelected();
          if (selected) {
              if (!"dxv".equals(JOptionPane.showInputDialog("Development Password"))) {
                  aButton.getModel().setSelected(false);
                  JOptionPane.showMessageDialog(this,"Wrong Password!", "Error", JOptionPane.ERROR_MESSAGE);
              }
              else {
                  DomEngine.developmentMode = true;
              }
          } else {
              DomEngine.developmentMode = false;
          }
      }
      if (aE.getActionCommand().equals( "WebHelp" )) {
    	  try {
			Desktop.getDesktop().browse(new URI("http://dominionsimulator.wordpress.com"));
		  } catch (Exception e) {
	        JOptionPane.showMessageDialog(this, "Can not open web site!", "", JOptionPane.ERROR_MESSAGE);
		  }
      }
      if (aE.getActionCommand().equals( "EditCreate" )) {
        myEditedSelector=myEditCreateButtons.get(aE.getSource());
        DomPlayer theStrategy;
		if (getSelectedPlayer(myEditedSelector) == null ) {
          theStrategy = new DomPlayer("A New Strategy");
        } else {
          theStrategy = getSelectedPlayer(myEditedSelector);
        }
        new DomBotEditor( myEngine, theStrategy);
      }
      if (aE.getActionCommand().equals( "CopyPaste" )) {
        myEditedSelector=myCopyPasteButtons.get(aE.getSource());
        DomPlayer theStrategy = getSelectedPlayer(myEditedSelector);
        if (theStrategy==null) {
          DomPlayer theNewPlayer = myEngine.loadUserBotsFromXML(new InputSource(new StringReader(getClipboard())));
          if (theNewPlayer!=null)
            refreshBotSelectors(theNewPlayer);
        } else {
          setClipboard(theStrategy.getXML());
          JOptionPane.showMessageDialog(this, "XML-code has been loaded in the clipboard (to share on the internet, via mail...)", "", JOptionPane.INFORMATION_MESSAGE);
        }
      }
      if (aE.getActionCommand().startsWith( "Save" )) {
    	myEngine.saveUserBots();
      }
      if (aE.getActionCommand().startsWith( "Load" )) {
        myEngine.loadUserBots();
        myEngine.orderBots();
        refreshBotSelectors(null);
      }
      if (aE.getActionCommand().startsWith( "Colonize" )) {
          myEditedSelector=myColonizeButtons.get(aE.getSource());
          if (getSelectedPlayer(myEditedSelector) == null)
        	  return; 
          DomPlayer theSelectedBot = getSelectedPlayer(myEditedSelector);
          refreshBotSelectors(colonize(theSelectedBot));
      }
    }

	 private DomPlayer getSelectedPlayer(JButton theSelector) {
		String theName = theSelector.getText();
		if (theName==null || theName.equals(""))
			return null;
		for (Object player : myEngine.getBotArray()){
			if (((DomPlayer)player).toString().equals(theName) )
			  return (DomPlayer)player;
		}
		return null;
	}

	 // If a string is on the system clip board, this method returns it;
	 // otherwise it returns null.
	 public String getClipboard() {
	     Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
	     try {
	         if (t != null && t.isDataFlavorSupported(DataFlavor.stringFlavor) ) {
	             return (String)t.getTransferData(DataFlavor.stringFlavor);
	         } else {
	        	 JOptionPane.showMessageDialog(this, "Your clipboard does not contain a valid XML!", "", JOptionPane.ERROR_MESSAGE);
	         }
	     } catch (Exception e) {
	    	JOptionPane.showMessageDialog(this, "Your clipboard does not contain a valid XML!", "", JOptionPane.ERROR_MESSAGE);
		 }
	     return null;
	 }
	
	 // This method writes a string to the system clip board
	 public void setClipboard(String str) {
	   StringSelection ss = new StringSelection(str);
	   Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
	 }

	private void showSampleGame() {
		File tempfile;
		try {
		  tempfile = File.createTempFile("SimulatorSampleGame", ".html");
		  BufferedWriter out = new BufferedWriter(new FileWriter(tempfile));
		  out.write("<FONT size=2 face=\"Arial\">"+ DomEngine.myLog);
		  out.close();
		  Desktop.getDesktop().open(tempfile);
		  tempfile.deleteOnExit();
		} catch (IOException e) {
		  JOptionPane.showMessageDialog(this, getSampleGamePanel(), "Sample Game", JOptionPane.PLAIN_MESSAGE);
		}
	}

    private DomPlayer colonize(DomPlayer theSelectedBot) {
       if (theSelectedBot.hasType(DomBotType.Colony))
    	   return theSelectedBot;
       DomPlayer theNewPlayer = theSelectedBot.getColonyCopy(theSelectedBot.toString() + "(Col)");
       theNewPlayer.addType(DomBotType.UserCreated);
       myEngine.addUserBot(theNewPlayer);
       return theNewPlayer;
	}

	private JPanel getAboutPanel() {
        JPanel theAboutPanel = new JPanel( new GridBagLayout() );
        GridBagConstraints theCons = getGridBagConstraints( 2 );
        JLabel lab = new JLabel( new ImageIcon( getClass().getResource("images/Godctoon.gif" ) ) );
        lab.setOpaque( false );
        theAboutPanel.add( lab, theCons );
        theCons.gridy++;
        JTextArea a = new JTextArea( 250, 260 );
        a.setText( "\n Geronimoo's Dominion Simulator \n\n"
            + " Version:\n      1.2.14\n" + " Written by:\n      Jeroen Aga\n"
            + " Released:\n      january 2017\n\n"
            + " Website:\n      http://dominionsimulator.wordpress.com\n" 
            + " Report bugs/Sing Praise:\n      jeroen_aga@yahoo.com\n" );
        theAboutPanel.add( a, theCons );
        theAboutPanel.setPreferredSize(new Dimension(250,260));
        return theAboutPanel;
	}

	/**
     * @param aE
     */
    private void startSimulation( ActionEvent aE ) {
          myPlayers = new HashMap<DomPlayer, JButton>();
          ArrayList<DomPlayer> thePlayers = new ArrayList<DomPlayer>(); 
          for (JButton theSelector : myBotSelectors ) {
            if (getSelectedPlayer(theSelector) == null) 
              continue;
            DomPlayer theSelectedBot = getSelectedPlayer(theSelector);
            DomPlayer thePlayer = theSelectedBot.getCopy( theSelectedBot + "(Plr " + (myBotSelectors.indexOf( theSelector )+1) + ")" );
            int j=0;
            for ( Enumeration< AbstractButton > theEnum = myStartStateButtonGroups.get( theSelector ).getElements();theEnum.hasMoreElements();j++) {
              if (theEnum.nextElement().isSelected() ){
                if (j==1) 
                  thePlayer.forceStart( 43 );
                if (j==2) 
                  thePlayer.forceStart( 52 );
              }
            }
            myPlayers.put(thePlayer, theSelector);
            thePlayers.add(thePlayer);
          }
          if (!myPlayers.isEmpty()) {
            int theNumber=0;
            boolean showLog = false;
            if (aE.getActionCommand().startsWith( "Start Fast" ))
              theNumber=1000;
            if (aE.getActionCommand().startsWith( "Start Accurate" ))
                theNumber=10000;
            if (aE.getActionCommand().startsWith( "Start Ultimate" ))
                theNumber=100000;
            if (aE.getActionCommand().startsWith( "Start sample" )){
              theNumber=1;
              showLog=true;
            }
            setCursor( new Cursor( Cursor.WAIT_CURSOR ) );
            myEngine.startSimulation(thePlayers, myOrderBox.isSelected(), theNumber, showLog);
            setCursor( new Cursor( Cursor.DEFAULT_CURSOR) );
          }
    }

    public static GridBagConstraints getGridBagConstraints( int anInset ) {
      GridBagConstraints theCons = new GridBagConstraints();
      theCons.insets = new Insets( anInset, anInset, anInset, anInset );
      theCons.gridx = 0;
      theCons.gridy = 0;
      theCons.fill=GridBagConstraints.BOTH;
      theCons.anchor=GridBagConstraints.NORTHWEST;
      theCons.weightx=1;
      return theCons;
    }

    public void setBarChart( DomBarChart aChart ) {
      myBarChart.getChartPanel().setChart(aChart.getChartPanel().getChart());       
    }

    public void setVPLineChart( DomLineChart aChart ) {
      myVPLineChart.getChartPanel().setChart(aChart.getChartPanel().getChart());       
    }

    /**
     * @param aChart
     */
    public void setMoneyLineChart( DomLineChart aChart ) {
        myMoneyLineChart.getChartPanel().setChart(aChart.getChartPanel().getChart());       
    }

    public JPanel getSampleGamePanel() {
      final JPanel thePanel = new JPanel( new GridBagLayout() );
      final GridBagConstraints theCons = getGridBagConstraints( 2 );
      final JTextPane a = new JTextPane();
      a.setContentType("text/html");
      final JScrollPane scr = new JScrollPane( a );
      scr.setPreferredSize( new Dimension( 700, 500 ) );
      scr.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
      scr.getVerticalScrollBar().setUnitIncrement(24);
      thePanel.add( scr, theCons );
      a.setText( "<FONT size=3 face=\"Arial\">"+ DomEngine.myLog );
      Runnable doScroll = new Runnable() {
        public void run() {
          scr.getVerticalScrollBar().setValue( 0 );
        }
      };
      SwingUtilities.invokeLater( doScroll );
      return thePanel;
   }

	public void refreshBotSelectors(DomPlayer theNewPlayer) {
		for (JButton button : myBotSelectors){
			if (!myEngine.doesBotExist(getSelectedPlayer(button)))
				clearSelectionButton(button);
		}
		if (myEditedSelector==null)
			return;
        if (theNewPlayer!=null){
          updateSelectionButtonText(myEditedSelector,theNewPlayer.toString());
        }else{
          clearSelectionButton(myEditedSelector);
        }
	}
	
    private void updateSelectionButtonText(JButton aSelector, String aText) {
    	aSelector.setFont(aSelector.getFont().deriveFont(Font.BOLD | Font.ITALIC, 12));
    	aSelector.setText(aText);
	}

	private void clearSelectionButton(JButton button) {
    	button.setFont(button.getFont().deriveFont(Font.ITALIC, 12));
		button.setText("Click to select a strategy for this player");
	}

	public JPanel getXMLPanel(DomPlayer theStrategy) {
        final JPanel thePanel = new JPanel( new GridBagLayout() );
        final GridBagConstraints theCons = DomGui.getGridBagConstraints( 2 );
        JTextArea theXMLStrategyArea = new JTextArea();
        theXMLStrategyArea.setFont( Font.decode( "Courrier 12" ) );
        theXMLStrategyArea.setWrapStyleWord( true );
        theXMLStrategyArea.setLineWrap( true );
        if (theStrategy==null) {
          theXMLStrategyArea.setText( "Paste XML here (delete this line)!!" );
        } else {
          theXMLStrategyArea.setText( theStrategy.getXML() );
        }
        final JScrollPane scr = new JScrollPane( theXMLStrategyArea);
        scr.setPreferredSize( new Dimension( 700, 500 ) );
        Runnable doScroll = new Runnable() {
          public void run() {
            scr.getVerticalScrollBar().setValue( 0 );
          }
        };
        SwingUtilities.invokeLater( doScroll );
        thePanel.add( scr, theCons );
        return thePanel;
    }

	public void showWinPercentage(DomPlayer thePlayer, int i) {
		JButton theBTN = myPlayers.get(thePlayer);
		((JLabel) myWinPercentageLBLs.get(theBTN)).setText("Wins: "+i+"%");
	}

	private JMenuBar createMenu() {
	    JMenuBar bar = new JMenuBar();
	    JMenu fileMenu = new JMenu( "File" );
	    fileMenu.setMnemonic( 'f' );
	    JMenuItem loadPool = new JMenuItem( "Import Bots", 'I' );
	    loadPool.addActionListener( this );
	    loadPool.setActionCommand( "Load" );
	    fileMenu.add( loadPool );
	    JMenuItem saveDeck = new JMenuItem( "Export Bots", 'E' );
	    saveDeck.addActionListener( this );
	    saveDeck.setActionCommand( "Save" );
	    fileMenu.add( saveDeck );
	    fileMenu.insertSeparator( 2 );
	    JMenuItem exit = new JMenuItem( "Exit", 'X' );
	    exit.addActionListener( new ActionListener() {
	      public void actionPerformed( ActionEvent ae ) {
	        shutDown();
	      }
	    } );
	    fileMenu.add( exit );
	    bar.add( fileMenu );
        JMenu devmodeMenu = new JMenu("Development");
        JCheckBoxMenuItem devMode = new JCheckBoxMenuItem( "Development Mode" );
        devMode.setSelected(DomEngine.developmentMode);
        devMode.addActionListener(this);
        devMode.setActionCommand("DevMode");
        devmodeMenu.add(devMode);
        bar.add(devmodeMenu);
	    JMenu helpMenu = new JMenu( "Help" );
	    helpMenu.setMnemonic( 'h' );
	    JMenuItem webHelp = new JMenuItem( "http://dominionsimulator.wordpress.com" );
	    webHelp.addActionListener( this );
	    webHelp.setActionCommand( "WebHelp" );
	    helpMenu.add( webHelp);
	    helpMenu.insertSeparator( 2 );
	    JMenuItem about = new JMenuItem( "About", 't' );
	    about.addActionListener( this );
	    about.setActionCommand( "About" );
	    helpMenu.add( about );
	    bar.add( helpMenu );
	    return bar;
  }

	public void showTiePercentage(int i) {
		myTiesLBL.setText("Ties: "+i+"%");
	}

	public void showAverageTurns(double aAverageTurns) {
		myAverageTurnsLBL.setText("Average number of turns: "+aAverageTurns);
	}
	public void showTime(double aTime) {
		myTimeLBL.setText("Time: "+aTime+"s");
	}

	public void show3EmptyPilesEndings(double d) {
		my3EmptyPilesLBL.setText("Three empty piles endings: "+(int)d+ "%");
	}

	public static void addHeavyLabel( final JPanel thePanel, final GridBagConstraints theCons ) {
	    theCons.weightx=100;
	    theCons.weighty=100;
	    thePanel.add( new JLabel("") , theCons);
	    theCons.weightx=1;
	    theCons.weighty=1;
	}

    public ArrayList<DomPlayer> getBots() {
        ArrayList<DomPlayer> theBots = new ArrayList<DomPlayer>();
        for (JButton theSelector : myBotSelectors) {
            if (getSelectedPlayer(theSelector) == null)
                continue;
            theBots.add(getSelectedPlayer(theSelector));
        }
        return theBots;
    }
}