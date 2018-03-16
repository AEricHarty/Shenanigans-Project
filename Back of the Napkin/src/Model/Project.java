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
import java.util.Observable;

/**
 * A DIY Project object.
 * 
 * @author Eric Harty hartye@uw.edu
 * @version .75
 */
public class Project extends Observable implements Serializable {

	/**Generated VersionID - 3/12- EH*/
	private static final long serialVersionUID = 9074716694653347193L;

	/**The name of this Project.*/
    private String myName;

	/**The list of Components.*/
    public LinkedList<ComponentListItem> myComponents;
    
    /**
     * Constructs a Project with a default name and empty fields.
     * @author Eric Harty - hartye@uw.edu
     * 
     */
    public Project() {
    	myName = "Untitled";
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
        	System.out.println("Incorrect file type");
        }
    	myName = temp.getName();
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
	 * @return the list of <ComponentListItem>
	 */
	public LinkedList<ComponentListItem> getComponents() {
		return myComponents;
	}

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
        	total = total.add(subt);
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
		BigDecimal total = BigDecimal.ZERO;
		for(ComponentListItem c : myComponents) {
        	BigDecimal subt = c.getComponent().getCost();
        	BigDecimal q = new BigDecimal(c.getQuantity());
        	subt = subt.multiply(q);
        	total = total.add(subt);
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
		double hrs = 0;
		for(ComponentListItem c : myComponents) {
        	double subt = c.getComponent().getManHrs();
        	subt = subt * c.getQuantity();
        	hrs += subt;
        }
		return hrs;
	}
	
	/**
	 * Calculates and returns the total weight for this project.
	 * @author Keegan Wantz - wantzkt@uw.edu
	 * 
	 * @return the total man-hours
	 */
	public double getTotalWeight() {
		double weight = 0;
		for(ComponentListItem c : myComponents) {
			weight += c.getComponent().getWeight() * c.getQuantity();
        }
		return weight;
	}


	
	/**
	 * @author Eric Harty - hartye@uw.edu
	 * @param myName the myName to set
	 */
	public void setName(String theName) {
		myName = theName;
		this.setChanged();
		this.notifyObservers();
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
		this.setChanged();
		this.notifyObservers();
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
				break;
			}
		}
		this.setChanged();
		this.notifyObservers();
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
    
	/**
	 * @author Keegan Wantz - wantzkt@uw.edu
	 */
    public String toString() {
    	return getName();
    }
}
