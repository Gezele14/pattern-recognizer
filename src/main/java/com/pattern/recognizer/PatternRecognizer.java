package com.pattern.recognizer;

import com.pattern.ASTgenerator.ASTgenerator;
import gastmappers.Language;
import gastmappers.exceptions.UnsupportedLanguageException;

import java.io.IOException;

public class PatternRecognizer {
  public static void main(String[] args) throws UnsupportedLanguageException, IOException {
    //Needed for comparing
    String BasePath = "D:\\huston\\src\\";
    String patterns[] = {"Hola", "bridge", "chainOfResponsibility", "command", "composite", "decorator", "factory", "observer",
                        "prototype", "proxy", "singleton", "state", "strategy", "template", "visitor"};

    ASTgenerator astGenerator = new ASTgenerator();

    for (String pattern: patterns) {
      astGenerator.InitGenerator(BasePath, Language.JAVA);
      astGenerator.AnalyzeInputFolder(pattern);
      System.out.println("Arbol del patron: "+pattern);
      System.out.println(astGenerator.AnalyzedFactsString());
      System.out.println("\n\n");
    }

  }
}
