import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPuzzleRental } from 'app/shared/model/puzzle-rental.model';
import { PuzzleRentalService } from './puzzle-rental.service';
import { PuzzleRentalDeleteDialogComponent } from './puzzle-rental-delete-dialog.component';

@Component({
  selector: 'jhi-puzzle-rental',
  templateUrl: './puzzle-rental.component.html',
})
export class PuzzleRentalComponent implements OnInit, OnDestroy {
  puzzleRentals?: IPuzzleRental[];
  eventSubscriber?: Subscription;

  constructor(
    protected puzzleRentalService: PuzzleRentalService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.puzzleRentalService.query().subscribe((res: HttpResponse<IPuzzleRental[]>) => (this.puzzleRentals = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPuzzleRentals();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPuzzleRental): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPuzzleRentals(): void {
    this.eventSubscriber = this.eventManager.subscribe('puzzleRentalListModification', () => this.loadAll());
  }

  delete(puzzleRental: IPuzzleRental): void {
    const modalRef = this.modalService.open(PuzzleRentalDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.puzzleRental = puzzleRental;
  }
}
