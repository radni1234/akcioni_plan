package com.plan.vr.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.plan.vr.domain.enumeration.KriterijumTip;

/**
 * A AdminKriterijum.
 */
@Entity
@Table(name = "admin_kriterijum")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AdminKriterijum implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "kriterijum_tip", nullable = false)
    private KriterijumTip kriterijumTip;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "naziv", length = 100, nullable = false)
    private String naziv;

    @NotNull
    @Column(name = "ponder", nullable = false)
    private Float ponder;

    @OneToMany(mappedBy = "adminKriterijum")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AdminKriterijumBodovanje> adminKriterijumBodovanjes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public KriterijumTip getKriterijumTip() {
        return kriterijumTip;
    }

    public AdminKriterijum kriterijumTip(KriterijumTip kriterijumTip) {
        this.kriterijumTip = kriterijumTip;
        return this;
    }

    public void setKriterijumTip(KriterijumTip kriterijumTip) {
        this.kriterijumTip = kriterijumTip;
    }

    public String getNaziv() {
        return naziv;
    }

    public AdminKriterijum naziv(String naziv) {
        this.naziv = naziv;
        return this;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Float getPonder() {
        return ponder;
    }

    public AdminKriterijum ponder(Float ponder) {
        this.ponder = ponder;
        return this;
    }

    public void setPonder(Float ponder) {
        this.ponder = ponder;
    }

    public Set<AdminKriterijumBodovanje> getAdminKriterijumBodovanjes() {
        return adminKriterijumBodovanjes;
    }

    public AdminKriterijum adminKriterijumBodovanjes(Set<AdminKriterijumBodovanje> adminKriterijumBodovanjes) {
        this.adminKriterijumBodovanjes = adminKriterijumBodovanjes;
        return this;
    }

    public AdminKriterijum addAdminKriterijumBodovanje(AdminKriterijumBodovanje adminKriterijumBodovanje) {
        this.adminKriterijumBodovanjes.add(adminKriterijumBodovanje);
        adminKriterijumBodovanje.setAdminKriterijum(this);
        return this;
    }

    public AdminKriterijum removeAdminKriterijumBodovanje(AdminKriterijumBodovanje adminKriterijumBodovanje) {
        this.adminKriterijumBodovanjes.remove(adminKriterijumBodovanje);
        adminKriterijumBodovanje.setAdminKriterijum(null);
        return this;
    }

    public void setAdminKriterijumBodovanjes(Set<AdminKriterijumBodovanje> adminKriterijumBodovanjes) {
        this.adminKriterijumBodovanjes = adminKriterijumBodovanjes;
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
        AdminKriterijum adminKriterijum = (AdminKriterijum) o;
        if (adminKriterijum.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adminKriterijum.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdminKriterijum{" +
            "id=" + getId() +
            ", kriterijumTip='" + getKriterijumTip() + "'" +
            ", naziv='" + getNaziv() + "'" +
            ", ponder=" + getPonder() +
            "}";
    }
}
