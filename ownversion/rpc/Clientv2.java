package rpc;
public class Clientv2 {
  public static void main(String [] arg) { 
java.net.Socket s = new java.net.Socket('localhost',8080);
java.io.DataOutputStream dos = new java.io.DataOutputStream(s.getOutputStream());
java.io.ObjectInputStream ois = new java.io.ObjectInputStream(s.getInputStream());

    dos.writeUTF("constructeur");
dos.writeInt(10);

    dos.writeUTF("calcul");
dos.writeInt("3");

    System.out.println("->" + res);
  }
}
