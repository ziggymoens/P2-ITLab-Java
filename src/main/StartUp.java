package main;

import domein.DomeinController;
import language.Talen;
import userinterface.cui.StartUpInterface;

import java.util.Scanner;

public class StartUp {
	public static void main(String[] args) {
		DomeinController dc = new DomeinController();

		//System.out.println(Talen.getString("Welkom"));

		System.out.println("welkom\n");
		new StartUpInterface(dc);


	}
}
