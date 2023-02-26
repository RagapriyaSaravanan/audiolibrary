package org.example.service;

import java.rmi.RemoteException;
import java.util.concurrent.ConcurrentHashMap;

import org.example.model.Audio;

public class AudioServiceImp implements AudioService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ConcurrentHashMap<String, Audio> audioDB = new ConcurrentHashMap<String, Audio>();

	@Override
	public String getAudioField(String key) throws RemoteException {
		return "string name";
	}

}
