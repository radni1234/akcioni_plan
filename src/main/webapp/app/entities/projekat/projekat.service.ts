import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProjekat } from 'app/shared/model/projekat.model';

type EntityResponseType = HttpResponse<IProjekat>;
type EntityArrayResponseType = HttpResponse<IProjekat[]>;

@Injectable({ providedIn: 'root' })
export class ProjekatService {
    private resourceUrl = SERVER_API_URL + 'api/projekats';

    constructor(private http: HttpClient) {}

    create(projekat: IProjekat): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(projekat);
        return this.http
            .post<IProjekat>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(projekat: IProjekat): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(projekat);
        return this.http
            .put<IProjekat>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IProjekat>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IProjekat[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    queryByAkcioniPlan(id: number, req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IProjekat[]>(`${this.resourceUrl}/${'ap'}/${id}`, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(projekat: IProjekat): IProjekat {
        const copy: IProjekat = Object.assign({}, projekat, {
            datumOd: projekat.datumOd != null && projekat.datumOd.isValid() ? projekat.datumOd.format(DATE_FORMAT) : null,
            datumDo: projekat.datumDo != null && projekat.datumDo.isValid() ? projekat.datumDo.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.datumOd = res.body.datumOd != null ? moment(res.body.datumOd) : null;
        res.body.datumDo = res.body.datumDo != null ? moment(res.body.datumDo) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((projekat: IProjekat) => {
            projekat.datumOd = projekat.datumOd != null ? moment(projekat.datumOd) : null;
            projekat.datumDo = projekat.datumDo != null ? moment(projekat.datumDo) : null;
        });
        return res;
    }
}
