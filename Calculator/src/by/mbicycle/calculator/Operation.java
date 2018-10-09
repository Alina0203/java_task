package by.mbicycle.calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;

public class Operation {
	public static final HashMap<String, Integer> MATH_OPERATIONS = new HashMap<>();
	static {
		MATH_OPERATIONS.put("*", 1);
		MATH_OPERATIONS.put("/", 1);
		MATH_OPERATIONS.put("%", 1);
		MATH_OPERATIONS.put("+", 2);
		MATH_OPERATIONS.put("-", 2);
	}

	public static String SortingStation(String expression, Map<String, Integer> operations, String leftBracket, String rightBracket) {

		if (expression == null || expression.length() == 0) {
			throw new IllegalArgumentException("Expression isn't specified.");
		}
		if (operations == null || operations.isEmpty()) {
			throw new IllegalArgumentException("Operations aren't specified.");
		}

		List<String> out = new ArrayList<>();
		Stack<String> stack = new Stack<>();

		expression = expression.replace(" ", "");

		Set<String> operationSymbols = new HashSet<>(operations.keySet());
		operationSymbols.add(leftBracket);
		operationSymbols.add(rightBracket);

		int index = 0;

		boolean findNext = true;
		while (findNext) {
			int nextOperationIn = expression.length();
			// may throw an error!
			String nextOperation = "";
			for (String operation : operationSymbols) {
				int i = expression.indexOf(operation, index);
				if (i >= 0 && i < nextOperationIn) {
					nextOperation = operation;
					nextOperationIn = i;
				}
			}
			if (nextOperationIn == expression.length()) {
				findNext = false;
			} else {
				if (index != nextOperationIn) {
					out.add(expression.substring(index, nextOperationIn));
				}
				if (nextOperation.equals(leftBracket)) {
					stack.push(nextOperation);
				} else if (nextOperation.equals(rightBracket)) {
					while (!stack.peek().equals(leftBracket)) {
						out.add(stack.pop());
						if (stack.isEmpty()) {
							throw new IllegalArgumentException("invalid exprexxion");
						}

					}
					stack.pop();
				} else {
					while (!stack.empty() && !stack.peek().equals(leftBracket) && (operations.get(nextOperation) >= operations.get(stack.peek()))) {
						out.add(stack.pop());
					}
					stack.push(nextOperation);
				}
				index = nextOperationIn + nextOperation.length();
			}
		}
		if (index != expression.length()) {
			out.add(expression.substring(index));
		}
		while (!stack.empty()) {
			out.add(stack.pop());
		}
		StringBuffer result = new StringBuffer();
		if (!out.isEmpty())
			result.append(out.remove(0));
		while (!out.isEmpty())
			result.append(" ").append(out.remove(0));
		System.out.println(result.toString());
		return result.toString();
	}

	public static String SortingStation(String expression, Map<String, Integer> operations) {
		return SortingStation(expression, operations, "(", ")");
	}

	public static BigDecimal calculate(String expression) {
		String rpn = SortingStation(expression, MATH_OPERATIONS);
		StringTokenizer tokenizer = new StringTokenizer(rpn, " ");
		Stack<BigDecimal> stack = new Stack<BigDecimal>();

		while (tokenizer.hasMoreTokens()) {
			try {
				String token = tokenizer.nextToken();
				if (!MATH_OPERATIONS.keySet().contains(token)) {
					stack.push(new BigDecimal(token));
				} else {
					BigDecimal second = stack.pop();
					BigDecimal first = stack.empty() ? BigDecimal.ZERO : stack.pop();
					if (token.equals("*")) {
						stack.push(first.multiply(second));
					} else if (token.equals("%")) {
						stack.push(first.multiply(second).divide(new BigDecimal(100)));
					} else if (token.equals("/")) {
						stack.push(first.divide(second, RoundingMode.HALF_UP));
					} else if (token.equals("+")) {
						stack.push(first.add(second));
					} else if (token.equals("-")) {
						stack.push(first.subtract(second));
					} else if (token.equals("%")) {
					}
				}
			} catch (ArithmeticException e) {
				e.printStackTrace();
			}
		}
		if (stack.size() != 1)
			throw new IllegalArgumentException("Expression syntax error.");
		return stack.pop();
	}

}