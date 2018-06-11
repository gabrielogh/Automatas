/*
 * Trabajo Practico Automatas Pila Deterministicos 2018.
 * Grupo: Gaido Laureana, Gonzalez Gabriel.
 */

package DFA;

import java.util.Set;
import java.util.Stack;
import java.util.Iterator;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public final class DFAPila{

	public static final Character Lambda = '_';
  public static final Character Joker = '@';
  public static final Character Initial = 'Z';
  public Boolean debugMode; //Used to show the execution trace of the program

  private Stack<Character> stack; //the stack of the automaton

  State initial;
  Character stackInitial;
  Set<State> states;
  Set<Character> alphabet;
  Set<Character> stackAlphabet; //Alphabet of the stack
  Set<Quintuple<State,Character,Character,String,State>> transitions; //delta function
  Set<State> finalStates;

  /**
   * Constructor of the class - returns a DFAPila object
   * @param states - states of the DFAPila
   * @param alphabet - the alphabet of the automaton 
   * @param stackAlphabet - the alphabet of the stack
   * @param transitions - transitions of the automaton
   * @param stackInitial - a Character which represents the initial element of the stack
   * @param initial - initial State of the automaton
   * @param final_states - acceptance states of the automaton
   * @throws IllegalArgumentException
   */
  public DFAPila(
    Set<State> states,
    Set<Character> alphabet,
    Set<Character> stackAlphabet,
    Set<Quintuple<State, Character,Character,String, State>> transitions,
    Character stackInitial,
    State initial,
    Set<State> final_states,
    Boolean debug)
    throws IllegalArgumentException 
  {
    this.debugMode = debug;
    this.states = states;
    this.alphabet = alphabet;
    this.stackAlphabet = stackAlphabet;
    stackAlphabet.add(Lambda); //the character lambda is used in the stack to know when do a pop
    stackAlphabet.add(Joker); //the mark of the stack
    this.transitions = transitions;
    this.stackInitial = stackInitial;
    this.initial = initial;
    this.finalStates = final_states;
    stack = new Stack<Character>();
    stack.add(stackInitial); //insert the mark in the stack
    if (!rep_ok()){
        throw new  IllegalArgumentException();
    }
    if(debugMode){
      System.out.println("Is a DFA Pila");
    }
  }
      
  //
  public void changeMode(Boolean mode){
    this.debugMode = mode;
  }

  /**
   * Find a transition from a state with a character
   * and get the next state
   * 
   * @param from: Current state.
   * @param c: Current character.
   * @return (State).
   */
  public State delta(State from, Character c){
    if(stack.empty()){
      return null;
    }
    //we obtain an iterator to travel through the set of quintuples to check if the transition exists.
    Iterator<Quintuple<State, Character,Character,String, State> > iterator =  this.transitions.iterator();
    Quintuple<State, Character,Character,String, State> aux = null;
    Boolean found = false;
    if(debugMode){System.out.println("Entramos a Delta con el estado: " + from.name());}

    // Check if exist an epsilon transition from the state 'from' to other state (can be only one or zero).
    State nextStateFromEpsilon = checkEpsilon(from);
    if(debugMode){
      System.out.println("El estado actual es: " + nextStateFromEpsilon.name() + " y el caracter es: " + c);
    }
    for(Quintuple<State, Character,Character,String, State> q : transitions){
      if ((!(stack.empty())) && q.first().equals(nextStateFromEpsilon) && q.second().equals(c) && (q.third().equals(this.stack.peek())||q.third().equals(Joker))){
        aux = q;
        break;
      }
    }
    if(debugMode){System.out.println("La quintuple encontrada es: " + aux.toString());}
    if(aux != null) {return executeDelta(aux);}
    return null;
  }

  /**
   * Find an Epsilon transition from a state, get the next state and do the stack changes with executeDelta.
   * @param from: Current state.
   * @return (State).
   */
  private State checkEpsilon(State from){
    State result =from;
    boolean continuar=true;
    Iterator<Quintuple<State, Character,Character,String, State> > iterator =  this.transitions.iterator();
    Quintuple<State, Character,Character,String, State> aux = null;
    for(Quintuple<State, Character,Character,String, State> q : transitions){
      if (q.first().equals(result)&&q.second().equals(Lambda)&&(q.third().equals(this.stack.peek())||q.third().equals(Joker))){
        aux = q;
        break;
      }
    }
    if(aux!=null) {result = executeDelta(aux);}
    return result;
  }

  /**
   * Execute the Quintuple, change the state of the Automaton (Stack).
   * and returns the next state to be used in other functions.
   * 
   * @param transicion: Delta definition.
   * @return (State): Next state.
   */
  private State executeDelta(Quintuple<State, Character, Character, String, State> transicion) {
    Character top = null;
    if(debugMode){
      System.out.println("La pila entro con: " + stack.toString());
    }
    if(!stack.empty()){
      top = stack.pop();
      if(debugMode){
        System.out.println("hacemos el pop de " + top);
      }
    }
    String toPush = transicion.fourth();
    int max = toPush.length();
    int i =0;
    while(i<max && toPush.charAt(i)!=Lambda){
      if(debugMode){
        System.out.println("Esto vamos a pushear a la pila: " + toPush.charAt(i));
      }
      if(toPush.charAt(i)==Joker) {stack.push(Joker);}
      else{
        stack.push(toPush.charAt(i));
        if(debugMode){
          System.out.println("hacemos el push de " + toPush.charAt(i));
        }
      }
      i++;
    }
    if(debugMode){
      System.out.println("La Pila salio: " + stack.toString());
      System.out.println("");
    }
    return transicion.fifth();
  }

  /**
   * Execute the Automaton by Final State with the String .
   * @param String: String to recognize.
   * @return Boolean: True if the Automaton ends the String and the last state visited is a final State.
   */ 
  public boolean acceptsFinalState(String string) {
    State currentState = this.initial;
    int stringPos = 0;
    while((stringPos<string.length()) && currentState!=null){
      if(debugMode){
        System.out.println("Posicion de la cadena: " + stringPos + " Es el caracter: " + string.charAt(stringPos));
      }
      currentState = delta(currentState, string.charAt(stringPos));
      if(debugMode){
        System.out.println("Nos fuimos al estado: " + currentState.name());
      }
      stringPos++;
    }
    currentState = checkEpsilon(currentState);
    if(stringPos == string.length() && currentState!=null){
      for(State s : finalStates){
        if (s.equals(currentState)) {return true;}
      }
    }
    return false;
  }

  /**
   * Execute the Automaton by Empty Stack with the String .
   * @param String: String to recognize.
   * @return Boolean: True if the Automaton ends the String and the Stack is Empty.
   */ 
  public boolean acceptsEmptyStack(String string) {
    State currentState = this.initial;
    int stringPos = 0;
    while((stringPos<string.length()) && currentState!=null) {
      if(debugMode){
        System.out.println("Posicion de la cadena: " + stringPos + " Es el caracter: " + string.charAt(stringPos) + " el estado actual es " + currentState.name() + " el tope de la pila es " + this.stack.peek());
      }
      currentState = delta(currentState, string.charAt(stringPos));

      if(debugMode){
        System.out.println("Nos fuimos al estado: " + currentState.name());
      }
      stringPos++;
    }
    currentState = checkEpsilon(currentState);
    if(stringPos == string.length() && currentState!=null && stack.empty()){
      return true;
    }
    return false;
  }

  /**
   * This method makes and saves a new state and it is assured that is not in the automaton states.
   * @param -
   * @return State: A new State.
   */ 
  private State newState(){
    int i=0;
    State newState = new State("q"+i);
    while(this.isIn(newState)) {
      i++;
      newState = new State("q"+i);
    }
    this.states.add(newState);
    return newState;
  }

  /**
   * This method are used on newState() to check if the new state is on the Automaton states.
   * @param s: State to check.
   * @return Boolean: Returns TRUE if the state is on the Automaton States.
   */ 
  private boolean isIn(State s){
    boolean result = false;
    for(State s2 : states){
      if(s.equals(s2)){
        return true;
      }
    }
    return result;
  }

  /**
   * This method makes two new states, an Initial and a Final. These states will be used to converts Automatons
   * @param -.
   * @return Tuple: Returns a Tuple (Pair) of States fst=initial, snd=final.
   */ 
  private Tuple newStates(){
    State newInitial = newState();
    State newFinal =  newState();
    Tuple newTuple = new Tuple<State, State> (newInitial,newFinal);
    return newTuple;
  }

  /**
   * This method converts an Automaton by Final State to an Automaton by Empty Stack.
   * We show the new automaton and write them on a new file.dot named as automataX.dot where X is the number of file.
   * @param -.
   * @return -.
   */ 
  public void toEmptyStack()throws IOException{
    Tuple newStates = newStates();
    Character newInitialChar = '#';

    Quintuple<State, Character,Character,String, State> newEpsilon =null;
    this.stackAlphabet.add(newInitialChar);

    //For each final state of the Automaton, we make a new Epsilon transition to the new Final State.
    for(State s: finalStates){
      if((!s.equals(newStates.second()))&&(!s.equals(newStates.first()))){
        newEpsilon = new Quintuple<State, Character,Character,String, State> (s,Lambda,newInitialChar,""+Lambda,newStates.second());
        this.transitions.add(newEpsilon);
      }
    }
    //We connect the new initial state with the previous initial state of the automaton.
    newEpsilon = new Quintuple<State, Character,Character,String, State> (newStates.first(),Lambda,newInitialChar,""+newInitialChar+""+this.stackInitial,this.initial);
    this.transitions.add(newEpsilon);
    //We make a new transition that it leave us on the new final state.
    newEpsilon = new Quintuple<State, Character,Character,String, State> (newStates.second(),Lambda,newInitialChar,""+Lambda,newStates.second());
    this.transitions.add(newEpsilon);

    //We delete all of the final states (we do not need it in an Automaton by Empty Stack).
    this.finalStates.clear();
    //We set the newInitialState as the new initial state.
    this.initial=newStates.first();
    this.stackInitial=newInitialChar;
    stack.pop();
    this.stack.push(newInitialChar);
    if(debugMode){
      System.out.println("La pila tiene en el tope: " + this.stack.peek());
      System.out.println("La funcion delta es: " + transitions.toString());
    }

    String result = this.dotIt();
    generateFile(result);
    System.out.println("El Automata Pila por Pila Vacia obtenido es:");    
    System.out.println(result);
  }

  /**
   * This method converts an Automaton by Empty Stack to an Automaton by Final States.
   * We show the new automaton and write them on a new file.dot named as automataX.dot where X is the number of file.
   * @param -.
   * @return -.
   */ 
  public void toFinalState()throws IOException{
    Tuple newStates = newStates();
    Character newInitialChar = '#';

    Quintuple<State, Character,Character,String, State> newEpsilon =null;
    this.stackAlphabet.add(newInitialChar);

    //For each state of the Automaton we make a new Epsilon transition to the new final state.
    for(State s: states){
      if(!s.equals(newStates.second())&&!s.equals(newStates.first())) {
        newEpsilon = new Quintuple<State, Character,Character,String, State> (s,Lambda,newInitialChar,""+Lambda,newStates.second());
        this.transitions.add(newEpsilon);
      }
    }
    //We connect the new newInitialState with the previous initial state of the automaton.
    newEpsilon = new Quintuple<State, Character,Character,String, State> (newStates.first(),Lambda,newInitialChar,""+newInitialChar+""+this.stackInitial,this.initial);
    this.transitions.add(newEpsilon);
    //We set the newFinalState as a new final state.
    this.finalStates.add(newStates.second());
    //We set the newInitialState as the new initial state.
    this.initial=newStates.first();
    this.stackInitial=newInitialChar;

    String result = this.dotIt();
    generateFile(result);
    System.out.println("El Automata Pila por Estado Final obtenido es:");    
    System.out.println(result);
  }

  /**
   * This method returns a Dot format from an Automaton.
   * @param -.
   * @return String: Represents the Automaton in DOT format.
   */ 
  public String dotIt(){
    char quotes= '"';
    String current;
    current = "digraph{\n";
    current = current + "inic[shape=point];\n" + "inic->" + this.initial.name() + ";\n";
    for(Quintuple<State, Character,Character,String, State> q: this.transitions){
      current = current + q.first().toString() + "->" + q.fifth().toString() + " [label=" +quotes+ q.second().toString() +"/"+ q.third()+"/"+q.fourth()+ quotes+ "];\n";
    }
    current = current+ "\n";
    for(State s: this.finalStates){
      current = current + s.name() + "[shape=doublecircle];\n";
    }
    current = current + "}";
    return current;
  }

  /**
   * This method returns a file from an Automaton in dot format.
   * @param String s: Represents the automaton in dot format.
   * @return - generates a new file.
   */ 
  private void generateFile(String s) throws IOException{
    String extension1 = ".dot";
    String extension2 = ".png";
    String ruta = "automata1";
    int i=1;
    File archivo = new File(ruta);
    BufferedWriter bw;
    if(archivo.exists()) {
      while(archivo.exists()){
        i++;
        ruta = "automata" + i + extension1;
        archivo = new File(ruta);
      }
      bw = new BufferedWriter(new FileWriter(archivo));
      bw.write(s);     
    } 
    else {
      bw = new BufferedWriter(new FileWriter(archivo));
      bw.write(s);
    }
    bw.close();
    Process graph = Runtime.getRuntime().exec("dot -Tpng -o automata" + i + extension2+ " " + ruta);
    System.out.println("------- Se crearon los archivos automata" + i + extension1 + " y el archivo automata" + i + extension2 +  "-------");
  }

  /**
   * This method represents the invariant representation.
   * @param -.
   * @return Boolean: True if the current Automaton is a valid representation of an Automaton.
   */ 
  public boolean rep_ok() {
    boolean transitionEpsilon=false;
    for(Quintuple<State,Character,Character,String,State> t:transitions){
      for(Quintuple<State,Character,Character,String,State> l:transitions){

      boolean equalsStates=!(t.equals(l));
      boolean sameState=(t.first().equals(l.first()));
      boolean tEplNep=((t.second()).equals(Lambda) && !((l.second()).equals(Lambda)) );
      boolean tNeplEp=(!((t.second()).equals(Lambda)) && ((l.second()).equals(Lambda)) );
      boolean sameChar=(t.second().equals(l.second()));
      boolean sameTop = (t.third().equals(l.third()));
      String sEqualsStates = "equalsStates encontro tupla equivalente ";
      String sSameState = " sameState coincide el from ";
      String sTEplNep = " tEplNep t tiene una trans epsilon y l no ";
      String sTNeplEp = " tNeplEp t no tiene trans E y l si ";
      String sSameChar = " sameChar va por el mismo caracter a 2 estados distintos ";
      if(debugMode){
        System.out.println(sEqualsStates + equalsStates+","+ sSameState +sameState+","+ sTEplNep +tEplNep+","+ sTNeplEp +tNeplEp + sSameChar + sameChar);
      }
      if(equalsStates && sameState && tEplNep && sameTop){
        if(debugMode){
          System.out.println(sEqualsStates + equalsStates+","+ sSameState +sameState+","+ sTEplNep +tEplNep);
          System.out.println("l: " + l.toString());
          System.out.println("t: " + t.toString());
        }
        System.out.println("EL AUTOMATA ES NO DETERMINISTA. ");
        System.out.println();
        return false;
      }
      if(equalsStates && sameState && tNeplEp && sameTop){
        if(debugMode){
          System.out.println(sEqualsStates+ equalsStates+","+ sSameState +sameState+","+ sTNeplEp +tNeplEp);
          System.out.println("l: " + l.toString());
          System.out.println("t: " + t.toString());
        }
        System.out.println("EL AUTOMATA ES NO DETERMINISTA. ");
        System.out.println();
        return false;
      }
      if(equalsStates && sameState && sameChar && sameTop){
        if(debugMode){
          System.out.println(sEqualsStates + equalsStates+","+ sSameState +sameState+","+ sSameChar +sameChar);
          System.out.println("l: " + l.toString());
          System.out.println("t: " + t.toString());
        }
        System.out.println("EL AUTOMATA ES NO DETERMINISTA. ");
        System.out.println();
        return false;
      }
        }
    }
    // We check if the set of Final States is biggest than the set of states
    if(this.finalStates.size() > this.states.size()){
    System.out.println("EL AUTOMATA ES NO DETERMINISTA. ");
    System.out.println();
      return false;
    }
    // We check if the set of states is empty.
    if(this.states.size() == 0){
      System.out.println("EL AUTOMATA ES NO DETERMINISTA. ");
      System.out.println();
        return false;
    }
    boolean reachable=false;
    // We need to check if each state s (except the initial) is the next state of other state t.
    // This implies that the state s is reachable from the state t.
    for (State s:states){
      reachable=false;
      if(!(initial.equals(s))){
        for(Quintuple<State,Character,Character,String,State> t:transitions){
          if(s.equals(t.fifth())){
          reachable=true;
          break;
        }
        }
        if (reachable==false){
          System.out.println("EL AUTOMATA CONTIENE UN ESTADO INALCANZABLE: ");
          System.out.println(s.toString());
          System.out.println();
          return false;
        }
      }
    }
    if(debugMode){
      System.out.println("EL AUTOMATA ES CORRECTOOOOO :) :)");
    }
    return true;
  }
}
