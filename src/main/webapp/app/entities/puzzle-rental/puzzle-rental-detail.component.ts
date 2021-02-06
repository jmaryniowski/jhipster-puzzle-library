import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPuzzleRental } from 'app/shared/model/puzzle-rental.model';

@Component({
  selector: 'jhi-puzzle-rental-detail',
  templateUrl: './puzzle-rental-detail.component.html',
})
export class PuzzleRentalDetailComponent implements OnInit {
  puzzleRental: IPuzzleRental | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ puzzleRental }) => (this.puzzleRental = puzzleRental));
  }

  previousState(): void {
    window.history.back();
  }
}
