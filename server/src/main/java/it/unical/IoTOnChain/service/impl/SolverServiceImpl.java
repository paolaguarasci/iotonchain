package it.unical.IoTOnChain.service.impl;

import it.unical.IoTOnChain.service.SolverService;
import lombok.extern.slf4j.Slf4j;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.potassco.clingo.control.Control;
import org.potassco.clingo.solving.Model;
import org.potassco.clingo.solving.SolveHandle;
import org.potassco.clingo.solving.SolveMode;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class SolverServiceImpl implements SolverService {
  private Control control;
  
  SolverServiceImpl() {
//    this.control = new Control("0");
  }
  
  @Override
  public List<String> solveString(String prog) {
    List<String> results = new ArrayList<>();
    control = new Control("0");
    control.add(prog);
    control.ground();
    
    SolveHandle handle = control.solve(SolveMode.YIELD);
    while (handle.hasNext()) {
      Model model = handle.next();
      results.add(model.toString());
    }
    
    control.close();
    return results;
  }
  
  @Override
  public List<String> solveFile(String filename) throws IOException, URISyntaxException {
    List<String> results = new ArrayList<>();
    control = new Control("0");
    control.add(readFileAdString("asp/" + filename));
    control.ground();
    
    SolveHandle handle = control.solve(SolveMode.YIELD);
    while (handle.hasNext()) {
      Model model = handle.next();
      results.add(model.toString());
    }
    
    control.close();
    return results;
  }
  
  @Override
  public List<String> solveFileAndString(String filename, String customString) throws IOException, URISyntaxException {
    List<String> results = new ArrayList<>();
    control = new Control("0");
    String prog = readFileAdString("asp/" + filename);
    log.info(prog);
    control.add(prog);
    control.add(customString);
    
    log.debug("Ciao, sono qui1!\n {}\n {}", prog, customString);
    
    control.ground();
    log.debug("Ciao, sono qui2!\n {}\n {}", prog, customString);
    try (SolveHandle handle = control.solve(SolveMode.YIELD)) {
      while (handle.hasNext()) {
        Model model = handle.next();
        results.add(model.toString());
      }
    }
    
    control.close();
    return results;
  }
  
  @Override
  public String readFileAdString(String p) throws IOException, URISyntaxException {
    Path path = Paths.get(getClass().getClassLoader().getResource(p).toURI());
    
    Stream<String> lines = Files.lines(path);
    String data = lines.collect(Collectors.joining(" "));
    lines.close();
    
    return data;
  }
  
  @Override
  public String listToAtomArita1(String name, List value1) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < value1.size(); i++) {
      sb.append(name).append("(").append(value1.get(i)).append("). ");
    }
    return sb.toString().trim();
  }
  
  
  @Override
  public String listToAtomArita2(String name, List<Pair> value) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < value.size(); i++) {
      sb.append(name).append("(").append(value.get(i).getValue0()).append(",").append(value.get(i).getValue1()).append("). ");
    }
    return sb.toString().trim();
  }
  
  @Override
  public String listToAtomArita3(String name, List<Triplet> value) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < value.size(); i++) {
      sb.append(name).append("(").append(value.get(i).getValue0()).append(",").append(value.get(i).getValue1()).append(",").append(value.get(i).getValue2()).append("). ");
    }
    return sb.toString();
  }
  
  @Override
  public List atomArita1ToList(String output, String atom) {
    return Arrays.stream(output.split(" "))
      .filter(a -> a.startsWith(atom))
      .map(a -> a.replace(atom, "").replace("(", "").replace(")", ""))
      .toList();
  }
  
  @Override
  public List<Pair> atomArita2ToList(String output, String atom) {
    List<Pair> result = new ArrayList<>();
    Arrays.stream(output.split(" "))
      .filter(a -> a.startsWith(atom))
      .forEach(a -> {
        var x = a.replace(atom, "").replace("(", "").replace(")", "");
        System.out.println(x);
        String[] split = x.split(",");
        result.add(Pair.with(split[0], split[1]));
      });
    return result;
  }
  
  @Override
  public List<Triplet> atomArita3ToList(String output, String atom) {
    List<Triplet> result = new ArrayList<>();
    Arrays.stream(output.split(" "))
      .filter(a -> a.startsWith(atom))
      .forEach(a -> {
        var x = a.replace(atom, "").replace("(", "").replace(")", "");
        String[] split = x.split(",");
        result.add(Triplet.with(split[0], split[1], split[2]));
      });
    return result;
  }
}
