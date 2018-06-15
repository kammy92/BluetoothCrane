package com.karman.bluetoothcrane.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.karman.bluetoothcrane.R;
import com.karman.bluetoothcrane.adapter.DeviceAdapter;
import com.karman.bluetoothcrane.model.Device;
import com.karman.bluetoothcrane.utils.AppDetailsPref;
import com.karman.bluetoothcrane.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DeviceListDialogFragment extends DialogFragment {
    OnDialogResultListener onDialogResultListener;
    
    RecyclerView rvDevices;
    List<Device> deviceList = new ArrayList<> ();
    private final BroadcastReceiver deviceReceiver = new BroadcastReceiver () {
        @Override
        public void onReceive (Context context, Intent intent) {
            String action = intent.getAction ();
            int size = deviceList.size ();
            Log.e ("karman", "Discovering Start");
            if (BluetoothDevice.ACTION_FOUND.equals (action)) {
                BluetoothDevice device = intent.getParcelableExtra (BluetoothDevice.EXTRA_DEVICE);
                int rssi = intent.getShortExtra (BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE);
                
                Log.e ("karman", device.getAddress ());
                for (int i = 0; i < deviceList.size (); i++) {
                    Device device1 = deviceList.get (i);
                    if (! device1.getAddress ().equalsIgnoreCase (device.getAddress ())) {
                        deviceList.add (
                                new Device (
                                        device,
                                        device.getName (),
                                        device.getAddress (),
                                        device.getBondState (),
                                        device.getType (),
                                        rssi)
                        );
                    }
                }
            }
            if (BluetoothDevice.EXTRA_DEVICE.equals (action)) {
                BluetoothDevice device = intent.getParcelableExtra (BluetoothDevice.EXTRA_DEVICE);
                int rssi = intent.getShortExtra (BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE);
                
                Log.e ("karman", device.getAddress ());
                for (int i = 0; i < deviceList.size (); i++) {
                    Device device1 = deviceList.get (i);
                    if (! device1.getAddress ().equalsIgnoreCase (device.getAddress ())) {
                        deviceList.add (
                                new Device (
                                        device,
                                        device.getName (),
                                        device.getAddress (),
                                        device.getBondState (),
                                        device.getType (),
                                        rssi)
                        );
                    }
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals (action)) {
                Utils.showLog (Log.ERROR, "karman", "Discovery Finished ", true);
                swipe_refresh_layout.setRefreshing (false);
                if (size != deviceList.size ()) {
                    deviceAdapter.notifyDataSetChanged ();
                } else {
                    Utils.showToast (getActivity (), "No New Devices Found", false);
                }
            }
        }
    };
    
    LinearLayoutManager linearLayoutManager;
    DeviceAdapter deviceAdapter;
    
    ImageView ivCancel;
    ImageView ivSearch;
    TextView tvTitle;
    RelativeLayout rlSearch;
    EditText etSearch;
    
    RelativeLayout rlNoResultFound;
    SwipeRefreshLayout swipe_refresh_layout;
    
    AppDetailsPref appDetailsPref;
    List<Device> deviceListTemp = new ArrayList<> ();
    BluetoothAdapter bluetoothAdapter;
    
    public static DeviceListDialogFragment newInstance () {
        DeviceListDialogFragment fragment = new DeviceListDialogFragment ();
        Bundle args = new Bundle ();
//        args.putInt (AppConfigTags.PROJECT_ID, project_id);
        fragment.setArguments (args);
        return fragment;
    }
    
    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setStyle (DialogFragment.STYLE_NORMAL, R.style.AppTheme);
    }
    
    @Override
    public void onActivityCreated (Bundle arg0) {
        super.onActivityCreated (arg0);
        Window window = getDialog ().getWindow ();
        window.getAttributes ().windowAnimations = R.style.DialogAnimation;
//        if (Build.VERSION.SDK_INT >= 21) {
//            window.clearFlags (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.addFlags (WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor (ContextCompat.getColor (getActivity (), R.color.text_color_white));
//        }
    }
    
    @Override
    public void onResume () {
        super.onResume ();
        getDialog ().setOnKeyListener (new DialogInterface.OnKeyListener () {
            @Override
            public boolean onKey (DialogInterface dialog, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_BACK)) {
                    //This is the filter
                    if (event.getAction () != KeyEvent.ACTION_UP)
                        return true;
                    else {
                        if (rlSearch.getVisibility () == View.VISIBLE) {
                            final Handler handler = new Handler ();
                            handler.postDelayed (new Runnable () {
                                @Override
                                public void run () {
                                    ivSearch.setVisibility (View.VISIBLE);
                                    etSearch.setText ("");
                                }
                            }, 300);
                            final Handler handler2 = new Handler ();
                            handler2.postDelayed (new Runnable () {
                                @Override
                                public void run () {
                                    final InputMethodManager imm = (InputMethodManager) getActivity ().getSystemService (Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow (getView ().getWindowToken (), 0);
                                }
                            }, 600);
                            rlSearch.setVisibility (View.GONE);
                        } else {
                            getDialog ().dismiss ();
                        }
                        //Hide your keyboard here!!!!!!
                        return true; // pretend we've processed it
                    }
                } else
                    return false; // pass on to be processed as normal
            }
        });
    }
    
    @Override
    public void onStart () {
        super.onStart ();
        Dialog d = getDialog ();
        if (d != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            d.getWindow ().setLayout (width, height);
        }
    }
    
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate (R.layout.fragment_dialog_device_list, container, false);
        initView (root);
        initBundle ();
        initData ();
        initListener ();
        setData ();
        return root;
    }
    
    private void initView (View root) {
        tvTitle = (TextView) root.findViewById (R.id.tvTitle);
        rvDevices = (RecyclerView) root.findViewById (R.id.rvDevices);
        ivCancel = (ImageView) root.findViewById (R.id.ivCancel);
        ivSearch = (ImageView) root.findViewById (R.id.ivSearch);
        etSearch = (EditText) root.findViewById (R.id.etSearch);
        rlSearch = (RelativeLayout) root.findViewById (R.id.rlSearch);
        rlNoResultFound = (RelativeLayout) root.findViewById (R.id.rlNoResultFound);
        swipe_refresh_layout = (SwipeRefreshLayout) root.findViewById (R.id.swipe_refresh_layout);
    }
    
    private void initBundle () {
        Bundle bundle = this.getArguments ();
//        project_id = bundle.getInt (AppConfigTags.PROJECT_ID);
    }
    
    private void initData () {
        Utils.setTypefaceToAllViews (getActivity (), tvTitle);
        appDetailsPref = AppDetailsPref.getInstance ();
        
        linearLayoutManager = new LinearLayoutManager (getActivity (), LinearLayoutManager.VERTICAL, false);
        
        deviceAdapter = new DeviceAdapter (getActivity (), deviceList);
        deviceAdapter.SetOnItemClickListener (new DeviceAdapter.OnItemClickListener () {
            @Override
            public void onItemClick (View view, int position) {
                Utils.hideSoftKeyboard (getActivity ());
                onDialogResultListener.onPositiveResult (deviceList.get (position).getBluetoothDevice ());
                getDialog ().dismiss ();
            }
        });
        
        rvDevices.setAdapter (deviceAdapter);
        rvDevices.setHasFixedSize (true);
        rvDevices.setLayoutManager (linearLayoutManager);
        rvDevices.setItemAnimator (new DefaultItemAnimator ());
        rvDevices.addItemDecoration (
                new DividerItemDecoration (getActivity (), linearLayoutManager.getOrientation ()) {
                    @Override
                    public void getItemOffsets (Rect outRect, View view, RecyclerView
                            parent, RecyclerView.State state) {
                        int position = parent.getChildAdapterPosition (view);
                        // hide the divider for the last child
                        if (position == parent.getAdapter ().getItemCount () - 1) {
                            outRect.setEmpty ();
                        } else {
                            super.getItemOffsets (outRect, view, parent, state);
                        }
                    }
                }
        );
    }
    
    private void initListener () {
        ivCancel.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                if (rlSearch.getVisibility () == View.VISIBLE) {
                    final Handler handler = new Handler ();
                    handler.postDelayed (new Runnable () {
                        @Override
                        public void run () {
                            ivSearch.setVisibility (View.VISIBLE);
                            etSearch.setText ("");
                        }
                    }, 600);
                    final Handler handler2 = new Handler ();
                    handler2.postDelayed (new Runnable () {
                        @Override
                        public void run () {
                            final InputMethodManager imm = (InputMethodManager) getActivity ().getSystemService (Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow (getView ().getWindowToken (), 0);
                        }
                    }, 300);
                    rlSearch.setVisibility (View.GONE);
                } else {
                    getDialog ().dismiss ();
                }
            }
        });
        ivSearch.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                final Handler handler = new Handler ();
                handler.postDelayed (new Runnable () {
                    @Override
                    public void run () {
                        ivSearch.setVisibility (View.GONE);
                        etSearch.requestFocus ();
                    }
                }, 300);
                final Handler handler2 = new Handler ();
                handler2.postDelayed (new Runnable () {
                    @Override
                    public void run () {
                        final InputMethodManager imm = (InputMethodManager) getActivity ().getSystemService (Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput (InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    }
                }, 600);
                rlSearch.setVisibility (View.VISIBLE);
            }
        });
        
        etSearch.addTextChangedListener (new TextWatcher () {
            @Override
            public void onTextChanged (CharSequence s, int start, int before, int count) {
            }
            
            @Override
            public void beforeTextChanged (CharSequence s, int start, int count, int after) {
            }
            
            @Override
            public void afterTextChanged (Editable s) {
                if (s.toString ().length () == 0) {
                    DeviceAdapter deviceAdapter = new DeviceAdapter (getActivity (), deviceList);
                    rvDevices.setAdapter (deviceAdapter);
                    
                    deviceAdapter.SetOnItemClickListener (new DeviceAdapter.OnItemClickListener () {
                        @Override
                        public void onItemClick (View view, int position) {
                            Utils.hideSoftKeyboard (getActivity ());
                            onDialogResultListener.onPositiveResult (deviceList.get (position).getBluetoothDevice ());
                            getDialog ().dismiss ();
                        }
                    });
                    
                    rlNoResultFound.setVisibility (View.GONE);
                    rvDevices.setVisibility (View.VISIBLE);
                }
                if (s.toString ().length () > 0) {
                    deviceListTemp.clear ();
                    for (Device device : deviceList) {
                        if (device.getName ().toUpperCase ().contains (s.toString ().toUpperCase ()) ||
                                device.getName ().toLowerCase ().contains (s.toString ().toLowerCase ()) ||
                                device.getAddress ().toUpperCase ().contains (s.toString ().toUpperCase ()) ||
                                device.getAddress ().toUpperCase ().contains (s.toString ().toUpperCase ())) {
                            deviceListTemp.add (device);
                        }
                    }
                    deviceAdapter = new DeviceAdapter (getActivity (), deviceListTemp);
                    rvDevices.setAdapter (deviceAdapter);
                    deviceAdapter.SetOnItemClickListener (new DeviceAdapter.OnItemClickListener () {
                        @Override
                        public void onItemClick (View view, int position) {
                            Utils.hideSoftKeyboard (getActivity ());
                            onDialogResultListener.onPositiveResult (deviceListTemp.get (position).getBluetoothDevice ());
                            getDialog ().dismiss ();
                        }
                    });
                    
                    if (deviceListTemp.size () == 0) {
                        rlNoResultFound.setVisibility (View.VISIBLE);
                        rvDevices.setVisibility (View.GONE);
                    } else {
                        rvDevices.setVisibility (View.VISIBLE);
                        rlNoResultFound.setVisibility (View.GONE);
                    }
                }
            }
        });
        swipe_refresh_layout.setOnRefreshListener (new SwipeRefreshLayout.OnRefreshListener () {
            @Override
            public void onRefresh () {
                setData ();
            }
        });
    }
    
    private void setData () {
        deviceList.clear ();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter ();
        try {
            Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices ();
            if (bondedDevices.isEmpty ()) {
//                Toast.makeText (getActivity (), "Please pair the device first", Toast.LENGTH_SHORT).show ();
            } else {
                for (BluetoothDevice device : bondedDevices) {
                    deviceList.add (new Device (device, device.getName (), device.getAddress (), device.getBondState (), device.getType (), 0));
                }
            }
    
            // Register the BroadcastReceiver
            IntentFilter filter = new IntentFilter (BluetoothDevice.ACTION_FOUND);
            filter.addAction (BluetoothDevice.EXTRA_DEVICE);
            filter.addAction (BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
            getActivity ().registerReceiver (deviceReceiver, filter);
    
            bluetoothAdapter.startDiscovery ();

//            if (bluetoothAdapter.isDiscovering ())
//                bluetoothAdapter.cancelDiscovery ();
//            bluetoothAdapter.startDiscovery ();

// This callback is added to the start scan method as a parameter in this way
//            bluetoothAdapter.startLeScan (mLeScanCallback);
    
    
            deviceAdapter.notifyDataSetChanged ();
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }
    
    public void setOnDialogResultListener (OnDialogResultListener listener) {
        this.onDialogResultListener = listener;
    }
    
    @Override
    public void onDismiss (DialogInterface dialog) {
        super.onDismiss (dialog);
        Utils.hideSoftKeyboard (getActivity ());
        if (onDialogResultListener != null) {
            onDialogResultListener.onNegativeResult ();
            dialog.cancel ();
        }
    }
    
    @Override
    public void onPause () {
        super.onPause ();
        getActivity ().unregisterReceiver (deviceReceiver);
        bluetoothAdapter.cancelDiscovery ();
    }
    
    public interface OnDialogResultListener {
        public abstract void onPositiveResult (BluetoothDevice bluetoothDevice);
    
        public abstract void onNegativeResult ();
    }
}