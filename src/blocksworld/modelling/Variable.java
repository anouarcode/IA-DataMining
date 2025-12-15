package blocksworld.modelling;

import java.util.Objects;
import java.util.Set;
public class Variable {
    String nom;
    Set<Object> domaine ;

    //constructeur 
    public Variable(String nom,Set<Object> domain){
        this.nom=nom;
        this.domaine = domain;
    }

    public String getName(){
        return this.nom;
    }

    public Set<Object> getDomain(){
        return this.domaine;
    }
    
    @Override 
    public boolean equals(Object o){
        if(o == null || !(o instanceof Variable)){
            return false;
        }
        if(o == this){
            return true;
        }
        Variable v = (Variable) o ;
        return nom.equals(v.getName());
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.nom);
    }

    
    @Override
    public String toString(){
        return this.nom;
    }
    
}
