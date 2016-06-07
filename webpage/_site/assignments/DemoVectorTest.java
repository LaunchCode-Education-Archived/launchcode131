package lab4;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Official CSE131 Lab 4 Vector JUnit test.  
 *   Demo this to get credit, but
 *      turn in your own tests as well. 
 * @author cytron
 *
 */
public class DemoVectorTest {
	@Test
	public void init() {
		Vector v = new Vector(5, -3);
		assertEquals("[5.0 -3.0]", v.toString());
		assertEquals(5.0,  v.getDeltaX());
		assertEquals(-3.0, v.getDeltaY());
	}
	
	@Test
	public void arith() {
		Vector v = new Vector(5, -3);
		assertEquals("[10.0 -6.0]", ""+v.plus(v));
		assertEquals("[0.0 0.0]",   ""+v.minus(v));
	}
	
	/**
	 * Why did I write this method?
	 * @param v a vector
	 * @return v's x component
	 */
	public double gx(Vector v) {
		return v.getDeltaX();
	}
	
	/**
	 * 
	 * @param v a vector
	 * @return v's y component
	 */
	public double gy(Vector v) {
		return v.getDeltaY();
	}
	
	/**
	 * Why did I write this method?
	 * @param one    one of two doubles to compare
	 * @param other  other of two doubles to compare
	 */
	public void compare(double one, double other) {
		assertEquals(one, other, 0.01);
	}
	
	@Test
	public void scale() {
		Vector v = new Vector(5, -3);
		Vector bigger = v.scale(1.5);
		Vector smaller = v.scale(0.75);
		compare( 7.5,   gx(bigger));
		compare(-4.5,   gy(bigger));
		compare( 3.75,  gx(smaller));
		compare( 2.25,  gy(smaller.deflectY()));
		compare(-2.25,  gy(smaller.deflectX()));
	}
	
	@Test
	public void rescale() {
		Vector v = new Vector(3, 4);
		compare(5.0, v.magnitude());
		compare(6.0, gx(v.rescale(10)));
		compare(8.0, gy(v.rescale(10)));
	}
	
	@Test
	public void specialCases() {
		Vector v = new Vector(0, 0);
		Vector r = v.rescale(5);
		compare(0, v.magnitude());
		compare(5, r.magnitude());
		compare(5, gx(r));
		compare(0, gy(r));
	}

}
