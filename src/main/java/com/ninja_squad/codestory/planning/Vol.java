package com.ninja_squad.codestory.planning;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

public class Vol {

    @JsonProperty("VOL")
    private String nom;
    @JsonProperty("DEPART")
    private int depart;
    @JsonProperty("DUREE")
    private int duree;
    @JsonProperty("PRIX")
    private int prix;

    Vol() {
    }

    public boolean canBeFollowedBy(Vol other) {
        return other.getDepart() >= this.getArrivee();
    }

    public Vol(String nom, int depart, int duree, int prix) {
        this.nom = nom;
        this.depart = depart;
        this.duree = duree;
        this.prix = prix;
    }

    public int getArrivee() {
        return getDepart() + getDuree();
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getDepart() {
        return depart;
    }

    public void setDepart(int depart) {
        this.depart = depart;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vol vol = (Vol) o;

        return Objects.equal(this.nom, vol.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nom);
    }
}
