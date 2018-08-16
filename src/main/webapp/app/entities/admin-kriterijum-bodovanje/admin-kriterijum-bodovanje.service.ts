import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAdminKriterijumBodovanje } from 'app/shared/model/admin-kriterijum-bodovanje.model';

type EntityResponseType = HttpResponse<IAdminKriterijumBodovanje>;
type EntityArrayResponseType = HttpResponse<IAdminKriterijumBodovanje[]>;

@Injectable({ providedIn: 'root' })
export class AdminKriterijumBodovanjeService {
    private resourceUrl = SERVER_API_URL + 'api/admin-kriterijum-bodovanjes';

    constructor(private http: HttpClient) {}

    create(adminKriterijumBodovanje: IAdminKriterijumBodovanje): Observable<EntityResponseType> {
        return this.http.post<IAdminKriterijumBodovanje>(this.resourceUrl, adminKriterijumBodovanje, { observe: 'response' });
    }

    update(adminKriterijumBodovanje: IAdminKriterijumBodovanje): Observable<EntityResponseType> {
        return this.http.put<IAdminKriterijumBodovanje>(this.resourceUrl, adminKriterijumBodovanje, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAdminKriterijumBodovanje>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAdminKriterijumBodovanje[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
