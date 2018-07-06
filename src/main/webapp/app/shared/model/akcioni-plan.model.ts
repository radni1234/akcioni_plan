import { Moment } from 'moment';
import { IKriterijum } from 'app/shared/model//kriterijum.model';
import { IProjekat } from 'app/shared/model//projekat.model';
import { IUser } from 'app/core/user/user.model';

export interface IAkcioniPlan {
    id?: number;
    naziv?: string;
    opis?: string;
    budzet?: number;
    datumOd?: Moment;
    datumDo?: Moment;
    kriterijums?: IKriterijum[];
    projekats?: IProjekat[];
    user?: IUser;
}

export class AkcioniPlan implements IAkcioniPlan {
    constructor(
        public id?: number,
        public naziv?: string,
        public opis?: string,
        public budzet?: number,
        public datumOd?: Moment,
        public datumDo?: Moment,
        public kriterijums?: IKriterijum[],
        public projekats?: IProjekat[],
        public user?: IUser
    ) {}
}
