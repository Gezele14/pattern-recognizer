package com.pattern.recognizer;

import ASTMCore.ASTMSource.CompilationUnit;
import com.pattern.ASTmanager.JSONEncoder;
import com.pattern.ASTmanager.ASTgenerator;
import com.pattern.ASTmanager.ASTmanager;
import gastmappers.Language;
import gastmappers.exceptions.UnsupportedLanguageException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.IOException;
import java.util.ArrayList;

public class PatternRecognizer {
  public static void main(String[] args) throws UnsupportedLanguageException, IOException, ParseException {

    ASTgenerator generator = new ASTgenerator();
    ASTmanager manager = new ASTmanager();

    /*
    Algoritmo para el Reconocimiento de Patrones:
      1. Generar el AST del codigo
      2. Convertir el AST al JSONArray simplificado
      3. Analizarlo con nuevo
     */

    //**** Paso 1 ****
    String codeDir = "D:\\Bibliotecas\\Descargas\\huston\\huston\\src\\singleton\\"; //Location of coded to analyze
    String[] classPath = {"C:\\Program Files\\Java\\jdk1.8.0_251\\jre\\lib\\rt.jar"};
    ArrayList<CompilationUnit> codeAST= new ArrayList<>();
    generator.InitGenerator(codeDir,classPath, Language.JAVA); //lee la ruta de los Source files
    generator.AnalyzeFacts(); // analiza el codigo
    codeAST = generator.getCompilationUnitsList(); //crea el AST

    //**** Paso 2 ****
    JSONEncoder Deco= new JSONEncoder();
    JSONParser parser = new JSONParser();
    JSONArray newAST = new JSONArray();
    for(CompilationUnit unit: codeAST){
      Deco.visitCompilationUnit(unit);
      newAST.add((JSONObject) parser.parse(Deco.getJsonClass()));
      Deco = new JSONEncoder();
    }
    System.out.println(newAST.toJSONString());

//    System.out.println(example.get(0).getgPackage());
  }
}

