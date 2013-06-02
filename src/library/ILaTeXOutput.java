package library;

import java.io.IOException;

public interface ILaTeXOutput {
    
    /**
     * Schrijf een String naar het texbestand
     */
    void writeToTeX(String s) throws IOException ;
    
    // AFKORTINGEN VOOR GEBRUIK VAN TEX
    /**
     * Default Top Matter Structure
     */
    void topMatter(String title, String author, String date) throws IOException ;
    
    /**
     * documentclass[options]{class}
     */
    void documentClass(String d) throws IOException ;
    void documentClass(String options, String d) throws IOException ;
    
    /**
     * usepackage[options]{package}
     */
    void usePackage(String p) throws IOException ;
    void usePackage(String options, String p) throws IOException ;
    
    /**
     * begin{environment}
     * end{environment}
     */
    void beginEnvironment(String e) throws IOException ;
    void endEnvironment(String e) throws IOException ;
    
    /**
     * sections[options]{name}
     */
    void section(String s) throws IOException ;
    void section(String options, String s) throws IOException ;
    void subsection(String s) throws IOException ;
    void subsection(String options, String s) throws IOException ;
    
    /**
     * begin{tabular}[pos]{table spec}
     */
    void tabular(String spec) throws IOException ;
    void tabular(String pos, String spec) throws IOException ;
    
    /**
     * newcommand{name}[num]{definition}
     */
    void newCommand(String name, String definition) throws IOException ;
    void newCommand(String name, int num, String definition) throws IOException ;
    
    /**
     * Maak een .pdf van de .tex
     */
    void pdfTeX();
    
    /**
     * Open de pdf
     */
    void showTeX();

}