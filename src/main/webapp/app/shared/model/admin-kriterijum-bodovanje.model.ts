import { IAdminKriterijum } from 'app/shared/model//admin-kriterijum.model';

export interface IAdminKriterijumBodovanje {
    id?: number;
    rb?: number;
    granica?: number;
    opis?: string;
    bodovi?: number;
    adminKriterijum?: IAdminKriterijum;
}

export class AdminKriterijumBodovanje implements IAdminKriterijumBodovanje {
    constructor(
        public id?: number,
        public rb?: number,
        public granica?: number,
        public opis?: string,
        public bodovi?: number,
        public adminKriterijum?: IAdminKriterijum
    ) {}
}
