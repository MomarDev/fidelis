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
public class TextureCheveux {

    private int id;
    private String textureCheveux;

    public TextureCheveux() {
    }

    public TextureCheveux(int id, String textureCheveux) {
        this.id = id;
        this.textureCheveux = textureCheveux;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTextureCheveux() {
        return textureCheveux;
    }

    public void setTextureCheveux(String textureCheveux) {
        this.textureCheveux = textureCheveux;
    }

    @Override
    public String toString() {
        return textureCheveux;
    }

}
