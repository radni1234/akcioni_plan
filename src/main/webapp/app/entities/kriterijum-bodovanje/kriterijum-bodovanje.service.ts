import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IKriterijumBodovanje } from 'app/shared/model/kriterijum-bodovanje.model';

type EntityResponseType = HttpResponse<IKriterijumBodovanje>;
type EntityArrayResponseType = HttpResponse<IKriterijumBodovanje[]>;

@Injectable({ providedIn: 'root' })
export class KriterijumBodovanjeService {
    private resourceUrl = SERVER_API_URL + 'api/kriterijum-bodovanjes';

    constructor(private http: HttpClient) {}

    create(kriterijumBodovanje: IKriterijumBodovanje): Observable<EntityResponseType> {
        return this.http.post<IKriterijumBodovanje>(this.resourceUrl, kriterijumBodovanje, { observe: 'response' });
    }

    update(kriterijumBodovanje: IKriterijumBodovanje): Observable<EntityResponseType> {
        return this.http.put<IKriterijumBodovanje>(this.resourceUrl, kriterijumBodovanje, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IKriterijumBodovanje>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IKriterijumBodovanje[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
