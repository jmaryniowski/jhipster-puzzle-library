import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PuzzlelibraryTestModule } from '../../../test.module';
import { PuzzleRentalDetailComponent } from 'app/entities/puzzle-rental/puzzle-rental-detail.component';
import { PuzzleRental } from 'app/shared/model/puzzle-rental.model';

describe('Component Tests', () => {
  describe('PuzzleRental Management Detail Component', () => {
    let comp: PuzzleRentalDetailComponent;
    let fixture: ComponentFixture<PuzzleRentalDetailComponent>;
    const route = ({ data: of({ puzzleRental: new PuzzleRental(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PuzzlelibraryTestModule],
        declarations: [PuzzleRentalDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PuzzleRentalDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PuzzleRentalDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load puzzleRental on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.puzzleRental).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
