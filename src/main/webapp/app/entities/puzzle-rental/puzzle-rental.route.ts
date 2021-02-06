import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPuzzleRental, PuzzleRental } from 'app/shared/model/puzzle-rental.model';
import { PuzzleRentalService } from './puzzle-rental.service';
import { PuzzleRentalComponent } from './puzzle-rental.component';
import { PuzzleRentalDetailComponent } from './puzzle-rental-detail.component';
import { PuzzleRentalUpdateComponent } from './puzzle-rental-update.component';

@Injectable({ providedIn: 'root' })
export class PuzzleRentalResolve implements Resolve<IPuzzleRental> {
  constructor(private service: PuzzleRentalService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPuzzleRental> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((puzzleRental: HttpResponse<PuzzleRental>) => {
          if (puzzleRental.body) {
            return of(puzzleRental.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PuzzleRental());
  }
}

export const puzzleRentalRoute: Routes = [
  {
    path: '',
    component: PuzzleRentalComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'puzzlelibraryApp.puzzleRental.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PuzzleRentalDetailComponent,
    resolve: {
      puzzleRental: PuzzleRentalResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'puzzlelibraryApp.puzzleRental.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PuzzleRentalUpdateComponent,
    resolve: {
      puzzleRental: PuzzleRentalResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'puzzlelibraryApp.puzzleRental.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PuzzleRentalUpdateComponent,
    resolve: {
      puzzleRental: PuzzleRentalResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'puzzlelibraryApp.puzzleRental.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
