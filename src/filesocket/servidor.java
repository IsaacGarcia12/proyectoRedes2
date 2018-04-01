package filesocket;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class servidor {

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(); // don't bind just yet
        ss.setReuseAddress(true);
        ss.bind(new InetSocketAddress(2800)); // can bind with reuse= true
        System.out.println("Escuchando a cliente");
        Socket s = null;
        s = ss.accept();
        int sizew = 0;
        //sending file
        //reading a file
        File file = new File("/home/eduardorobles14/Desktop/sender.txt");

        FileInputStream fi = new FileInputStream(file);
        //we get the size of the file in bytes
        int size = (int) file.length();

        //We print the actual size of the file
        System.out.println("EL tamaño del arreglo es: " + size);

        //we define the size of the workers
        if (size % 3 == 0) {
            sizew = size / 3;
            System.out.println("El tamaño que le corresponde a cada worker es: " + sizew);
        }

        //we define the array in wich we sasve the file
        byte[] b = new byte[size];
        //copy the file to the array 
        fi.read(b, 0, b.length);

        //convert file to stream for client 
        //here we can actually divide the file
        /*this is for the client module*/
        String sizefile = Integer.toString(size);
        DataOutputStream dOut = new DataOutputStream(s.getOutputStream());
        dOut.writeUTF(sizefile);

        OutputStream os = s.getOutputStream();
        os.write(b, 0, b.length);

        DataOutputStream dOutW = new DataOutputStream(s.getOutputStream());
        dOutW.writeUTF(sizefile);
        s.close();
        //actua como cliente para los workers

        Socket socketWorker = new Socket("localhost", 2850);

        DataOutputStream dOS = new DataOutputStream(socketWorker.getOutputStream());
        String sizeW = Integer.toString(sizew);
        dOS.writeUTF(sizeW);
        //33 bytes /3 =11  -> 1era parte=11(0-11)-1-11 11-22 22-33 2da parte=11(12-22) 3ra parte=11 (23-33)
        //passin the first part of file to worker1
        OutputStream osW = socketWorker.getOutputStream();
        osW.write(b, 0, sizew);
        //passin the second part of file to worker2
        OutputStream osW1 = socketWorker.getOutputStream();
        osW1.write(b, sizew, sizew);

        //passin the third part of file to worker3
        OutputStream osW2 = socketWorker.getOutputStream();
        osW2.write(b, 2 * sizew, sizew);

        socketWorker.close();

    }
}
