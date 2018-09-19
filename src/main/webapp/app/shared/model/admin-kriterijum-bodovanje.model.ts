import { IAdminKriterijum } from 'app/shared/model//admin-kriterijum.model';

export interface IAdminKriterijumBodovanje {
    id?: number;
    granicaOd?: number;
    granicaDo?: number;
    opis?: string;
    bodovi?: number;
    adminKriterijum?: IAdminKriterijum;
}

export class AdminKriterijumBodovanje implements IAdminKriterijumBodovanje {
    constructor(
        public id?: number,
        public granicaOd?: number,
        public granicaDo?: number,
        public opis?: string,
        public bodovi?: number,
        public adminKriterijum?: IAdminKriterijum
    ) {}
}
