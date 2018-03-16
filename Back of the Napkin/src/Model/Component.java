package Model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * A component for a DIY project.
 * 
 * @author Eric Harty - hartye@uw.edu
 * @modified Keegan Wantz - wantzkt@uw.edu (see ctor, getCostPerManHr, getRadius)
 * @version .75
 */
public class Component {

	/**A margin of error for comparing doubles*/
    private final double TOLERANCE = .000001;
	
	/**The ID of this component.*/
    private final int myID;
	
    /**The name of this component.*/
    private final String myName;

    /**The unit price of the component.*/
    private final BigDecimal myCost;
    
    /**The unit price per month of the component.*/
    private final BigDecimal myCostPerMonth;
    
    /**The double width of the component.*/
    private final double myWidth;
    
    /**The double length of the component.*/
    private final double myLength;
    
    /**The double height of the component.*/
    private final double myHeight;
    
    /**The double radius of the component.*/
    private final double myRadius;
    
    /**The double weight of the component.*/
    private final double myWeight;
    
    /**The material of this component.*/
    private final String myMaterial;
    
    /**The estimated man-hours.*/
    private final double myManHrs;
    
    /**The cost per man hours*/
    private final BigDecimal myCostPerManHr;
    
    /**The list of subcomponents.*/
    private final List<ComponentListItem> mySubComponents;
    
    
    
    /**
     * Constructs a DIYComponent with the specified name and quantity.
     * @author Eric Harty - hartye@uw.edu
     * @modified Keegan Wantz - wantzkt@uw.edu (added man hour cost, removed energy cost, added radius)
     * @modified Keegan Wantz - wantzkt@uw.edu (List instead of LinkedList)
     * 
     * @param theID
     * @param theName
     * @param theCost - unit cost
     * @param theCostPerMonth
     * @param theWidth
     * @param theLength
     * @param theHeight
     * @param theWeight
     * @param theMaterial
     * @param theManHrs
     * @param theSubComponents - LinkedList<ComponentListItem>
     */
    public Component(final int theID, final String theName, final BigDecimal theCost,
    		final BigDecimal theCostPerMonth, final double theLength, final double theWidth,
    		final double theHeight, final double theRadius, final double theWeight, final String theMaterial,
    		final double theManHrs, final BigDecimal theManHourCost,
    		final List<ComponentListItem> theSubComponents) {
		myID = theID;
    	myName = theName;
		myCost = theCost;
		myCostPerMonth = theCostPerMonth;
		myWidth = theWidth;
		myLength = theLength;
		myHeight = theHeight;
		myRadius = theRadius;
		myWeight = theWeight;
		myMaterial = theMaterial;
		myManHrs = theManHrs;
		myCostPerManHr = theManHourCost;
		
		if (theSubComponents == null)
			mySubComponents = new ArrayList<>();
		else
			mySubComponents = theSubComponents;
	}

	/**
	 * @author Eric Harty - hartye@uw.edu
	 * @return the myID
	 */
	public int getMyID() {
		return myID;
	}


	/**
	 * @author Eric Harty - hartye@uw.edu
	 * @return the Name
	 */
	public String getName() {
		return myName;
	}
	
	/**
	 * @author Eric Harty - hartye@uw.edu
	 * @return the Width
	 */
	public double getWidth() {
		return myWidth;
	}

	/**
	 * @author Eric Harty - hartye@uw.edu
	 * @return the Length
	 */
	public double getLength() {
		return myLength;
	}

	/**
	 * @author Eric Harty - hartye@uw.edu
	 * @return the Height
	 */
	public double getHeight() {
		return myHeight;
	}

	/**
	 * @author Eric Harty - hartye@uw.edu
	 * @return the myWeight
	 */
	public double getWeight() {
		return myWeight;
	}
	
	/**
	 * @author Keegan Wantz - wantzkt@uw.edu
	 * @return the radius of this component.
	 */
	public double getMyRadius() {
		return myRadius;
	}

	/**
	 * @author Eric Harty - hartye@uw.edu
	 * @return the myMaterial
	 */
	public String getMaterial() {
		return myMaterial;
	}

	/**
	 * @author Eric Harty - hartye@uw.edu
	 * @modified Keegan Wantz - wantzkt@uw.edu (List instead of LinkedList)
	 * @return the SubComponents
	 */
	public List<ComponentListItem> getSubComponents() {
		return mySubComponents;
	}

    /**
     * Returns the Unit price of this component.
     * @author Eric Harty - hartye@uw.edu
     * 
     * @return the BigDecimal unit cost
     */
    public final BigDecimal getUnitCost() {
    	return myCost;
    }

    /**
     * Calculates and returns the total price of one of this
     * component and all subcomponents.
     * @author Eric Harty - hartye@uw.edu
     * 
     * @return the BigDecimal cost
     */
    public final BigDecimal getCost() {
        BigDecimal total = myCost;
        
        for(ComponentListItem c : mySubComponents) {
        	BigDecimal subt = c.getComponent().getCost();
        	BigDecimal q = new BigDecimal(c.getQuantity());
        	subt = subt.multiply(q);
        	total.add(subt);
        }
    	return total;
    }
    
    /**
     * Returns the Unit cost-per-month of this component.
     * @author Eric Harty - hartye@uw.edu
     * 
     * @return the BigDecimal price
     */
    public final BigDecimal getUnitCPM() {
    	return myCostPerMonth;
    }
    
    /**
     * Calculates and returns the total price-per-month for one of 
     * this component and all subcomponents.
     * @author Eric Harty - hartye@uw.edu
     * 
     * @return the BigDecimal cost per month
     */
    public final BigDecimal getCostPerMonth() {
        BigDecimal total = myCostPerMonth;
    	
        for(ComponentListItem c : mySubComponents) {
        	BigDecimal subt = c.getComponent().getCostPerMonth();
        	BigDecimal q = new BigDecimal(c.getQuantity());
        	subt = subt.multiply(q);
        	total = total.add(subt);
        }
    	return total;
    }
    
    /**
     * Returns the Unit man-hours of this component.
     * @author Eric Harty - hartye@uw.edu
     * 
     * @return the ManHrs
	 */
	public double getUnitManHrs() {
		return myManHrs;
	}
    
    /**
     * Returns the estimated man-hours for this component 
     * and all subcomponents.
     * @author Eric Harty - hartye@uw.edu
     * 
     * @return the double ManHours
     */
    public final double getManHrs() {
    	double hrs = myManHrs;
    	for(ComponentListItem c : mySubComponents) {
        	double subt = c.getComponent().getManHrs();
        	subt = subt * c.getQuantity();
        	hrs += subt;
        }
    	return hrs;
    }

    /**
     * Returns the cost per man hour of this component.
     * 
     * @author Keegan Wantz - wantzkt@uw.edu
     * 
     * @return The summed cost of this component.
     */
    public final BigDecimal getUnitCostPerManHr() {
    	return myCostPerManHr;
    }
    
    /**
     * Returns the cost per man hour of this + all subcomponents.
     * 
     * @author Keegan Wantz - wantzkt@uw.edu
     * 
     * @return The summed cost of this component + all subcomponents.
     */
    public final BigDecimal getCostPerManHr() {
    	BigDecimal cpmh = myCostPerManHr;
    	for(ComponentListItem c : mySubComponents) {
    		cpmh = cpmh.add(c.getComponent().getCostPerManHr());
        }
    	
    	return cpmh;
    }
    
    /**
	 * Override so Components can be compared.
	 * @author Eric Harty - hartye@uw.edu
	 * 
	 * @see java.lang.Object#hashCode()
	 * @return hash code
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((myCost == null) ? 0 : myCost.hashCode());
		result = prime * result + ((myCostPerMonth == null) ? 0 : myCostPerMonth.hashCode());
		long temp;
		temp = Double.doubleToLongBits(myHeight);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(myID);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(myLength);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(myManHrs);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((myMaterial == null) ? 0 : myMaterial.hashCode());
		result = prime * result + ((myName == null) ? 0 : myName.hashCode());
		result = prime * result + ((mySubComponents == null) ? 0 : mySubComponents.hashCode());
		temp = Double.doubleToLongBits(myWeight);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(myWidth);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}


	/**
	 * Override so Components can be compared.
	 * @author Eric Harty - hartye@uw.edu
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 * @return true if equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Component)) {
			return false;
		}
		Component other = (Component) obj;
		if (myCost == null) {
			if (other.myCost != null) {
				return false;
			}
		} else if (!myCost.equals(other.myCost)) {
			return false;
		}
		if (myCostPerMonth == null) {
			if (other.myCostPerMonth != null) {
				return false;
			}
		} else if (!myCostPerMonth.equals(other.myCostPerMonth)) {
			return false;
		}
		if (!myCostPerManHr.equals(other.myCostPerManHr)) {
			return false;
		}
		if (Math.abs(Double.doubleToLongBits(myHeight) - Double.doubleToLongBits(other.myHeight)) > TOLERANCE){
			return false;
		}
		if (myID != other.myID) {
			return false;
		}
		if (Math.abs(Double.doubleToLongBits(myLength) - Double.doubleToLongBits(other.myLength)) > TOLERANCE){
			return false;
		}
		if (Math.abs(Double.doubleToLongBits(myManHrs) - Double.doubleToLongBits(other.myManHrs)) > TOLERANCE){
			return false;
		}
		if (myMaterial == null) {
			if (other.myMaterial != null) {
				return false;
			}
		} else if (!myMaterial.equals(other.myMaterial)) {
			return false;
		}
		if (myName == null) {
			if (other.myName != null) {
				return false;
			}
		} else if (!myName.equals(other.myName)) {
			return false;
		}
		if (mySubComponents == null) {
			if (other.mySubComponents != null) {
				return false;
			}
		} else if (!mySubComponents.equals(other.mySubComponents)) {
			return false;
		}
		if (Math.abs(Double.doubleToLongBits(myWeight) - Double.doubleToLongBits(other.myWeight)) > TOLERANCE){
			return false;
		}
		if (Math.abs(Double.doubleToLongBits(myWidth) - Double.doubleToLongBits(other.myWidth)) > TOLERANCE){
			return false;
		}
		return true;
	}
	

}