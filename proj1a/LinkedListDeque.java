public class LinkedListDeque<AClass> {
    private class IntNode {
        public IntNode pre;
        public AClass item;
        public IntNode next;

        public IntNode(IntNode preNode, AClass x, IntNode nextNode) {
            pre = preNode;
            item = x;
            next = nextNode;
        }
    }
    public IntNode sentinal;
    public int size;
    public LinkedListDeque(){
        sentinal = new IntNode(null, null, null);
        size = 0;
    }
    public boolean isEmpty(){
        return size == 0;
    }
    public void addFirst(AClass x){
       if (isEmpty()){
            IntNode newNode = new IntNode(sentinal, x, sentinal);
            sentinal.next = newNode;
            sentinal.pre = newNode;
        }else{
           IntNode p = sentinal.next;
           IntNode newNode = new IntNode(sentinal, x, p);
           p.pre = newNode;
           sentinal.next = newNode;
       }
        size += 1;
    }
    public void addLast(AClass x){
        if (isEmpty()){
            IntNode newNode = new IntNode(sentinal, x, sentinal);
            sentinal.next = newNode;
            sentinal.pre = newNode;
        }else{
            IntNode p = sentinal.pre;
            IntNode newNode = new IntNode(p, x, sentinal);
            p.next =newNode;
            sentinal.pre = newNode;
        }
        size += 1;
    }
    public int size(){
        return size;
    }
    public void printDeque(){
        IntNode current = sentinal.next;
        while (current != sentinal){
            System.out.print(current.item);
            System.out.print(" ");
            current = current.next;
        }
    }
    public AClass removeFirst(){
        if (isEmpty()){
            return null;
        }
        IntNode p1 = sentinal.next;
        AClass shouldReturn = p1.item;
        p1 = p1.next;
        sentinal.next = p1;
        p1.pre = sentinal;
        size -= 1;
        return shouldReturn;
    }
    public AClass removeLast() {
        if (isEmpty()){
            return null;
        }
        IntNode p1 = sentinal.pre;
        AClass shouldReturn = p1.item;
        p1 = p1.pre;
        p1.next =sentinal;
        sentinal.pre= p1;
        size -= 1;
        return shouldReturn;
    }
    public AClass get(int index) {
        if (index > size - 1){
           return null;
        }
        IntNode current = sentinal.next;
        for (int i = 0; i<index; i++) {
            current = current.next;
        }
        return current.item;
    }
    public AClass getRecursive(int index){
        if(index > size -1){
            return null;
        }
        return getRecursiveHelper(sentinal.next, index);
    }
    private AClass getRecursiveHelper(IntNode start, int index){
       if(index == 0) {
           return start.item;
       }
       return getRecursiveHelper(start.next, index - 1);
    }
}
