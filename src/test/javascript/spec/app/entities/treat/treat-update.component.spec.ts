import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MtrtTestModule } from '../../../test.module';
import { TreatUpdateComponent } from 'app/entities/treat/treat-update.component';
import { TreatService } from 'app/entities/treat/treat.service';
import { Treat } from 'app/shared/model/treat.model';

describe('Component Tests', () => {
  describe('Treat Management Update Component', () => {
    let comp: TreatUpdateComponent;
    let fixture: ComponentFixture<TreatUpdateComponent>;
    let service: TreatService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MtrtTestModule],
        declarations: [TreatUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TreatUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TreatUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TreatService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Treat(123);
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
        const entity = new Treat();
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
