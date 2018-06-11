/*
 * Trabajo Practico Automatas Pila Deterministicos 2018.
 * Grupo: Gaido Laureana, Gonzalez Gabriel.
 */
package DFA;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;
import java.util.Scanner;
import java.util.Arrays;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class App{

  private static Boolean debugMode; //Used to show the execution trace of the program

  /**
   * This is the main method.
   * @param args: Array of Strings that contains the file name.
   * @return -.
   */ 
  public static void main(String[] args) {

    System.out.println("Ingrese el nombre del archivo que desea cargar: Ejemplo automata1.dot");
    Scanner arch = new Scanner(System.in);
    String dataFile = "files/" + arch.next();

    System.out.println("Desea correr el sistema en modo DEBUG? s/n:");
    Scanner db = new Scanner(System.in);
    char debug = db.next().charAt(0);

    if(debug==83 || debug==115){debugMode = true;} else{debugMode = false;}

    if (dataFile.length() > 0) {
      String[] data = reading(dataFile);
      try {
        DFAPila automatonEntry = generateAutomaton(data);
        if (automatonEntry == null) {
          System.out.println("Entrada incorrecta, revise el archivo");
        }
         menu(automatonEntry);
      }
      catch (Exception e) {
        System.out.println("Algo falló y no tenemos idea de por qué T_T.");
        System.out.println(e.getMessage());
        System.out.println(e.getLocalizedMessage());
      }
    }
    else {
      System.out.println("Debe ingresar la ruta del archivo. Ejemplo: java Main 'automata1.dot'");
    }
  }

  /**
   * This is the main menu.
   * @param DFAPila utomatonEntry: The method uses the automaton to recognize words, or chage his type 
   * (DFA with final states to Empty Stack or Empty Stack to Final States).
   * @return -.
   */ 
  private static void menu(DFAPila automatonEntry)throws IOException {
    char c = 's';
    while(c!=70 && c!=110){
      Scanner sc = new Scanner(System.in);
      System.out.println("1 - Verificar aceptacion por pila vacia");
      System.out.println("2 - Verificar aceptacion por estado final");
      System.out.println("3 - Estado final a pila vacia");
      System.out.println("4 - Pila vacia a estado final");
      System.out.println("5 - Salir");
      int option = sc.nextInt();
      sc.nextLine();
      if(option==5) {
        break;
      }
      if(option==3) {
      	automatonEntry.toEmptyStack();
      }
      if(option==4) {
      	automatonEntry.toFinalState();
      }
      if(option==2) {
        System.out.println("Ingrese la cadena ");
        String s = sc.nextLine();
        if (automatonEntry.acceptsFinalState(s)){
    		 System.out.println("Fue aceptado por estado final");
        }
        else{
          System.out.println("La cadena " + s + " no fue aceptada por estado final.");
        }
    	}
      if(option==1) {
        System.out.println("Ingrese la cadena ");
        String s2 = sc.nextLine();
        if(automatonEntry.acceptsEmptyStack(s2)){
    		  System.out.println("Fue aceptado por pila vacia");
        }
        else{
          System.out.println("La cadena " + s2 + " no fue aceptada por pila vacia.");
        }
    	}
      System.out.println("Desea probar otra opcion? S/N");
      Scanner db = new Scanner(System.in);
      c = db.next().charAt(0);
      if(c!=70 && c!=110){
        System.out.println("Desea correr el sistema en modo DEBUG? s/n:");
        Scanner again = new Scanner(System.in);
        char debug = again.next().charAt(0);
        if(debug==83 || debug==115){
          automatonEntry.changeMode(true);
        }else{
          automatonEntry.changeMode(false);
        }
      }
    }
  }

  /**
   * This method makes an automaton from a data file.
   * @param String[] dataFile: The data file lines converted to an String[].
   * @return DFAPila: The DFAPila generated from the data file lines.
   */ 
  private static DFAPila generateAutomaton(String[] dataFile) throws Exception {
    if(debugMode){
      System.out.println("Trabajando...");
    }
    Set<Quintuple<State, Character, Character, String, State> > transitions = new HashSet<Quintuple<State, Character, Character, String, State> >();
    Set<State> states = new HashSet<State>();
    Set<Character> alphabet = new HashSet<Character>();
    Set<Character> stackAlphabet = new HashSet<Character>();
    Set<State> finalstates = new HashSet<State>();
    if(debugMode){
      System.out.println("Todos los conjuntos fueron creados");
    }
    int i = 1;
    boolean transitionBoolean = true;
    State initialStateG = null;
    Quintuple<State, Character, Character, String, State> currentTransition = null;
    Character startStack = 'Z';
    if(debugMode){
      System.out.println("dataFile[0]:" + dataFile[0]);
    }
    if (dataFile[0].matches("digraph")) {
      State currentSFinal=null;
      while (i < dataFile.length && dataFile[i] != null) {
        if(debugMode){
          System.out.println(dataFile[i]);
        }
        if (dataFile[i].startsWith("inic[shape=point")||dataFile[i].replaceAll("\\s", "").length()==0) {
          if(debugMode){
            System.out.println("Leyo bien el inic");
          }
          transitionBoolean = false;
        }
        if (dataFile[i].startsWith("inic->")) {
          initialStateG = new State("" + dataFile[i].charAt(6) + dataFile[i].charAt(7));
          if(debugMode){
            System.out.println("EVALUACION ESTADO INICIAL " + isIn(initialStateG, states));
          }
          if(!isIn(initialStateG, states)){
            states.add(initialStateG); 
          }
          transitionBoolean = false;
          if(debugMode){
            System.out.println("Estado inicial encontrado!:"+ dataFile[i].substring(6));
            System.out.println(initialStateG.name());
          }
        }

        if (dataFile[i].endsWith("[shape=doublecircle]") && dataFile[i].length() > 20){
          currentSFinal = new State((String)dataFile[i].substring(0,dataFile[i].length() - 20));
          if(debugMode){
            System.out.println("ESTADO FINAL: " + currentSFinal.toString());
            System.out.println("resultado de isIn(currentSFinal, states): " + isIn(currentSFinal, states));
          }
          if(!isIn(currentSFinal, states)) {states.add(currentSFinal); }
          if(!isIn(currentSFinal, finalstates)) {finalstates.add(currentSFinal); }
          transitionBoolean = false;
        }
        if (transitionBoolean) {
          currentTransition = detectTrans(dataFile[i]);
          if (currentTransition != null) {
            transitions.add(currentTransition);
            if(currentTransition.first().equals(initialStateG)){
              startStack = currentTransition.third();
            }
          }
        }
        i++;
        transitionBoolean = true;
      }
      if(debugMode){
        System.out.println("Se recorreran las transiciones para generar los alfabetos");
      }
      for(Quintuple<State, Character, Character, String, State> q : transitions){
        alphabet.add(q.second());
        if(!isIn(q.first(), states)){
  				states.add(q.first());
          if(q.first().equals(initialStateG) && !alphabet.contains(q.third())){
            startStack = q.third();
          }
  			}
        if(!isIn(q.fifth(), states)){
  				states.add(q.fifth()); 
  			}

        stackAlphabet.add(q.third());
        int j = 0;
        while (j < q.fourth().length()) {
          stackAlphabet.add(q.fourth().charAt(j));
          j++;
        }
      }
      DFAPila d = new DFAPila(states, alphabet, stackAlphabet, transitions, startStack, initialStateG, finalstates, debugMode);
      if(debugMode){
        System.out.println("termino el seteo de los alfabetos");
        System.out.println("ESTADOS:");
        System.out.println(states.toString());
        System.out.println("ALFABETO:");
        System.out.println(alphabet.toString());
        System.out.println("ALFABETO PILA:");
        System.out.println(stackAlphabet.toString());
        System.out.println("ESTADOS FINales:");
        System.out.println(finalstates.toString());
        System.out.println("DELTA:");
        System.out.println(transitions.toString());
        System.out.println("Creamos el automata y retornamos :");
        d.dotIt();
      }
      return d;
    }
    return null;
  }

  /**
   * This method read the transitions
   * @param String dataEntry: a line of the data file that contains a transition.
   * @return Quintuple that represents the transition.
   */ 
  private static Quintuple<State, Character, Character, String, State> detectTrans(String dataEntry) {
    int i = 0; int j=0;

    // read the name of the 'from' state
    while (j < dataEntry.length() && dataEntry.charAt(j) != '-') {
      j++;
    }
    State from =new State(dataEntry.substring(i, j));
    j++; j++; i = j;

    // read the next state
    while (j < dataEntry.length() && dataEntry.charAt(j) != '[') {
      j++;
    }
    State to = new State(dataEntry.substring(i, j));

    // advance to the next char after thw word label=" that contains 7 characters and save it.
    j+=8; i=j;
    char character =dataEntry.charAt(i);

    // advance 2 characters, here is the top of the stack.
    i+=2;
    char stackTop =dataEntry.charAt(i);

    //advance 2 characters, here is the character/string to push on the stack.
    i+=2;
    j=i;
    while (j < dataEntry.length() && dataEntry.charAt(j) != '"') {
      j++;
    }
    String toPush =dataEntry.substring(i, j);
    System.out.println();

    return new Quintuple<State, Character, Character, String, State>(from , character, stackTop, toPush, to);
  }

  /**
   * This method read the transitions
   * @param String dataEntry: a line of the data file that contains a transition.
   * @return Quintuple that represents the transition.
   */ 
  public static String[] reading(String filename) {
    Scanner in = null;
    try {
      in = new Scanner(new FileReader(filename));
    } 
    catch (Exception e) {
      System.out.println("Error al leer el archivo");
    }
    StringBuilder sb = new StringBuilder();
    while (in.hasNext()) {
      sb.append(in.next());
    }
    String automaton = sb.toString().replaceAll("\\s", "");
    String[] dataFile = getting(automaton);
    in.close();
    return dataFile;
  }

  /**
   * This method read a line of the file and returns them as a String[].
   * @param String dataLine: a line of the data file.
   * @return String[] that represents data file line.
   */ 
  public static String[] getting(String dataLine) {
    String[] result = new String[100];
    if(debugMode){
      System.out.println(dataLine);
    }
    int indexOfArray = 0;
    int i = 0;
    int j = 0;
    while (j < dataLine.length()-1) {
      while (j < dataLine.length() && dataLine.charAt(j) != ';' && dataLine.charAt(j) != '{' && dataLine.charAt(j) != '}') {
        j++;
      }
      result[indexOfArray] = dataLine.substring(i, j);
      indexOfArray++;
      j++;
      i = j;
    }
    return result;
  }

  /**
   * This method are used on newState() to check if the new state is on the Automaton states.
   * @param s: State to check.
   * @return Boolean: Returns TRUE if the state is on the Automaton States.
   */ 
  public static boolean isIn(State s, Set<State> conj){
    boolean result = false;
    if(conj.size() == 0){
      return false;
    }
    for(State a: conj){
      if(a.equals(s)){
        return true;
      }
    }
    return result;
  }
}
