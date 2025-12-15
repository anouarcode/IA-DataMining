package blocksworld.datamining;

import java.util.Set;

import blocksworld.modelling.*;

public class AssociationRule {
    private Set<BooleanVariable> premisse;
    private Set<BooleanVariable> conclusion;
    private float frequence;
    private float confiance;
    public AssociationRule(Set<BooleanVariable> premisse,Set<BooleanVariable> conclusion,float frequence,float confiance){
        this.premisse = premisse;
        this.conclusion = conclusion;
        this.frequence = frequence;
        this.confiance = confiance;
    }

    public Set<BooleanVariable> getPremise(){
        return this.premisse;
    }

    public Set<BooleanVariable> getConclusion(){
        return this.conclusion;
    }

    public float getFrequency(){
        return this.frequence;
    }

    public float getConfidence(){
        return this.confiance;
    }

    
    @Override
    public String toString() {
        return premisse.toString() + "-->" + conclusion.toString();
    }
    
}
