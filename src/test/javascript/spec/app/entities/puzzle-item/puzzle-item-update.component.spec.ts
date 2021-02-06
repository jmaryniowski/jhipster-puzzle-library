import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PuzzlelibraryTestModule } from '../../../test.module';
import { PuzzleItemUpdateComponent } from 'app/entities/puzzle-item/puzzle-item-update.component';
import { PuzzleItemService } from 'app/entities/puzzle-item/puzzle-item.service';
import { PuzzleItem } from 'app/shared/model/puzzle-item.model';

describe('Component Tests', () => {
  describe('PuzzleItem Management Update Component', () => {
    let comp: PuzzleItemUpdateComponent;
    let fixture: ComponentFixture<PuzzleItemUpdateComponent>;
    let service: PuzzleItemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PuzzlelibraryTestModule],
        declarations: [PuzzleItemUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PuzzleItemUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PuzzleItemUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PuzzleItemService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PuzzleItem(123);
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
        const entity = new PuzzleItem();
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
