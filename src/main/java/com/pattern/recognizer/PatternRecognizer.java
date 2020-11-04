package com.pattern.recognizer;

import ASTMCore.ASTMSource.CompilationUnit;
import com.pattern.ASTmanager.ASTgenerator;
import com.pattern.ASTmanager.ASTmanager;
import gastmappers.Language;
import gastmappers.exceptions.UnsupportedLanguageException;


import java.io.IOException;
import java.util.ArrayList;

public class PatternRecognizer {
  public static void main(String[] args) throws UnsupportedLanguageException, IOException {
    //Needed for comparing
    String codeDir = "D:\\huston\\src\\"; //Location of coded to analyze
    String[] classPath = {"C:\\Program Files\\Java\\jdk1.8.0_251\\jre\\lib\\rt.jar"};

    ASTgenerator generator = new ASTgenerator();
    ASTmanager manager = new ASTmanager();

    ArrayList<String> patterns = manager.GetDBPatterns(); //Obtiene la lista de los patrones de la base de datos

    ArrayList<CompilationUnit> example, example2 = new ArrayList<>();
    generator.InitGenerator(codeDir+patterns.get(0),classPath, Language.JAVA);
    generator.AnalyzeFacts();
    example = manager.GetPatternFromDB(patterns.get(0));
    example2 = generator.getCompilationUnitsList();
  }
}
