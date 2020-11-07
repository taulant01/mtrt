import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITreat } from 'app/shared/model/treat.model';

type EntityResponseType = HttpResponse<ITreat>;
type EntityArrayResponseType = HttpResponse<ITreat[]>;

@Injectable({ providedIn: 'root' })
export class TreatService {
  public resourceUrl = SERVER_API_URL + 'api/treats';

  constructor(protected http: HttpClient) {}

  create(treat: ITreat): Observable<EntityResponseType> {
    return this.http.post<ITreat>(this.resourceUrl, treat, { observe: 'response' });
  }

  update(treat: ITreat): Observable<EntityResponseType> {
    return this.http.put<ITreat>(this.resourceUrl, treat, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITreat>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITreat[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
