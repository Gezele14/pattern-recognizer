package com.pattern.ASTmanager;

import ASTMCore.ASTMSource.CompilationUnit;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;
import gastmappers.Language;
import gastmappers.Mapper;

import gastmappers.MapperFactory;
import gastmappers.exceptions.UnsupportedLanguageException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ASTgenerator {
  private String inputPath;
  private String[] classPath;
  private Mapper mapper;
  private Language language;
  private ArrayList<String> retrievedGastList;
  private ArrayList<ASTMCore.ASTMSource.CompilationUnit> compilationUnitsList;

  /**
   * @author jnavas05
   * edited by @gezele14
   * @param inputFolder: Directory where file is stored
   * @param language: Programming languaje of the file
   *
   * */
  public void InitGenerator(String inputFolder, String[] classPath, Language language) throws UnsupportedLanguageException {
    MapperFactory factory = new MapperFactory();
    this.inputPath = inputFolder;
    this.classPath = classPath;
    this.language = language;
    this.mapper = factory.createMapper(language);
    this.retrievedGastList = new ArrayList<>();
    this.compilationUnitsList = new ArrayList<>();
  }

  /**
   * Clear all Object atributes
   * @author gezele14
   *
   * */
  public void clearData(){
    this.inputPath = null;
    this.language = null;
    this.mapper = null;
    this.retrievedGastList.clear();
    this.retrievedGastList = null;
    this.compilationUnitsList.clear();
    this.compilationUnitsList = null;
  }

  /**
   * Analyze the source file to generate the compilation Unit
   * author: jnavas05
   *
   * */
  public void AnalyzeFacts() throws IOException, UnsupportedLanguageException {
    RetrieveInputFolder(this.inputPath);
  }

  /**
   * Analyze the directory to search java source files
   * author: @jnavas05
   * edited by @gezele14
   * @param inputPath: Directory where file is stored
   * */
  public void RetrieveInputFolder(String inputPath) throws IOException, UnsupportedLanguageException {
    File folders = new File(inputPath);
    String folderPath = folders.getCanonicalPath() + File.separator;
    File root = new File(folderPath);
    File[] sourceFiles = root.listFiles();
    String sourcePath;
    if (sourceFiles != null){
      for (File s: sourceFiles) {
        sourcePath = s.getAbsolutePath();
        if (s.isFile()) {
          if (sourcePath.contains(Language.getFileExtension(this.language)))
            RetrieveSourceFile(sourcePath, s.getName());
          continue;
        }
        RetrieveInputFolder(sourcePath);
      }
    }else {
      System.out.println("<i> Error al leer el directorio: "+inputPath);
    }
  }

  private void RetrieveSourceFile(String sourcePath, String fileName) throws  IOException{
    Genson genson = new GensonBuilder()
            .useClassMetadata(true)
            .useRuntimeType(true)
            .useConstructorWithArguments(true)
            .create();
    String[] sources = { this.inputPath };
    ArrayList<CompilationUnit> units = mapper.getGastCompilationUnit(sourcePath,fileName, sources, classPath);
    for (CompilationUnit unit: units) {
      compilationUnitsList.add(unit);
      retrievedGastList.add(genson.serialize(unit));
    }
  }

  /**Getters and Setters**/



  public ArrayList<String> getRetrievedGastList() {
    return retrievedGastList;
  }

  public ArrayList<CompilationUnit> getCompilationUnitsList() {
    return compilationUnitsList;
  }

  public String AnalyzedFactsString() {
    return retrievedGastList.toString();
  }
}
