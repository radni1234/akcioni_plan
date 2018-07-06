import { Moment } from 'moment';
import { IProjekatBodovanje } from 'app/shared/model//projekat-bodovanje.model';
import { IAkcioniPlan } from 'app/shared/model//akcioni-plan.model';

export interface IProjekat {
    id?: number;
    naziv?: string;
    datumOd?: Moment;
    datumDo?: Moment;
    aktivnost?: string;
    lokacija?: string;
    opstiCilj?: string;
    posebniCilj?: string;
    indikatori?: string;
    referentniParametar?: string;
    projektovanjaPotrosnja?: string;
    potrosnjaNakonMere?: string;
    investicija?: string;
    vrednostUstede?: string;
    vremePovracaja?: string;
    smanjenjeEmisije?: string;
    opisMere?: string;
    vremenskiOkvir?: string;
    odgovornaOsoba?: string;
    izvorFinansiranja?: string;
    ostalo?: string;
    slikaContentType?: string;
    slika?: any;
    projekatBodovanjes?: IProjekatBodovanje[];
    akcioniPlan?: IAkcioniPlan;
}

export class Projekat implements IProjekat {
    constructor(
        public id?: number,
        public naziv?: string,
        public datumOd?: Moment,
        public datumDo?: Moment,
        public aktivnost?: string,
        public lokacija?: string,
        public opstiCilj?: string,
        public posebniCilj?: string,
        public indikatori?: string,
        public referentniParametar?: string,
        public projektovanjaPotrosnja?: string,
        public potrosnjaNakonMere?: string,
        public investicija?: string,
        public vrednostUstede?: string,
        public vremePovracaja?: string,
        public smanjenjeEmisije?: string,
        public opisMere?: string,
        public vremenskiOkvir?: string,
        public odgovornaOsoba?: string,
        public izvorFinansiranja?: string,
        public ostalo?: string,
        public slikaContentType?: string,
        public slika?: any,
        public projekatBodovanjes?: IProjekatBodovanje[],
        public akcioniPlan?: IAkcioniPlan
    ) {}
}
