import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PuzzlelibraryTestModule } from '../../../test.module';
import { PuzzleRentalUpdateComponent } from 'app/entities/puzzle-rental/puzzle-rental-update.component';
import { PuzzleRentalService } from 'app/entities/puzzle-rental/puzzle-rental.service';
import { PuzzleRental } from 'app/shared/model/puzzle-rental.model';

describe('Component Tests', () => {
  describe('PuzzleRental Management Update Component', () => {
    let comp: PuzzleRentalUpdateComponent;
    let fixture: ComponentFixture<PuzzleRentalUpdateComponent>;
    let service: PuzzleRentalService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PuzzlelibraryTestModule],
        declarations: [PuzzleRentalUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PuzzleRentalUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PuzzleRentalUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PuzzleRentalService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PuzzleRental(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new PuzzleRental();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
