package com.pattern.recognizer;

import com.google.gson.Gson;
import com.pattern.ASTgenerator.ASTgenerator;
import gastmappers.Language;
import gastmappers.exceptions.UnsupportedLanguageException;
import ASTMCore.ASTMSource.CompilationUnit;


import java.io.IOException;
import java.util.ArrayList;

public class PatternRecognizer {
  public static void main(String[] args) throws UnsupportedLanguageException, IOException {
    //Needed for comparing
    String BasePath = "D:\\huston\\src\\"; //Location of example patterns
    String[] classPath =
            {
                    "C:\\Program Files\\Java\\jdk1.8.0_251\\jre\\lib\\rt.jar"
            };
    String[] patterns; // Example Patterns
    patterns = new String[]{"bridge", "chainOfResponsibility", "command", "composite", "decorator", "factory", "observer",
                        "prototype", "proxy", "singleton", "state", "strategy", "template", "visitor"};

    ASTgenerator astGenerator = new ASTgenerator();
    for (String pattern:patterns){
      astGenerator.InitGenerator(BasePath, classPath, Language.JAVA);
      CompilationUnit unit = astGenerator.getCompilationUnitsListFromFile("src/main/java/com/pattern/examplePatterns/", pattern).get(0);
      System.out.println(unit.getLanguage());
      astGenerator.clearData();
    }

  }
}
