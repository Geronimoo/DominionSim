package be.aga.dominionSimulator;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import be.aga.dominionSimulator.enums.DomCardName;


public class StartState {

	private ArrayList< DomCardName> myHand = new ArrayList<DomCardName>();
	private ArrayList< DomCardName> myDrawDeck = new ArrayList<DomCardName>();
	private ArrayList< DomCardName> myDiscard = new ArrayList<DomCardName>();
	private boolean shuffleDrawDeck = true;
	public String myOriginalHand;
	public String myOriginalDrawDeck;
	public String myOriginalDiscard;

	public boolean addHand(String aHand) {
		myOriginalHand = aHand;
		return dissectAndAdd(aHand, myHand);
	}

	public boolean addDrawDeck(String aDrawDeck, String aShuffle) {
		myOriginalDrawDeck=aDrawDeck;
		shuffleDrawDeck=aShuffle.equals("true");
		return dissectAndAdd(aDrawDeck, myDrawDeck);
	}

	public boolean addDiscard(String aDiscard) {
		myOriginalDiscard = aDiscard;
		return dissectAndAdd(aDiscard, myDiscard);
	}

	public static boolean dissectAndAdd(String aString, ArrayList<DomCardName> aDestination) {
		if (aString.trim().isEmpty()) {
			aDestination= new ArrayList<DomCardName>();
			return true;
		}
    	for (String tag : aString.split(",| and")){
            Pattern pattern = Pattern.compile(" *(\\d*) *(.*)");
            Matcher matcher = pattern.matcher(tag.trim());
            if (!matcher.matches())
              return false;
            String theString = matcher.group(2);
            theString=theString.replaceAll( "\\s|-", "_" ).replaceAll( "'", "\\$" );
            DomCardName theCardName;
			try {
				theCardName = DomCardName.valueOf(theString);
			} catch (Exception e) {
				return false;
			} 
            Integer theNumber = 1; 
            if (matcher.group(1).trim().length()>0)
            	theNumber = new Integer(matcher.group(1));
    		for (int j=0;j<theNumber;j++){
               aDestination.add(0,theCardName);        			
    		}
    	}
		return true;
	}

	public ArrayList<DomCardName> getHand() {
	  return myHand;
	}

	public ArrayList<DomCardName> getDrawDeck() {
		return myDrawDeck;
	}

	public ArrayList<DomCardName> getDiscard() {
		return myDiscard;
	}

	public boolean isShuffleDrawDeck() {
		return shuffleDrawDeck;
	}

	public String getXML() {
        String newline = System.getProperty( "line.separator" );
		StringBuilder builder = new StringBuilder();
		builder.append("  <start_state>").append(newline);
		builder.append("    <hand contents=\"").append(myOriginalHand).append("\"/>").append(newline);
		builder.append("    <discard contents=\"").append(myOriginalDiscard).append("\"/>").append(newline);
		builder.append("    <drawdeck contents=\"").append(myOriginalDrawDeck).append("\"");
		builder.append(" shuffle=\"").append(shuffleDrawDeck).append("\"/>").append(newline);
		builder.append("  </start_state>").append(newline);
		return builder.toString();
	}
}