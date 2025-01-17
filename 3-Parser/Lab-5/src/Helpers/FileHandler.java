package Helpers;

import DataStructures.CanonicalCollection;
import DataStructures.HashTable;
import DataStructures.Pair;

import java.io.*;
import java.util.Map;
import java.util.Scanner;

public class FileHandler {
    public static void populateSymbolTable(String filename, HashTable table) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                table.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFromFile(String filePath) throws FileNotFoundException {
        StringBuilder fileContent = new StringBuilder();
        Scanner scanner = new Scanner(new File(filePath));
        while(scanner.hasNextLine()){
            fileContent.append(scanner.nextLine()).append("\n");
        }

        return fileContent.toString().replace("\t", "");
    }

    public static void writeToFile(String filePath, Object object) {
        try(PrintStream printStream = new PrintStream(filePath)) {
            printStream.println(object);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void writeLineToFile(String file, String line) throws IOException {
        FileWriter fileWriter = new FileWriter(file, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(line);
        bufferedWriter.newLine();
        bufferedWriter.close();
    }

    public static void printCanonicalCollection(CanonicalCollection canonicalCollection) {
        System.out.println("States:");
        for (int i = 0; i < canonicalCollection.getStates().size(); i++) {
            System.out.println(i + " " + canonicalCollection.getStates().get(i));
        }

        System.out.println("\nState.State transitions:");
        for (Map.Entry<Pair<Integer, String>, Integer> entry : canonicalCollection.getAdjacencyList().entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}
