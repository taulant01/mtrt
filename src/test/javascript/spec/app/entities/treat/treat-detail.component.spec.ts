import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MtrtTestModule } from '../../../test.module';
import { TreatDetailComponent } from 'app/entities/treat/treat-detail.component';
import { Treat } from 'app/shared/model/treat.model';

describe('Component Tests', () => {
  describe('Treat Management Detail Component', () => {
    let comp: TreatDetailComponent;
    let fixture: ComponentFixture<TreatDetailComponent>;
    const route = ({ data: of({ treat: new Treat(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MtrtTestModule],
        declarations: [TreatDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TreatDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TreatDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load treat on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.treat).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
