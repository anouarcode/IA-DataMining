package blocksworld.modelling;

import java.util.Set;

public class BooleanVariable extends Variable {
    static Set<Object> domain = Set.of(true,false);
    boolean value;
    public BooleanVariable(String nom){
        super(nom,domain);
        this.value = false;
    }

    public boolean getValue(){
        return this.value;
    }

    public void setValue(boolean value){
        this.value = value;
    }
    
}
