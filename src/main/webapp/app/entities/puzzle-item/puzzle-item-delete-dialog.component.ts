import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPuzzleItem } from 'app/shared/model/puzzle-item.model';
import { PuzzleItemService } from './puzzle-item.service';

@Component({
  templateUrl: './puzzle-item-delete-dialog.component.html',
})
export class PuzzleItemDeleteDialogComponent {
  puzzleItem?: IPuzzleItem;

  constructor(
    protected puzzleItemService: PuzzleItemService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.puzzleItemService.delete(id).subscribe(() => {
      this.eventManager.broadcast('puzzleItemListModification');
      this.activeModal.close();
    });
  }
}
