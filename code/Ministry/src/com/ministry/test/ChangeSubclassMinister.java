package com.ministry.test;

import java.util.HashMap;

import church.ministry.control.ecode.EMapper;
import church.ministry.control.log.Logger;
import church.ministry.control.org.OrgHandler;
import church.ministry.model.file.FileAccess;
import church.ministry.model.oracle.DatabaseConnectionManager;
import church.ministry.util.Init;

import com.vaadin.ui.Notification;

public class ChangeSubclassMinister {

	public static void start() {

		String[] input = FileAccess.readFileIntoArray("input.txt");

		Logger.init();
		Init.initiate();

		if (!DatabaseConnectionManager.connect()) {
			int result = EMapper.ECODE_DATABASE_CONN_FAILURE;
			Notification.show(EMapper.getEDesc(result), EMapper.getNType(result));
			return;
		}

		String subClassName, ministerName;

		for (int i = 0; i < input.length; i++) {
			ministerName = input[i].split(",")[0];
			subClassName = input[i].split(",")[1];

			System.out.println("minister: " + ministerName);
			System.out.println("subclass: " + subClassName);

			HashMap<String, String> requestData = new HashMap<String, String>();

			requestData.put("subClassName", subClassName);
			requestData.put("ministerName", ministerName);

			OrgHandler.changeSubClassMinister(requestData);
		}

	}
}
