import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PuzzlelibraryTestModule } from '../../../test.module';
import { PuzzlePersonComponent } from 'app/entities/puzzle-person/puzzle-person.component';
import { PuzzlePersonService } from 'app/entities/puzzle-person/puzzle-person.service';
import { PuzzlePerson } from 'app/shared/model/puzzle-person.model';

describe('Component Tests', () => {
  describe('PuzzlePerson Management Component', () => {
    let comp: PuzzlePersonComponent;
    let fixture: ComponentFixture<PuzzlePersonComponent>;
    let service: PuzzlePersonService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PuzzlelibraryTestModule],
        declarations: [PuzzlePersonComponent],
      })
        .overrideTemplate(PuzzlePersonComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PuzzlePersonComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PuzzlePersonService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PuzzlePerson(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.puzzlePeople && comp.puzzlePeople[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
