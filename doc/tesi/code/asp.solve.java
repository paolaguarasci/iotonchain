  @Override
  public List<String> solveFileAndString(String filename, String customString) throws IOException, URISyntaxException {
    List<String> results = new ArrayList<>();
    control = new Control("0");
    String prog = readFileAdString("asp/" + filename);
    control.add(prog);
    control.add(customString);
    control.ground();
    try (SolveHandle handle = control.solve(SolveMode.YIELD)) {
      while (handle.hasNext()) {
        Model model = handle.next();
        results.add(model.toString());
      }
    }   
    control.close();
    return results;
  }