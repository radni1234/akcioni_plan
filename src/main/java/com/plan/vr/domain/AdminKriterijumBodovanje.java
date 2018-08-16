package com.plan.vr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A AdminKriterijumBodovanje.
 */
@Entity
@Table(name = "admin_kriterijum_bodovanje")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AdminKriterijumBodovanje implements Serializable {

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
    @JsonIgnoreProperties("adminKriterijumBodovanjes")
    private AdminKriterijum adminKriterijum;

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

    public AdminKriterijumBodovanje granicaOd(Double granicaOd) {
        this.granicaOd = granicaOd;
        return this;
    }

    public void setGranicaOd(Double granicaOd) {
        this.granicaOd = granicaOd;
    }

    public Double getGranicaDo() {
        return granicaDo;
    }

    public AdminKriterijumBodovanje granicaDo(Double granicaDo) {
        this.granicaDo = granicaDo;
        return this;
    }

    public void setGranicaDo(Double granicaDo) {
        this.granicaDo = granicaDo;
    }

    public Double getBodovi() {
        return bodovi;
    }

    public AdminKriterijumBodovanje bodovi(Double bodovi) {
        this.bodovi = bodovi;
        return this;
    }

    public void setBodovi(Double bodovi) {
        this.bodovi = bodovi;
    }

    public AdminKriterijum getAdminKriterijum() {
        return adminKriterijum;
    }

    public AdminKriterijumBodovanje adminKriterijum(AdminKriterijum adminKriterijum) {
        this.adminKriterijum = adminKriterijum;
        return this;
    }

    public void setAdminKriterijum(AdminKriterijum adminKriterijum) {
        this.adminKriterijum = adminKriterijum;
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
        AdminKriterijumBodovanje adminKriterijumBodovanje = (AdminKriterijumBodovanje) o;
        if (adminKriterijumBodovanje.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adminKriterijumBodovanje.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdminKriterijumBodovanje{" +
            "id=" + getId() +
            ", granicaOd=" + getGranicaOd() +
            ", granicaDo=" + getGranicaDo() +
            ", bodovi=" + getBodovi() +
            "}";
    }
}
