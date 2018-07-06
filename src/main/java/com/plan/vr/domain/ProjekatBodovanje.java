package com.plan.vr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ProjekatBodovanje.
 */
@Entity
@Table(name = "projekat_bodovanje")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProjekatBodovanje implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vrednost")
    private Double vrednost;

    @Column(name = "bodovi")
    private Double bodovi;

    @Column(name = "ponder")
    private Double ponder;

    @Column(name = "ponderisani_bodovi")
    private Double ponderisaniBodovi;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("projekatPodovanjes")
    private Kriterijum kriterijum;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("projekatBodovanjes")
    private Projekat projekat;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getVrednost() {
        return vrednost;
    }

    public ProjekatBodovanje vrednost(Double vrednost) {
        this.vrednost = vrednost;
        return this;
    }

    public void setVrednost(Double vrednost) {
        this.vrednost = vrednost;
    }

    public Double getBodovi() {
        return bodovi;
    }

    public ProjekatBodovanje bodovi(Double bodovi) {
        this.bodovi = bodovi;
        return this;
    }

    public void setBodovi(Double bodovi) {
        this.bodovi = bodovi;
    }

    public Double getPonder() {
        return ponder;
    }

    public ProjekatBodovanje ponder(Double ponder) {
        this.ponder = ponder;
        return this;
    }

    public void setPonder(Double ponder) {
        this.ponder = ponder;
    }

    public Double getPonderisaniBodovi() {
        return ponderisaniBodovi;
    }

    public ProjekatBodovanje ponderisaniBodovi(Double ponderisaniBodovi) {
        this.ponderisaniBodovi = ponderisaniBodovi;
        return this;
    }

    public void setPonderisaniBodovi(Double ponderisaniBodovi) {
        this.ponderisaniBodovi = ponderisaniBodovi;
    }

    public Kriterijum getKriterijum() {
        return kriterijum;
    }

    public ProjekatBodovanje kriterijum(Kriterijum kriterijum) {
        this.kriterijum = kriterijum;
        return this;
    }

    public void setKriterijum(Kriterijum kriterijum) {
        this.kriterijum = kriterijum;
    }

    public Projekat getProjekat() {
        return projekat;
    }

    public ProjekatBodovanje projekat(Projekat projekat) {
        this.projekat = projekat;
        return this;
    }

    public void setProjekat(Projekat projekat) {
        this.projekat = projekat;
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
        ProjekatBodovanje projekatBodovanje = (ProjekatBodovanje) o;
        if (projekatBodovanje.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), projekatBodovanje.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProjekatBodovanje{" +
            "id=" + getId() +
            ", vrednost=" + getVrednost() +
            ", bodovi=" + getBodovi() +
            ", ponder=" + getPonder() +
            ", ponderisaniBodovi=" + getPonderisaniBodovi() +
            "}";
    }
}
