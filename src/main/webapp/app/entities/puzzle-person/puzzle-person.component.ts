import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPuzzlePerson } from 'app/shared/model/puzzle-person.model';
import { PuzzlePersonService } from './puzzle-person.service';
import { PuzzlePersonDeleteDialogComponent } from './puzzle-person-delete-dialog.component';

@Component({
  selector: 'jhi-puzzle-person',
  templateUrl: './puzzle-person.component.html',
})
export class PuzzlePersonComponent implements OnInit, OnDestroy {
  puzzlePeople?: IPuzzlePerson[];
  eventSubscriber?: Subscription;

  constructor(
    protected puzzlePersonService: PuzzlePersonService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.puzzlePersonService.query().subscribe((res: HttpResponse<IPuzzlePerson[]>) => (this.puzzlePeople = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPuzzlePeople();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPuzzlePerson): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPuzzlePeople(): void {
    this.eventSubscriber = this.eventManager.subscribe('puzzlePersonListModification', () => this.loadAll());
  }

  delete(puzzlePerson: IPuzzlePerson): void {
    const modalRef = this.modalService.open(PuzzlePersonDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.puzzlePerson = puzzlePerson;
  }
}
