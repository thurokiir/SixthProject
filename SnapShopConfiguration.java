// Write your short report here (-2 if there is no report)

/**
 * A class to configure the SnapShop application
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class SnapShopConfiguration {
	/**
	 * Method to configure the SnapShop. Call methods like addFilter and
	 * setDefaultFilename here.
	 * 
	 * @param theShop
	 *            A pointer to the application
	 */
	public static void configure(SnapShop theShop) {

		theShop.setDefaultFilename("billg.jpg");
		theShop.setDefaultFilename("seattle.jpg");
		theShop.setDefaultFilename("Checkers.jpg");
		
		theShop.addFilter(new FlipHorizontalFilter(), "Flip Horizontal");
		theShop.addFilter(new FlipVerticalFilter(), "Flip Vertical");
		theShop.addFilter(new GaussianFilter(), "Gausses Image");
		theShop.addFilter(new LaplacianFilter(), "Highlight Image");
		theShop.addFilter(new UnsharpMaskFilter(), "Sharpen Image");
		theShop.addFilter(new EdgyFilter(), "Edge Image");
		theShop.addFilter(new TimerFilter(1, 5), "DANGER");
	}
}










