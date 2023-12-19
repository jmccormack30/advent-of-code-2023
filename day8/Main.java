import java.io.*;
import java.util.*;

class Main {
	public static void main(String[] args) throws Exception {
		String fileName = "input.txt";

		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			long startTime = System.nanoTime();
			long value = decodeFile_Part1(br);
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
