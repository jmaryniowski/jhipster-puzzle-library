import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPuzzleRental } from 'app/shared/model/puzzle-rental.model';
import { PuzzleRentalService } from './puzzle-rental.service';

@Component({
  templateUrl: './puzzle-rental-delete-dialog.component.html',
})
export class PuzzleRentalDeleteDialogComponent {
  puzzleRental?: IPuzzleRental;

  constructor(
    protected puzzleRentalService: PuzzleRentalService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.puzzleRentalService.delete(id).subscribe(() => {
      this.eventManager.broadcast('puzzleRentalListModification');
      this.activeModal.close();
    });
  }
}
