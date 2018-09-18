package com.plan.vr.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A AkcioniPlan.
 */
@Entity
@Table(name = "akcioni_plan")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AkcioniPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "naziv", length = 100, nullable = false)
    private String naziv;

    @Column(name = "opis")
    private String opis;

    @Column(name = "budzet")
    private Double budzet;

    @Column(name = "datum_od")
    private LocalDate datumOd;

    @Column(name = "datum_do")
    private LocalDate datumDo;

    @OneToMany(mappedBy = "akcioniPlan")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Kriterijum> kriterijums = new HashSet<>();

    @OneToMany(mappedBy = "akcioniPlan")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Projekat> projekats = new HashSet<>();

    @ManyToOne(optional = false)
//    @NotNull
    @JsonIgnoreProperties("")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public AkcioniPlan naziv(String naziv) {
        this.naziv = naziv;
        return this;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public AkcioniPlan opis(String opis) {
        this.opis = opis;
        return this;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Double getBudzet() {
        return budzet;
    }

    public AkcioniPlan budzet(Double budzet) {
        this.budzet = budzet;
        return this;
    }

    public void setBudzet(Double budzet) {
        this.budzet = budzet;
    }

    public LocalDate getDatumOd() {
        return datumOd;
    }

    public AkcioniPlan datumOd(LocalDate datumOd) {
        this.datumOd = datumOd;
        return this;
    }

    public void setDatumOd(LocalDate datumOd) {
        this.datumOd = datumOd;
    }

    public LocalDate getDatumDo() {
        return datumDo;
    }

    public AkcioniPlan datumDo(LocalDate datumDo) {
        this.datumDo = datumDo;
        return this;
    }

    public void setDatumDo(LocalDate datumDo) {
        this.datumDo = datumDo;
    }

    public Set<Kriterijum> getKriterijums() {
        return kriterijums;
    }

    public AkcioniPlan kriterijums(Set<Kriterijum> kriterijums) {
        this.kriterijums = kriterijums;
        return this;
    }

    public AkcioniPlan addKriterijum(Kriterijum kriterijum) {
        this.kriterijums.add(kriterijum);
        kriterijum.setAkcioniPlan(this);
        return this;
    }

    public AkcioniPlan removeKriterijum(Kriterijum kriterijum) {
        this.kriterijums.remove(kriterijum);
        kriterijum.setAkcioniPlan(null);
        return this;
    }

    public void setKriterijums(Set<Kriterijum> kriterijums) {
        this.kriterijums = kriterijums;
    }

    public Set<Projekat> getProjekats() {
        return projekats;
    }

    public AkcioniPlan projekats(Set<Projekat> projekats) {
        this.projekats = projekats;
        return this;
    }

    public AkcioniPlan addProjekat(Projekat projekat) {
        this.projekats.add(projekat);
        projekat.setAkcioniPlan(this);
        return this;
    }

    public AkcioniPlan removeProjekat(Projekat projekat) {
        this.projekats.remove(projekat);
        projekat.setAkcioniPlan(null);
        return this;
    }

    public void setProjekats(Set<Projekat> projekats) {
        this.projekats = projekats;
    }

    public User getUser() {
        return user;
    }

    public AkcioniPlan user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AkcioniPlan akcioniPlan = (AkcioniPlan) o;
        if (akcioniPlan.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), akcioniPlan.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AkcioniPlan{" +
            "id=" + getId() +
            ", naziv='" + getNaziv() + "'" +
            ", opis='" + getOpis() + "'" +
            ", budzet=" + getBudzet() +
            ", datumOd='" + getDatumOd() + "'" +
            ", datumDo='" + getDatumDo() + "'" +
            "}";
    }
}
