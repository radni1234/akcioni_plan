import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProjekatBodovanje } from 'app/shared/model/projekat-bodovanje.model';

type EntityResponseType = HttpResponse<IProjekatBodovanje>;
type EntityArrayResponseType = HttpResponse<IProjekatBodovanje[]>;

@Injectable({ providedIn: 'root' })
export class ProjekatBodovanjeService {
    private resourceUrl = SERVER_API_URL + 'api/projekat-bodovanjes';

    constructor(private http: HttpClient) {}

    create(projekatBodovanje: IProjekatBodovanje): Observable<EntityResponseType> {
        return this.http.post<IProjekatBodovanje>(this.resourceUrl, projekatBodovanje, { observe: 'response' });
    }

    update(projekatBodovanje: IProjekatBodovanje): Observable<EntityResponseType> {
        return this.http.put<IProjekatBodovanje>(this.resourceUrl, projekatBodovanje, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IProjekatBodovanje>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IProjekatBodovanje[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    queryByProjekat(id: number, req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IProjekatBodovanje[]>(`${this.resourceUrl}/${'ap'}/${id}`, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
