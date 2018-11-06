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

    @Column(name = "rb")
    private Integer rb;

    @Column(name = "granica")
    private Double granica;

    @Column(name = "opis")
    private String opis;

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

    public Integer getRb() {
        return rb;
    }

    public AdminKriterijumBodovanje rb(Integer rb) {
        this.rb = rb;
        return this;
    }

    public void setRb(Integer rb) {
        this.rb = rb;
    }

    public Double getGranica() {
        return granica;
    }

    public AdminKriterijumBodovanje granica(Double granica) {
        this.granica = granica;
        return this;
    }

    public void setGranica(Double granica) {
        this.granica = granica;
    }

    public String getOpis() {
        return opis;
    }

    public AdminKriterijumBodovanje opis(String opis) {
        this.opis = opis;
        return this;
    }

    public void setOpis(String opis) {
        this.opis = opis;
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
            ", rb=" + getRb() +
            ", granica=" + getGranica() +
            ", opis='" + getOpis() + "'" +
            ", bodovi=" + getBodovi() +
            "}";
    }
}
