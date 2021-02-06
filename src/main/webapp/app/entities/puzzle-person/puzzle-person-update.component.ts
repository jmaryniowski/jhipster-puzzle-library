import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPuzzlePerson, PuzzlePerson } from 'app/shared/model/puzzle-person.model';
import { PuzzlePersonService } from './puzzle-person.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-puzzle-person-update',
  templateUrl: './puzzle-person-update.component.html',
})
export class PuzzlePersonUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    user: [],
  });

  constructor(
    protected puzzlePersonService: PuzzlePersonService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ puzzlePerson }) => {
      this.updateForm(puzzlePerson);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(puzzlePerson: IPuzzlePerson): void {
    this.editForm.patchValue({
      id: puzzlePerson.id,
      user: puzzlePerson.user,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const puzzlePerson = this.createFromForm();
    if (puzzlePerson.id !== undefined) {
      this.subscribeToSaveResponse(this.puzzlePersonService.update(puzzlePerson));
    } else {
      this.subscribeToSaveResponse(this.puzzlePersonService.create(puzzlePerson));
    }
  }

  private createFromForm(): IPuzzlePerson {
    return {
      ...new PuzzlePerson(),
      id: this.editForm.get(['id'])!.value,
      user: this.editForm.get(['user'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPuzzlePerson>>): void {
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

  trackById(index: number, item: IUser): any {
    return item.id;
  }
}
