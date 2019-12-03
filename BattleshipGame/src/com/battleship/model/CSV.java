package com.battleship.model;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class CSV {
	
	private String csvFile = "csv\\PlayersScore.csv";
	private File file = new File(csvFile);
	
	public void writeDataLine(String name, Integer score) throws FileNotFoundException {
		try {
			Scanner sc = new Scanner(System.in);
			BufferedWriter out = new BufferedWriter(new FileWriter(csvFile, true));
			out.write(name);
			out.write(',');
			out.write(score.toString());
			out.newLine();
			out.close();
			sc.close();

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public void deleteCSV(){
		
		file.delete();
	}
	
	public void copyToArray(ArrayList<String> names, ArrayList<Integer> scores) {
		BufferedReader filereader = null;
		String line;
		
		try {
			filereader = new BufferedReader(new FileReader(csvFile));
			while((line = filereader.readLine()) != null) {
				
				names.add(line.substring(0, line.indexOf(",")));
				scores.add(Integer.valueOf(line.substring(line.indexOf(",")+1)));
				
			}
			filereader.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (@SuppressWarnings("hiding") IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void copyFromArray(ArrayList<String> names, ArrayList<Integer> scores) throws FileNotFoundException {
		try {
			int i = 0;
			Scanner sc = new Scanner(System.in);
			BufferedWriter out = new BufferedWriter(new FileWriter(csvFile, true));
			while(i<names.size()) {
			out.write(names.get(i));
			out.write(',');
			out.write(scores.get(i).toString());
			out.newLine();
			i++;
			}
			out.close();
			sc.close();

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public void sort(ArrayList<String> names, ArrayList<Integer> scores, String name) {
		int i = names.indexOf(name);
		int j = 0;
		while(j!=i) {
			if (scores.get(j)<scores.get(i))
				break;
			else
				j++;
		}
		if(i!=j) {
			scores.add(j, scores.get(i));
			names.add(j, names.get(i));
			scores.remove(i+1);
			names.remove(i+1);
		}
	}
	
	public File getFile() {
		return file;
	}
}







