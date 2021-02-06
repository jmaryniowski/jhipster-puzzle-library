import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPuzzlePerson } from 'app/shared/model/puzzle-person.model';

@Component({
  selector: 'jhi-puzzle-person-detail',
  templateUrl: './puzzle-person-detail.component.html',
})
export class PuzzlePersonDetailComponent implements OnInit {
  puzzlePerson: IPuzzlePerson | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ puzzlePerson }) => (this.puzzlePerson = puzzlePerson));
  }

  previousState(): void {
    window.history.back();
  }
}
