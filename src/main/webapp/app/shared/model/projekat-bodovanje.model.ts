import { IKriterijum } from 'app/shared/model//kriterijum.model';
import { IProjekat } from 'app/shared/model//projekat.model';

export interface IProjekatBodovanje {
    id?: number;
    vrednost?: number;
    bodovi?: number;
    ponder?: number;
    ponderisaniBodovi?: number;
    kriterijum?: IKriterijum;
    projekat?: IProjekat;
}

export class ProjekatBodovanje implements IProjekatBodovanje {
    constructor(
        public id?: number,
        public vrednost?: number,
        public bodovi?: number,
        public ponder?: number,
        public ponderisaniBodovi?: number,
        public kriterijum?: IKriterijum,
        public projekat?: IProjekat
    ) {}
}
