import java.awt.image.*;


/**
 * Provides an interface to a picture as an array of Pixels
 */
public class PixelImage {
	private BufferedImage myImage;
	private int width;
	private int height;

	/**
	 * Map this PixelImage to a real image
	 * 
	 * @param bi
	 *            The image
	 */
	public PixelImage(BufferedImage bi) {
		// initialize instance variables
		this.myImage = bi;
		this.width = bi.getWidth();
		this.height = bi.getHeight();
	}

	/**
	 * Return the width of the image
	 */
	public int getWidth() {
		return this.width;
	}

	/**
	 * Return the height of the image
	 */
	public int getHeight() {
		return this.height;
	}

	/**
	 * Return the BufferedImage of this PixelImage
	 */
	public BufferedImage getImage() {
		return this.myImage;
	}

	/**
	 * Return the image's pixel data as an array of Pixels. The first coordinate
	 * is the x-coordinate, so the size of the array is [width][height], where
	 * width and height are the dimensions of the array
	 * 
	 * @return The array of pixels
	 */
	public Pixel[][] getData() {
		Raster r = this.myImage.getRaster();
		Pixel[][] data = new Pixel[r.getHeight()][r.getWidth()];
		int[] samples = new int[3];

		for (int row = 0; row < r.getHeight(); row++) {
			for (int col = 0; col < r.getWidth(); col++) {
				samples = r.getPixel(col, row, samples);
				Pixel newPixel = new Pixel(samples[0], samples[1], samples[2]);
				data[row][col] = newPixel;
			}
		}

		return data;
	}

	/**
	 * Set the image's pixel data from an array. This array matches that
	 * returned by getData(). It is an error to pass in an array that does not
	 * match the image's dimensions or that has pixels with invalid values (not
	 * 0-255)
	 * 
	 * @param data
	 *            The array to pull from
	 */
	public void setData(Pixel[][] data) {
		int[] pixelValues = new int[3]; // a temporary array to hold r,g,b
										// values
		WritableRaster wr = this.myImage.getRaster();

		if (data.length != wr.getHeight()) {
			throw new IllegalArgumentException("Array size does not match");
		} else if (data[0].length != wr.getWidth()) {
			throw new IllegalArgumentException("Array size does not match");
		}

		for (int row = 0; row < wr.getHeight(); row++) {
			for (int col = 0; col < wr.getWidth(); col++) {
				pixelValues[0] = data[row][col].red;
				pixelValues[1] = data[row][col].green;
				pixelValues[2] = data[row][col].blue;
				wr.setPixel(col, row, pixelValues);
			}
		}
	}

	// add a method to compute a new image given weighted averages
	public void transformImage(int[][] weights) {
		
		int[][] weight = weights;
		
		Pixel[][] data = this.getData();
		Pixel[][] data2 = this.getData(); // this way data2
		// has automatically the same size as data
		
//		int[] pixelValues = new int[3];
		
		int matrixTotal = 0; //the sum of the filter matrix
		
		WritableRaster wr = this.myImage.getRaster();
		
		if (data.length != wr.getHeight()) {
			throw new IllegalArgumentException("Array size does not match");
		} else if (data[0].length != wr.getWidth()) {
			throw new IllegalArgumentException("Array size does not match");
		}

		
		for(int i = 0; i < weight.length; i++){
			for(int j = 0; j < weight[i].length; j++){
				matrixTotal += weight[i][j];
			}
		}
		
		if(matrixTotal <= 0){
			matrixTotal = 1;
		}
		
		
		for(int j = 1; j < wr.getHeight()-1; j++){
			for(int i = 1; i < wr.getWidth()-1; i++){
				
				int red = 0;
				int blue = 0;
				int green = 0;
				
				
				for(int wOne = 0; wOne < weight.length; wOne++){
					for(int wTwo = 0; wTwo < weight[wOne].length; wTwo++){
						red += weight[wOne][wTwo] * data[j - 1 + wOne][i - 1 + wOne].red;
						blue += weight[wOne][wTwo] * data[j - 1 + wOne][i - 1 + wOne].blue;
						green += weight[wOne][wTwo] * data[j - 1 + wOne][i - 1 + wOne].green;
						
					}
				}
				
				
				
				if(red <= 0){
					red = 0;
				}
				if(blue <= 0){
					blue = 0;
				}
				if(green <= 0){
					green = 0;
				}
				
				int redColorOutput = red/matrixTotal;
				int blueColorOutput = blue/matrixTotal;
				int greenColorOutput = green/matrixTotal;
				
				if(redColorOutput >= 255){
					redColorOutput = 255;
				}
				if(blueColorOutput >= 255){
					blueColorOutput = 255;
				}
				if(greenColorOutput >= 255){
					greenColorOutput = 255;
				}
				
				
				data2[j][i].red = redColorOutput;
				data2[j][i].blue = blueColorOutput;
				data2[j][i].green = greenColorOutput;
				

			}

		}

		this.setData(data2);
	}
	
}















