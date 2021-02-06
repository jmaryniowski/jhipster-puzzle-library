import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PuzzlelibrarySharedModule } from 'app/shared/shared.module';
import { PuzzleRentalComponent } from './puzzle-rental.component';
import { PuzzleRentalDetailComponent } from './puzzle-rental-detail.component';
import { PuzzleRentalUpdateComponent } from './puzzle-rental-update.component';
import { PuzzleRentalDeleteDialogComponent } from './puzzle-rental-delete-dialog.component';
import { puzzleRentalRoute } from './puzzle-rental.route';

@NgModule({
  imports: [PuzzlelibrarySharedModule, RouterModule.forChild(puzzleRentalRoute)],
  declarations: [PuzzleRentalComponent, PuzzleRentalDetailComponent, PuzzleRentalUpdateComponent, PuzzleRentalDeleteDialogComponent],
  entryComponents: [PuzzleRentalDeleteDialogComponent],
})
export class PuzzlelibraryPuzzleRentalModule {}
