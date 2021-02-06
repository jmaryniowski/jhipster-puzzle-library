import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PuzzlelibraryTestModule } from '../../../test.module';
import { PuzzlePersonDetailComponent } from 'app/entities/puzzle-person/puzzle-person-detail.component';
import { PuzzlePerson } from 'app/shared/model/puzzle-person.model';

describe('Component Tests', () => {
  describe('PuzzlePerson Management Detail Component', () => {
    let comp: PuzzlePersonDetailComponent;
    let fixture: ComponentFixture<PuzzlePersonDetailComponent>;
    const route = ({ data: of({ puzzlePerson: new PuzzlePerson(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PuzzlelibraryTestModule],
        declarations: [PuzzlePersonDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PuzzlePersonDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PuzzlePersonDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load puzzlePerson on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.puzzlePerson).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
