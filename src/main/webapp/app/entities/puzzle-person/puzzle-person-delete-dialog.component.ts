import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPuzzlePerson } from 'app/shared/model/puzzle-person.model';
import { PuzzlePersonService } from './puzzle-person.service';

@Component({
  templateUrl: './puzzle-person-delete-dialog.component.html',
})
export class PuzzlePersonDeleteDialogComponent {
  puzzlePerson?: IPuzzlePerson;

  constructor(
    protected puzzlePersonService: PuzzlePersonService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.puzzlePersonService.delete(id).subscribe(() => {
      this.eventManager.broadcast('puzzlePersonListModification');
      this.activeModal.close();
    });
  }
}
