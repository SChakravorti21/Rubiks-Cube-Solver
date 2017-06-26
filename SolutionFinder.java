
public class SolutionFinder {

	private static Cube cube;
	
	public static void main(String[] args) {
		double sum = 0;
		
		long startTime = System.nanoTime();
		System.out.println("Initializing...");
		cube = new Cube();
		//Scramble it up
		System.out.println("Scramble: " + cube.scramble("L R' U2 B2 L2 U2 B2 L D2 R' F2 D B F2 L' D U2 B R' U' L'") + "\n");
		
		System.out.println("Making the sunflower:");
		if(!cube.testCross())
			System.out.println(cube.makeSunflower() + "\n");
		
		System.out.println("Making the white cross:");
		if(!cube.testCross())
			System.out.println(cube.makeWhiteCross());
		System.out.println("Made Cross: " + cube.testCross() + "\n");
		
		System.out.println("Inserting the white corners:");
		System.out.println(cube.finishWhiteLayer());
		
		System.out.println("Finishing second layer:");
		System.out.println(cube.insertAllEdges());
		
		System.out.println("Making the yellow cross:");
		System.out.println(cube.makeYellowCross() + "\n");
		
		System.out.println("Orienting the last layer:");
		System.out.println(cube.orientLastLayer() + "\n");
		
		System.out.println("Permuting the last layer:");
		System.out.println(cube.permuteLastLayer() + "\n");
		
		long endTime = System.nanoTime();
		long runtime = endTime - startTime;
		sum+=runtime;
		System.out.println("Done in " + (sum/1000000) + " milliseconds" + "\n\n\n\n");
		
	}
}



