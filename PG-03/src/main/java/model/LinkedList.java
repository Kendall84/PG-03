package model;

public class LinkedList<T> implements List<T> {
    private Node<T> head; //Inicio de la lista
    private Node<T> tail; //Final de la lista

    public LinkedList(){
        head = null;
        tail = null;
    }


    @Override
    public int size() throws ListException {
        if(isEmpty()){
            throw new ListException("Linked List is empty");

        }
        Node<T> aux = head;
        int count = 0 ;
        while(aux != null){
            count++;
            aux = aux.next;
        }
        return count;
    }

    @Override
    public void clear() {
        head = tail = null;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public void add(T element) {
        Node<T> node = new Node<>(element);
        if (head == null) {
            head = node;
            tail = node;
        }else { //significa que head apunta a un nodo existente
            Node<T> aux = head;
            //Me muevo por la lista hasta alcanzar el ultimo elemento
            while(aux.next != null) {
                aux = aux.next;//Lo mueve al siguiente nodo
            }
            //Cuando se sale del while aux.next == nulo
            aux.next = node;
            tail = node;// Lo ponemos a apuntar al ultimo nodo de la lista
        }

    }

    @Override
    public void addFirst(T element) {
        Node<T> node = new Node<>(element);
        node = head;
        head = node; // para que el nuevo nodo quede de primero
    }

    @Override
    public void addLast(T element) {
        add(element); // el metodo add por default agrega al final
    }

    @Override
    public void addInSortedList(T element) {

    }

    @Override
    public void remove(T element) throws ListException {

    }

    @Override
    public T removeFirst() throws ListException {
        return null;
    }

    @Override
    public T removeLast() throws ListException {
        return null;
    }

    @Override
    public boolean contains(T element) throws ListException {
        if (isEmpty()) {
            throw new ListException("Linked List is empty");

        }
        Node<T> aux = head;//Aux para moverme por la lista
        while(aux != null){
            if(equals(aux.data, element)) return true;
            aux = aux.next;
        }
        return false;
    }


    @Override
    public void sort() throws ListException {

    }

    @Override
    public int indexOf(T element) throws ListException {
        if(isEmpty()){
            throw new ListException("Linked List is empty");
        }
        Node<T> aux = head;
        int index = 1; //El indice de la lista enlazada inicia en 1
        while(aux != null){
             if(equals(aux.data, element)) return index;
            index++;
            aux = aux.next;
        }
        return -1; //Indica que no encontró el elemento
    }

    @Override
    public T getFirst() throws ListException {
        if(isEmpty()){
            throw new ListException("Linked List is empty");
        }
        return head.data;
    }

    @Override
    public T getLast() throws ListException {
        if (isEmpty()) {
            throw new ListException("Linked List is empty");
        }
        return tail.data;
    }

    @Override
    public T getPrev(T element) throws ListException {

    }

    @Override
    public T getNext(T element) throws ListException {
        return null;
    }

    @Override
    public T get(int index) throws ListException {
        if (isEmpty()) {
            throw new ListException("Linked List is empty");
        }
        Node<T> aux = head;
        int count = 1;
        while (aux != null) {
            if (count++==index) return aux.data;
            aux= aux.next;
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("HEAD ->");
        Node<T> aux = head;
        while( aux.next != null){
            sb.append(("[")).append(aux.data).append("]");
            if(aux.next != null) sb.append(" -> ");
            aux = aux.next;
        }
        sb.append(" -> NULL");//Que apunte a nulo en el ultimo nodo
        return sb.toString();
    }

    //=====AYUDAS=====


    private boolean equals(T a, T b){
        return a==null ? b==null : a.equals(b);
    }

    public Node<T> getNode(T element) throws ListException {
        if(isEmpty()){
            throw new ListException("Linked List is empty");
        }
        Node<T> aux = head;
        while(aux != null){
            if(equals(aux.data, element)) return aux;
            aux = aux.next;
        }
        return null;
    }

}
