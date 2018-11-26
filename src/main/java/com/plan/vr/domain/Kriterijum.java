package com.plan.vr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
 * A Kriterijum.
 */
@Entity
@Table(name = "kriterijum")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Kriterijum implements Serializable {

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

    @OneToMany(mappedBy = "kriterijum")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<KriterijumBodovanje> kriterijumBodovanjes = new HashSet<>();

    @OneToMany(mappedBy = "kriterijum")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProjekatBodovanje> projekatBodovanjes = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("kriterijums")
    private AkcioniPlan akcioniPlan;

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

    public Kriterijum kriterijumTip(KriterijumTip kriterijumTip) {
        this.kriterijumTip = kriterijumTip;
        return this;
    }

    public void setKriterijumTip(KriterijumTip kriterijumTip) {
        this.kriterijumTip = kriterijumTip;
    }

    public String getNaziv() {
        return naziv;
    }

    public Kriterijum naziv(String naziv) {
        this.naziv = naziv;
        return this;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Float getPonder() {
        return ponder;
    }

    public Kriterijum ponder(Float ponder) {
        this.ponder = ponder;
        return this;
    }

    public void setPonder(Float ponder) {
        this.ponder = ponder;
    }

    public Set<KriterijumBodovanje> getKriterijumBodovanjes() {
        return kriterijumBodovanjes;
    }

    public Kriterijum kriterijumBodovanjes(Set<KriterijumBodovanje> kriterijumBodovanjes) {
        this.kriterijumBodovanjes = kriterijumBodovanjes;
        return this;
    }

    public Kriterijum addKriterijumBodovanje(KriterijumBodovanje kriterijumBodovanje) {
        this.kriterijumBodovanjes.add(kriterijumBodovanje);
        kriterijumBodovanje.setKriterijum(this);
        return this;
    }

    public Kriterijum removeKriterijumBodovanje(KriterijumBodovanje kriterijumBodovanje) {
        this.kriterijumBodovanjes.remove(kriterijumBodovanje);
        kriterijumBodovanje.setKriterijum(null);
        return this;
    }

    public void setKriterijumBodovanjes(Set<KriterijumBodovanje> kriterijumBodovanjes) {
        this.kriterijumBodovanjes = kriterijumBodovanjes;
    }

    public Set<ProjekatBodovanje> getProjekatBodovanjes() {
        return projekatBodovanjes;
    }

    public Kriterijum projekatPodovanjes(Set<ProjekatBodovanje> projekatBodovanjes) {
        this.projekatBodovanjes = projekatBodovanjes;
        return this;
    }

    public Kriterijum addProjekatPodovanje(ProjekatBodovanje projekatBodovanje) {
        this.projekatBodovanjes.add(projekatBodovanje);
        projekatBodovanje.setKriterijum(this);
        return this;
    }

    public Kriterijum removeProjekatPodovanje(ProjekatBodovanje projekatBodovanje) {
        this.projekatBodovanjes.remove(projekatBodovanje);
        projekatBodovanje.setKriterijum(null);
        return this;
    }

    public void setProjekatBodovanjes(Set<ProjekatBodovanje> projekatBodovanjes) {
        this.projekatBodovanjes = projekatBodovanjes;
    }

    public AkcioniPlan getAkcioniPlan() {
        return akcioniPlan;
    }

    public Kriterijum akcioniPlan(AkcioniPlan akcioniPlan) {
        this.akcioniPlan = akcioniPlan;
        return this;
    }

    public void setAkcioniPlan(AkcioniPlan akcioniPlan) {
        this.akcioniPlan = akcioniPlan;
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
        Kriterijum kriterijum = (Kriterijum) o;
        if (kriterijum.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), kriterijum.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Kriterijum{" +
            "id=" + getId() +
            ", kriterijumTip='" + getKriterijumTip() + "'" +
            ", naziv='" + getNaziv() + "'" +
            ", ponder=" + getPonder() +
            "}";
    }
}
