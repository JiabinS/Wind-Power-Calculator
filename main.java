import java.io.*;
import java.util.Scanner;

public class main {
	public static void main(String[] args) throws IOException { 
		int number, count = 0;
		double sum = 0;
		double average;
		// Adjust values under this line to effect calculation, blade size is in
		// m^2, in density in kg/m^2, eff as a percentage divided by 100,
		// priceofmwh in dollars and cents, and cost of install in full dollars only.
		double bladesize = 3500;
		// Blade area swept by GE 1.5s, the most common, in m^2
		double airdecensity = 1.225;
		double resultaswatts;
		double eff = .2; // ranges from something close to 0, to .593, the Betz's limit
						// http://en.wikipedia.org/wiki/Betz%27s_law
						// It's literally the highest possible, realistically we can expect to see .2 at the best.
		double priceofmwh = 39.4; // see http://www.powerisknowledge.com for updating price
		int costofturbine = 2000000; //Real cost of purchase varies greatly, see sources.

		// 
		// Read File In of Wind Speed
		// 

		File myFile = new File("WindAvgMPH.txt"); // Reads textfile in same directory (!) as driver in, wind speeds expected in MPH.
		Scanner inputFile = new Scanner(myFile);

		if (!myFile.exists()) {
			System.out.println("Error: file does not exist."); // If this is being thrown your input file is in the wrong directly or named incorrectly
			System.exit(0);
		}

		while (inputFile.hasNext()) { //iterate until at last item

			number = inputFile.nextInt(); // iterate through each entry in the text file, each time adding one to a counter
			count++;

			resultaswatts = .5	* eff* airdecensity	* bladesize * ((number * 0.44704) * (number * 0.44704) * (number * 0.44704)); //run wind power density equation for each point
			sum += resultaswatts; //add the result to the sum
			
			
		}
		average = sum / count; //find average in watts
		// THIS IS NOT THE AVERAGE WINDSPEED, BUT AVERAGE PRODUCED WATTS
		// do not ask about on stack exchange, you will feel like a fool. :(

		// 
		// Wind Density Results
		//
		
		System.out.println("You will generate " + average / 1000000+ " megawatt hours of power with one turbine."); //convert watts into megawatt hours
		System.out.println("This is " + ((average / 1000000) * 24)+ " megawatt hours per day."); // find day total
		System.out.println("This means the turbine is producing $"+ (((average / 1000000) * 24) * priceofmwh)+ " worth of power each day."); //find year total
		System.out.println("In one year the turbine will produce $"+ (((average / 1000000) * 24) * priceofmwh * 365)+ " worth of power in one year."); //find total saved in a year, assuming no leapyears, leap seconds, it's really not worth it
		System.out.println("It will take "	+ costofturbine	/ ((((average / 1000000) * 24) * priceofmwh * 365))	+ " years to make the installation cost back in power savings."); // a rough estimate on when you can expect to see return on investment

	} 
} 