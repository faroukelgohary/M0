package com.example.m0;

import android.os.AsyncTask;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class MessageSender extends AsyncTask<String,Void,Void>
{
    Socket s;
    DataOutputStream dos;
    PrintWriter pw;

    /*DataInputStream din = new DataInputStream(s.getInputStream());
    DataOutputStream dout = new DataOutputStream(s.getOutputStream());
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));*/

    @Override
    protected Void doInBackground(String... voids)
    {
        String message = voids[0];
        
        try
        {
            s = new Socket("192.168.1.12",9999);
            pw = new PrintWriter(s.getOutputStream());
            pw.write(message);
            pw.flush();
            pw.close();
            
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        
        
        
        
        return null;
    }
}
