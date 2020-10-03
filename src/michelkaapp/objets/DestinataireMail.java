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
public class DestinataireMail {
    private int id;
    private int email;
    private String mail;
    private String destinataire;

    public DestinataireMail() {
    }

    public DestinataireMail(int id, String mail, String destinataire) {
        this.id = id;
        this.mail = mail;
        this.destinataire = destinataire;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getDestinataire() {
        return destinataire;
    }

    public void setDestinataire(String destinataire) {
        this.destinataire = destinataire;
    }
    
    
}
