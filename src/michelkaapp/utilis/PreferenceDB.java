/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package michelkaapp.utilis;

/**
 *
 * @author leyu
 */
public class PreferenceDB {

    private String hostname;
    private String port;
    private String dbname;
    private String username;
    private String password;

    public PreferenceDB(String hostname, String port, String dbname, String username, String password) {
        this.hostname = hostname;
        this.port = port;
        this.dbname = dbname;
        this.username = username;
        this.password = password;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getDbname() {
        return dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public PreferenceDB() {
    }

    @Override
    public String toString() {
        return "PreferenceDB{" + "hostname=" + hostname + ", port=" + port + ", dbname=" + dbname + ", username=" + username + ", password=" + password + '}';
    }



}
