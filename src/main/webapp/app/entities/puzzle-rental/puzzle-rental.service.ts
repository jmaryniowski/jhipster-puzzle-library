import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPuzzleRental } from 'app/shared/model/puzzle-rental.model';

type EntityResponseType = HttpResponse<IPuzzleRental>;
type EntityArrayResponseType = HttpResponse<IPuzzleRental[]>;

@Injectable({ providedIn: 'root' })
export class PuzzleRentalService {
  public resourceUrl = SERVER_API_URL + 'api/puzzle-rentals';

  constructor(protected http: HttpClient) {}

  create(puzzleRental: IPuzzleRental): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(puzzleRental);
    return this.http
      .post<IPuzzleRental>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(puzzleRental: IPuzzleRental): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(puzzleRental);
    return this.http
      .put<IPuzzleRental>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPuzzleRental>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPuzzleRental[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(puzzleRental: IPuzzleRental): IPuzzleRental {
    const copy: IPuzzleRental = Object.assign({}, puzzleRental, {
      startDate: puzzleRental.startDate && puzzleRental.startDate.isValid() ? puzzleRental.startDate.format(DATE_FORMAT) : undefined,
      endDate: puzzleRental.endDate && puzzleRental.endDate.isValid() ? puzzleRental.endDate.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startDate = res.body.startDate ? moment(res.body.startDate) : undefined;
      res.body.endDate = res.body.endDate ? moment(res.body.endDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((puzzleRental: IPuzzleRental) => {
        puzzleRental.startDate = puzzleRental.startDate ? moment(puzzleRental.startDate) : undefined;
        puzzleRental.endDate = puzzleRental.endDate ? moment(puzzleRental.endDate) : undefined;
      });
    }
    return res;
  }
}
