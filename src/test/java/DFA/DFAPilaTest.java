package DFA;

import DFA.DFAPila;
import DFA.State;
import DFA.Quintuple;

import java.util.Set;
import java.util.HashSet;
import org.junit.*;
import static org.junit.Assert.*;

public class DFAPilaTest {

  Set<Quintuple<State, Character, Character, String, State> > transitions = new HashSet<Quintuple<State, Character, Character, String, State> >();
  Set<State> states = new HashSet<State>();
  Set<Character> alphabet = new HashSet<Character>();
  Set<Character> stackAlphabet = new HashSet<Character>();
  Set<State> finalstates = new HashSet<State>();
  char initial;
  State initialState;
  char startStack = 'z';

  @Test(expected=IllegalArgumentException.class)
  public void setOfStatesEmpty(){
    //El conjunto States nunca fue cargado. Deberia fallar la creacion del automata.
    char a = 'a';
    char b = 'b';
    char arroba = '@';
    char gion_ = '_';
    alphabet.add(a);
    alphabet.add(b);
    State initialState =  new State("q0");
    State q1 = new State("q1");
    State q2 = new State("q2");
    finalstates.add(q2);
    Quintuple<State, Character, Character, String, State> t1 = new Quintuple<State, Character, Character, String, State>(initialState, 'a', '@', "a", q1);
    Quintuple<State, Character, Character, String, State> t2 = new Quintuple<State, Character, Character, String, State>(q1, 'a', '@', "aa", q1);
    Quintuple<State, Character, Character, String, State> t3 = new Quintuple<State, Character, Character, String, State>(q1, 'b', 'a', "_", q2);
    Quintuple<State, Character, Character, String, State> t4 = new Quintuple<State, Character, Character, String, State>(q2, 'b', 'a', "_", q2);
    transitions.add(t1);
    transitions.add(t2);
    transitions.add(t3);
    transitions.add(t4);
    stackAlphabet.add(a);
    stackAlphabet.add(b);
    stackAlphabet.add(arroba);
    stackAlphabet.add(gion_);

    DFAPila d = new DFAPila(states, alphabet, stackAlphabet, transitions, startStack, initialState, finalstates, false);
     
  }
  @Test(expected=IllegalArgumentException.class)
  public void invalidAutomaton2(){
    //El automata no es deterministra, se muevo por q0, a, @ hacia dos estados distintos.
    char a = 'a';
    char b = 'b';
    char arroba = '@';
    char gion_ = '_';
    alphabet.add(a);
    alphabet.add(b);
    State initialState =  new State("q0");
    State q1 = new State("q1");
    State q2 = new State("q2");
    states.add(initialState);
    states.add(q1);
    states.add(q2);
    finalstates.add(q2);
    Quintuple<State, Character, Character, String, State> t1 = new Quintuple<State, Character, Character, String, State>(initialState, 'a', '@', "a", q1);
    Quintuple<State, Character, Character, String, State> t12 = new Quintuple<State, Character, Character, String, State>(initialState, 'a', '@', "a", q2);
    Quintuple<State, Character, Character, String, State> t2 = new Quintuple<State, Character, Character, String, State>(q1, 'a', '@', "aa", q1);
    Quintuple<State, Character, Character, String, State> t3 = new Quintuple<State, Character, Character, String, State>(q1, 'b', 'a', "_", q2);
    Quintuple<State, Character, Character, String, State> t4 = new Quintuple<State, Character, Character, String, State>(q2, 'b', 'a', "_", q2);
    transitions.add(t1);
    transitions.add(t2);
    transitions.add(t3);
    transitions.add(t4);
    transitions.add(t12);
    stackAlphabet.add(a);
    stackAlphabet.add(b);
    stackAlphabet.add(arroba);
    stackAlphabet.add(gion_);

    DFAPila d = new DFAPila(states, alphabet, stackAlphabet, transitions, startStack, initialState, finalstates, false);
     
  }
  @Test(expected=IllegalArgumentException.class)
  public void invalidAutomaton3(){
    //El automata no es deterministra, se muevo por q0, _, @ hacia q1 y por q0, a, @  hacia q1.
    char a = 'a';
    char b = 'b';
    char arroba = '@';
    char gion_ = '_';
    alphabet.add(a);
    alphabet.add(b);
    State initialState =  new State("q0");
    State q1 = new State("q1");
    State q2 = new State("q2");
    states.add(initialState);
    states.add(q1);
    states.add(q2);
    finalstates.add(q2);
    Quintuple<State, Character, Character, String, State> t1 = new Quintuple<State, Character, Character, String, State>(initialState, 'a', '@', "a", q1);
    Quintuple<State, Character, Character, String, State> t12 = new Quintuple<State, Character, Character, String, State>(initialState, '_', '@', "a", q1);
    Quintuple<State, Character, Character, String, State> t2 = new Quintuple<State, Character, Character, String, State>(q1, 'a', '@', "aa", q1);
    Quintuple<State, Character, Character, String, State> t3 = new Quintuple<State, Character, Character, String, State>(q1, 'b', 'a', "_", q2);
    Quintuple<State, Character, Character, String, State> t4 = new Quintuple<State, Character, Character, String, State>(q2, 'b', 'a', "_", q2);
    transitions.add(t1);
    transitions.add(t2);
    transitions.add(t3);
    transitions.add(t4);
    transitions.add(t12);
    stackAlphabet.add(a);
    stackAlphabet.add(b);
    stackAlphabet.add(arroba);
    stackAlphabet.add(gion_);

    DFAPila d = new DFAPila(states, alphabet, stackAlphabet, transitions, startStack, initialState, finalstates, false);
     
  }
  @Test(expected=IllegalArgumentException.class)
  public void invalidAutomaton4(){
    //El automata no es deterministra, se muevo por q0, _, @ hacia q1 y por q0, _, @  hacia q2.
    char a = 'a';
    char b = 'b';
    char arroba = '@';
    char gion_ = '_';
    alphabet.add(a);
    alphabet.add(b);
    State initialState =  new State("q0");
    State q1 = new State("q1");
    State q2 = new State("q2");
    states.add(initialState);
    states.add(q1);
    states.add(q2);
    finalstates.add(q2);
    Quintuple<State, Character, Character, String, State> t1 = new Quintuple<State, Character, Character, String, State>(initialState, '_', '@', "a", q1);
    Quintuple<State, Character, Character, String, State> t12 = new Quintuple<State, Character, Character, String, State>(initialState, '_', '@', "a", q2);
    Quintuple<State, Character, Character, String, State> t2 = new Quintuple<State, Character, Character, String, State>(q1, 'a', '@', "aa", q1);
    Quintuple<State, Character, Character, String, State> t3 = new Quintuple<State, Character, Character, String, State>(q1, 'b', 'a', "_", q2);
    Quintuple<State, Character, Character, String, State> t4 = new Quintuple<State, Character, Character, String, State>(q2, 'b', 'a', "_", q2);
    transitions.add(t1);
    transitions.add(t2);
    transitions.add(t3);
    transitions.add(t4);
    transitions.add(t12);
    stackAlphabet.add(a);
    stackAlphabet.add(b);
    stackAlphabet.add(arroba);
    stackAlphabet.add(gion_);

    DFAPila d = new DFAPila(states, alphabet, stackAlphabet, transitions, startStack, initialState, finalstates, false);
     
  }
  @Test(expected=IllegalArgumentException.class)
  public void invalidAutomaton5(){
    //El automata no es deterministra, se muevo por q2, e, b hacia q1 y por q1, b, a hacia q2.
    char a = 'a';
    char b = 'b';
    char arroba = '@';
    char gion_ = '_';
    alphabet.add(a);
    alphabet.add(b);
    State initialState =  new State("q0");
    State q1 = new State("q1");
    State q2 = new State("q2");
    states.add(initialState);
    states.add(q1);
    states.add(q2);
    finalstates.add(q2);
    Quintuple<State, Character, Character, String, State> t1 = new Quintuple<State, Character, Character, String, State>(initialState, '_', '@', "a", q1);
    Quintuple<State, Character, Character, String, State> t12 = new Quintuple<State, Character, Character, String, State>(q2, '_', 'a', "aa", q1);
    Quintuple<State, Character, Character, String, State> t2 = new Quintuple<State, Character, Character, String, State>(q1, 'a', '@', "aa", q1);
    Quintuple<State, Character, Character, String, State> t3 = new Quintuple<State, Character, Character, String, State>(q1, 'b', 'a', "_", q2);
    Quintuple<State, Character, Character, String, State> t4 = new Quintuple<State, Character, Character, String, State>(q2, 'b', 'a', "_", q2);
    transitions.add(t1);
    transitions.add(t2);
    transitions.add(t3);
    transitions.add(t4);
    transitions.add(t12);
    stackAlphabet.add(a);
    stackAlphabet.add(b);
    stackAlphabet.add(arroba);
    stackAlphabet.add(gion_);

    DFAPila d = new DFAPila(states, alphabet, stackAlphabet, transitions, startStack, initialState, finalstates, false);
     
  }
  @Test(expected=IllegalArgumentException.class)
  public void invalidAutomaton6(){
    //El automata no es deterministico, tiene un estado inalcanzable.
    char a = 'a';
    char b = 'b';
    char arroba = '@';
    char gion_ = '_';
    alphabet.add(a);
    alphabet.add(b);
    State initialState =  new State("q0");
    State q1 = new State("q1");
    State q2 = new State("q2");
    State q3 = new State("q3");
    states.add(initialState);
    states.add(q1);
    states.add(q2);
    states.add(q3);
    finalstates.add(q2);
    Quintuple<State, Character, Character, String, State> t1 = new Quintuple<State, Character, Character, String, State>(initialState, 'a', '@', "a", q1);
    Quintuple<State, Character, Character, String, State> t2 = new Quintuple<State, Character, Character, String, State>(q1, 'a', 'a', "aa", q1);
    Quintuple<State, Character, Character, String, State> t3 = new Quintuple<State, Character, Character, String, State>(q1, 'b', 'a', "_", q2);
    Quintuple<State, Character, Character, String, State> t4 = new Quintuple<State, Character, Character, String, State>(q2, 'b', 'a', "_", q2);
    transitions.add(t1);
    transitions.add(t2);
    transitions.add(t3);
    transitions.add(t4);
    stackAlphabet.add(a);
    stackAlphabet.add(b);
    stackAlphabet.add(arroba);
    stackAlphabet.add(gion_);

    DFAPila d = new DFAPila(states, alphabet, stackAlphabet, transitions, startStack, initialState, finalstates, false);
     
  }
  @Test(expected=IllegalArgumentException.class)
  public void invalidAutomaton7(){
    //El automata no es deterministico, el conjunto de estados finales no es subconjunto del conjunto de estados.
    char a = 'a';
    char b = 'b';
    char arroba = '@';
    char gion_ = '_';
    alphabet.add(a);
    alphabet.add(b);
    State initialState =  new State("q0");
    State q1 = new State("q1");
    State q2 = new State("q2");
    State q3 = new State("q3");
    states.add(initialState);
    states.add(q1);
    states.add(q2);
    finalstates.add(initialState);
    finalstates.add(q1);
    finalstates.add(q2);
    finalstates.add(q3);

    Quintuple<State, Character, Character, String, State> t1 = new Quintuple<State, Character, Character, String, State>(initialState, 'a', '@', "a", q1);
    Quintuple<State, Character, Character, String, State> t2 = new Quintuple<State, Character, Character, String, State>(q1, 'a', 'a', "aa", q1);
    Quintuple<State, Character, Character, String, State> t3 = new Quintuple<State, Character, Character, String, State>(q1, 'b', 'a', "_", q2);
    Quintuple<State, Character, Character, String, State> t4 = new Quintuple<State, Character, Character, String, State>(q2, 'b', 'a', "_", q2);
    transitions.add(t1);
    transitions.add(t2);
    transitions.add(t3);
    transitions.add(t4);
    stackAlphabet.add(a);
    stackAlphabet.add(b);
    stackAlphabet.add(arroba);
    stackAlphabet.add(gion_);

    DFAPila d = new DFAPila(states, alphabet, stackAlphabet, transitions, startStack, initialState, finalstates, false);
     
  }
  @Test
  public void acceptsFinalState1(){
    char a = 'a';
    char b = 'b';
    char arroba = '@';
    char gion_ = '_';
    alphabet.add(a);
    alphabet.add(b);
    State initialState =  new State("q0");
    State q1 = new State("q1");
    State q2 = new State("q2");
    states.add(initialState);
    states.add(q1);
    states.add(q2);
    finalstates.add(q2);

    Quintuple<State, Character, Character, String, State> t1 = new Quintuple<State, Character, Character, String, State>(initialState, 'a', '@', "a", q1);
    Quintuple<State, Character, Character, String, State> t2 = new Quintuple<State, Character, Character, String, State>(q1, 'a', 'a', "aa", q1);
    Quintuple<State, Character, Character, String, State> t3 = new Quintuple<State, Character, Character, String, State>(q1, 'b', 'a', "_", q2);
    Quintuple<State, Character, Character, String, State> t4 = new Quintuple<State, Character, Character, String, State>(q2, 'b', 'a', "_", q2);
    transitions.add(t1);
    transitions.add(t2);
    transitions.add(t3);
    transitions.add(t4);
    stackAlphabet.add(a);
    stackAlphabet.add(b);
    stackAlphabet.add(arroba);
    stackAlphabet.add(gion_);

    DFAPila d = new DFAPila(states, alphabet, stackAlphabet, transitions, startStack, initialState, finalstates, false);
    assertTrue("La cadena fue reconocida por Estado Final", d.acceptsFinalState("aaaabbbb"));
  }
  @Test
  public void acceptsFinalState2(){
    char a = 'a';
    char b = 'b';
    char arroba = '@';
    char gion_ = '_';
    alphabet.add(a);
    alphabet.add(b);
    State initialState =  new State("q0");
    State q1 = new State("q1");
    State q2 = new State("q2");
    states.add(initialState);
    states.add(q1);
    states.add(q2);
    finalstates.add(q2);

    Quintuple<State, Character, Character, String, State> t1 = new Quintuple<State, Character, Character, String, State>(initialState, 'a', '@', "a", q1);
    Quintuple<State, Character, Character, String, State> t2 = new Quintuple<State, Character, Character, String, State>(q1, 'a', 'a', "aa", q1);
    Quintuple<State, Character, Character, String, State> t3 = new Quintuple<State, Character, Character, String, State>(q1, 'b', 'a', "_", q2);
    Quintuple<State, Character, Character, String, State> t4 = new Quintuple<State, Character, Character, String, State>(q2, 'b', 'a', "_", q2);
    transitions.add(t1);
    transitions.add(t2);
    transitions.add(t3);
    transitions.add(t4);
    stackAlphabet.add(a);
    stackAlphabet.add(b);
    stackAlphabet.add(arroba);
    stackAlphabet.add(gion_);

    DFAPila d = new DFAPila(states, alphabet, stackAlphabet, transitions, startStack, initialState, finalstates, false);
    assertTrue("La cadena fue reconocida por Estado Final", d.acceptsFinalState("aaaab"));
  }
  @Test
  public void acceptsFinalState3(){
    char a = 'a';
    char b = 'b';
    char arroba = '@';
    char gion_ = '_';
    alphabet.add(a);
    alphabet.add(b);
    State initialState =  new State("q0");
    State q1 = new State("q1");
    State q2 = new State("q2");
    states.add(initialState);
    states.add(q1);
    states.add(q2);
    finalstates.add(q2);

    Quintuple<State, Character, Character, String, State> t1 = new Quintuple<State, Character, Character, String, State>(initialState, 'a', '@', "a", q1);
    Quintuple<State, Character, Character, String, State> t2 = new Quintuple<State, Character, Character, String, State>(q1, 'a', 'a', "aa", q1);
    Quintuple<State, Character, Character, String, State> t3 = new Quintuple<State, Character, Character, String, State>(q1, 'b', 'a', "_", q2);
    Quintuple<State, Character, Character, String, State> t4 = new Quintuple<State, Character, Character, String, State>(q2, 'b', 'a', "_", q2);
    transitions.add(t1);
    transitions.add(t2);
    transitions.add(t3);
    transitions.add(t4);
    stackAlphabet.add(a);
    stackAlphabet.add(b);
    stackAlphabet.add(arroba);
    stackAlphabet.add(gion_);

    DFAPila d = new DFAPila(states, alphabet, stackAlphabet, transitions, startStack, initialState, finalstates, false);
    assertFalse("La cadena fue reconocida por Estado Final", d.acceptsFinalState("aaaa"));
  }

  @Test
  public void acceptsFinalState4(){
    char a = 'a';
    char b = 'b';
    char arroba = '@';
    char gion_ = '_';
    alphabet.add(a);
    alphabet.add(b);
    State initialState =  new State("q0");
    State q1 = new State("q1");
    State q2 = new State("q2");
    states.add(initialState);
    states.add(q1);
    states.add(q2);
    finalstates.add(q2);

    Quintuple<State, Character, Character, String, State> t1 = new Quintuple<State, Character, Character, String, State>(initialState, 'a', '@', "a", q1);
    Quintuple<State, Character, Character, String, State> t2 = new Quintuple<State, Character, Character, String, State>(q1, 'a', 'a', "aa", q1);
    Quintuple<State, Character, Character, String, State> t3 = new Quintuple<State, Character, Character, String, State>(q1, 'b', 'a', "_", q2);
    Quintuple<State, Character, Character, String, State> t4 = new Quintuple<State, Character, Character, String, State>(q2, 'b', 'a', "_", q2);
    transitions.add(t1);
    transitions.add(t2);
    transitions.add(t3);
    transitions.add(t4);
    stackAlphabet.add(a);
    stackAlphabet.add(b);
    stackAlphabet.add(arroba);
    stackAlphabet.add(gion_);

    DFAPila d = new DFAPila(states, alphabet, stackAlphabet, transitions, startStack, initialState, finalstates, false);
    assertFalse("La cadena fue reconocida por Estado Final", d.acceptsFinalState("bbbbb"));
  }

  @Test
  public void acceptsEmptyStack1(){
    char a = 'a';
    char b = 'b';
    char arroba = '@';
    char gion_ = '_';
    alphabet.add(a);
    alphabet.add(b);
    State initialState =  new State("q0");
    State q1 = new State("q1");
    State q2 = new State("q2");
    states.add(initialState);
    states.add(q1);
    states.add(q2);
    finalstates.add(q2);

    Quintuple<State, Character, Character, String, State> t1 = new Quintuple<State, Character, Character, String, State>(initialState, 'a', '@', "a", q1);
    Quintuple<State, Character, Character, String, State> t2 = new Quintuple<State, Character, Character, String, State>(q1, 'a', 'a', "aa", q1);
    Quintuple<State, Character, Character, String, State> t3 = new Quintuple<State, Character, Character, String, State>(q1, 'b', 'a', "_", q2);
    Quintuple<State, Character, Character, String, State> t4 = new Quintuple<State, Character, Character, String, State>(q2, 'b', 'a', "_", q2);
    transitions.add(t1);
    transitions.add(t2);
    transitions.add(t3);
    transitions.add(t4);
    stackAlphabet.add(a);
    stackAlphabet.add(b);
    stackAlphabet.add(arroba);
    stackAlphabet.add(gion_);

    DFAPila d = new DFAPila(states, alphabet, stackAlphabet, transitions, startStack, initialState, finalstates, false);
    assertTrue("La cadena fue reconocida por Pila Vacia", d.acceptsEmptyStack("aaaabbbb"));
  }

  @Test
  public void acceptsEmptyStack2(){
    char a = 'a';
    char b = 'b';
    char arroba = '@';
    char gion_ = '_';
    alphabet.add(a);
    alphabet.add(b);
    State initialState =  new State("q0");
    State q1 = new State("q1");
    State q2 = new State("q2");
    states.add(initialState);
    states.add(q1);
    states.add(q2);
    finalstates.add(q2);

    Quintuple<State, Character, Character, String, State> t1 = new Quintuple<State, Character, Character, String, State>(initialState, 'a', '@', "a", q1);
    Quintuple<State, Character, Character, String, State> t2 = new Quintuple<State, Character, Character, String, State>(q1, 'a', 'a', "aa", q1);
    Quintuple<State, Character, Character, String, State> t3 = new Quintuple<State, Character, Character, String, State>(q1, 'b', 'a', "_", q2);
    Quintuple<State, Character, Character, String, State> t4 = new Quintuple<State, Character, Character, String, State>(q2, 'b', 'a', "_", q2);
    transitions.add(t1);
    transitions.add(t2);
    transitions.add(t3);
    transitions.add(t4);
    stackAlphabet.add(a);
    stackAlphabet.add(b);
    stackAlphabet.add(arroba);
    stackAlphabet.add(gion_);

    DFAPila d = new DFAPila(states, alphabet, stackAlphabet, transitions, startStack, initialState, finalstates, false);
    assertTrue("La cadena fue reconocida por Pila Vacia", d.acceptsEmptyStack("aaaabbbb"));
  }

  @Test
  public void acceptsEmptyStack3(){
    char a = 'a';
    char b = 'b';
    char arroba = '@';
    char gion_ = '_';
    alphabet.add(a);
    alphabet.add(b);
    State initialState =  new State("q0");
    State q1 = new State("q1");
    State q2 = new State("q2");
    states.add(initialState);
    states.add(q1);
    states.add(q2);
    finalstates.add(q2);

    Quintuple<State, Character, Character, String, State> t1 = new Quintuple<State, Character, Character, String, State>(initialState, 'a', '@', "a", q1);
    Quintuple<State, Character, Character, String, State> t2 = new Quintuple<State, Character, Character, String, State>(q1, 'a', 'a', "aa", q1);
    Quintuple<State, Character, Character, String, State> t3 = new Quintuple<State, Character, Character, String, State>(q1, 'b', 'a', "_", q2);
    Quintuple<State, Character, Character, String, State> t4 = new Quintuple<State, Character, Character, String, State>(q2, 'b', 'a', "_", q2);
    transitions.add(t1);
    transitions.add(t2);
    transitions.add(t3);
    transitions.add(t4);
    stackAlphabet.add(a);
    stackAlphabet.add(b);
    stackAlphabet.add(arroba);
    stackAlphabet.add(gion_);

    DFAPila d = new DFAPila(states, alphabet, stackAlphabet, transitions, startStack, initialState, finalstates, false);
    assertFalse("La cadena no fue reconocida por Pila Vacia", d.acceptsEmptyStack("aaaaaaabbbb"));
  }
}
