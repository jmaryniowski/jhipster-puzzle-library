import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IPuzzleRental, PuzzleRental } from 'app/shared/model/puzzle-rental.model';
import { PuzzleRentalService } from './puzzle-rental.service';
import { IPuzzleItem } from 'app/shared/model/puzzle-item.model';
import { PuzzleItemService } from 'app/entities/puzzle-item/puzzle-item.service';
import { IPuzzlePerson } from 'app/shared/model/puzzle-person.model';
import { PuzzlePersonService } from 'app/entities/puzzle-person/puzzle-person.service';

type SelectableEntity = IPuzzleItem | IPuzzlePerson;

@Component({
  selector: 'jhi-puzzle-rental-update',
  templateUrl: './puzzle-rental-update.component.html',
})
export class PuzzleRentalUpdateComponent implements OnInit {
  isSaving = false;
  puzzleitems: IPuzzleItem[] = [];
  puzzlepeople: IPuzzlePerson[] = [];
  startDateDp: any;
  endDateDp: any;

  editForm = this.fb.group({
    id: [],
    startDate: [],
    endDate: [],
    isActive: [],
    puzzleItem: [],
    puzzlePerson: [],
  });

  constructor(
    protected puzzleRentalService: PuzzleRentalService,
    protected puzzleItemService: PuzzleItemService,
    protected puzzlePersonService: PuzzlePersonService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ puzzleRental }) => {
      this.updateForm(puzzleRental);

      this.puzzleItemService
        .query({ filter: 'puzzlerental-is-null' })
        .pipe(
          map((res: HttpResponse<IPuzzleItem[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IPuzzleItem[]) => {
          if (!puzzleRental.puzzleItem || !puzzleRental.puzzleItem.id) {
            this.puzzleitems = resBody;
          } else {
            this.puzzleItemService
              .find(puzzleRental.puzzleItem.id)
              .pipe(
                map((subRes: HttpResponse<IPuzzleItem>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IPuzzleItem[]) => (this.puzzleitems = concatRes));
          }
        });

      this.puzzlePersonService.query().subscribe((res: HttpResponse<IPuzzlePerson[]>) => (this.puzzlepeople = res.body || []));
    });
  }

  updateForm(puzzleRental: IPuzzleRental): void {
    this.editForm.patchValue({
      id: puzzleRental.id,
      startDate: puzzleRental.startDate,
      endDate: puzzleRental.endDate,
      isActive: puzzleRental.isActive,
      puzzleItem: puzzleRental.puzzleItem,
      puzzlePerson: puzzleRental.puzzlePerson,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const puzzleRental = this.createFromForm();
    if (puzzleRental.id !== undefined) {
      this.subscribeToSaveResponse(this.puzzleRentalService.update(puzzleRental));
    } else {
      this.subscribeToSaveResponse(this.puzzleRentalService.create(puzzleRental));
    }
  }

  private createFromForm(): IPuzzleRental {
    return {
      ...new PuzzleRental(),
      id: this.editForm.get(['id'])!.value,
      startDate: this.editForm.get(['startDate'])!.value,
      endDate: this.editForm.get(['endDate'])!.value,
      isActive: this.editForm.get(['isActive'])!.value,
      puzzleItem: this.editForm.get(['puzzleItem'])!.value,
      puzzlePerson: this.editForm.get(['puzzlePerson'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPuzzleRental>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
