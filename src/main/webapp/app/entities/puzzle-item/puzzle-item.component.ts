import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPuzzleItem } from 'app/shared/model/puzzle-item.model';
import { PuzzleItemService } from './puzzle-item.service';
import { PuzzleItemDeleteDialogComponent } from './puzzle-item-delete-dialog.component';

@Component({
  selector: 'jhi-puzzle-item',
  templateUrl: './puzzle-item.component.html',
})
export class PuzzleItemComponent implements OnInit, OnDestroy {
  puzzleItems?: IPuzzleItem[];
  eventSubscriber?: Subscription;

  constructor(
    protected puzzleItemService: PuzzleItemService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.puzzleItemService.query().subscribe((res: HttpResponse<IPuzzleItem[]>) => (this.puzzleItems = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPuzzleItems();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPuzzleItem): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInPuzzleItems(): void {
    this.eventSubscriber = this.eventManager.subscribe('puzzleItemListModification', () => this.loadAll());
  }

  delete(puzzleItem: IPuzzleItem): void {
    const modalRef = this.modalService.open(PuzzleItemDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.puzzleItem = puzzleItem;
  }
}
