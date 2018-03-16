/**
 * 
 */
package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.LinkedList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Model.Component;
import Model.ComponentListItem;

/**
 * Tests the component class
 * 
 * @author Eric Harty - hartye@uw.edu
 */
class ComponentTest {

    private final String myName = "Test Name";
    private final String myName2 = "Test Name 2";
    private final BigDecimal myCost = new BigDecimal("10.15");
    private final BigDecimal myCost2 = new BigDecimal("5.15");
    private final BigDecimal myCPMH = new BigDecimal("18.80");
    private final BigDecimal myCPMH2 = new BigDecimal("8.80");
    private final BigDecimal mySubCPMH = new BigDecimal("37.60");
    private final BigDecimal myCPM = new BigDecimal("17.50");
    private final BigDecimal myCPM2 = new BigDecimal("7.50");
    private final BigDecimal mySubCPM = new BigDecimal("105.00");
    private final double myManHrs = 9.5;
    private final double myManHrs2 = 4.5;
    private final double mySubManHrs = 57;
	
    private final Component subComp = new Component(3, myName, myCost, myCPM,
			myManHrs, myManHrs, myManHrs, myManHrs, myManHrs, myName,
			myManHrs, myCPMH, null);
    private final ComponentListItem subItem = new ComponentListItem(subComp, 5);
    private final LinkedList<ComponentListItem> subList = new LinkedList<ComponentListItem>();
    
	private final Component comp = new Component(2, myName, myCost, myCPM,
			myManHrs, myManHrs, myManHrs, myManHrs, myManHrs, myName,
			myManHrs, myCPMH, null);
    
	private Component comp2;
    
    private final Component comp3 = new Component(3, myName2, myCost2, myCPM2,
			myManHrs2, myManHrs2, myManHrs2, myManHrs2, myManHrs2, myName2,
			myManHrs2, myCPMH2, null);
	
	
	/**
	 * @author Eric Harty - hartye@uw.edu
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		subList.add(subItem);
		comp2 = new Component(2, myName, myCost, myCPM,
				myManHrs, myManHrs, myManHrs, myManHrs, myManHrs, myName,
				myManHrs, myCPMH, subList);
	}
	
	/**@author Eric Harty - hartye@uw.edu*/
	@Test
	void testTotals() {
		assertEquals(comp.getCost(), myCost);
		assertEquals(comp.getCostPerMonth(), myCPM);
		assertEquals(comp.getManHrs(), myManHrs);
		assertEquals(comp.getCostPerManHr(), myCPMH);
	}
	
	/**
	 * Test with subComponents
	 * 
	 * @author Eric Harty - hartye@uw.edu*/
	@Test
	void testTotals2() {
		assertEquals(comp2.getCost(), myCost);
		assertEquals(comp2.getCostPerMonth(), mySubCPM);
		assertEquals(comp2.getManHrs(), mySubManHrs);
		assertEquals(comp2.getCostPerManHr(), mySubCPMH);
	}
	
	/**@author Eric Harty - hartye@uw.edu*/
	@Test
	void testEquals() {
		assertEquals(comp, comp);
		assertNotEquals(comp, comp2);
		assertNotEquals(comp, comp3);
		assertNotEquals(comp, null);
		assertNotEquals(comp, myCost);
	}
	
	/**@author Eric Harty - hartye@uw.edu*/
	@Test
	void testHashCode() {
		assertEquals(comp.hashCode(), comp.hashCode());
		assertNotEquals(comp.hashCode(), comp2.hashCode());
		assertNotEquals(comp.hashCode(), comp3.hashCode());
	}
	

}
