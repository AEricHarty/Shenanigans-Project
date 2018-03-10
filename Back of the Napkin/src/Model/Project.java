package Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * A DIY Project object.
 * 
 * @author Eric Harty hartye@uw.edu
 * @version .75
 */
public class Project implements Serializable {

	/**Generated VersionID - 3/10- EH*/
	private static final long serialVersionUID = 9074716694653347193L;

	/**The name of this Project.*/
    private String myName;

    /**The misc cost of the project.*/
    private BigDecimal myMiscCost;
    
    /**The estimated man-hours.*/
    private double myManHrs;
    
    /**The current cost per kWh of energy used.*/
    private BigDecimal myPowerCost;

	/**The list of Components.*/
    public LinkedList<ComponentListItem> myComponents;
    
    /**
     * Constructs a Project with a default name and empty fields.
     * @author Eric Harty - hartye@uw.edu
     * 
     */
    public Project() {
		myName = "Untitled";
		myMiscCost = new BigDecimal(0);
		myPowerCost = new BigDecimal(0);
		myManHrs = 0;
		myComponents = new LinkedList<ComponentListItem>();
	}
    
    /**
     * Overloaded constructor for loading files.
     * @author Eric Harty - hartye@uw.edu
     * 
     */
    public Project(File theFile) {
    	Project temp = null;
    	try {
            FileInputStream fi = new FileInputStream(theFile);
            ObjectInputStream oi = new ObjectInputStream(fi);
            temp = (Project) oi.readObject();        
            oi.close();
            fi.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    	myName = temp.getName();
		myMiscCost = temp.getMiscCost();
		myPowerCost = temp.getPowerCost();
		myManHrs = temp.getManHrs();
		myComponents = temp.getComponents();
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
	 * @return the current cost per kWh of energy used
	 */
	public BigDecimal getPowerCost() {
		return myPowerCost;
	}

	/**
	 * @author Eric Harty - hartye@uw.edu
	 * @return the ManHrs
	 */
	public double getManHrs() {
		return myManHrs;
	}

	/**
	 * @author Eric Harty - hartye@uw.edu
	 * @return the MiscCost
	 */
	public BigDecimal getMiscCost() {
		return myMiscCost;
	}
	
	/**
	 * @author Eric Harty - hartye@uw.edu
	 * @return the list of <ComponentListItem>
	 */
	public LinkedList<ComponentListItem> getComponents() {
		return myComponents;
	}
	
	/**
	 * Calculates and returns the total energy cost in kWh for this project.
	 * @author Eric Harty - hartye@uw.edu
	 * 
	 * @return the total energy consumption
	 */
	/*public double getTotalEnergy() {
		double kWh = 0;
		for(ComponentListItem c : myComponents) {
        	double subt = c.getComponent().getEnergyConsumption();
        	subt = subt * c.getQuantity();
        	kWh += subt;
        }
		return kWh;
	}*/
	
	/**
	 * Calculates and returns the total energy cost in kWh for this project.
	 * @author Eric Harty - hartye@uw.edu
	 * 
	 * @return the cost to power this project
	 */
	/*public BigDecimal getTotalPowerCost() {
		BigDecimal total = myPowerCost;
		BigDecimal use = new BigDecimal(this.getTotalEnergy());
		total.multiply(use);
		return total;
	}*/

	/**
	 * Calculates and returns the cost per month for this project.
	 * @author Eric Harty - hartye@uw.edu
	 * 
	 * @return the CostPerMonth
	 */
	public BigDecimal getCostPerMonth() {
		BigDecimal total = new BigDecimal(0);
		for(ComponentListItem c : myComponents) {
        	BigDecimal subt = c.getComponent().getCostPerMonth();
        	BigDecimal q = new BigDecimal(c.getQuantity());
        	subt = subt.multiply(q);
        	total.add(subt);
        }
		return total;
	}
	
	/**
	 * Calculates and returns the total up front cost for this project.
	 * @author Eric Harty - hartye@uw.edu
	 * 
	 * @return the Total Cost
	 */
	public BigDecimal getTotalUpfrontCost() {
		BigDecimal total = myMiscCost;
		for(ComponentListItem c : myComponents) {
        	BigDecimal subt = c.getComponent().getCost();
        	BigDecimal q = new BigDecimal(c.getQuantity());
        	subt = subt.multiply(q);
        	total.add(subt);
        }
		return total;
	}
	
	/**
	 * Calculates and returns the total man-hours for this project.
	 * @author Eric Harty - hartye@uw.edu
	 * 
	 * @return the total man-hours
	 */
	public double getTotalManHrs() {
		double hrs = myManHrs;
		for(ComponentListItem c : myComponents) {
        	double subt = c.getComponent().getManHrs();
        	subt = subt * c.getQuantity();
        	hrs += subt;
        }
		return hrs;
	}

	
	/**
	 * @author Eric Harty - hartye@uw.edu
	 * @param myName the myName to set
	 */
	public void setName(String theName) {
		myName = theName;
	}
	
	/**
	 * @author Eric Harty - hartye@uw.edu
	 * @param powerCost the powerCost to set
	 */
	public void setPowerCost(BigDecimal powerCost) {
		myPowerCost = powerCost;
	}
	
	/**
	 * @author Eric Harty - hartye@uw.edu
	 * @param the ManHrs to set
	 */
	public void setManHrs(double theManHrs) {
		myManHrs = theManHrs;
	}
	
	/**
	 * @author Eric Harty - hartye@uw.edu
	 * @param the MiscCost to set
	 */
	public void setMiscCost(BigDecimal theMiscCost) {
		myMiscCost = theMiscCost;
	}

	/**
	 * Adds a component with the given quantity to the component list.
	 * @author Eric Harty - hartye@uw.edu
	 * 
	 * @param the Component to add
	 * @param the Quantity of the component
	 */
	public void addComponent(Component theComponent, int theQuantity) {
		ComponentListItem c = new ComponentListItem(theComponent, theQuantity);
		myComponents.add(c);
	}
	
	/**
	 * Removes a component entry from the component list,
	 * if there is more than one instance of a component
	 * (regardless of quantity) in the list, removes all.
	 * @author Eric Harty - hartye@uw.edu
	 * @modified Keegan Wantz - wantzkt@uw.edu (remove by ID);
	 * @param the Component to remove
	 * @throws NoSuchElementException
	 */
	public void removeComponent(int theID) {
		for (ComponentListItem c : myComponents) {
			if (c.getComponent().getMyID() == theID) {
				myComponents.remove(c);
			}
		}
	}
	
	/**
	 * Saves the project as name.txt in the same folder as the jar.
	 * @author Eric Harty - hartye@uw.edu
	 * 
	 */
	public void saveProject() {
        try {
			FileOutputStream f = new FileOutputStream(new File(myName + ".txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(this);
            o.close();
            f.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        }
	}
    
    
}
