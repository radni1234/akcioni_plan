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

    @Column(name = "granica")
    private Double granica;

    @Column(name = "opis")
    private String opis;

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

    public Double getGranica() {
        return granica;
    }

    public KriterijumBodovanje granica(Double granica) {
        this.granica = granica;
        return this;
    }

    public void setGranica(Double granica) {
        this.granica = granica;
    }

    public String getOpis() {
        return opis;
    }

    public KriterijumBodovanje opis(String opis) {
        this.opis = opis;
        return this;
    }

    public void setOpis(String opis) {
        this.opis = opis;
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
            ", granica=" + getGranica() +
            ", opis='" + getOpis() + "'" +
            ", bodovi=" + getBodovi() +
            "}";
    }
}
