package com.ministry.test;

import java.util.HashMap;

import com.vaadin.ui.Notification;

import church.ministry.control.ecode.EMapper;
import church.ministry.control.handler.RequestHandler;
import church.ministry.control.log.Logger;
import church.ministry.model.file.FileAccess;
import church.ministry.model.oracle.DatabaseConnectionManager;
import church.ministry.util.Init;

public class DeleteChild {
	public static void main(String[] args) {

		String[] input = FileAccess.readFileIntoArray("input/deleted_children/20170825_1506.txt");

		Logger.init();
		Init.initiate();

		if (!DatabaseConnectionManager.connect()) {
			int result = EMapper.ECODE_DATABASE_CONN_FAILURE;
			Notification.show(EMapper.getEDesc(result), EMapper.getNType(result));
			return;
		}

		String id, name;

		for (int i = 0; i < input.length; i++) {
			id = input[i].split(",")[0];
			name = input[i].split(",")[1];

			HashMap<String, String> requestData = new HashMap<String, String>();
			requestData.put("id", id);
			requestData.put("childName", name);

			int result = RequestHandler.handleUpdateRequest("deleteChild", requestData);

			if (result == EMapper.ECODE_SUCCESS) {
				Logger.info("DELETE_CHILD:" + id + "," + name + "," + "Succeeded");
				FileAccess.appendToFile("delete_logs.txt", "DELETE_CHILD:" + id + "," + name + ","
						+ "Succeeded\r\n");
			} else {
				Logger.error("DELETE_CHILD:" + id + "," + name + "," + "Failed");
				Logger.info("DELETE_CHILD:" + id + "," + name + "," + "Failed");
				FileAccess.appendToFile("delete_logs.txt", "DELETE_CHILD:" + id + "," + name + ","
						+ "Failed\r\n");
			}
		}

	}
}
