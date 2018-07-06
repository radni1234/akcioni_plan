package com.plan.vr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A KriterijumBodovanje.
 */
@Entity
@Table(name = "kriterijum_bodovanje")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class KriterijumBodovanje implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "granica_od", nullable = false)
    private Double granicaOd;

    @NotNull
    @Column(name = "granica_do", nullable = false)
    private Double granicaDo;

    @NotNull
    @Column(name = "bodovi", nullable = false)
    private Double bodovi;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("kriterijumBodovanjes")
    private Kriterijum kriterijum;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getGranicaOd() {
        return granicaOd;
    }

    public KriterijumBodovanje granicaOd(Double granicaOd) {
        this.granicaOd = granicaOd;
        return this;
    }

    public void setGranicaOd(Double granicaOd) {
        this.granicaOd = granicaOd;
    }

    public Double getGranicaDo() {
        return granicaDo;
    }

    public KriterijumBodovanje granicaDo(Double granicaDo) {
        this.granicaDo = granicaDo;
        return this;
    }

    public void setGranicaDo(Double granicaDo) {
        this.granicaDo = granicaDo;
    }

    public Double getBodovi() {
        return bodovi;
    }

    public KriterijumBodovanje bodovi(Double bodovi) {
        this.bodovi = bodovi;
        return this;
    }

    public void setBodovi(Double bodovi) {
        this.bodovi = bodovi;
    }

    public Kriterijum getKriterijum() {
        return kriterijum;
    }

    public KriterijumBodovanje kriterijum(Kriterijum kriterijum) {
        this.kriterijum = kriterijum;
        return this;
    }

    public void setKriterijum(Kriterijum kriterijum) {
        this.kriterijum = kriterijum;
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
        KriterijumBodovanje kriterijumBodovanje = (KriterijumBodovanje) o;
        if (kriterijumBodovanje.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), kriterijumBodovanje.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "KriterijumBodovanje{" +
            "id=" + getId() +
            ", granicaOd=" + getGranicaOd() +
            ", granicaDo=" + getGranicaDo() +
            ", bodovi=" + getBodovi() +
            "}";
    }
}
