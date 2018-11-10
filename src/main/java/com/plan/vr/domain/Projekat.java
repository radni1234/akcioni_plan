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
 * A Projekat.
 */
@Entity
@Table(name = "projekat")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Projekat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "naziv", length = 100, nullable = false)
    private String naziv;

    @Column(name = "datum_od")
    private LocalDate datumOd;

    @Column(name = "datum_do")
    private LocalDate datumDo;

    @Column(name = "lokacija")
    private String lokacija;

    @Column(name = "opis_mere")
    private String opisMere;

    @Column(name = "odgovorna_osoba")
    private String odgovornaOsoba;

    @Column(name = "izvor_finansiranja")
    private String izvorFinansiranja;

    @Column(name = "ostalo")
    private String ostalo;

    @Lob
    @Column(name = "slika")
    private byte[] slika;

    @Column(name = "slika_content_type")
    private String slikaContentType;

    @OneToMany(mappedBy = "projekat")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProjekatBodovanje> projekatBodovanjes = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("projekats")
    private AkcioniPlan akcioniPlan;

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

    public Projekat naziv(String naziv) {
        this.naziv = naziv;
        return this;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public LocalDate getDatumOd() {
        return datumOd;
    }

    public Projekat datumOd(LocalDate datumOd) {
        this.datumOd = datumOd;
        return this;
    }

    public void setDatumOd(LocalDate datumOd) {
        this.datumOd = datumOd;
    }

    public LocalDate getDatumDo() {
        return datumDo;
    }

    public Projekat datumDo(LocalDate datumDo) {
        this.datumDo = datumDo;
        return this;
    }

    public void setDatumDo(LocalDate datumDo) {
        this.datumDo = datumDo;
    }

    public String getLokacija() {
        return lokacija;
    }

    public Projekat lokacija(String lokacija) {
        this.lokacija = lokacija;
        return this;
    }

    public void setLokacija(String lokacija) {
        this.lokacija = lokacija;
    }

    public String getOpisMere() {
        return opisMere;
    }

    public Projekat opisMere(String opisMere) {
        this.opisMere = opisMere;
        return this;
    }

    public void setOpisMere(String opisMere) {
        this.opisMere = opisMere;
    }

    public String getOdgovornaOsoba() {
        return odgovornaOsoba;
    }

    public Projekat odgovornaOsoba(String odgovornaOsoba) {
        this.odgovornaOsoba = odgovornaOsoba;
        return this;
    }

    public void setOdgovornaOsoba(String odgovornaOsoba) {
        this.odgovornaOsoba = odgovornaOsoba;
    }

    public String getIzvorFinansiranja() {
        return izvorFinansiranja;
    }

    public Projekat izvorFinansiranja(String izvorFinansiranja) {
        this.izvorFinansiranja = izvorFinansiranja;
        return this;
    }

    public void setIzvorFinansiranja(String izvorFinansiranja) {
        this.izvorFinansiranja = izvorFinansiranja;
    }

    public String getOstalo() {
        return ostalo;
    }

    public Projekat ostalo(String ostalo) {
        this.ostalo = ostalo;
        return this;
    }

    public void setOstalo(String ostalo) {
        this.ostalo = ostalo;
    }

    public byte[] getSlika() {
        return slika;
    }

    public Projekat slika(byte[] slika) {
        this.slika = slika;
        return this;
    }

    public void setSlika(byte[] slika) {
        this.slika = slika;
    }

    public String getSlikaContentType() {
        return slikaContentType;
    }

    public Projekat slikaContentType(String slikaContentType) {
        this.slikaContentType = slikaContentType;
        return this;
    }

    public void setSlikaContentType(String slikaContentType) {
        this.slikaContentType = slikaContentType;
    }

    public Set<ProjekatBodovanje> getProjekatBodovanjes() {
        return projekatBodovanjes;
    }

    public Projekat projekatBodovanjes(Set<ProjekatBodovanje> projekatBodovanjes) {
        this.projekatBodovanjes = projekatBodovanjes;
        return this;
    }

    public Projekat addProjekatBodovanje(ProjekatBodovanje projekatBodovanje) {
        this.projekatBodovanjes.add(projekatBodovanje);
        projekatBodovanje.setProjekat(this);
        return this;
    }

    public Projekat removeProjekatBodovanje(ProjekatBodovanje projekatBodovanje) {
        this.projekatBodovanjes.remove(projekatBodovanje);
        projekatBodovanje.setProjekat(null);
        return this;
    }

    public void setProjekatBodovanjes(Set<ProjekatBodovanje> projekatBodovanjes) {
        this.projekatBodovanjes = projekatBodovanjes;
    }

    public AkcioniPlan getAkcioniPlan() {
        return akcioniPlan;
    }

    public Projekat akcioniPlan(AkcioniPlan akcioniPlan) {
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
        Projekat projekat = (Projekat) o;
        if (projekat.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), projekat.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Projekat{" +
            "id=" + getId() +
            ", naziv='" + getNaziv() + "'" +
            ", datumOd='" + getDatumOd() + "'" +
            ", datumDo='" + getDatumDo() + "'" +
            ", lokacija='" + getLokacija() + "'" +
            ", opisMere='" + getOpisMere() + "'" +
            ", odgovornaOsoba='" + getOdgovornaOsoba() + "'" +
            ", izvorFinansiranja='" + getIzvorFinansiranja() + "'" +
            ", ostalo='" + getOstalo() + "'" +
            ", slika='" + getSlika() + "'" +
            ", slikaContentType='" + getSlikaContentType() + "'" +
            "}";
    }
}
