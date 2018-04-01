/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filesocket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author eduardorobles14
 */
public class threadPool {
    
    public static void main(String[] args){
        ExecutorService executor=Executors.newFixedThreadPool(3);
        Runnable worker=new worker(conWorker, 1, 0, sizeW, sizeW,input);
    }
    
}
