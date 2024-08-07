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