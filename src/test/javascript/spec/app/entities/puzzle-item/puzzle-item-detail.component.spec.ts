import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { PuzzlelibraryTestModule } from '../../../test.module';
import { PuzzleItemDetailComponent } from 'app/entities/puzzle-item/puzzle-item-detail.component';
import { PuzzleItem } from 'app/shared/model/puzzle-item.model';

describe('Component Tests', () => {
  describe('PuzzleItem Management Detail Component', () => {
    let comp: PuzzleItemDetailComponent;
    let fixture: ComponentFixture<PuzzleItemDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ puzzleItem: new PuzzleItem(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PuzzlelibraryTestModule],
        declarations: [PuzzleItemDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PuzzleItemDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PuzzleItemDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load puzzleItem on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.puzzleItem).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
