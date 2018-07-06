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

    @Column(name = "aktivnost")
    private String aktivnost;

    @Column(name = "lokacija")
    private String lokacija;

    @Column(name = "opsti_cilj")
    private String opstiCilj;

    @Column(name = "posebni_cilj")
    private String posebniCilj;

    @Column(name = "indikatori")
    private String indikatori;

    @Column(name = "referentni_parametar")
    private String referentniParametar;

    @Column(name = "projektovanja_potrosnja")
    private String projektovanjaPotrosnja;

    @Column(name = "potrosnja_nakon_mere")
    private String potrosnjaNakonMere;

    @Column(name = "investicija")
    private String investicija;

    @Column(name = "vrednost_ustede")
    private String vrednostUstede;

    @Column(name = "vreme_povracaja")
    private String vremePovracaja;

    @Column(name = "smanjenje_emisije")
    private String smanjenjeEmisije;

    @Column(name = "opis_mere")
    private String opisMere;

    @Column(name = "vremenski_okvir")
    private String vremenskiOkvir;

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

    public String getAktivnost() {
        return aktivnost;
    }

    public Projekat aktivnost(String aktivnost) {
        this.aktivnost = aktivnost;
        return this;
    }

    public void setAktivnost(String aktivnost) {
        this.aktivnost = aktivnost;
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

    public String getOpstiCilj() {
        return opstiCilj;
    }

    public Projekat opstiCilj(String opstiCilj) {
        this.opstiCilj = opstiCilj;
        return this;
    }

    public void setOpstiCilj(String opstiCilj) {
        this.opstiCilj = opstiCilj;
    }

    public String getPosebniCilj() {
        return posebniCilj;
    }

    public Projekat posebniCilj(String posebniCilj) {
        this.posebniCilj = posebniCilj;
        return this;
    }

    public void setPosebniCilj(String posebniCilj) {
        this.posebniCilj = posebniCilj;
    }

    public String getIndikatori() {
        return indikatori;
    }

    public Projekat indikatori(String indikatori) {
        this.indikatori = indikatori;
        return this;
    }

    public void setIndikatori(String indikatori) {
        this.indikatori = indikatori;
    }

    public String getReferentniParametar() {
        return referentniParametar;
    }

    public Projekat referentniParametar(String referentniParametar) {
        this.referentniParametar = referentniParametar;
        return this;
    }

    public void setReferentniParametar(String referentniParametar) {
        this.referentniParametar = referentniParametar;
    }

    public String getProjektovanjaPotrosnja() {
        return projektovanjaPotrosnja;
    }

    public Projekat projektovanjaPotrosnja(String projektovanjaPotrosnja) {
        this.projektovanjaPotrosnja = projektovanjaPotrosnja;
        return this;
    }

    public void setProjektovanjaPotrosnja(String projektovanjaPotrosnja) {
        this.projektovanjaPotrosnja = projektovanjaPotrosnja;
    }

    public String getPotrosnjaNakonMere() {
        return potrosnjaNakonMere;
    }

    public Projekat potrosnjaNakonMere(String potrosnjaNakonMere) {
        this.potrosnjaNakonMere = potrosnjaNakonMere;
        return this;
    }

    public void setPotrosnjaNakonMere(String potrosnjaNakonMere) {
        this.potrosnjaNakonMere = potrosnjaNakonMere;
    }

    public String getInvesticija() {
        return investicija;
    }

    public Projekat investicija(String investicija) {
        this.investicija = investicija;
        return this;
    }

    public void setInvesticija(String investicija) {
        this.investicija = investicija;
    }

    public String getVrednostUstede() {
        return vrednostUstede;
    }

    public Projekat vrednostUstede(String vrednostUstede) {
        this.vrednostUstede = vrednostUstede;
        return this;
    }

    public void setVrednostUstede(String vrednostUstede) {
        this.vrednostUstede = vrednostUstede;
    }

    public String getVremePovracaja() {
        return vremePovracaja;
    }

    public Projekat vremePovracaja(String vremePovracaja) {
        this.vremePovracaja = vremePovracaja;
        return this;
    }

    public void setVremePovracaja(String vremePovracaja) {
        this.vremePovracaja = vremePovracaja;
    }

    public String getSmanjenjeEmisije() {
        return smanjenjeEmisije;
    }

    public Projekat smanjenjeEmisije(String smanjenjeEmisije) {
        this.smanjenjeEmisije = smanjenjeEmisije;
        return this;
    }

    public void setSmanjenjeEmisije(String smanjenjeEmisije) {
        this.smanjenjeEmisije = smanjenjeEmisije;
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

    public String getVremenskiOkvir() {
        return vremenskiOkvir;
    }

    public Projekat vremenskiOkvir(String vremenskiOkvir) {
        this.vremenskiOkvir = vremenskiOkvir;
        return this;
    }

    public void setVremenskiOkvir(String vremenskiOkvir) {
        this.vremenskiOkvir = vremenskiOkvir;
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
            ", aktivnost='" + getAktivnost() + "'" +
            ", lokacija='" + getLokacija() + "'" +
            ", opstiCilj='" + getOpstiCilj() + "'" +
            ", posebniCilj='" + getPosebniCilj() + "'" +
            ", indikatori='" + getIndikatori() + "'" +
            ", referentniParametar='" + getReferentniParametar() + "'" +
            ", projektovanjaPotrosnja='" + getProjektovanjaPotrosnja() + "'" +
            ", potrosnjaNakonMere='" + getPotrosnjaNakonMere() + "'" +
            ", investicija='" + getInvesticija() + "'" +
            ", vrednostUstede='" + getVrednostUstede() + "'" +
            ", vremePovracaja='" + getVremePovracaja() + "'" +
            ", smanjenjeEmisije='" + getSmanjenjeEmisije() + "'" +
            ", opisMere='" + getOpisMere() + "'" +
            ", vremenskiOkvir='" + getVremenskiOkvir() + "'" +
            ", odgovornaOsoba='" + getOdgovornaOsoba() + "'" +
            ", izvorFinansiranja='" + getIzvorFinansiranja() + "'" +
            ", ostalo='" + getOstalo() + "'" +
            ", slika='" + getSlika() + "'" +
            ", slikaContentType='" + getSlikaContentType() + "'" +
            "}";
    }
}
