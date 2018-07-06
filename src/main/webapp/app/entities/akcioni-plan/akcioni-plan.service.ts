import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAkcioniPlan } from 'app/shared/model/akcioni-plan.model';

type EntityResponseType = HttpResponse<IAkcioniPlan>;
type EntityArrayResponseType = HttpResponse<IAkcioniPlan[]>;

@Injectable({ providedIn: 'root' })
export class AkcioniPlanService {
    private resourceUrl = SERVER_API_URL + 'api/akcioni-plans';

    constructor(private http: HttpClient) {}

    create(akcioniPlan: IAkcioniPlan): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(akcioniPlan);
        return this.http
            .post<IAkcioniPlan>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(akcioniPlan: IAkcioniPlan): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(akcioniPlan);
        return this.http
            .put<IAkcioniPlan>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IAkcioniPlan>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IAkcioniPlan[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(akcioniPlan: IAkcioniPlan): IAkcioniPlan {
        const copy: IAkcioniPlan = Object.assign({}, akcioniPlan, {
            datumOd: akcioniPlan.datumOd != null && akcioniPlan.datumOd.isValid() ? akcioniPlan.datumOd.format(DATE_FORMAT) : null,
            datumDo: akcioniPlan.datumDo != null && akcioniPlan.datumDo.isValid() ? akcioniPlan.datumDo.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.datumOd = res.body.datumOd != null ? moment(res.body.datumOd) : null;
        res.body.datumDo = res.body.datumDo != null ? moment(res.body.datumDo) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((akcioniPlan: IAkcioniPlan) => {
            akcioniPlan.datumOd = akcioniPlan.datumOd != null ? moment(akcioniPlan.datumOd) : null;
            akcioniPlan.datumDo = akcioniPlan.datumDo != null ? moment(akcioniPlan.datumDo) : null;
        });
        return res;
    }
}
