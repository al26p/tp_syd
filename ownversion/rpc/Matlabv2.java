package rpc;
public class Matlab implements IMatlab{
  private int i;

  public Matlab(int i) {
    this.i = i;
  }

  public Result calcul(int in) {
    return new Result(in * this.i);
  }
}
public static void main(String[] arg){
java.net.ServerSocket sos = new java.net.ServerSocket(8);
java.net.Socket s = sos.accept();
java.io.DataInputStream dis = new java.io.DataInputStream(s.getInputStream());
java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(s.getOutputStream());

}
