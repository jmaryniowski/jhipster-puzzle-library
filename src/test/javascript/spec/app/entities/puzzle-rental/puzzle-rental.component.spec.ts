import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PuzzlelibraryTestModule } from '../../../test.module';
import { PuzzleRentalComponent } from 'app/entities/puzzle-rental/puzzle-rental.component';
import { PuzzleRentalService } from 'app/entities/puzzle-rental/puzzle-rental.service';
import { PuzzleRental } from 'app/shared/model/puzzle-rental.model';

describe('Component Tests', () => {
  describe('PuzzleRental Management Component', () => {
    let comp: PuzzleRentalComponent;
    let fixture: ComponentFixture<PuzzleRentalComponent>;
    let service: PuzzleRentalService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PuzzlelibraryTestModule],
        declarations: [PuzzleRentalComponent],
      })
        .overrideTemplate(PuzzleRentalComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PuzzleRentalComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PuzzleRentalService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PuzzleRental(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.puzzleRentals && comp.puzzleRentals[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
