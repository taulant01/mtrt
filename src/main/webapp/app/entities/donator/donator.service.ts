import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDonator } from 'app/shared/model/donator.model';

type EntityResponseType = HttpResponse<IDonator>;
type EntityArrayResponseType = HttpResponse<IDonator[]>;

@Injectable({ providedIn: 'root' })
export class DonatorService {
  public resourceUrl = SERVER_API_URL + 'api/donators';

  constructor(protected http: HttpClient) {}

  create(donator: IDonator): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(donator);
    return this.http
      .post<IDonator>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(donator: IDonator): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(donator);
    return this.http
      .put<IDonator>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDonator>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDonator[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(donator: IDonator): IDonator {
    const copy: IDonator = Object.assign({}, donator, {
      paymentDate: donator.paymentDate && donator.paymentDate.isValid() ? donator.paymentDate.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.paymentDate = res.body.paymentDate ? moment(res.body.paymentDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((donator: IDonator) => {
        donator.paymentDate = donator.paymentDate ? moment(donator.paymentDate) : undefined;
      });
    }
    return res;
  }
}
