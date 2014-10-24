package com.example.vaadinproj.domain;

import java.util.Date;

public class Wydarzenie {
	private String data;
	private String tresc = "";
	public Wydarzenie(String data, String tresc) {
		this.data = data;
		this.tresc = tresc;
	}
	public Wydarzenie () {
		
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getTresc() {
		return tresc;
	}
	public void setTresc(String tresc) {
		this.tresc = tresc;
	}
}
