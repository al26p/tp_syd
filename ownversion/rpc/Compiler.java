import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.lang.StringBuilder;
import java.lang.StringBuffer;
import java.util.regex.*;

/*
Interface of a <example.java> file must be named <Iexample.java>
*/

public class Compiler {
  public static void main(String [] arg) throws IOException{
    String fileName = arg[0];
    int port = Integer.parseInt(arg[1]);
    PrintWriter out;
    String future_file;


    String[] fileName_split = fileName.split("\\.");
    out = new PrintWriter(new BufferedWriter(new FileWriter(fileName_split[0]+"v2."+fileName_split[1])));
    BufferedReader buf  = new BufferedReader(new FileReader(fileName));
    StringBuilder sb = new StringBuilder();
    String line;
    Boolean client = false;
    while ((line=buf.readLine()) != null){
      sb.append(line);
      if (line.contains("public static void main")) {
        client = true;
      }
      sb.append("\n");
    }
    future_file = sb.toString();
    if (client){
    future_file = generateClient(future_file, port, fileName_split[0]);
    }
    else
    {
      future_file = generateServer(future_file, port, fileName_split[0]);
    }



    /*finally {
      if (in != null){
        in.close();
      }
      if (out != null){
        out.close();
      }
    }*/
    /*future_file += "public static void main(String[] args){

    }"*/
    out.write(future_file);
    out.close();


  }


  public static String generateClient(String init, int port, String fileName){
    init = init.replaceAll(fileName, fileName+"v2");
    String all_constructors = "";
    String add = "public static void main(String [] arg) { \n" +
    "java.net.Socket s = new java.net.Socket('localhost',"+port+");\n"+
    "java.io.DataOutputStream dos = new java.io.DataOutputStream(s.getOutputStream());\n"+
    "java.io.ObjectInputStream ois = new java.io.ObjectInputStream(s.getInputStream());\n";
    init = init.replaceAll("public static void main\\(String \\[\\] arg\\) \\{", add);
    Pattern p = Pattern.compile("([A-z]*) ([A-z]*) = (new) ([A-z]*)\\(([0-z]*)\\);");
    Matcher m = p.matcher(init);
    while(m.find()){
      String to_find = m.group(0);
      to_find = to_find.replaceAll("\\(", "\\\\\\(");
      to_find = to_find.replaceAll("\\)", "\\\\\\)");
      all_constructors = m.group(2) + "|";
      String param = m.group(5);
      init = init.replaceAll(to_find, "dos.writeUTF(\"constructeur\");\n"+"dos.writeInt("+param+");\n");
    }

    p = Pattern.compile("[A-z]* [A-z]* = ("+all_constructors+")\\.([A-z]*)\\(([0-9])\\);");
    m = p.matcher(init);
    while(m.find()){
      String param = m.group(3);
      String to_find = m.group(0);
      to_find = to_find.replaceAll("\\(", "\\\\\\(");
      to_find = to_find.replaceAll("\\)", "\\\\\\)");
      init = init.replaceAll(to_find, "dos.writeUTF(\""+m.group(2)+"\");\n"+"dos.writeInt(\""+param+"\");\n");

    }

    return init;

  }

  public static String generateServer(String init, int port, String fileName){
    return init +=
    "public static void main(String[] arg){\n"+
    "java.net.ServerSocket sos = new java.net.ServerSocket("+port+");\n"+
    "java.net.Socket s = sos.accept();\n"+
    "java.io.DataInputStream dis = new java.io.DataInputStream(s.getInputStream());\n"+
    "java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(s.getOutputStream());\n"+
    "String fonction = dis.readUTF();\n";
  }

}
