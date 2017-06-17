
public class SolutionFinder {

	private static Cube cube;
	
	public static void main(String[] args) {
		double sum = 0;
		
		long startTime = System.nanoTime();
		//System.out.println("Initializing...");
		cube = new Cube();
		//Scramble it up
		System.out.println("Scramble:" + cube.scramble("F2 D2 B' D2 L2 D2 B' R2 F' L2 F U B F2 R' D L' B U B F'"));
		System.out.println("Making the sunflower:");
		System.out.print(cube.makeSunflower());
		System.out.println();
		
		System.out.println("Making the white cross:");
		System.out.println(cube.makeCross());
		System.out.println("Made Cross: " + cube.testCross());
		
		System.out.println("Inserting the white corners:");
		System.out.println(cube.finishWhiteLayer());
		
		long endTime = System.nanoTime();
		long runtime = endTime - startTime;
		sum+=runtime;
		System.out.println("Done in " + (sum/1000000) + " milliseconds");
		System.out.println("\n\n\n\n");
		
	}
}



