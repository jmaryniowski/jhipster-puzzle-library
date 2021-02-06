import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPuzzlePerson } from 'app/shared/model/puzzle-person.model';

type EntityResponseType = HttpResponse<IPuzzlePerson>;
type EntityArrayResponseType = HttpResponse<IPuzzlePerson[]>;

@Injectable({ providedIn: 'root' })
export class PuzzlePersonService {
  public resourceUrl = SERVER_API_URL + 'api/puzzle-people';

  constructor(protected http: HttpClient) {}

  create(puzzlePerson: IPuzzlePerson): Observable<EntityResponseType> {
    return this.http.post<IPuzzlePerson>(this.resourceUrl, puzzlePerson, { observe: 'response' });
  }

  update(puzzlePerson: IPuzzlePerson): Observable<EntityResponseType> {
    return this.http.put<IPuzzlePerson>(this.resourceUrl, puzzlePerson, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPuzzlePerson>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPuzzlePerson[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
