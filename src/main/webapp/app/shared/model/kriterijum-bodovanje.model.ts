import { IKriterijum } from 'app/shared/model//kriterijum.model';

export interface IKriterijumBodovanje {
    id?: number;
    rb?: number;
    granica?: number;
    opis?: string;
    bodovi?: number;
    kriterijum?: IKriterijum;
}

export class KriterijumBodovanje implements IKriterijumBodovanje {
    constructor(
        public id?: number,
        public rb?: number,
        public granica?: number,
        public opis?: string,
        public bodovi?: number,
        public kriterijum?: IKriterijum
    ) {}
}
