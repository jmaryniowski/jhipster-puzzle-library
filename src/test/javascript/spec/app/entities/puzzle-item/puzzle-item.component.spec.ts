import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PuzzlelibraryTestModule } from '../../../test.module';
import { PuzzleItemComponent } from 'app/entities/puzzle-item/puzzle-item.component';
import { PuzzleItemService } from 'app/entities/puzzle-item/puzzle-item.service';
import { PuzzleItem } from 'app/shared/model/puzzle-item.model';

describe('Component Tests', () => {
  describe('PuzzleItem Management Component', () => {
    let comp: PuzzleItemComponent;
    let fixture: ComponentFixture<PuzzleItemComponent>;
    let service: PuzzleItemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PuzzlelibraryTestModule],
        declarations: [PuzzleItemComponent],
      })
        .overrideTemplate(PuzzleItemComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PuzzleItemComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PuzzleItemService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PuzzleItem(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.puzzleItems && comp.puzzleItems[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
