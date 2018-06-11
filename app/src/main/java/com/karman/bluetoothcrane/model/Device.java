package com.karman.bluetoothcrane.model;

import android.bluetooth.BluetoothDevice;

public class Device {
    private String name;
    private String address;
    private boolean connected;
    private BluetoothDevice bluetoothDevice;
    private String type;

    public Device(BluetoothDevice bluetoothDevice, String name, String address, String type,boolean connected) {
        this.bluetoothDevice = bluetoothDevice;
        this.name = name;
        this.address = address;
        this.type = type;
        this.connected = connected;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BluetoothDevice getBluetoothDevice() {
        return bluetoothDevice;
    }

    public void setBluetoothDevice(BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
}