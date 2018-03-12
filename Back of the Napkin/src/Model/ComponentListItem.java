package Model;

/**
 * An pair of component and quantity items for the component list.
 * 
 * @author Eric Harty - hartye@uw.edu
 * @version .75
 */
public class ComponentListItem {

	/**The component.*/
    private final Component myComponent;

    /**The quantity of the component in the list.*/
    private int myQuantity;
    
    /**
     * Constructs a ComponentListItem with the specified component and quantity.
     * @author Eric Harty - hartye@uw.edu
     * 
     * @param theComponent
     * @param theQuantity
     * @throws IllegalArgumentException if theQuantity is less than or equal to zero
     */
    public ComponentListItem(final Component theComponent, final int theQuantity) {
		myComponent = theComponent;
		
		if(theQuantity <= 0) {
			throw new IllegalArgumentException();
		}
		myQuantity = theQuantity;
	}

	/**
	 * @author Eric Harty - hartye@uw.edu
	 * 
	 * @param the Quantity to set
	 */
	public void setMyQuantity(int theQuantity) {
		myQuantity = theQuantity;
	}

	/**
	 * @author Eric Harty - hartye@uw.edu
	 * 
	 * @return the Component
	 */
	public Component getComponent() {
		return myComponent;
	}

	/**
	 * @author Eric Harty - hartye@uw.edu
	 * 
	 * @return the Quantity
	 */
	public int getQuantity() {
		return myQuantity;
	}

	/**
	 * Override so Components can be compared.
	 * @author Eric Harty - hartye@uw.edu
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((myComponent == null) ? 0 : myComponent.hashCode());
		return result;
	}

	/**
	 * Override so Components can be compared, 
	 * uses only component and not quantity.
	 * @author Eric Harty - hartye@uw.edu
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ComponentListItem)) {
			return false;
		}
		ComponentListItem other = (ComponentListItem) obj;
		if (myComponent == null) {
			if (other.myComponent != null) {
				return false;
			}
		} else if (!myComponent.equals(other.myComponent)) {
			return false;
		}
		return true;
	}
	
	/** 
	 * @author Eric Harty - hartye@uw.edu
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ComponentListItem [myComponent=" + myComponent.getName() + "]";
	}
	
	
}
