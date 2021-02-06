import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPuzzleItem, PuzzleItem } from 'app/shared/model/puzzle-item.model';
import { PuzzleItemService } from './puzzle-item.service';
import { PuzzleItemComponent } from './puzzle-item.component';
import { PuzzleItemDetailComponent } from './puzzle-item-detail.component';
import { PuzzleItemUpdateComponent } from './puzzle-item-update.component';

@Injectable({ providedIn: 'root' })
export class PuzzleItemResolve implements Resolve<IPuzzleItem> {
  constructor(private service: PuzzleItemService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPuzzleItem> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((puzzleItem: HttpResponse<PuzzleItem>) => {
          if (puzzleItem.body) {
            return of(puzzleItem.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PuzzleItem());
  }
}

export const puzzleItemRoute: Routes = [
  {
    path: '',
    component: PuzzleItemComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'puzzlelibraryApp.puzzleItem.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PuzzleItemDetailComponent,
    resolve: {
      puzzleItem: PuzzleItemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'puzzlelibraryApp.puzzleItem.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PuzzleItemUpdateComponent,
    resolve: {
      puzzleItem: PuzzleItemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'puzzlelibraryApp.puzzleItem.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PuzzleItemUpdateComponent,
    resolve: {
      puzzleItem: PuzzleItemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'puzzlelibraryApp.puzzleItem.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
