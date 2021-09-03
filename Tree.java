
public class Tree extends BTreePrinter {
    Node root;

    public Tree(Node root) {
        this.root = root;
    }
    public Tree() {
    }
    public void printTree() {
        // แสดงผลต้นไม้ออกมาผ่านฟังก์ชัน printTree() จาก BTreePrinter
        if (root == null)
            System.out.println("Empty tree!!!");
        else
            super.printTree(root);
    }
    public static void printNode(Node node) {
        // แสดงค่าของ Node
        if (node == null)
            System.out.println("Node not found!!!");
        else
            System.out.println(node.key);
    }
    public Node find(int search_key) {
        // หา Node ที่มี key == search_key ใน BinaryTree นี้ โดยจะเรียก recursive function
        return find(root, search_key);
    }
    public static Node find(Node node, int search_key) {
        // วิธีการ
        // 1. หาก Node ที่เราอยู่ key == search_key ก็ return Node นั้นเลย
        // 2. หากไม่ จะมีอยู่ 2 กรณี
        //      - หาก search_key > node.key ให้ไปยัง node.right
        //      - หาก search_key < node.key ให้ไปยัง node.left
        // 3. หากถึง leaf ของ BinaryTree แล้วยังไม่เจอ Node ที่ต้องการ ก็ return null

        // ดักกรณี root == null
        if (node == null)
            return null;

        if (node.key == search_key)
            return node;
        else if (node.key < search_key && node.right != null)
            return find(node.right, search_key);
        else if (node.key > search_key && node.left != null)
            return find(node.left, search_key);
        else
            return null;
    }
    public Node findMin() {
        // หา Node ที่มีค่าต่ำสุดของ BinaryTree
        return findMin(root);
    }
    public static Node findMin(Node node) {
        // หา Node ที่มีค่าต่ำสุดใน subtree ของ "node"
        // หลักการ : Node ที่อยู่ทางซ้ายสุด จะมีค่า key ต่ำสุด
        // วิธีการ
        // 1. ถ้า Node นั้นมี left child ก็จะไปยัง node.left ไปเรื่อยๆ
        // 2. หากไม่มี left child ก็ return Node นั้นได้เลย
        if (node.left == null)
            return node;
        return findMin(node.left);
    }
    public Node findMax() {
        // หา Node ที่มีค่าต่ำสุดของ BinaryTree
        return findMax(root);
    }
    public static Node findMax(Node node) {
        // หา Node ที่มีค่ามากสุดใน subtree ของ "node"
        // หลักการ : Node ที่อยู่ทางขวาสุด จะมีค่า key สูงสุด
        // วิธีการ
        // 1. ถ้า Node นั้นมี right child ก็จะไปยัง node.right ไปเรื่อยๆ
        // 2. หากไม่มี right child ก็ return Node นั้นได้เลย
        if (node.right == null)
            return node;
        return findMax(node.right);
    }
    public Node findClosestLeaf(int search_key) {
        // หา Parent ของ Node ที่มี key == search_key
        return findClosestLeaf(root, search_key);
    }
    public static Node findClosestLeaf(Node node, int search_key) {
        // หลักการ : ใช้หลักการเดียวกับ find() และจะ return Node นั้น
        // ก็ต่อเมื่อไปต่อไม่ได้
        // วิธีการ
        // 1. (don't care) หาก Node ที่เราอยู่ key == search_key ก็ return Node นั้นเลย
        // 2. หากไม่ จะมีอยู่ 2 กรณี
        //      - หาก search_key > node.key
        //          - ถ้า node.right == null ก็ return Node นั้นเลย
        //          - ถ้าไม่ ให้ไปยัง node.right ต่อ
        //      - หาก search_key < node.key
        //          - ถ้า node.left == null ก็ return Node นั้นเลย
        //          - ถ้าไม่ ให้ไปยัง node.left ต่อ

        // ดักกรณี root == null
        if (node == null)
            return null;

        if (node.key > search_key) {
            if (node.left != null)
                return findClosestLeaf(node.left, search_key);
            else
                return node;
        } else if (node.key < search_key) {
            if (node.right != null)
                return findClosestLeaf(node.right, search_key);
            else
                return node;
        }
        return null;
    }
    public Node findClosest(int search_key) {
        // หา Node ที่มีค่าใกล้เคียงกับ search_key มากที่สุด
        // หลักการ : เราจะไล่ Node เหมือนกับ find() แต่จะทำการเก็บ min_diff และ update
        // ค่าที่ใกล้เคียงที่สุด (current) ไปด้วย
        // วิธีการ
        // 1. สร้าง Pointer ขึ้นมา 2 ตัว
        //      - current ขึ้นมาไว้ใช้ในการไล่ Node
        //      - closest ไว้เก็บ Node ที่
        // 2. ในแต่ละ loop เราจะเช็คว่า หาก |current.key - searh_key| < min_diff 
        //    ก็จะ update ค่า min_diff และ closest ใหม่
        // 3. หาก current.key == search_key ก็ break เลย (min_diff == 0 ซึ่งน้อยสุดแน่นอน)
        // 4. หากไม่ จะมีอยู่ 2 กรณี
        //      - หาก search_key > current.key ให้ไปยัง current.right
        //      - หาก search_key < current.key ให้ไปยัง current.left
        // 5. ทำไปเรื่อยจนกว่า current == null (leaf ของ BinaryTree)

        Node current, closest;
        closest = current = root;
        int min_diff = Integer.MAX_VALUE;
        while (current != null) {
            if (Math.abs(current.key - search_key) < min_diff) {
                min_diff = Math.abs(current.key - search_key);
                closest = current;
            }
            if (current.key < search_key)
                current = current.right;
            else if (current.key > search_key)
                current = current.left;
            else
                break;
        }
        return closest;
    }
    public void insert(int key) {
        // เพิ่ม Node ใหม่เข้า BinaryTree
        // หลักการ : ทำการหา findClosestLeaf(root , key) และ นำ Node ใหม่ไปเชื่อม
        // วิธีการ :
        // 1. เรียก findClosestLeaf() (Parent) และ สร้าง Node ใหม่ (Child)
        // 2. ถ้า Parent == null ก็ให้ Child เป็น root ใหม่
        // 3. ถ้า Child มีค่ามากกว่า Parent ก็ให้ Child เป็นลูกทางซ้ายของ Parent
        //    ถ้า Child มีค่าน้อยกว่า Parent ก็ให้ Child เป็นลูกทางขวาของ Parent
        Node Parent = findClosestLeaf(root, key);
        Node Child = new Node(key);

        if (Parent == null)
            root = Child;
        else {
            Child.parent = Parent;
            if (Parent.key > key)
                Parent.left = Child;
            else
                Parent.right = Child;
        }
    }
    public void printPreOrderDFT() {
        // Depth First Traversal แบบ PreOrder
        System.out.print("PreOrder DFT node sequence [ ");
        printPreOrderDFT(root);
        System.out.println("]");
    }
    public static void printPreOrderDFT(Node node) {
        // หลักการ: จะแสดงค่าของ Node ก่อน แล้วค่อยไล่ไปยัง ลูกทางซ้าย (node.left) และ ลูกทางขวา (node.right)
        if (node == null)
            return;
        System.out.print(node.key + " ");
        printPreOrderDFT(node.left);
        printPreOrderDFT(node.right);
    }
    public void printInOrderDFT() {
        // Depth First Traversal แบบ InOrder
        System.out.print("InOrder DFT node sequence [ ");
        printInOrderDFT(root);
        System.out.println("]");
    }
    public static void printInOrderDFT(Node node) {
        // หลักการ: จะไล่ไปยังลูกทางซ้าย (node.left) ก่อน จากนั้นแสดงค่าของ Node แล้วค่อยไล่ไปยังลูกทางขวา (node.right)
        if (node == null)
            return;
        printInOrderDFT(node.left);
        System.out.print(node.key + " ");
        printInOrderDFT(node.right);
    }
    public void printPostOrderDFT() {
        // Depth First Traversal แบบ PostOrder
        System.out.print("PostOrder DFT node sequence [ ");
        printPostOrderDFT(root);
        System.out.println("]");
    }
    public static void printPostOrderDFT(Node node) {
        // หลักการ: จะไล่ไปยังลูกทางซ้าย (node.left) และ ลูกทางขวา (node.right) ก่อน จากนั้นแสดงค่าของ Node
        if (node == null)
            return;
        printPostOrderDFT(node.left);
        printPostOrderDFT(node.right);
        System.out.print(node.key + " ");
    }
    public static int height(Node node) {
        // ความสูงของ node = max(ความสูงของลูกทางซ้าย , ความสูงของลูกทางขวา) + 1  
        // base case (node == null): height = -1 
        // recursive case : height(node) = max(height(node.left) , height(node.right)) + 1
        if (node == null)
            return -1;
        return Math.max(height(node.left), height(node.right)) + 1;
    }
    public static int size(Node node) {
        // size ของ node = size ของลูกทางซ้าย + size ของลูกทางขวา + 1  
        // base case (node == null): size = 0 
        // recursive case : size(node) = size(node.left) + size(node.right) + 1
        if (node == null)
            return 0;
        return size(node.left) + size(node.right) + 1;
    }
    public static int depth(Node root, Node node) {
        // หลักการ: ไล่จาก node เริ่มต้นไปยัง root
        // base case (node == root): depth = 0
        // recursive case : depth(root , node) = depth(root , node.parent) + 1  
        if (node == null)
            return -1;
        if (node == root)
            return 0;
        return depth(root, node.parent) + 1;
    }
    public int height() {
        // หา height ของ BinaryTree
        return height(root);
    }
    public int size() {
        // หา size ของ BinaryTree
        return size(root);
    }
    public int depth() {
        // หา depth ของ BinaryTree ซึ่ง depth ของ Tree == height ของ Tree
        return height(root);
    }
    public Node findKthSmallest(int k) {
        // หา Node ลำดับที่ k โดยเรียงตาม key ของ แต่ละ Node
        return findKthSmallest(root, k);
    }
    public static Node findKthSmallest(Node node, int k) {
        // ตาม pseudocode ใน slide
        // ถ้า k = l + 1 , return root
        // ถ้า k < l + 1 , return findKthSmallest(node.left, k)
        // ถ้า k > l + 1 , return findKthSmallest(node.right, k - l - 1)
        int l = size(node.left);
        if (k == l + 1)
            return node;
        else if (k < l + 1)
            return findKthSmallest(node.left, k);
        else
            return findKthSmallest(node.right, k - l - 1);
    }
    public static Node findNext(Node node) {
        // หา Node ลำดับถัดไป โดยเรียงตาม key ของ แต่ละ Node
        // ตาม pseudocode ใน slide
        if (node.right != null)
            return leftDescendant(node.right);
        else
            return rightAncestor(node);
    }
    public static Node leftDescendant(Node node) {
        // ตาม pseudocode ใน slide
        if (node.left == null)
            return node;
        else
            return leftDescendant(node.left);
    }
    public static Node rightAncestor(Node node) {
        // ตาม pseudocode ใน slide
        if (node.parent == null)
            return null;
        if (node.key < node.parent.key)
            return node.parent;
        else
            return rightAncestor(node.parent);
    }
    public List rangeSearch(int x, int y) {
        // หา Node ที่มีค่าอยู่ในช่วง x ถึง y
        // วิธีการ
        // 1. หา Node ที่ใกล้เคียงค่า x ที่สุด (findClosest())
        // 2. จากนั้น เช็คว่าอยู่ในช่วง [x,y] หรือไม่ ถ้าอยู่ ให้ append เข้า List
        // 3. ให้วนลูปไปเรื่อยๆ (findNext(Node)) จนกว่า Node จะมีค่าเกิน y
        List L = new List(100);
        Node N = findClosest(x);

        while (N != null && N.key <= y) {
            if (N.key >= x)
                L.append(N);
            N = findNext(N);
        }
        return L;
    }
    public void delete(int key) {
        // กรณีทั่วไป
        //      1. Empty tree
        //      2. Key not found
        // หาก Node ที่จะลบเป็น root
        //      3. Root ไม่มี child - ลบได้เลย (root = null)
        //      4-5. Root มี child 1 ตัว (left child or right child) ให้ left/right child ขึ้นมาแทน 
        //      6. Full root (left child or right child) หา Node ที่มี key น้อยที่สุดของ right-subtree 
        //         แล้วนำมาแทน root จากนั้น จึงลบ Node นั้นออก

        Node del = find(key);

        // Empty tree
        if (root == null)
            System.out.println("Empty Tree!!!");
        // Key not found
        else if (del == null)
            System.out.println("Key not found!!!");
        // Key found && key == root
        else if (del == root) {
            // Root ไม่มี child
            if (root.left == null && root.right == null)
                root = null;
            // Root มี child อยู่ทางด้านซ้าย
            else if (root.left != null && root.right == null) {
                Node newRoot = root.left;
                newRoot.parent = null;
                root.left = null;
                root = newRoot;
            }
            // Root มี child อยู่ทางด้านขวา 
            else if (root.left == null && root.right != null) {
                Node newRoot = root.right;
                newRoot.parent = null;
                root.right = null;
                root = newRoot;
            } 
            // Root เป็น Full node
            else {
                Node min = findMin(root.right);
                int min_value = min.key;
                delete(min);
                root.key = min_value;
            }
        } 
        // Key found && key != root
        else {
            delete(del);
        }
    }
    public static void delete(Node node) {
        // หาก Node ที่จะลบไม่ใช่ root
        //      7-8. Node ไม่มี child  -> ให้ลบ node นี้ได้เลย โดยตัดสาย parent ออก 
        //      9-12. Node มี child 1 ตัว -> นำ child ขึ้นมาแทน Node นี้
        //      13. Full node -> า Node ที่มี key น้อยที่สุดของ right-subtree 
        //         แล้วนำมาแทน root จากนั้น จึงลบ Node นั้นออก

        // Node ไม่มี child
        if (node.left == null && node.right == null) {
            Node par = node.parent;
            node.parent = null;
            // par.key < node.key - Node อยู่ทางขวาของ Parent
            // par.key > node.key - Node อยู่ทางซ้ายของ Parent
            if (par.key < node.key)
                par.right = null;
            else
                par.left = null;
        } 
        // Node มี child อยู่ทางด้านขวา
        else if (node.left == null && node.right != null) {
            Node par = node.parent;
            Node child = node.right;
            node.parent = null;
            node.right = null;
            // par.key < node.key - child อยู่ทางขวาของ Parent
            // par.key > node.key - child อยู่ทางซ้ายของ Parent
            if (par.key < node.key)
                par.right = child;
            else
                par.left = child;
            child.parent = par;
        } 
        // Node มี child อยู่ทางด้านซ้าย
        else if (node.left != null && node.right == null) {
            Node par = node.parent;
            Node child = node.left;
            node.parent = null;
            node.right = null;
            // par.key < node.key - child อยู่ทางขวาของ Parent
            // par.key > node.key - child อยู่ทางซ้ายของ Parent
            if (par.key < node.key)
                par.right = child;
            else
                par.left = child;
            child.parent = par;
        } 
        // Full node
        else {
            Node min = findMin(node.right);
            int min_value = min.key;
            delete(min);
            node.key = min_value;
        }
    }

}
