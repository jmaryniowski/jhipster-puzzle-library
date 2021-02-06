import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPuzzlePerson, PuzzlePerson } from 'app/shared/model/puzzle-person.model';
import { PuzzlePersonService } from './puzzle-person.service';
import { PuzzlePersonComponent } from './puzzle-person.component';
import { PuzzlePersonDetailComponent } from './puzzle-person-detail.component';
import { PuzzlePersonUpdateComponent } from './puzzle-person-update.component';

@Injectable({ providedIn: 'root' })
export class PuzzlePersonResolve implements Resolve<IPuzzlePerson> {
  constructor(private service: PuzzlePersonService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPuzzlePerson> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((puzzlePerson: HttpResponse<PuzzlePerson>) => {
          if (puzzlePerson.body) {
            return of(puzzlePerson.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PuzzlePerson());
  }
}

export const puzzlePersonRoute: Routes = [
  {
    path: '',
    component: PuzzlePersonComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'puzzlelibraryApp.puzzlePerson.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PuzzlePersonDetailComponent,
    resolve: {
      puzzlePerson: PuzzlePersonResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'puzzlelibraryApp.puzzlePerson.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PuzzlePersonUpdateComponent,
    resolve: {
      puzzlePerson: PuzzlePersonResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'puzzlelibraryApp.puzzlePerson.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PuzzlePersonUpdateComponent,
    resolve: {
      puzzlePerson: PuzzlePersonResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'puzzlelibraryApp.puzzlePerson.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
