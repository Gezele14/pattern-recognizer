package com.pattern.recognizer;

import com.pattern.ASTgenerator.ASTgenerator;
import gastmappers.Language;
import gastmappers.exceptions.UnsupportedLanguageException;

import java.io.IOException;

public class PatternRecognizer {
  public static void main(String[] args) throws UnsupportedLanguageException, IOException {
    //Needed for comparing
    String BasePath = "D:\\huston\\src\\"; //Location of example patterns
    String[] patterns; // Example Patterns
    patterns = new String[]{"bridge", "chainOfResponsibility", "command", "composite", "decorator", "factory", "observer",
                        "prototype", "proxy", "singleton", "state", "strategy", "template", "visitor"};

    ASTgenerator astGenerator = new ASTgenerator();

    for (String pattern: patterns) {
      astGenerator.InitGenerator(BasePath, Language.JAVA);
      astGenerator.AnalyzeInputFolder(pattern);
      astGenerator.saveAnalyzedPattern("src/main/java/com/pattern/examplePatterns/", pattern);
    }

  }
}
