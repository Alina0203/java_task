package by.mbicycle.calculator;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;

public class Runner {

	public static void main(String[] args) throws IOException {
		Scanner scanner = null;
		scanner = new Scanner(System.in);
		while (scanner.hasNext()) {
			String expression = scanner.next();
			System.out.println(expression);
			// System.out.print(Operation.calculate(expression)+ "\n");
			BigDecimal result = Operation.calculate(expression);
			System.out.println(result);
		}
	}
}