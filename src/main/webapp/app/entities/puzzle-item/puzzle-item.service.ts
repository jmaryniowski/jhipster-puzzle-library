import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPuzzleItem } from 'app/shared/model/puzzle-item.model';

type EntityResponseType = HttpResponse<IPuzzleItem>;
type EntityArrayResponseType = HttpResponse<IPuzzleItem[]>;

@Injectable({ providedIn: 'root' })
export class PuzzleItemService {
  public resourceUrl = SERVER_API_URL + 'api/puzzle-items';

  constructor(protected http: HttpClient) {}

  create(puzzleItem: IPuzzleItem): Observable<EntityResponseType> {
    return this.http.post<IPuzzleItem>(this.resourceUrl, puzzleItem, { observe: 'response' });
  }

  update(puzzleItem: IPuzzleItem): Observable<EntityResponseType> {
    return this.http.put<IPuzzleItem>(this.resourceUrl, puzzleItem, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPuzzleItem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPuzzleItem[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
