package com.pattern.ASTgenerator;

import ASTMCore.ASTMSource.CompilationUnit;
import com.google.gson.Gson;
import gastmappers.Language;
import gastmappers.Mapper;
import gastmappers.MapperFactory;
import gastmappers.exceptions.UnsupportedLanguageException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ASTgenerator {
  private String inputPath;
  private Mapper mapper;
  private Language language;
  private ArrayList<String> analyzedFactsList;
  private ArrayList<ASTMCore.ASTMSource.CompilationUnit> compilationUnitsList;

  /**
   * author: jnavas05
   * edited by @gezele14
   * @param inputFolder: Directorio en donde se encuentra el codigo fuente
   * @param language:
   *
   * */
  public void InitGenerator(String inputFolder, Language language) throws UnsupportedLanguageException {
    MapperFactory factory = new MapperFactory();
    this.inputPath = inputFolder;
    this.language = language;
    this.mapper = factory.createMapper(language);
    this.analyzedFactsList = new ArrayList<String>();
    this.compilationUnitsList = new ArrayList<CompilationUnit>();
  }

  /**
   * Analiza el directorio suministrado para ver si har archivos de codigo fuente
   * author: @jnavas05
   * edited by @gezele14
   * @param inputPath: path del directorio con el código.
   * */
  public void AnalyzeInputFolder(String inputPath) throws IOException, UnsupportedLanguageException {

    File folders = new File(this.inputPath + inputPath);
    String folderPath = folders.getCanonicalPath() + File.separator;
    File root = new File(folderPath);
    File[] sourceFiles = root.listFiles();
    String sourcePath = null;
    if (sourceFiles != null){
      for (File s: sourceFiles) {
        sourcePath = s.getAbsolutePath();
        if (s.isFile()) {
          if (sourcePath.contains(Language.getFileExtension(this.language)))
            AnalyzeSourceFile(sourcePath, s.getName());
          continue;
        }
        AnalyzeInputFolder(sourcePath);
      }
    }else {
      System.out.println("<i> Error al leer el directorio: "+inputPath);
    }
  }

  /**
   * Analiza el directorio suministrado para ver si har archivos de codigo fuente
   * author: jnavas05
   * @param sourcePath: path del directorio con el código.
   * */
  public void AnalyzeSourceFile(String sourcePath, String filename) throws IOException {
    Gson json = new Gson();
    String[] sources = { this.inputPath };
    ArrayList<ASTMCore.ASTMSource.CompilationUnit> units = mapper.getGastCompilationUnit(sourcePath);
    for (CompilationUnit unit : units) {
      compilationUnitsList.add(unit);
      analyzedFactsList.add(json.toJson(unit).replaceAll("null,", "")); // IS THIS RIGHT?
    }
  }

  public ArrayList<String> getAnalyzedFactsList() {
    return analyzedFactsList;
  }

  public ArrayList<CompilationUnit> getCompilationUnitsList() {
    return compilationUnitsList;
  }

  public String AnalyzedFactsString() {
    return analyzedFactsList.toString();
  }
}
