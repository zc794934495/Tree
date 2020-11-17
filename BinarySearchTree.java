public class BinarySearchTree {
    public static void main(String[] args) {
        int[] arr = new int[] {5,3,4,1,7,8,2,6,0,9};
        BinarySearchTree binarySearchTree = new BinarySearchTree();
        for (int value : arr) {
            binarySearchTree.insert(value);
        }
        binarySearchTree.inOrder();
        binarySearchTree.preOrder();
        System.out.println(binarySearchTree.search(5).key);
        binarySearchTree.remove(5);
        binarySearchTree.inOrder();
        binarySearchTree.preOrder();
    }
    public static class Node {
        int key;
        Node left;
        Node right;

        public Node(int key) {
            this.key = key;
        }
    }
    Node root = null;
    //插入
    public boolean insert(int key) {
        if(root == null) { //树为空
            root = new Node(key);
            return true;
        }
        Node cur = root;
        Node parent = null;
        //找到该放的位置
        while(cur != null) {
            if(cur.key > key) {
                parent = cur;
                cur = cur.left;
            }else if(cur.key < key) {
                parent = cur;
                cur = cur.right;
            }else {
                return false;
            }
        }
        //判断该放parent的左还是右
        if(parent.key > key) {
            parent.left = new Node(key);
        }else {
            parent.right = new Node(key);
        }
        return true;
    }
    //查找
    public Node search(int key) {
        Node cur = root;
        while (cur != null) {
            if(cur.key > key) {
                cur = cur.left;
            }else if(cur.key < key){
                cur = cur.right;
            }else {
                return cur;
            }
        }
        return null;
    }
    //删除
    public boolean remove(int key) {
        Node cur = root;
        Node parent = null;
        //找到要删除的节点
        while (cur != null) {
            if(cur.key > key) {
                parent = cur;
                cur = cur.left;
            }else if(cur.key < key){
                parent = cur;
                cur = cur.right;
            }else {
                break;
            }
        }
        if(cur == null) { //没找到
            return false;
        }
        if(cur.left == null && cur.right == null) { //要删除的节点没有孩子
            //要删的节点是root，需要单独处理，因为parent为空
            if(cur == root) {
                root = null;
                return true;
            }
            if(parent.left == cur) {
                parent.left = null;
            }else {
                parent.right = null;
            }
        }else if(cur.left != null && cur.right != null) { //要删除的节点既有左孩子，又有右孩子
            Node temp = cur.right; //temp指向cur的右子树
            Node tempParent = cur;
            //寻找中序下的第一个结点
            while(temp.left != null) {
                tempParent = temp;
                temp = temp.left;
            }
            //temp的值填充到cur
            cur.key = temp.key;
            //如果cur的右孩子就是右子树中序下的第一个结点：
            //     tempParent的右孩子指向temp的右子树
            //否则：
            //     tempParent的左孩子指向temp的右子树
            if(tempParent == cur) {
                tempParent.right = temp.right;
            }else {
                tempParent.left = temp.right;
            }


        }else if(cur.left != null){ //要删除的节点只有左子树
            //要删的节点是root，需要单独处理，因为parent为空
            if(cur == root) {
                root = cur.left;
                return true;
            }
            if(parent.left == cur) {
                parent.left = cur.left;
            }else {
                parent.right = cur.left;
            }
        }else { //要删除的节点只有右子树
            //要删的节点是root，需要单独处理，因为parent为空
            if(cur == root) {
                root = cur.right;
                return true;
            }
            if(parent.left == cur) {
                parent.left = cur.right;
            }else {
                parent.right = cur.right;
            }
        }
        return true;
    }
    public void inOrder() {
        inOrder2(root);
        System.out.println();
    }
    private void inOrder2(Node root) {
        if(root == null) {
            return;
        }
        inOrder2(root.left);
        System.out.print(root.key + " ");
        inOrder2(root.right);
    }
    public void preOrder() {
        preOrder2(root);
        System.out.println();
    }
    private void preOrder2(Node root) {
        if(root == null) {
            return;
        }
        System.out.print(root.key + " ");
        preOrder2(root.left);
        preOrder2(root.right);
    }
}
