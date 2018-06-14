package com.karman.bluetoothcrane.activity;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

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
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private final UUID PORT_UUID = UUID.fromString ("00001101-0000-1000-8000-00805f9b34fb");
    
    
    private BluetoothSocket socket;
    private OutputStream outputStream;
    
    TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10, tv11, tv12;
    
    TextView tvConnect;
    
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        initView ();
        initData ();
        initListener ();
    }
    
    private void initView () {
        tv1 = (TextView) findViewById (R.id.tv1);
        tv2 = (TextView) findViewById (R.id.tv2);
        tv3 = (TextView) findViewById (R.id.tv3);
        tv4 = (TextView) findViewById (R.id.tv4);
        tv5 = (TextView) findViewById (R.id.tv5);
        tv6 = (TextView) findViewById (R.id.tv6);
        tv7 = (TextView) findViewById (R.id.tv7);
        tv8 = (TextView) findViewById (R.id.tv8);
        tv9 = (TextView) findViewById (R.id.tv9);
        tv10 = (TextView) findViewById (R.id.tv10);
        tv11 = (TextView) findViewById (R.id.tv11);
        tv12 = (TextView) findViewById (R.id.tv12);
        tvConnect = (TextView) findViewById (R.id.tvConnect);
    }
    
    private void initData () {
        initBluetooth ();
    }
    
    @SuppressLint("ClickableViewAccessibility")
    private void initListener () {
        tv1.setOnTouchListener (new View.OnTouchListener () {
            @Override
            public boolean onTouch (View v, MotionEvent event) {
                if (event.getAction () == MotionEvent.ACTION_DOWN) {
                    sendData (1);
                } else if (event.getAction () == MotionEvent.ACTION_UP) {
                    sendData (0);
                }
                return false;
            }
        });
        
        tv2.setOnTouchListener (new View.OnTouchListener () {
            @Override
            public boolean onTouch (View v, MotionEvent event) {
                if (event.getAction () == MotionEvent.ACTION_DOWN) {
                    sendData (2);
                } else if (event.getAction () == MotionEvent.ACTION_UP) {
                    sendData (0);
                }
                return false;
            }
        });
        
        tv3.setOnTouchListener (new View.OnTouchListener () {
            @Override
            public boolean onTouch (View v, MotionEvent event) {
                if (event.getAction () == MotionEvent.ACTION_DOWN) {
                    sendData (3);
                } else if (event.getAction () == MotionEvent.ACTION_UP) {
                    sendData (0);
                }
                return false;
            }
        });
        
        tv4.setOnTouchListener (new View.OnTouchListener () {
            @Override
            public boolean onTouch (View v, MotionEvent event) {
                if (event.getAction () == MotionEvent.ACTION_DOWN) {
                    sendData (4);
                } else if (event.getAction () == MotionEvent.ACTION_UP) {
                    sendData (0);
                }
                return false;
            }
        });
        
        tv5.setOnTouchListener (new View.OnTouchListener () {
            @Override
            public boolean onTouch (View v, MotionEvent event) {
                if (event.getAction () == MotionEvent.ACTION_DOWN) {
                    sendData (5);
                } else if (event.getAction () == MotionEvent.ACTION_UP) {
                    sendData (0);
                }
                return false;
            }
        });
        
        tv6.setOnTouchListener (new View.OnTouchListener () {
            @Override
            public boolean onTouch (View v, MotionEvent event) {
                if (event.getAction () == MotionEvent.ACTION_DOWN) {
                    sendData (6);
                } else if (event.getAction () == MotionEvent.ACTION_UP) {
                    sendData (0);
                }
                return false;
            }
        });
        
        tv7.setOnTouchListener (new View.OnTouchListener () {
            @Override
            public boolean onTouch (View v, MotionEvent event) {
                if (event.getAction () == MotionEvent.ACTION_DOWN) {
                    sendData (7);
                } else if (event.getAction () == MotionEvent.ACTION_UP) {
                    sendData (0);
                }
                return false;
            }
        });
        
        tv8.setOnTouchListener (new View.OnTouchListener () {
            @Override
            public boolean onTouch (View v, MotionEvent event) {
                if (event.getAction () == MotionEvent.ACTION_DOWN) {
                    sendData (8);
                } else if (event.getAction () == MotionEvent.ACTION_UP) {
                    sendData (0);
                }
                return false;
            }
        });
        
        tv9.setOnTouchListener (new View.OnTouchListener () {
            @Override
            public boolean onTouch (View v, MotionEvent event) {
                if (event.getAction () == MotionEvent.ACTION_DOWN) {
                    sendData (9);
                } else if (event.getAction () == MotionEvent.ACTION_UP) {
                    sendData (0);
                }
                return false;
            }
        });
        
        tv10.setOnTouchListener (new View.OnTouchListener () {
            @Override
            public boolean onTouch (View v, MotionEvent event) {
                if (event.getAction () == MotionEvent.ACTION_DOWN) {
                    sendData (10);
                } else if (event.getAction () == MotionEvent.ACTION_UP) {
                    sendData (0);
                }
                return false;
            }
        });
        
        tv11.setOnTouchListener (new View.OnTouchListener () {
            @Override
            public boolean onTouch (View v, MotionEvent event) {
                if (event.getAction () == MotionEvent.ACTION_DOWN) {
                    sendData (11);
                } else if (event.getAction () == MotionEvent.ACTION_UP) {
                    sendData (0);
                }
                return false;
            }
        });
        
        tv12.setOnTouchListener (new View.OnTouchListener () {
            @Override
            public boolean onTouch (View v, MotionEvent event) {
                if (event.getAction () == MotionEvent.ACTION_DOWN) {
                    sendData (12);
                } else if (event.getAction () == MotionEvent.ACTION_UP) {
                    sendData (0);
                }
                return false;
            }
        });
        
        tvConnect.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
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
                                        Utils.showLog (Log.ERROR, "karman", "Name : " + bluetoothDevice.getName () + ", Address : " + bluetoothDevice.getAddress (), false);
                                        startConnection (bluetoothDevice);
                                    }
                                }).build ();
                        dialog.show ();
                    }
    
                    @Override
                    public void onNegativeResult () {
                    }
                });
                fragment.show (ft, AppConfigTags.DEVICES);
            }
        });
    }
    
    public void initBluetooth () {
        try {
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter ();
            //Checks if the device supports bluetooth
            if (bluetoothAdapter == null) {
                Utils.showToast (this, "Device doesn't support bluetooth", false);
            }
            //Checks if bluetooth is enabled. If not, the program will ask permission from the user to enable it
            if (! bluetoothAdapter.isEnabled ()) {
                startActivityForResult (new Intent (BluetoothAdapter.ACTION_REQUEST_ENABLE), 0);
            }
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }
    
    public void sendData (int data) {
        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream (4);
            output.write (data);
            outputStream.write (output.toByteArray ());
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }
    
    public int receiveData () {
        try {
            byte[] buffer = new byte[4];
            ByteArrayInputStream input = new ByteArrayInputStream (buffer);
            InputStream inputStream = socket.getInputStream ();
            inputStream.read (buffer);
            return input.read ();
        } catch (IOException e) {
            e.printStackTrace ();
            return 0;
        }
    }
    
    public void startConnection (BluetoothDevice device) {
        try {
            socket = device.createRfcommSocketToServiceRecord (PORT_UUID);
            socket.connect ();
            outputStream = socket.getOutputStream ();
        } catch (IOException e) {
            e.printStackTrace ();
            Utils.showLog (Log.ERROR, "karman", "Could not connect : " + e.toString (), true);
        }
    }
    
    public void closeConnection () {
        try {
            socket.close ();
        } catch (IOException e) {
            Utils.showLog (Log.ERROR, "karman", "Could not close connection:" + e.toString (), true);
        }
    }
    
    @Override
    public void onDestroy () {
        super.onDestroy ();
        closeConnection ();
    }
}