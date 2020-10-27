package com.pattern.ASTgenerator;

import ASTMCore.ASTMSource.CompilationUnit;
import com.google.gson.Gson;
import gastmappers.Language;
import gastmappers.Mapper;
import gastmappers.exceptions.UnsupportedLanguageException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ASTgenerator {
  private String inputPath;
  private String classPath;
  private Mapper mapper;
  private Language language;
  private ArrayList<String> analyzedFactsList;
  private ArrayList<ASTMCore.ASTMSource.CompilationUnit> compilationUnitsList;

  public ASTgenerator(String inputPath, String classPath, Mapper mapper, Language language) {
    this.inputPath = inputPath;
    this.classPath = classPath;
    this.mapper = mapper;
    this.language = language;
  }

  /**
   * Analiza el directorio suministrado para ver si har archivos de codigo fuente
   * @param inputPath: path del directorio con el código.
   *                 
   * */
  private void AnalyzeInputFolder(String inputPath) throws IOException, UnsupportedLanguageException {
    File folders = new File(inputPath);
    String folderPath = folders.getCanonicalPath() + File.separator;
    File root = new File(folderPath);
    File[] sourceFiles = root.listFiles();
    String sourcePath = null;
    for (File s: sourceFiles) {
      sourcePath = s.getAbsolutePath();
      if (s.isFile()) {
        if (sourcePath.contains(Language.getFileExtension(this.language)))
          AnalyzeSourceFile(sourcePath, s.getName());
        continue;
      }
      AnalyzeInputFolder(sourcePath);
    }
  }

  public void AnalyzeSourceFile(String sourcePath, String filename) throws IOException {
    Gson json = new Gson();
    String[] sources = { this.inputPath };
    String[] classpath = { this.classPath };
    ArrayList<ASTMCore.ASTMSource.CompilationUnit> units = mapper.getGastCompilationUnit(sourcePath);
    for (CompilationUnit unit : units) {
      compilationUnitsList.add(unit);
      analyzedFactsList.add(json.toJson(unit).replaceAll("null,", "")); // IS THIS RIGHT?
    }
  }
}
