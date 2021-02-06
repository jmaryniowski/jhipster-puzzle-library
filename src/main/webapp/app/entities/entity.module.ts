import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'puzzle-person',
        loadChildren: () => import('./puzzle-person/puzzle-person.module').then(m => m.PuzzlelibraryPuzzlePersonModule),
      },
      {
        path: 'puzzle-item',
        loadChildren: () => import('./puzzle-item/puzzle-item.module').then(m => m.PuzzlelibraryPuzzleItemModule),
      },
      {
        path: 'puzzle-rental',
        loadChildren: () => import('./puzzle-rental/puzzle-rental.module').then(m => m.PuzzlelibraryPuzzleRentalModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class PuzzlelibraryEntityModule {}
