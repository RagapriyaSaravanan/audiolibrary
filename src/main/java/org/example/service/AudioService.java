package org.example.service;

import java.rmi.RemoteException;

interface AudioService {

	public String getAudioField(String key) throws RemoteException;
}
