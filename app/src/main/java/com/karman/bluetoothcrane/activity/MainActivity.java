package com.karman.bluetoothcrane.activity;

import android.app.FragmentTransaction;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.karman.bluetoothcrane.R;
import com.karman.bluetoothcrane.dialog.DeviceListDialogFragment;
import com.karman.bluetoothcrane.utils.AppConfigTags;
import com.karman.bluetoothcrane.utils.SetTypeFace;
import com.karman.bluetoothcrane.utils.Utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private final String DEVICE_ADDRESS = "98:D3:31:90:82:9A"; //MAC Address of Bluetooth Module
    private final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

    private BluetoothDevice device;
    private BluetoothSocket socket;
    private OutputStream outputStream;

    Button forward_btn, forward_left_btn, forward_right_btn, reverse_btn, reverse_left_btn, reverse_right_btn, bluetooth_connect_btn;

    String command; //string variable that will store value to be transmitted to the bluetooth module





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //declaration of button variables
        forward_btn = (Button) findViewById(R.id.forward_btn);
        forward_left_btn = (Button) findViewById(R.id.forward_left_btn);
        forward_right_btn = (Button) findViewById(R.id.forward_right_btn);
        reverse_btn = (Button) findViewById(R.id.reverse_btn);
        reverse_right_btn = (Button) findViewById(R.id.reverse_right_btn);
        reverse_left_btn = (Button) findViewById(R.id.reverse_left_btn);
        bluetooth_connect_btn = (Button) findViewById(R.id.bluetooth_connect_btn);



        BTInitialization();




        //OnTouchListener code for the forward button (button long press)
        forward_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    try {
                        sendData(1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    try {
                        sendData(10);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });






        //OnTouchListener code for the reverse button (button long press)
        reverse_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    command = "2";
                    try {
                        outputStream.write(command.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    command = "10";
                    try {
                        outputStream.write(command.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });

        //OnTouchListener code for the forward left button (button long press)
        forward_left_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    command = "3";
                    try {
                        outputStream.write(command.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    command = "10";
                    try {
                        outputStream.write(command.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });

        //OnTouchListener code for the forward right button (button long press)
        forward_right_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    command = "4";
                    try {
                        outputStream.write(command.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    command = "10";
                    try {
                        outputStream.write(command.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });

        //OnTouchListener code for the reverse left button (button long press)
        reverse_left_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    command = "5";
                    try {
                        outputStream.write(command.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    command = "10";
                    try {
                        outputStream.write(command.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });

        //OnTouchListener code for the reverse right button (button long press)
        reverse_right_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    command = "6";
                    try {
                        outputStream.write(command.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    command = "10";
                    try {
                        outputStream.write(command.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });

        //Button that connects the device to the bluetooth module when pressed
        bluetooth_connect_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager ().beginTransaction ();
                DeviceListDialogFragment fragment = DeviceListDialogFragment.newInstance ();
                fragment.setOnDialogResultListener (new DeviceListDialogFragment.OnDialogResultListener () {
                    @Override
                    public void onPositiveResult (final BluetoothDevice bluetoothDevice, final String name, String address) {


                        Utils.hideSoftKeyboard (MainActivity.this);
                        MaterialDialog dialog = new MaterialDialog.Builder (MainActivity.this)
                                .content ("Do you wish to connect to  " + name + "?")
                                .positiveColor (getResources ().getColor (R.color.primary_text))
                                .contentColor (getResources ().getColor (R.color.primary_text))
                                .negativeColor (getResources ().getColor (R.color.primary_text))
                                .typeface (SetTypeFace.getTypeface (MainActivity.this), SetTypeFace.getTypeface (MainActivity.this))
                                .canceledOnTouchOutside (true)
                                .cancelable (true)
                                .positiveText (R.string.dialog_action_yes)
                                .negativeText (R.string.dialog_action_no)
                                .onPositive (new MaterialDialog.SingleButtonCallback () {
                                    @Override
                                    public void onClick (@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        device = bluetoothDevice;
                                        Log.e("karman", device.getName());
                                        Log.e("karman", device.getAddress());
                                        connect(bluetoothDevice, PORT_UUID);
                                    }
                                }).build ();
                        dialog.show ();
                    }

                    @Override
                    public void onNegativeResult () {
                    }
                });
                fragment.show (ft, AppConfigTags.DEVICES);


//                if (BTInitialization()) {
//                    BTConnect();
//                }
            }
        });

    }

    //Initializes bluetooth module
    public void BTInitialization() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        //Checks if the device supports bluetooth
        if (bluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), "Device doesn't support bluetooth", Toast.LENGTH_SHORT).show();
        }

        //Checks if bluetooth is enabled. If not, the program will ask permission from the user to enable it
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableAdapter = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableAdapter, 0);
        }
    }


    public void sendData(int data) throws IOException{
        ByteArrayOutputStream output = new ByteArrayOutputStream(4);
        output.write(data);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(output.toByteArray());
    }

    public int receiveData() throws IOException{
        byte[] buffer = new byte[4];
        ByteArrayInputStream input = new ByteArrayInputStream(buffer);
        InputStream inputStream = socket.getInputStream();
        inputStream.read(buffer);
        return input.read();
    }

    public boolean connect(BluetoothDevice bTDevice, UUID mUUID) {
        try {
            socket = bTDevice.createRfcommSocketToServiceRecord(mUUID);
        } catch (IOException e) {
            Log.e("karman","Could not create RFCOMM socket:" + e.toString());
            return false;
        }
        try {
            socket.connect();
        } catch(IOException e) {
            Log.e("karman","Could not connect: " + e.toString());
            try {
                socket.close();
            } catch(IOException close) {
                Log.e("karman", "Could not close connection:" + e.toString());
                return false;
            }
        }

        try {
            //gets the output stream of the socket
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    public boolean cancel() {
        try {
            socket.close();
        } catch(IOException e) {
            Log.d("karman","Could not close connection:" + e.toString());
            return false;
        }
        return true;
    }

    @Override
    public  void onDestroy(){
        super.onDestroy();
        cancel();
    }
}