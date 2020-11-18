public class AVLTree {
    public static void main(String[] args) {
        int[] arr = new int[] {4,3,6,5,7,8};//需要左旋
        int[] arr2 = new int[] {10,12,8,9,7,6};//需要右旋
        int[] arr3 = new int[] {10,11,7,6,8,9};//需要双旋转
        AVLTree avlTree = new AVLTree();
        for (int value : arr3) {
            avlTree.insert(value);
        }
        avlTree.inOrder();
        avlTree.preOrder();
        //打印高度差
        System.out.println(avlTree.heightDifference());
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
        //当添加完一个节点后，右子树-左子树大于1
        if(height(root.right) - height(root.left) > 1) {
            //当前节点的右孩子不为空，并且右孩子的左子树高度大于右孩子的右子树的节点，先进行右旋转
            if(root.right != null && height(root.right.left) > height(root.right.right)) {
                rightRotate(root.right);
            }
            leftRotate(root);
            return true;
        }
        //当添加完一个节点后，左子树-右子树大于1
        if(height(root.left) - height(root.right) > 1) {
            //当前节点的左孩子不为空，并且左孩子的右子树高度大于左孩子的左子树的节点，先进行左旋转
            if(root.left != null && height(root.left.right) > height(root.left.left)) {
                leftRotate(root.left);
            }
            rightRotate(root);
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
    //中序遍历
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
    //前序遍历
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
    //返回左右子树高度差
    public int heightDifference() {
        if(root == null) {
            return 0;
        }
        return Math.abs(height(root.left) - height(root.right));
    }
    //返回树的高度
    private int height(Node root) {
        if(root == null) {
            return 0;
        }
        return Math.max(height(root.left),height(root.right)) + 1;
    }
    //左旋转
    private void leftRotate(Node root) {
        //以当前根节点的值创建新的节点
        Node newNode = new Node(root.key);
        //把新的节点的左子树设置为当前节点的左子树
        newNode.left = root.left;
        //把新的节点的右子树设置为当前节点的右子树的左子树
        newNode.right = root.right.left;
        //把当前节点的值替换为当前节点右孩子的值
        root.key = root.right.key;
        //把当前节点的右子树设置为当前节点右子树的右子树
        root.right = root.right.right;
        //把当前节点的左子树设置为新的节点
        root.left = newNode;
    }
    //右旋转
    private void rightRotate(Node root) {
        Node newNode = new Node(root.key);
        newNode.right = root.right;
        newNode.left = root.left.right;
        root.key = root.left.key;
        root.left = root.left.left;
        root.right = newNode;
    }
}
