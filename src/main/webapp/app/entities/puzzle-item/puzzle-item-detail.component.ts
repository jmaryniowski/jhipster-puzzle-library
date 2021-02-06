import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPuzzleItem } from 'app/shared/model/puzzle-item.model';

@Component({
  selector: 'jhi-puzzle-item-detail',
  templateUrl: './puzzle-item-detail.component.html',
})
export class PuzzleItemDetailComponent implements OnInit {
  puzzleItem: IPuzzleItem | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ puzzleItem }) => (this.puzzleItem = puzzleItem));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
