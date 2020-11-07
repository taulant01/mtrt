import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MtrtTestModule } from '../../../test.module';
import { DonatorUpdateComponent } from 'app/entities/donator/donator-update.component';
import { DonatorService } from 'app/entities/donator/donator.service';
import { Donator } from 'app/shared/model/donator.model';

describe('Component Tests', () => {
  describe('Donator Management Update Component', () => {
    let comp: DonatorUpdateComponent;
    let fixture: ComponentFixture<DonatorUpdateComponent>;
    let service: DonatorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MtrtTestModule],
        declarations: [DonatorUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DonatorUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DonatorUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DonatorService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Donator(123);
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
        const entity = new Donator();
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
