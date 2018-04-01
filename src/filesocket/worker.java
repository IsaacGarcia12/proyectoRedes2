/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filesocket;

import java.io.IOException;
import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author eduardorobles14
 */
public class worker implements Runnable {

    String name;
    int rangoI, rangoF, num, len;
    Socket conWorker = null;
    InputStream input;
    OutputStream output;

    worker(Socket conWorker, int num, int rangoI, int rangoF, int len, InputStream input) {
        this.conWorker = conWorker;
        this.num = num;
        this.rangoI = rangoI;
        this.rangoF = rangoF;
        this.len = len;
        this.input = input;
    }

    public static void main(String[] args) throws IOException, InterruptedException {


        ServerSocket ss = new ServerSocket(); // don't bind just yet
        ss.setReuseAddress(true);
        ss.bind(new InetSocketAddress(2850)); // can bind with reuse= true

        System.out.println("Escuchando a servidor");

        try {

            Socket conWorker = ss.accept();
            System.out.println("Conectado");

            DataInputStream dIS = new DataInputStream(conWorker.getInputStream());
            String size = dIS.readUTF();
            int sizeW = Integer.parseInt(size);

            System.out.println("El tamaño correspondiente a cada worker: " + sizeW + " bytes");

            ExecutorService executor = Executors.newFixedThreadPool(3);//creating a pool of 5 threads  
            InputStream input = conWorker.getInputStream();
            Runnable worker = new worker(conWorker, 1, 0, sizeW, sizeW, input);
            executor.execute(worker);

            InputStream input1 = conWorker.getInputStream();
            Runnable worker1 = new worker(conWorker, 2, sizeW, sizeW, sizeW, input1);
            executor.execute(worker1);

            InputStream input2 = conWorker.getInputStream();
            Runnable worker2 = new worker(conWorker, 3, 2 * sizeW, sizeW, sizeW, input2);
            executor.execute(worker2);

            executor.shutdown();
            /*
            InputStream input=conWorker.getInputStream();
            Thread t1 = new Thread(new worker(conWorker, 1, 0, sizeW, sizeW,input), "t1");
            t1.start();
            t1.join();
            
            InputStream input1=conWorker.getInputStream();
            Thread t2 = new Thread(new worker(conWorker, 2, sizeW, sizeW, sizeW,input1), "t2");
            t2.start();
            t2.join();
            
            InputStream input2=conWorker.getInputStream();
            Thread t3 = new Thread(new worker(conWorker, 3, 2 * sizeW, sizeW, sizeW,input2), "t3");
            t3.start();
            t3.join();
             */

        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            ss.close();
        }
    }

    public void run() {
        try {
            //array for the file of the workers
            byte[] b = new byte[len];
            String fileName = Integer.toString(num);
            FileOutputStream fo = new FileOutputStream("/home/eduardorobles14/Desktop/receiver" + fileName
                    + ".txt");

            input.read(b, 0, b.length);
            fo.write(b, 0, b.length);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
