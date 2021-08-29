package com.mabu.MabuWebStore.uploadfile;

import java.io.File;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("storage")
public class StorageProperties {

	/**
	 * Folder location for storing files
	 */
	private String location = "C:\\Users\\Asus\\Documents\\GitHub\\MabuWebStore\\target\\classes\\static\\img";
//	private String location = "D:\\DuAn2\\DOCUMENT\\MabuWebStore\\src\\main\\resources\\static\\img";
	//private String location = "E:\\TTK\\mabu-27082021\\MabuWebStore\\src\\main\\resources\\static\\img";

	public String getLocation() {
		System.out.println(location);
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
