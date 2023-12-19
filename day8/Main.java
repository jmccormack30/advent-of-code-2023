import java.io.*;
import java.util.*;
import java.math.*;

class Main {
	public static void main(String[] args) throws Exception {
		String fileName = "input.txt";

		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			long startTime = System.nanoTime();
			BigInteger value = decodeFile_Part2(br);
			long endTime = System.nanoTime();
			System.out.println("\n" + value);
			System.out.println(getExecutionTimeString(startTime, endTime));
		}
		catch (FileNotFoundException fnfe) {
			System.out.println(fnfe.getMessage());
		}
		catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
	}

	private static int decodeFile_Part1(BufferedReader br) throws Exception {
		StringBuilder builder = new StringBuilder();
		String line;
		while ((line = br.readLine()) != null) {
			builder.append(line);
			builder.append(System.lineSeparator());
		}
		String inputStr = builder.toString();
		String[] arr = inputStr.split("\\n\\s*\\n");
		String instructionsStr = arr[0].trim();
		String nodesStr = arr[1];
		char[] instructions = instructionsStr.toCharArray();
		Map<String, Node> nodeMap = createNodeMap(nodesStr);
		updateNodes(nodeMap, nodesStr);
		return calculateSteps(nodeMap, instructions, nodeMap.get("AAA"), nodeMap.get("ZZZ"));
	}

	private static BigInteger decodeFile_Part2(BufferedReader br) throws Exception {
		StringBuilder builder = new StringBuilder();
		String line;
		while ((line = br.readLine()) != null) {
			builder.append(line);
			builder.append(System.lineSeparator());
		}
		String inputStr = builder.toString();
		String[] arr = inputStr.split("\\n\\s*\\n");
		String instructionsStr = arr[0].trim();
		String nodesStr = arr[1];
		char[] instructions = instructionsStr.toCharArray();
		Map<String, Node> nodeMap = createNodeMap(nodesStr);
		updateNodes(nodeMap, nodesStr);
		Set<Node> startNodes = getStartNodes_Part2(nodeMap, 'A');
		return calculateSteps_Part2(nodeMap, startNodes, instructions, 'Z');
	}

	private static Map<String, Node> createNodeMap(String nodesStr) {
		Map<String, Node> nodeMap = new HashMap<>();
		String[] nodeStrArr = nodesStr.split("\\n");
		for (String nodeStr: nodeStrArr) {
			String name = nodeStr.substring(0, 3);
			Node node = new Node(name);
			nodeMap.put(name, node);
		}
		return nodeMap;
	}

	private static void updateNodes(Map<String, Node> nodeMap, String nodesStr) {
		String[] nodeStrArr = nodesStr.split("\\n");
		for (String nodeStr: nodeStrArr) {
			String name = nodeStr.substring(0, 3);
			String leftName = nodeStr.substring(7, 10);
			String rightName = nodeStr.substring(12, 15);
			Node node = nodeMap.get(name);
			node.setLeft(nodeMap.get(leftName));
			node.setRight(nodeMap.get(rightName));
		}
	}

	private static int calculateSteps(Map<String, Node> nodeMap, char[] instructions, Node start, Node end) {
		int steps = 0;
		int i = 0;
		Node currentNode = start;
		while (currentNode != end) {
			if (i == instructions.length) i = 0;
			char direction = instructions[i];
			System.out.println(currentNode.getName() + " | " + i + " | " + direction);
			currentNode = ('L' == direction) ? currentNode.getLeft() : currentNode.getRight();
			i += 1;
			steps += 1;
		}
		System.out.println(currentNode.getName());
		return steps;
	}

	private static BigInteger calculateSteps_Part2(Map<String, Node> nodeMap, Set<Node> nodes, char[] instructions, char endChar) {
		BigInteger[] leastStepsArr = new BigInteger[6];
		Iterator<Node> iterator = nodes.iterator();
		for (int i = 0; i < nodes.size(); i++) {
			Node node = iterator.next();
			BigInteger leastSteps = calculateLeastSteps(nodeMap, instructions, node, endChar);
			leastStepsArr[i] = leastSteps;
		}
		return getLCM(leastStepsArr);
	}

	private static BigInteger calculateLeastSteps(Map<String, Node> nodeMap, char[] instructions, Node node, char endChar) {
		BigInteger steps = new BigInteger("0");
		int i = 0;
		Node curNode = node;
		while (endChar != curNode.getName().charAt(curNode.getName().length() - 1)) {
			if (i == instructions.length) i = 0;
			char direction = instructions[i];
			curNode = ('L' == direction) ? curNode.getLeft() : curNode.getRight();
			i += 1;
			steps = steps.add(BigInteger.ONE);
		}
		return steps;
	}

	private static BigInteger getLCM(BigInteger[] arr) {
		BigInteger b1 = findLCM(arr[0], arr[1]);
		BigInteger b2 = findLCM(arr[2], arr[3]);
		BigInteger b3 = findLCM(arr[4], arr[5]);

		BigInteger b4 = findLCM(b1, b2);
		return findLCM(b4, b3);
	}

	private static BigInteger findLCM(BigInteger a, BigInteger b) {
		return a.multiply(b).divide(findGCD(a, b));
	}

	private static BigInteger findGCD(BigInteger a, BigInteger b) {
		System.out.println(a + " | " + b);
		return a.gcd(b);
	}

	private static Set<Node> getStartNodes_Part2(Map<String, Node> nodeMap, char character) {
		Set<Node> startNodes = new HashSet<>();
		for (Node node: nodeMap.values()) {
			if (character == node.getName().charAt(node.getName().length() - 1)) {
				startNodes.add(node);
			}
		}
		return startNodes;
	}

	private static String getExecutionTimeString(long startTime, long endTime) {
		long elapsedTimeInNanoseconds = endTime - startTime;
    	double elapsedTimeInSeconds = (double) elapsedTimeInNanoseconds / 1_000_000_000;
		double minutes = Math.floor(elapsedTimeInSeconds / 60);
		double seconds = (elapsedTimeInSeconds % 60);
		int wholeSeconds = (int) seconds;
		double decimalSeconds = seconds - wholeSeconds;
		double milliseconds = Math.floor(decimalSeconds * 1000);
		return "Elapsed time: " + (int)minutes + " minutes, " + (int)seconds + " seconds, " + (int)milliseconds + " ms.";
	}
}
