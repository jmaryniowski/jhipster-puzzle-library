import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PuzzlelibrarySharedModule } from 'app/shared/shared.module';
import { PuzzleItemComponent } from './puzzle-item.component';
import { PuzzleItemDetailComponent } from './puzzle-item-detail.component';
import { PuzzleItemUpdateComponent } from './puzzle-item-update.component';
import { PuzzleItemDeleteDialogComponent } from './puzzle-item-delete-dialog.component';
import { puzzleItemRoute } from './puzzle-item.route';

@NgModule({
  imports: [PuzzlelibrarySharedModule, RouterModule.forChild(puzzleItemRoute)],
  declarations: [PuzzleItemComponent, PuzzleItemDetailComponent, PuzzleItemUpdateComponent, PuzzleItemDeleteDialogComponent],
  entryComponents: [PuzzleItemDeleteDialogComponent],
})
export class PuzzlelibraryPuzzleItemModule {}
