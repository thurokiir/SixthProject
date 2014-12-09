import java.util.*;

public class TimerFilter implements Filter {
	Timer timer;
	int[][] weights = { { 1, 2, 1 }, { 2, 4, 2 }, { 1, 2, 1 } };
	int seconds;
	int runs;

	public TimerFilter(int seconds, int runs) {
		this.seconds = seconds;
		this.runs = runs;
	}

	public void filter(PixelImage pi) {
		final PixelImage PIE = pi;
		for (int i = 0; i < runs; i++) {
			PIE.transformImage(weights);
			try {
				wait(seconds * 1000 * i);
			} catch (InterruptedException e) {
				// TODO auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
