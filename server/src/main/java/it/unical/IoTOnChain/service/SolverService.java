package it.unical.IoTOnChain.service;

import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface SolverService {
  
  List<String> solveString(String prog);
  
  List<String> solveFile(String filename) throws IOException, URISyntaxException;
  
  List<String> solveFileAndString(String filename, String customString) throws IOException, URISyntaxException;
  
  String readFileAdString(String p) throws IOException, URISyntaxException;
  
  String listToAtomArita1(String name, List value1);
  
  String listToAtomArita2(String name, List<Pair> value);
  
  String listToAtomArita3(String name, List<Triplet> value);
  
  List atomArita1ToList(String output, String atom);
  
  List<Pair> atomArita2ToList(String output, String atom);
  
  List<Triplet> atomArita3ToList(String output, String atom);
}
