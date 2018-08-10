import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IKriterijum } from 'app/shared/model/kriterijum.model';

type EntityResponseType = HttpResponse<IKriterijum>;
type EntityArrayResponseType = HttpResponse<IKriterijum[]>;

@Injectable({ providedIn: 'root' })
export class KriterijumService {
    private resourceUrl = SERVER_API_URL + 'api/kriterijums';

    constructor(private http: HttpClient) {}

    create(kriterijum: IKriterijum): Observable<EntityResponseType> {
        return this.http.post<IKriterijum>(this.resourceUrl, kriterijum, { observe: 'response' });
    }

    update(kriterijum: IKriterijum): Observable<EntityResponseType> {
        return this.http.put<IKriterijum>(this.resourceUrl, kriterijum, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IKriterijum>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IKriterijum[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    queryByAkcioniPlan(id: number, req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IKriterijum[]>(`${this.resourceUrl}/${'ap'}/${id}`, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
