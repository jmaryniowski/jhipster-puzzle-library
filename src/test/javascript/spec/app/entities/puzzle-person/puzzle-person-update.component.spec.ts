import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PuzzlelibraryTestModule } from '../../../test.module';
import { PuzzlePersonUpdateComponent } from 'app/entities/puzzle-person/puzzle-person-update.component';
import { PuzzlePersonService } from 'app/entities/puzzle-person/puzzle-person.service';
import { PuzzlePerson } from 'app/shared/model/puzzle-person.model';

describe('Component Tests', () => {
  describe('PuzzlePerson Management Update Component', () => {
    let comp: PuzzlePersonUpdateComponent;
    let fixture: ComponentFixture<PuzzlePersonUpdateComponent>;
    let service: PuzzlePersonService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PuzzlelibraryTestModule],
        declarations: [PuzzlePersonUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PuzzlePersonUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PuzzlePersonUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PuzzlePersonService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PuzzlePerson(123);
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
        const entity = new PuzzlePerson();
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
