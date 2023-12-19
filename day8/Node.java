class Node {
	String name;
	Node left;
	Node right;

	Node(String name) {
		this.name = name;
	}

	Node(String name, Node left, Node right) {
		this.name = name;
		this.left = left;
		this.right = right;
	}

	public String getName() {
		return name;
	}

	public Node getLeft() {
		return left;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public Node getRight() {
		return right;
	}

	public void setRight(Node right) {
		this.right = right;
	}

	@Override
	public String toString() {
		String s = "Node: " + name;
		if (left != null) s = s + ",   Left: " + left.getName();
		if (right != null) s = s + ",   Right: " + right.getName();
		return s;
	}
}
