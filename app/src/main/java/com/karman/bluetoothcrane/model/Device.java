package com.karman.bluetoothcrane.model;

import android.bluetooth.BluetoothDevice;

public class Device {
    private BluetoothDevice bluetoothDevice;
    private String name;
    private String address;
    private int state;
    private int type;
    private int rssi;
    
    public Device (BluetoothDevice bluetoothDevice, String name, String address, int state, int type, int rssi) {
        this.bluetoothDevice = bluetoothDevice;
        this.name = name;
        this.address = address;
        this.state = state;
        this.type = type;
        this.rssi = rssi;
    }
    
    public BluetoothDevice getBluetoothDevice () {
        return bluetoothDevice;
    }
    
    public void setBluetoothDevice (BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
    }
    
    public String getName () {
        return name;
    }
    
    public void setName (String name) {
        this.name = name;
    }
    
    public String getAddress () {
        return address;
    }
    
    public void setAddress (String address) {
        this.address = address;
    }
    
    public int getState () {
        return state;
    }
    
    public void setState (int state) {
        this.state = state;
    }
    
    public int getType () {
        return type;
    }
    
    public void setType (int type) {
        this.type = type;
    }
    
    public int getRssi () {
        return rssi;
    }
    
    public void setRssi (int rssi) {
        this.rssi = rssi;
    }
}