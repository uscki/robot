package lib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class LaTeXOutput implements ILaTeXOutput {

    private FileWriter fw;
    private static File path;
    
    /**
     * De commando's om de LaTeX te parseren en te laten zien
     */
    private String commandPdfLatex;
    private String commandPdfLatexPhil;
    private String commandOpenMac;
    private String commandOpenWindows;

    public LaTeXOutput(String pathname, String filename) throws IOException {
        fw = new FileWriter(pathname + filename + ".tex");
        path = new File(pathname);
        commandPdfLatex = "pdflatex " + filename + ".tex";
        commandPdfLatexPhil = "/usr/local/teTeX/bin/i386-apple-darwin-current/pdflatex " + filename + ".tex";
        commandOpenMac = "open " + filename + ".pdf";
        commandOpenWindows = "cmd /C " + filename + ".pdf";
    }

    /**
     * Schrijf een String naar het texbestand
     */
    public void writeToTeX(String s) throws IOException {
        fw.write(s);
    }
    
    public void topMatter(String t, String a, String d) throws IOException {
        writeToTeX("\\title{" + t + "}\n");
        writeToTeX("\\author{" + a + "}\n");
        writeToTeX("\\date{" + d + "}\n");
        writeToTeX("\\maketitle\n");
    }
    
    public void documentClass(String d) throws IOException {
        writeToTeX("\\documentclass{" + d + "}\n");
    }
    public void documentClass(String options, String d) throws IOException {
        writeToTeX("\\documentclass[" + options + "]{" + d + "}\n");
    }
    
    public void usePackage(String p) throws IOException {
        writeToTeX("\\usepackage{" + p + "}\n");
    }
    public void usePackage(String options, String p) throws IOException {
        writeToTeX("\\usepackage[" + options + "]{" + p + "}\n");
    }
    
    public void beginEnvironment(String e) throws IOException {
        writeToTeX("\\begin{" + e + "}\n");
    }
    
    public void endEnvironment(String e) throws IOException {
        writeToTeX("\\end{" + e + "}\n");
    }
    
    public void section(String s) throws IOException {
        writeToTeX("\\section{" + s + "}\n");
    }
    public void section(String options, String s) throws IOException {
        writeToTeX("\\section[" + options + "]{" + s + "}\n");
    }
    public void subsection(String s) throws IOException {
        writeToTeX("\\subsection{" + s + "}\n");
    }
    public void subsection(String options, String s) throws IOException {
        writeToTeX("\\subsection[" + options + "]{" + s + "}\n");
    }
    
    public void tabular(String spec) throws IOException {
        writeToTeX("\\begin{tabular}{" + spec + "}\n");
    }
    public void tabular(String pos, String spec) throws IOException {
        writeToTeX("\\begin{tabular}[" + pos + "]{" + spec + "}\n");
    }
    
    public void newCommand(String name, String definition) throws IOException {
        writeToTeX("\\newcommand{\\" + name + "}{" + definition + "}\n");
    }
    public void newCommand(String name, int num, String definition) throws IOException {
        writeToTeX("\\newcommand{\\" + name + "}[" + num + "]{" + definition + "}\n");
    }
    /**
     * Maakt .pdf van .tex
     */
    public void pdfTeX(){
        if (isPhil()) {
            runCommand(commandPdfLatexPhil);
        } else {
            runCommand(commandPdfLatex);
        }
    }
    
    /**
     * Opent de pdf
     */
    public void showTeX(){
        if (isWindows()) {
            runCommand(commandOpenWindows);
        } else {
            runCommand(commandOpenMac);
        }
    }
    
    /**
     * Sluit de filewriter netjes af
     */
    public void closeFileWriter() throws IOException {
        fw.close();
    }
    
    /**
     * Voert het gegeven commando uit
     */
    private static void runCommand(String command) {
        try {
            Runtime run = Runtime.getRuntime();
            Process pr = run.exec(command,null,path);
            BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line = "";
            while ((line = buf.readLine()) != null) {
                System.out.println(line);
            }
            pr.waitFor();
        } catch (Exception e) {
            System.out.println("Kon commando niet uitvoeren: " + e.toString());
        }
    }

    /**
     * Bepaal of we onder windows werken
     */
    private static boolean isWindows() {
        return System.getProperty("os.name").startsWith("Windows");
    }

    /**
     * Bepaal of dit een wijsbegeerte mac is
     */
    private static boolean isPhil() {
        String home = System.getProperty("user.home");
        if (home == null)
            return false;
        return home.indexOf("fourier.students.phil.uu.nl") > -1;
    }

}