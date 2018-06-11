package DFA;

public class Tuple <A,B> {

    // Private attributes
    private State first;
    private State second;
    
    /**
     * Construct of the class - returns a Quintuple Object
     * @param fst
     * @param snd
     */
    public Tuple(State fst, State snd) {
        first = fst;
        second = snd;
    }

    /**
     * Getter method
     * @return the first element of the Quintuple
     */
    public State first() {
        return first;
    }

    /**
     * Getter method
     * @return the second element of the Quintuple
     */
    public State second() {
        return second;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((first == null) ? 0 : first.hashCode());
        result = prime * result + ((second == null) ? 0 : second.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Tuple other = (Tuple) obj;
        if (first == null) {
            if (other.first != null) {
                return false;
            }
        } else if (!first.equals(other.first)) {
            return false;
        }
        if (second == null) {
            if (other.second != null) {
                return false;
            } else {
            }
        } else if (!second.equals(other.second)) {
            return false;
        }
        return true;
    }
    
    public String toString(){
        return "("+ first.toString() + "," + second.toString()+")";
    }
	
	
}
