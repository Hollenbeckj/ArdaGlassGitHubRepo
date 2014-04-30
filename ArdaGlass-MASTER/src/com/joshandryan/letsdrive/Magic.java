// this app was created by Joshua Hollenbeck 
// this application is open to modifications and contributions. This is my first application ever made for android so I hope those of you who use it find it helpfull 
// a special thanks to Matt Bell for providing an open source working example of serial communication for android and Cody Engel for providing his Hello Glass example as a base for this project 
// Source Credit 1: Matt Bell -- (http://bellcode.wordpress.com/2012/01/02/android-and-arduino-bluetooth-communication/)
// Source Credit 2: Cody Engel -- (https://plus.google.com/+CodyEngel/posts)
package com.joshandryan.letsdrive;

import android.app.Activity;

import com.google.android.glass.app.Card;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;  
import android.widget.Button;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Set;
import java.util.UUID;


public class Magic extends Activity 
{
	
	 BluetoothAdapter mBluetoothAdapter; // initialize a bluetooth adapter 
	 BluetoothSocket mmSocket; // establish a socket 
	 BluetoothDevice mmDevice; // establish a bluetooth device 
	 OutputStream mmOutputStream; // setup an output stream for data 
	 InputStream mmInputStream; // setup an input stream for data 
	 Thread workerThread; // initialize a thread action 
	 byte[] readBuffer; // array for bytes 
	 int readBufferPosition; // position of the bytes in the array 
	 int counter;
	 volatile boolean stopWorker; // used to control the thread activity 
	 String ArduinoData = " "; // string for arduino Data 
	 boolean deviceFound; // true or false statement for telling the app if devices are found or not 
	 boolean streamStarted; // tells the app if bytes are coming in 
	 Card card1 = new Card(this); // constructor statement for the card class specific to the GDK 

	
    void searchForBluetooth()
    {
    	if(!deviceFound) // if no bluetooth device is found 
    	{
  
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter(); // search until something is found 
        if(mBluetoothAdapter == null) // if no bluetooth device has been found keep the boolean statement false which will keep the top half re running
        {
          deviceFound = false;
          card1.setText("Searching"); // set card to say searching 
          View card1View = card1.toView(); // allow the card to be viewed 
  		setContentView(card1View); // set the cards contents to the ones specified above 
        }
        
        if(!mBluetoothAdapter.isEnabled()) // if a bluetooth device is enabled 
        {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE); // make the connected device the primary device to get data from 
            startActivityForResult(enableBluetooth, 0); // start activity that will enable the bluetooth
            
        }
        
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices(); // set the paired device to be the adapter
        if(pairedDevices.size() > 0) // if the paired device size is greater than zero
        {
            for(BluetoothDevice device : pairedDevices) // search for a device 
            {
                if(device.getName().equals("<ArduinoBT>")) // if the device is equal to the specified name pair to the glasses NOTE: if you are using an arduino bluetooth mate you can change its name to be what ever you want following the instructions on sparkfuns website here -> (https://www.sparkfun.com/products/12576)
                {
                    mmDevice = device; // the device specified above is the device needed to connect 
                    deviceFound = true; // set the device found boolean to be true 
                    try { // once a device is found use a try sequence and run a script that opens the bluetooth socket 
						openSerial(); // see script bellow for more info
					} catch (IOException e) { // if something goes wrong in the script run a fall back script 
						deviceFound = false; // set the device found equal to false which will re run the above set of lines 
						e.printStackTrace(); 
					}
                    break;
                }
            }
        }
    	}
   }
    
	
	void openSerial() throws IOException // opens a standard bluetooth socket to establish data transferring 
    {
		
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //Standard SerialPortService ID
        mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid); // the socket established above is where the information will be coming from         
        mmSocket.connect(); // connect to the established socket 
        mmOutputStream = mmSocket.getOutputStream(); // standard setup for outgoing data
        mmInputStream = mmSocket.getInputStream(); // standard setup for incoming data 
        streamStarted = true; // tells the app that the stream has been successfully started 
		
        
    }
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) // create a saved instance of what is happening if the user accidentally quits the app so they can resume the data stream 
	{
		final Handler handler = new Handler(); // sets up a handler for the saved instance 
        final byte delimiter = 10; //This is the ASCII code for a newline character
        searchForBluetooth(); // run the find BT script 
        
        stopWorker = false; // stopWorker which controls if the bellow lines run 
        readBufferPosition = 0; // position of data 
        readBuffer = new byte[1024]; // number of data bytes allowed before clearing them which keeps the app lag free when it runs for a long time 
        super.onCreate(savedInstanceState); // create a save of the user's actions 
        workerThread = new Thread(new Runnable() // begin a thread of instructions that run every frame
        {
            public void run() // execute the lines bellow 
            {                
               while(!Thread.currentThread().isInterrupted() && !stopWorker) // while the current thread isn't the current thread, is interupted, and stopworker equals false run the lines bellow 
               {
                    try 
                    {
                        int bytesAvailable = mmInputStream.available();// establish where the bytes are coming from and check if the input stream is available                      
                        if(bytesAvailable > 0) // if the bytes available are greater than zero 
                        {
                            byte[] packetBytes = new byte[bytesAvailable]; // store the bytes in an array 
                            mmInputStream.read(packetBytes); // read the bytes from the input stream 
                            for(int i=0;i<bytesAvailable;i++)
                            {
                                byte b = packetBytes[i]; // I equals the number of bytes in the array 
                                if(b == delimiter)
                                {
                                    byte[] encodedBytes = new byte[readBufferPosition]; // read from the bytes position
                                    System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length); // get array information from the bytes array 
                                    final String data = new String(encodedBytes, "US-ASCII");
                                    
                                    readBufferPosition = 0; // read from the latest buffer position 
                                    
                                    handler.post(new Runnable() // run the lines bellow when the above lines have completed 
                                    {
                                        public void run()
                                        {
                                            card1.setText("Arduino Data: " + data); // Main text for the live card
                                    		View card1View = card1.toView(); // set the content of the card to be seen
                                    		setContentView(card1View); // set the card to be viewed with the content 
                                        }
                                    });
                                }
                                else 
                                {
                                    readBuffer[readBufferPosition++] = b;
                                }
                            }
                        }
                    } 
                    catch (IOException ex) 
                    {
                        stopWorker = true; // if something goes wrong set the stopworker to true which will disable all of the above lines of code 
                    }
               }
            }
        });

        workerThread.start(); // start the thread
		
	}
	
}