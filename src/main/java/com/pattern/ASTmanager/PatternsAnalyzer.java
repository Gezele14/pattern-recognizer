package com.pattern.ASTmanager;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


import java.util.ArrayList;

public class PatternsAnalyzer {
  private JSONArray jsonPattern;
  private ArrayList<String> implementedPatterns;


  /**
   * Constructor
   * @author gezele14
   * */
  public PatternsAnalyzer(JSONArray json){
    this.jsonPattern = json;
    this.implementedPatterns = new ArrayList<>();
  }

  /**
   * Analyze jsonArray to search Pattens
   * @author gezele14
   * */
  public ArrayList<String> getPatternsOfAST(){
    if (this.isSingleton()){
      this.implementedPatterns.add("Singleton");
    }

    return this.implementedPatterns;
  }

  /**
   * Analyze to search Singleton pattern
   * @author gezele14
   * */
  private boolean isSingleton() {
    boolean hasConstructor = false;
    boolean hasMethod = false;
    boolean hasArgument = false;
    for (int i=0; i <= this.jsonPattern.size()-1; i++){
      String className = (String) ((JSONObject) jsonPattern.get(i)).get("className");
      ArrayList<JSONObject> methods = this.getMethods((JSONObject) jsonPattern.get(i));

      //Analyze methods
      for(JSONObject method: methods){
        ArrayList<String> modifiers = this.getModifiers(method);
        if(method.get("returnType") == null) { //null significa que es el constructor
          for (String modifier : modifiers) {
            if (modifier.equals("public")) {
              return false;
            }
            hasConstructor = true;
          }
        }else if(((String)method.get("returnType")).equals(className)) {
          String cond = "";
          for (String modifier : modifiers) {
            cond += modifier + " ";
          }
          if (!cond.equals("public static ")) {
            return false;
          } else{
            hasMethod = true;
          }
        }
      }

      ArrayList<JSONObject> arguments = this.getArguments((JSONObject) jsonPattern.get(i));
      for(JSONObject arg: arguments){
        ArrayList<String> modifiers = this.getModifiers(arg);
        if (((String) arg.get("type")).equals(className)){
          String cond = "";
          for (String modifier : modifiers) {
            cond += modifier + " ";
          }
          if (cond.equals("private static ")) {
            hasArgument = true;
          }
        }
      }
    }

    if(hasConstructor && hasMethod && hasArgument){
      return true;
    }else {
      return false;
    }
  }

  private ArrayList<String> getModifiers(JSONObject object){
    ArrayList<String> modifiers = new ArrayList<>();
    JSONArray mod = (JSONArray) object.get("modifier");
    for(int i=0; i<= mod.size()-1;i++){
      modifiers.add((String) mod.get(i));
    }
    return modifiers;
  }


  private ArrayList<JSONObject> getMethods(JSONObject object){
    ArrayList<JSONObject> methods = new ArrayList<>();
    JSONArray mod = (JSONArray) object.get("methods");
    for(int i=0; i<= mod.size()-1;i++){
      methods.add((JSONObject) mod.get(i));
    }
    return methods;
  }

  private ArrayList<JSONObject> getArguments(JSONObject object){
    ArrayList<JSONObject> args = new ArrayList<>();
    JSONArray mod = (JSONArray) object.get("atributes");
    for(int i=0; i<= mod.size()-1;i++){
      args.add((JSONObject) mod.get(i));
    }
    return args;
  }

}
