package main;

import domein.DomeinController;
import userinterface.cui.StartUpInterface;

public class StartUp {
	public static void main(String[] args) {
		DomeinController dc = new DomeinController();
		System.out.println("welkom");
		new StartUpInterface(dc);
	}
}
