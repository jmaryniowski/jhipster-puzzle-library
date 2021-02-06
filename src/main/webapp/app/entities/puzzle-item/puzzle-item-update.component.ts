import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IPuzzleItem, PuzzleItem } from 'app/shared/model/puzzle-item.model';
import { PuzzleItemService } from './puzzle-item.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IPuzzlePerson } from 'app/shared/model/puzzle-person.model';
import { PuzzlePersonService } from 'app/entities/puzzle-person/puzzle-person.service';

@Component({
  selector: 'jhi-puzzle-item-update',
  templateUrl: './puzzle-item-update.component.html',
})
export class PuzzleItemUpdateComponent implements OnInit {
  isSaving = false;
  puzzlepeople: IPuzzlePerson[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    description: [],
    type: [],
    picture: [],
    pictureContentType: [],
    puzzlePerson: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected puzzleItemService: PuzzleItemService,
    protected puzzlePersonService: PuzzlePersonService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ puzzleItem }) => {
      this.updateForm(puzzleItem);

      this.puzzlePersonService.query().subscribe((res: HttpResponse<IPuzzlePerson[]>) => (this.puzzlepeople = res.body || []));
    });
  }

  updateForm(puzzleItem: IPuzzleItem): void {
    this.editForm.patchValue({
      id: puzzleItem.id,
      name: puzzleItem.name,
      description: puzzleItem.description,
      type: puzzleItem.type,
      picture: puzzleItem.picture,
      pictureContentType: puzzleItem.pictureContentType,
      puzzlePerson: puzzleItem.puzzlePerson,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('puzzlelibraryApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const puzzleItem = this.createFromForm();
    if (puzzleItem.id !== undefined) {
      this.subscribeToSaveResponse(this.puzzleItemService.update(puzzleItem));
    } else {
      this.subscribeToSaveResponse(this.puzzleItemService.create(puzzleItem));
    }
  }

  private createFromForm(): IPuzzleItem {
    return {
      ...new PuzzleItem(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      type: this.editForm.get(['type'])!.value,
      pictureContentType: this.editForm.get(['pictureContentType'])!.value,
      picture: this.editForm.get(['picture'])!.value,
      puzzlePerson: this.editForm.get(['puzzlePerson'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPuzzleItem>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IPuzzlePerson): any {
    return item.id;
  }
}
