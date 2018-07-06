import { IKriterijumBodovanje } from 'app/shared/model//kriterijum-bodovanje.model';
import { IProjekatBodovanje } from 'app/shared/model//projekat-bodovanje.model';
import { IAkcioniPlan } from 'app/shared/model//akcioni-plan.model';

export const enum KriterijumTip {
    BOD = 'BOD',
    VREDNOST = 'VREDNOST'
}

export interface IKriterijum {
    id?: number;
    kriterijumTip?: KriterijumTip;
    naziv?: string;
    ponder?: number;
    kriterijumBodovanjes?: IKriterijumBodovanje[];
    projekatPodovanjes?: IProjekatBodovanje[];
    akcioniPlan?: IAkcioniPlan;
}

export class Kriterijum implements IKriterijum {
    constructor(
        public id?: number,
        public kriterijumTip?: KriterijumTip,
        public naziv?: string,
        public ponder?: number,
        public kriterijumBodovanjes?: IKriterijumBodovanje[],
        public projekatPodovanjes?: IProjekatBodovanje[],
        public akcioniPlan?: IAkcioniPlan
    ) {}
}
