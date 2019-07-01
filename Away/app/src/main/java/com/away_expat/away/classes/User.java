package com.away_expat.away.classes;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String mail;
    private String password;
    private String firstname;
    private String lastname;
    private String birth;
    private String country;
    private City at;
    private int idCity;
    private String avatar;

    public User() {
    }

    public User(String email, String password) {
        this.mail = email;
        this.password = password;
        this.idCity = -1;
    }

    public User(String email, String password, String firstname, String lastname, String birthday, String country) {
        this.mail = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birth = birthday;
        this.country = country;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getBirthday() {
        return birth;
    }

    public String getCountry() {
        return country;
    }

    public void setEmail(String email) {
        this.mail = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setBirthday(String birthday) {
        this.birth = birthday;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public City getCity() {
        return at;
    }

    public void setCity(City at) {
        this.at = at;
    }

    public int getIdCity() {
        return idCity;
    }

    public void setIdCity(int idCity) {
        this.idCity = idCity;
    }

    public String getAvatar() {
        return avatar;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", birth='" + birth + '\'' +
                ", country='" + country + '\'' +
                ", idCity='" + idCity + '\'' +
                '}';
    }
}
