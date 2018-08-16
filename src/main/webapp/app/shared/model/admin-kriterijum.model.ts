import { IAdminKriterijumBodovanje } from 'app/shared/model//admin-kriterijum-bodovanje.model';

export const enum KriterijumTip {
    BOD = 'BOD',
    VREDNOST = 'VREDNOST'
}

export interface IAdminKriterijum {
    id?: number;
    kriterijumTip?: KriterijumTip;
    naziv?: string;
    ponder?: number;
    adminKriterijumBodovanjes?: IAdminKriterijumBodovanje[];
}

export class AdminKriterijum implements IAdminKriterijum {
    constructor(
        public id?: number,
        public kriterijumTip?: KriterijumTip,
        public naziv?: string,
        public ponder?: number,
        public adminKriterijumBodovanjes?: IAdminKriterijumBodovanje[]
    ) {}
}
