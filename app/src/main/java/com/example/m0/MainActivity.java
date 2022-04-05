package com.example.m0;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity
{
    
    private static Socket s;
    private static DataOutputStream dataOutputStream;
    
    String message = "";
    String output_message = "";
    private static final String ip = "172.20.10.2";
    private static final int port = 9998;
    private static int counter = 1;
    private static boolean connected = false;
    
    private EditText e1;
    private TextView t1;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1 = (EditText)findViewById(R.id.editText);
        t1 = (TextView)findViewById(R.id.textView);
    }
    
    public void connect(View v)
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        
        try
        {
            if (counter == 1)
            {
                s = new Socket(ip,port);
                counter = 0;
                connected = true;
                Toast.makeText(getApplicationContext(),"You are now connected to the server",Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(),"You are already connected to the Server",Toast.LENGTH_LONG).show();
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public void send(View v)
    {
        
        
        if (connected == false)
        {
            try
            {
                Toast.makeText(getApplicationContext(),"Please connect to the server first",Toast.LENGTH_LONG).show();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        else if (e1.getText().toString().isEmpty())
        {
            try
            {
                Toast.makeText(getApplicationContext(),"Your message is empty",Toast.LENGTH_LONG).show();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        
        else
        {
            message = e1.getText().toString();
            t1.setGravity(Gravity.CENTER_VERTICAL| Gravity.CENTER_HORIZONTAL);
            t1.setMovementMethod(new ScrollingMovementMethod());
            e1.setText("");
            Toast.makeText(getApplicationContext(),"Text sent to CAPITALIZER",Toast.LENGTH_LONG).show();
    
            try
            {
                dataOutputStream = new DataOutputStream(s.getOutputStream());
                dataOutputStream.write(message.getBytes()); //sending
                System.out.println("test1");
                DataInputStream dataInputStream = new DataInputStream(s.getInputStream());
                System.out.println("test2");
                output_message = dataInputStream.readLine(); //receiving
                System.out.println("test3");
                //t1.setText(output_message);
                t1.append(output_message + "\n");
                final int scrollAmount = t1.getLayout().getLineTop(t1.getLineCount()) - t1.getHeight();
                // if there is no need to scroll, scrollAmount will be <=0
                if (scrollAmount > 0)
                    t1.scrollTo(0, scrollAmount);
                else
                    t1.scrollTo(0, 0);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
    public void disconnect(View v)
    {
        try
        {
            if(counter == 0)
            {
                final String disconnect_msg = "CLOSE SOCKET";
                dataOutputStream = new DataOutputStream(s.getOutputStream());
                dataOutputStream.writeUTF(disconnect_msg);
                s.close();
                Toast.makeText(getApplicationContext(),"You have disconnected from the server",Toast.LENGTH_LONG).show();
                counter = 1;
                connected = false;
                t1.setText("");
            }
            else
                Toast.makeText(getApplicationContext(),"You must first connect to the server to be able to disconnect",Toast.LENGTH_LONG).show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}