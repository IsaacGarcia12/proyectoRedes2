package filesocket;

import java.io.*;
import java.net.*;

public class cliente {

    int size = 0;

    public static void main(String[] args) throws IOException {
        //dividimos la longitud de bytes del archivo entre 3

        Socket s = new Socket("192.168.100.2", 2500);
        DataInputStream input = new DataInputStream(s.getInputStream());
        String size = input.readUTF();
        int sizeFile = Integer.parseInt(size);

        byte[] b = new byte[sizeFile];

        InputStream is = s.getInputStream();

        FileOutputStream fo = new FileOutputStream("/home/eduardorobles14/Desktop/receiver.txt");

        //we read the stream and then copy to b array
        is.read(b, 0, b.length);
        //we copy the array to the 
        fo.write(b, 0, b.length);

    }
}
