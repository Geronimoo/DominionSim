package be.aga.dominionSimulator.gui;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import be.aga.dominionSimulator.enums.DomCardName;

public class DomCardLabel extends JLabel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1464348634649471489L;
	private static final double STANDARD_FIT = 0.9;
	private ImageIcon myOriginalIcon;
	private ImageIcon myScaledIcon;
	private Object myParentSize;
	private DomCardName myCardName;
	
	public DomCardLabel(DomCardName aCardName) {
		myCardName = aCardName;
		setText("<html>"+aCardName.toHTML()+"</html>");
//		URL url = aCardName.getImageURL();
//		if (url!=null) {
//	        myOriginalIcon=new ImageIcon(url);
//	        setIcon(myOriginalIcon);
//		} else {
			myOriginalIcon=new ImageIcon(aCardName.getCompleteImageLocation());
			setIcon(myOriginalIcon);
//		}
			
        setVerticalTextPosition(SwingConstants.CENTER);
        setHorizontalTextPosition(SwingConstants.CENTER);
	}
	
	public DomCardLabel(DomCardName name, boolean b) {
		this(name);
	    myOriginalIcon=null;
	    setIcon(null);
	}

	public DomCardLabel() {
		super();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (myOriginalIcon==null
		  || (myParentSize!=null && getParent().getSize().equals(myParentSize)))
		  //if we don't have an image or if the size of image hasn't changed we're done
		  return;
		
		myParentSize=getParent().getSize();
		
//		System.out.println("resize");
        double theScale = getParent().getHeight()*STANDARD_FIT/myOriginalIcon.getIconHeight();
        int scaledY = (int) (getParent().getHeight()* STANDARD_FIT);
        int scaledX = (int) (myOriginalIcon.getIconWidth()*theScale);
        Image scaledImage = myOriginalIcon.getImage().getScaledInstance(scaledX , scaledY, Image.SCALE_SMOOTH);
        myScaledIcon = new ImageIcon(scaledImage);
        setIcon(myScaledIcon);
	}
	
	public void setCardName(DomCardName aCardName) {
	   if (aCardName==myCardName)
		return;
	   myParentSize=null;
       myCardName=aCardName;
	   myOriginalIcon=new ImageIcon(aCardName.getCompleteImageLocation());
	   setIcon(myOriginalIcon);
	}

}