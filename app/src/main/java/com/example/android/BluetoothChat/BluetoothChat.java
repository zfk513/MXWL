/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.BluetoothChat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.TabActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the main Activity that displays the current chat session.
 */
public class BluetoothChat extends TabActivity {
	
	TextView textView1;
	TextView textView2;
	TextView textView3;
	TextView textView4;
	TextView textView5;
	TextView textView6;
	TextView textView7;
	TextView textView8;
	TextView textView9;
	TextView textView10;
	TextView textView11;
	TextView textView12;
	
	TextView samenum1;
	TextView samenum2;
	TextView samenum3;
	TextView samenum4;
	TextView samenum5;
	Button button1_clear;

	String name,gender,age,diseaseInfo,kNum,averageRate;

	public int ihang=0,jlie=0;
	public Button button1;
	Button button_clean;

	TabHost mytab;
	public String Inputnum[][] = new String[12][2];
    public List<String> outputdata = new ArrayList<String>();
	int flag=0;
	int a=0,b=0;
	String Outputnum1[]= new String[12] ;
	String Outputnum2[]= new String[12] ;
	int h=0,g=0;
//	public String savenum[][] = new String[5][12];
    //不知道这是什么情况，师姐的代码没用上
	
	
    // Debugging
    private static final String TAG = "BluetoothChat";
    private static final boolean D = true;

    // Message types sent from the BluetoothChatService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    // Key names received from the BluetoothChatService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;

    // Layout Views
    private TextView mTitle;
    private ListView mConversationView;
//    private EditText mOutEditText;
//    private Button mSendButton;

    // Name of the connected device
    private String mConnectedDeviceName = null;
    // Array adapter for the conversation thread
    private ArrayAdapter<String> mConversationArrayAdapter;
    // String buffer for outgoing messages
    private StringBuffer mOutStringBuffer;
    // Local Bluetooth adapter
    private BluetoothAdapter mBluetoothAdapter = null;
    // Member object for the chat services
    private BluetoothChatService mChatService = null;
// ToDo add github

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(D) Log.e(TAG, "+++ ON CREATE +++");

        // Set up the window layout
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.bluetoothchat);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);

        mytab=this.getTabHost();
//        mytab.setBackgroundColor(Color.YELLOW);
        mytab.addTab(mytab.newTabSpec("tag1").setIndicator("脉象记录",getResources().getDrawable(R.drawable.record)).setContent(R.id.tab1));
        mytab.addTab(mytab.newTabSpec("tag2").setIndicator("日常建议",getResources().getDrawable(R.drawable.doctor)).setContent(R.id.tab2));
        mytab.addTab(mytab.newTabSpec("tag3").setIndicator("监视窗口",getResources().getDrawable(R.drawable.found)).setContent(R.id.tab3));

        // Set up the custom title
        mTitle = (TextView) findViewById(R.id.title_left_text);
        mTitle.setText(R.string.app_name);
        mTitle = (TextView) findViewById(R.id.title_right_text);

        // Get local Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        samenum1=(TextView)findViewById(R.id.samenum1);
        samenum2=(TextView)findViewById(R.id.samenum2);
        samenum3=(TextView)findViewById(R.id.samenum3);
        samenum4=(TextView)findViewById(R.id.samenum4);
        samenum5=(TextView)findViewById(R.id.samenum5);
        button1_clear=(Button)findViewById(R.id.button1_clear);


       textView1=(TextView)findViewById(R.id.textView1);
       textView2=(TextView)findViewById(R.id.textView2);
       textView3=(TextView)findViewById(R.id.textView3);
       textView4=(TextView)findViewById(R.id.textView4);
       textView5=(TextView)findViewById(R.id.textView5);
       textView6=(TextView)findViewById(R.id.textView6);
       textView7=(TextView)findViewById(R.id.textView7);
       textView8=(TextView)findViewById(R.id.textView8);
       textView9=(TextView)findViewById(R.id.textView9);
       textView10=(TextView)findViewById(R.id.textView10);
       textView11=(TextView)findViewById(R.id.textView11);
       textView12=(TextView)findViewById(R.id.textView12);

       button1=(Button)findViewById(R.id.button1);
       button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

//			for(g=0;g<12;g++)
//				savenum[h][g]=Outputnum2[g];

			if(h==0)
				samenum1.setText(Outputnum1[0]+"."+Outputnum2[0]+"%"+Outputnum1[1]+"."+Outputnum2[1]+"%"+Outputnum1[2]+"."+Outputnum2[2]+"%"+Outputnum1[3]+"."+Outputnum2[3]+"%"+Outputnum1[4]+"."+Outputnum2[4]+"%"+Outputnum1[5]+"."+Outputnum2[5]+"%"
						+Outputnum1[6]+"."+Outputnum2[6]+"%"+Outputnum1[7]+"."+Outputnum2[7]+"%"+Outputnum1[8]+"."+Outputnum2[8]+"%"+Outputnum1[9]+"."+Outputnum2[9]+"%"+Outputnum1[10]+"."+Outputnum2[10]+"%"+Outputnum1[11]+"."+Outputnum2[11]+"%");
			if(h==1)
				samenum2.setText(Outputnum1[0]+"."+Outputnum2[0]+"%"+Outputnum1[1]+"."+Outputnum2[1]+"%"+Outputnum1[2]+"."+Outputnum2[2]+"%"+Outputnum1[3]+"."+Outputnum2[3]+"%"+Outputnum1[4]+"."+Outputnum2[4]+"%"+Outputnum1[5]+"."+Outputnum2[5]+"%"
						+Outputnum1[6]+"."+Outputnum2[6]+"%"+Outputnum1[7]+"."+Outputnum2[7]+"%"+Outputnum1[8]+"."+Outputnum2[8]+"%"+Outputnum1[9]+"."+Outputnum2[9]+"%"+Outputnum1[10]+"."+Outputnum2[10]+"%"+Outputnum1[11]+"."+Outputnum2[11]+"%");
			if(h==2)
				samenum3.setText(Outputnum1[0]+"."+Outputnum2[0]+"%"+Outputnum1[1]+"."+Outputnum2[1]+"%"+Outputnum1[2]+"."+Outputnum2[2]+"%"+Outputnum1[3]+"."+Outputnum2[3]+"%"+Outputnum1[4]+"."+Outputnum2[4]+"%"+Outputnum1[5]+"."+Outputnum2[5]+"%"
						+Outputnum1[6]+"."+Outputnum2[6]+"%"+Outputnum1[7]+"."+Outputnum2[7]+"%"+Outputnum1[8]+"."+Outputnum2[8]+"%"+Outputnum1[9]+"."+Outputnum2[9]+"%"+Outputnum1[10]+"."+Outputnum2[10]+"%"+Outputnum1[11]+"."+Outputnum2[11]+"%");
			if(h==3)
				samenum4.setText(Outputnum1[0]+"."+Outputnum2[0]+"%"+Outputnum1[1]+"."+Outputnum2[1]+"%"+Outputnum1[2]+"."+Outputnum2[2]+"%"+Outputnum1[3]+"."+Outputnum2[3]+"%"+Outputnum1[4]+"."+Outputnum2[4]+"%"+Outputnum1[5]+"."+Outputnum2[5]+"%"
						+Outputnum1[6]+"."+Outputnum2[6]+"%"+Outputnum1[7]+"."+Outputnum2[7]+"%"+Outputnum1[8]+"."+Outputnum2[8]+"%"+Outputnum1[9]+"."+Outputnum2[9]+"%"+Outputnum1[10]+"."+Outputnum2[10]+"%"+Outputnum1[11]+"."+Outputnum2[11]+"%");
			if(h==4)
				samenum5.setText(Outputnum1[0]+"."+Outputnum2[0]+"%"+Outputnum1[1]+"."+Outputnum2[1]+"%"+Outputnum1[2]+"."+Outputnum2[2]+"%"+Outputnum1[3]+"."+Outputnum2[3]+"%"+Outputnum1[4]+"."+Outputnum2[4]+"%"+Outputnum1[5]+"."+Outputnum2[5]+"%"
						+Outputnum1[6]+"."+Outputnum2[6]+"%"+Outputnum1[7]+"."+Outputnum2[7]+"%"+Outputnum1[8]+"."+Outputnum2[8]+"%"+Outputnum1[9]+"."+Outputnum2[9]+"%"+Outputnum1[10]+"."+Outputnum2[10]+"%"+Outputnum1[11]+"."+Outputnum2[11]+"%");
			h++;if(h==5)h=0;

			Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_SHORT).show();
			}
		});



        button1_clear.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				//
				h=0;
				samenum1.setText(" ");
				samenum2.setText(" ");
				samenum3.setText(" ");
				samenum4.setText(" ");
				samenum5.setText(" ");
				Toast.makeText(getApplicationContext(), "清空成功", Toast.LENGTH_SHORT).show();
			}
		});

//        // 测试 SDK 是否正常工作的代码
//        AVObject testObject = new AVObject("TestObject");
//        testObject.put("测试中文是否可行","Hello World!");
//        testObject.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(AVException e) {
//                if(e == null){
//                    Log.d("saved","success!");
//                    Toast.makeText(getApplicationContext(),"云端测试成功",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        Button Start=findViewById(R.id.start);
//        Start.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sendMessage("1");
//            }
//        });
    }
    public void SendMessage(View view){
        sendMessage("1");

    }

//    public void SendStartMessage(View view){
//        sendMessage("2");
//
//    }


    @Override
    public void onStart() {
        super.onStart();
        if(D) Log.e(TAG, "++ ON START ++");

        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        // Otherwise, setup the chat session
        } else {
            if (mChatService == null) setupChat();
        }
    }

    @Override
    public synchronized void onResume() {
        super.onResume();
        if(D) Log.e(TAG, "+ ON RESUME +");

        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        if (mChatService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mChatService.getState() == BluetoothChatService.STATE_NONE) {
              // Start the Bluetooth chat services
              mChatService.start();
            }
        }
    }

    private void setupChat() {
        Log.d(TAG, "setupChat()");

        // Initialize the array adapter for the conversation thread
        mConversationArrayAdapter = new ArrayAdapter<String>(this, R.layout.message);
        mConversationView = (ListView) findViewById(R.id.in);
        mConversationView.setAdapter(mConversationArrayAdapter);
        button_clean=(Button)findViewById(R.id.button_clean);
        button_clean.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//
				 mConversationArrayAdapter.clear();

				 for(ihang=0;ihang<12;ihang++)
				 {
					 Inputnum[ihang][0]="00";
					 Inputnum[ihang][1]="00";
					 Outputnum1[ihang]="00";
					 Outputnum2[ihang]="00";
				 }
				 ihang=0;jlie=0;

				 textView1.setText("00"+"."+"00"+"%");
				 textView2.setText("00"+"."+"00"+"%");
				 textView3.setText("00"+"."+"00"+"%");
				 textView4.setText("00"+"."+"00"+"%");
				 textView5.setText("00"+"."+"00"+"%");
				 textView6.setText("00"+"."+"00"+"%");
				 textView7.setText("00"+"."+"00"+"%");
				 textView8.setText("00"+"."+"00"+"%");
				 textView9.setText("00"+"."+"00"+"%");
				 textView10.setText("00"+"."+"00"+"%");
				 textView11.setText("00"+"."+"00"+"%");
				 textView12.setText("00"+"."+"00"+"%");
			}
		});
        // Initialize the compose field with a listener for the return key
//        mOutEditText = (EditText) findViewById(R.id.edit_text_out);
//        mOutEditText.setOnEditorActionListener(mWriteListener);

        // Initialize the send button with a listener that for click events
//        mSendButton = (Button) findViewById(R.id.button_send);
//        mSendButton.setOnClickListener(new OnClickListener() {
//            public void onClick(View v) {
//                // Send a message using content of the edit text widget
//                TextView view = (TextView) findViewById(R.id.edit_text_out);
//                String message = view.getText().toString();
//                sendMessage(message);
//            }
//        });

        // Initialize the BluetoothChatService to perform bluetooth connections
        mChatService = new BluetoothChatService(this, mHandler);

        // Initialize the buffer for outgoing messages
        mOutStringBuffer = new StringBuffer("");
    }

    @Override
    public synchronized void onPause() {
        super.onPause();
        if(D) Log.e(TAG, "- ON PAUSE -");
    }

    @Override
    public void onStop() {
        super.onStop();
        if(D) Log.e(TAG, "-- ON STOP --");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Stop the Bluetooth chat services
        if (mChatService != null) mChatService.stop();
        if(D) Log.e(TAG, "--- ON DESTROY ---");
    }

    private void ensureDiscoverable() {
        if(D) Log.d(TAG, "ensure discoverable");
        if (mBluetoothAdapter.getScanMode() !=
            BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }

    public void save(List<String> data) {
        FileOutputStream out;
        BufferedWriter writer = null;
        try {
            out = openFileOutput("MXWLdata", Context.MODE_APPEND);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(String.valueOf((data)));
            writer.write("\r\n");
//            数据和换行符，ArrayList不知道为什么不能存，换行符很迷，网上什么样的都有
//            后面去谷歌官方API中找到readline才答疑解惑
// A line is considered to be terminated by any one of a line feed ('\n'), a carriage return ('\r'),
// or a carriage return followed immediately by a linefeed.
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
//                    Log.println(getFilesDir());
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void UploadData(){
        AVObject cloud = new AVObject("Info");

        cloud.put("Name",name);
        cloud.put("Gender",gender);
        cloud.put("Age",age);
        cloud.put("DiseaseInfo",diseaseInfo);
        cloud.put("FirstNum",Outputnum1[0]+"."+Outputnum2[0]);
        cloud.put("SecondNum",Outputnum1[1]+"."+Outputnum2[1]);
        cloud.put("ThirdNum",Outputnum1[2]+"."+Outputnum2[2]);
        cloud.put("ForthNum",Outputnum1[3]+"."+Outputnum2[3]);
        cloud.put("FifthNum",Outputnum1[4]+"."+Outputnum2[4]);
        cloud.put("SixthNum",Outputnum1[5]+"."+Outputnum2[5]);
        cloud.put("SeventhNum",Outputnum1[6]+"."+Outputnum2[6]);
        cloud.put("EighthNum",Outputnum1[7]+"."+Outputnum2[7]);
        cloud.put("NinthNum",Outputnum1[8]+"."+Outputnum2[8]);
        cloud.put("TenthNum",Outputnum1[9]+"."+Outputnum2[9]);
        cloud.put("EleventhNum",Outputnum1[10]+"."+Outputnum2[10]);
        cloud.put("TwelfthNum",Outputnum1[11]+"."+Outputnum2[11]);
//        cloud.put("kNum",kNum);
//        cloud.put("AverageHeartRate",averageRate);

//     				          cloud.put("owner", AVUser.getCurrentUser());
        cloud.saveInBackground// 保存到服务端
                (new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if(e == null){
                            Log.d("saved","success!");
                            Toast.makeText(getApplicationContext(),"云端上传成功",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"上传失败，已保存至本地",Toast.LENGTH_SHORT).show();
                            List<String> outputdata = new ArrayList<>();
//                            将数据转换成数组,但是后面想想还是直接用string好了
                            outputdata.add(name);
                            outputdata.add(gender);
                            outputdata.add(age);
                            outputdata.add(diseaseInfo);
//                            outputdata.add(kNum);
//                            outputdata.add(averageRate);
                            for(int i=0;i<12;i++){
                                outputdata.add(Outputnum1[i]);
                                outputdata.add(Outputnum2[i]);
                            }

//                            String [] outputdata = {name,gender,age,diseaseInfo,Outputnum1[0]+"."+Outputnum1[0],Outputnum2[1]+"."+Outputnum2[1],Outputnum1[2]+"."+Outputnum2[2],Outputnum1[3]
//                                    +"."+Outputnum2[3],Outputnum1[4]+"."+Outputnum2[4],Outputnum1[5]+"."+Outputnum2[5],Outputnum1[6]+"."+Outputnum2[6]
//                                    ,Outputnum1[7]+"."+Outputnum2[7],Outputnum1[8]+"."+Outputnum2[4]};数组还是太麻烦，健壮性太差

                            save( outputdata);
//                            写入内部储存，坏处是卸载后数据会失效，好处是外部访问不了，随时可用
                        }
                    }
                });
    }
    public void UpdateOfflineData(View view){

//        setContentView(R.layout.activity_input_info);
        load();
        Toast.makeText(this,"离线数据上传成功",Toast.LENGTH_SHORT);
//        为什么这个没显示 云端上传成功

    }
    public void ConvertInput(String mString) throws JSONException {

        JSONArray temp = new JSONArray(mString);
        String[] ReadLineData= temp.join(",").split(",");

        name=ReadLineData[0];
        gender=ReadLineData[1];
        age=ReadLineData[2];
        diseaseInfo=ReadLineData[3];
        for(int i =0;i<12;i=i+2) {
            Outputnum1[i]=ReadLineData[i+3];
            Outputnum2[i]=ReadLineData[i+4];
        }

    }

    public void load( ){
//        用来读数据
        FileInputStream in;
        BufferedReader reader=null;
        StringBuilder content=new StringBuilder();

        try {
            in=openFileInput("MXWLdata");
            reader=new BufferedReader(new InputStreamReader(in));
            String line="";
            while ((line=reader.readLine())!=null){
                    ConvertInput(line);
                    UploadData();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if(reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
//        return content.toString();
         deleteFile("MXWLdata");

    }

    /**
     * Sends a message.
     * @param message  A string of text to send.
     */
    private void sendMessage(String message) {
        // Check that we're actually connected before trying anything
        if (mChatService.getState() != BluetoothChatService.STATE_CONNECTED) {
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }

        // Check that there's actually something to send
        if (message.length() > 0) {
            // Get the message bytes and tell the BluetoothChatService to write
            byte[] send = message.getBytes();
            mChatService.write(send);

            // Reset out string buffer to zero and clear the edit text field
            mOutStringBuffer.setLength(0);
//            mOutEditText.setText(mOutStringBuffer);
            Toast.makeText(getApplicationContext(),"脉诊仪已经执行下一步",Toast.LENGTH_SHORT).show();
        }
    }

    // The action listener for the EditText widget, to listen for the return key
//    private TextView.OnEditorActionListener mWriteListener =
//        new TextView.OnEditorActionListener() {
//        public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
//            // If the action is a key-up event on the return key, send the message
//            if (actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_UP) {
//                String message = view.getText().toString();
//                sendMessage(message);
//            }
//            if(D) Log.i(TAG, "END onEditorAction");
//            return true;
//        }
//    };

    // The Handler that gets information back from the BluetoothChatService
    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {


    	 String newCode2 = "";
    	@Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
            case MESSAGE_STATE_CHANGE:
                if(D) Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
                switch (msg.arg1) {
                case BluetoothChatService.STATE_CONNECTED:
                    mTitle.setText(R.string.title_connected_to);
                    mTitle.append(mConnectedDeviceName);
                    mConversationArrayAdapter.clear();
                    break;
                case BluetoothChatService.STATE_CONNECTING:
                    mTitle.setText(R.string.title_connecting);
                    break;
                case BluetoothChatService.STATE_LISTEN:
                case BluetoothChatService.STATE_NONE:
                    mTitle.setText(R.string.title_not_connected);
                    break;
                }
                break;
            case MESSAGE_WRITE:
                byte[] writeBuf = (byte[]) msg.obj;
                // construct a string from the buffer
                String writeMessage = new String(writeBuf);
                mConversationArrayAdapter.add("Me:  " + writeMessage);
                break;
            case MESSAGE_READ:


                byte[] readBuf = (byte[]) msg.obj;
                // construct a string from the valid bytes in the buffer
                String readMessage = new String(readBuf, 0, msg.arg1);

            	newCode2 = bytesToHexStringTwo(readBuf,1);

            	Inputnum[ihang][jlie]=newCode2;
            	jlie++;
            	if(jlie>=2){jlie=0;ihang++;}//这样写的移植性不太好
            	if(ihang>11) {flag=1;}

            	mConversationArrayAdapter.add(mConnectedDeviceName+":  " + newCode2 );
            	 if(flag==1){
     		      	for(a=0;a<12;a++)
     		      	{
     		      		Outputnum1[a]=Integer.toString(Integer.parseInt(Inputnum[a][0], 16));
     		      		Outputnum2[a]=Integer.toString(Integer.parseInt(Inputnum[a][1], 16));
     		      	}
     				 textView1.setText(Outputnum1[0]+"."+Outputnum2[0]+"%");
     				 textView2.setText(Outputnum1[1]+"."+Outputnum2[1]+"%");
     				 textView3.setText(Outputnum1[2]+"."+Outputnum2[2]+"%");
     				 textView4.setText(Outputnum1[3]+"."+Outputnum2[3]+"%");
     				 textView5.setText(Outputnum1[4]+"."+Outputnum2[4]+"%");
     				 textView6.setText(Outputnum1[5]+"."+Outputnum2[5]+"%");
     				 textView7.setText(Outputnum1[6]+"."+Outputnum2[6]+"%");
     				 textView8.setText(Outputnum1[7]+"."+Outputnum2[7]+"%");
     				 textView9.setText(Outputnum1[8]+"."+Outputnum2[8]+"%");
     				 textView10.setText(Outputnum1[9]+"."+Outputnum2[9]+"%");
     				 textView11.setText(Outputnum1[10]+"."+Outputnum2[10]+"%");
     				 textView12.setText(Outputnum1[11]+"."+Outputnum2[11]+"%");

                     Intent intent = getIntent();
//                     Bundle GetMultiplyInfo=intent.getExtras();
                     //之前的Bundle方法失败
//                     String name =GetMultiplyInfo.getString(InputInfo.passName);
//                     String gender = GetMultiplyInfo.getString(InputInfo.passGender);
//                     String age = GetMultiplyInfo.getString(InputInfo.passAge);
                     String[] GetNameGenderAge = intent.getStringArrayExtra(InputInfo.NameGenderAge);

                     name = GetNameGenderAge[0];
                     gender = GetNameGenderAge[1];
                     age = GetNameGenderAge[2];
                     diseaseInfo = GetNameGenderAge[3];

//                     String kNum = String.valueOf((((Float.valueOf(Outputnum1[12]))*10)+Float.valueOf(Outputnum2[12]))/1000);
//                     String averageRate = String.valueOf((Integer.parseInt(Outputnum1[13])*10)+(Integer.parseInt(Outputnum2[13])));

//                     Toast.makeText(getApplicationContext(),"名字: "+name,Toast.LENGTH_SHORT).show();
//                     Toast.makeText(getApplicationContext(),"性别: "+gender,Toast.LENGTH_SHORT).show();
//                     Toast.makeText(getApplicationContext(),"年龄: "+age,Toast.LENGTH_SHORT).show();
//                     Toast.makeText(getApplicationContext(),"疾病: "+diseaseInfo,Toast.LENGTH_SHORT).show();
//
//                     Toast.makeText(getApplicationContext(),"K值: "+String.valueOf(kNum),Toast.LENGTH_SHORT).show();
//                     Toast.makeText(getApplicationContext(),"心率："+String.valueOf(averageRate),Toast.LENGTH_SHORT).show();
                     Toast.makeText(getApplicationContext(),"数据采集成功",Toast.LENGTH_SHORT).show();

//        // 测试 SDK 是否正常工作的代码
//        AVObject testObject = new AVObject("TestObject");
//        testObject.put("测试中文是否可行","Hello World!");
//        testObject.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(AVException e) {
//                if(e == null){
//                    Log.d("saved","success!");
//                    Toast.makeText(getApplicationContext(),"云端测试成功",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
                    UploadData();

     				 flag=0;
     			}

            	break;
            case MESSAGE_DEVICE_NAME:
                // save the connected device's name
                mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                Toast.makeText(getApplicationContext(), "Connected to "
                               + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                break;
            case MESSAGE_TOAST:
                Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
                               Toast.LENGTH_SHORT).show();
                break;
            }
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(D) Log.d(TAG, "onActivityResult " + resultCode);
        switch (requestCode) {
        case REQUEST_CONNECT_DEVICE:
            // When DeviceListActivity returns with a device to connect
            if (resultCode == Activity.RESULT_OK) {
                // Get the device MAC address
                String address = data.getExtras()
                                     .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                // Get the BLuetoothDevice object
                BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
                // Attempt to connect to the device
                mChatService.connect(device);
            }
            break;
        case REQUEST_ENABLE_BT:
            // When the request to enable Bluetooth returns
            if (resultCode == Activity.RESULT_OK) {
                // Bluetooth is now enabled, so set up a chat session
                setupChat();
            } else {
                // User did not enable Bluetooth or an error occured
                Log.d(TAG, "BT not enabled");
                Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.scan:
            // Launch the DeviceListActivity to see devices and do scan
            Intent serverIntent = new Intent(this, DeviceListActivity.class);
            startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
            return true;
        case R.id.discoverable:
            // Ensure this device is discoverable by others
            ensureDiscoverable();
            return true;
        }
        return false;
    }

	/** *//**
	    * 把字节数组转换成16进制字符串
	    * @param bArray
	    * @return
	    */
	public static final String bytesToHexStringTwo(byte[] bArray,int count) {
	    StringBuffer sb = new StringBuffer(bArray.length); 
	    String sTemp; 
	    for (int i = 0; i < count; i++) { 
	     sTemp = Integer.toHexString(0xFF & bArray[i]); 
	     if (sTemp.length() < 2) 
	      sb.append(0); 
	     sb.append(sTemp.toUpperCase()); 
	    } 
	    return sb.toString(); 
	}
		

}