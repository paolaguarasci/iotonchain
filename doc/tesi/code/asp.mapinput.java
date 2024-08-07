@Override
public String listToAtomArita3(String name, List<Triplet> value) {
  StringBuilder sb = new StringBuilder();
  for (int i = 0; i < value.size(); i++) {      sb.append(name).append("(").append(value.get(i).getValue0()).append(",").append(value.get(i).getValue1()).append(",").append(value.get(i).getValue2()).append("). ");
    }
  return sb.toString();
}