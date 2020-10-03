/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package michelkaapp.objets;

/**
 *
 * @author leyu
 */
public class TypeCheveux {
    private int id;
    private String typeCheveux;

    public TypeCheveux() {
    }

    public TypeCheveux(int id, String typeCheveux) {
        this.id = id;
        this.typeCheveux = typeCheveux;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeCheveux() {
        return typeCheveux;
    }

    public void setTypeCheveux(String typeCheveux) {
        this.typeCheveux = typeCheveux;
    }

    @Override
    public String toString() {
        return typeCheveux;
    }

}
