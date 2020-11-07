import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MtrtTestModule } from '../../../test.module';
import { DonatorDetailComponent } from 'app/entities/donator/donator-detail.component';
import { Donator } from 'app/shared/model/donator.model';

describe('Component Tests', () => {
  describe('Donator Management Detail Component', () => {
    let comp: DonatorDetailComponent;
    let fixture: ComponentFixture<DonatorDetailComponent>;
    const route = ({ data: of({ donator: new Donator(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MtrtTestModule],
        declarations: [DonatorDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DonatorDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DonatorDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load donator on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.donator).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
