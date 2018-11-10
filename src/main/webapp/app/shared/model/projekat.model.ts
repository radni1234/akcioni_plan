import { Moment } from 'moment';
import { IProjekatBodovanje } from 'app/shared/model//projekat-bodovanje.model';
import { IAkcioniPlan } from 'app/shared/model//akcioni-plan.model';

export interface IProjekat {
    id?: number;
    naziv?: string;
    datumOd?: Moment;
    datumDo?: Moment;
    lokacija?: string;
    investicija?: string;
    opisMere?: string;
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
        public lokacija?: string,
        public investicija?: string,
        public opisMere?: string,
        public odgovornaOsoba?: string,
        public izvorFinansiranja?: string,
        public ostalo?: string,
        public slikaContentType?: string,
        public slika?: any,
        public projekatBodovanjes?: IProjekatBodovanje[],
        public akcioniPlan?: IAkcioniPlan
    ) {}
}
