package com.pattern.ASTmanager;

import ASTMCore.ASTMSource.CompilationUnit;
import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ASTmanager {
  private String dataBasePath;

  public ASTmanager(){
    this.dataBasePath = "src/main/java/com/pattern/examplePatterns/";
  }

  /**
   * Store AST into the given path
   * @author gezele14
   * @param path: Directory where file is stored
   * @param filename: Name of the file
   * */
  public void SaveAnalyzedPattern(String AST, String path, String filename) throws IOException {
    String dir = path+filename+".json";
    FileWriter fw = new FileWriter(dir);
    fw.write(AST);
    fw.close();
    System.out.println("<i> AST del patron "+filename+ " creado correctamente.");
  }

  /**
   * Store AST into the Data Base
   * @author gezele14
   * @param pattern: Name of the file
   * */
  public void SavePatternInDB(String AST, String pattern) throws IOException {
    String dir = this.dataBasePath+pattern+".json";
    FileWriter fw = new FileWriter(dir);
    fw.write(AST);
    fw.close();
    System.out.println("<i> AST del patron "+pattern+ " creado correctamente.");
  }

  /**
   * Read the generated AST into the given path
   * @author gezele14
   * @param path: Directory where file is stored
   * @param filename: Name of the file
   * */
  private String ReadASTfromFile(String path, String filename) throws IOException{
    String dir = path+filename+".json";
    File f = new File(dir);
    Scanner myReader = new Scanner(f);
    return myReader.nextLine();
  }

  /**
   * Convert AST from File to CompilationUnit Array
   * @author gezele14
   * @param path: Directory where file is stored
   * @param filename: Name of the file
   * */
  public ArrayList<CompilationUnit> getCompilationUnitsListFromFile(String path, String filename) throws  IOException{
    String AST = ReadASTfromFile(path, filename);
    Genson genson = new GensonBuilder()
            .useClassMetadata(true)
            .useRuntimeType(true)
            .useConstructorWithArguments(true)
            .create();
    return genson.deserialize(AST, new GenericType<>() {
    });
  }

  /**
   * Get Design Pattern Compilation Unit from DB
   * @author gezele14
   * @param pattern: Name of the design pattern
   * */
  public ArrayList<CompilationUnit> GetPatternFromDB(String pattern) throws  IOException{
    String AST = ReadASTfromFile(this.dataBasePath, pattern);
    Genson genson = new GensonBuilder()
            .useClassMetadata(true)
            .useRuntimeType(true)
            .useConstructorWithArguments(true)
            .create();
    return genson.deserialize(AST, new GenericType<>() {
    });
  }

  /**
   * Get patterns of the DB
   * @author gezele14
   * */

  public ArrayList<String> GetDBPatterns() throws IOException {
    File folders = new File(this.dataBasePath);
    String folderPath = folders.getCanonicalPath() + File.separator;
    File root = new File(folderPath);
    File[] sourceFiles = root.listFiles();
    ArrayList<String> patterns = new ArrayList<>();
    assert sourceFiles != null;
    for (File s: sourceFiles) {
      if(s.isFile()){
        String name = FilenameUtils.removeExtension(s.getName());
        patterns.add(name);
      }
    }

    return patterns;
  }

  /**Getters and Setters**/

  public void setDataBasePath(String dataBasePath) {
    this.dataBasePath = dataBasePath;
  }
}
