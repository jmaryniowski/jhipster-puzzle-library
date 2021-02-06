import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PuzzlelibrarySharedModule } from 'app/shared/shared.module';
import { PuzzlePersonComponent } from './puzzle-person.component';
import { PuzzlePersonDetailComponent } from './puzzle-person-detail.component';
import { PuzzlePersonUpdateComponent } from './puzzle-person-update.component';
import { PuzzlePersonDeleteDialogComponent } from './puzzle-person-delete-dialog.component';
import { puzzlePersonRoute } from './puzzle-person.route';

@NgModule({
  imports: [PuzzlelibrarySharedModule, RouterModule.forChild(puzzlePersonRoute)],
  declarations: [PuzzlePersonComponent, PuzzlePersonDetailComponent, PuzzlePersonUpdateComponent, PuzzlePersonDeleteDialogComponent],
  entryComponents: [PuzzlePersonDeleteDialogComponent],
})
export class PuzzlelibraryPuzzlePersonModule {}
