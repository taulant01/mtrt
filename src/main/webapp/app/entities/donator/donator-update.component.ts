import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDonator, Donator } from 'app/shared/model/donator.model';
import { DonatorService } from './donator.service';
import { ITreat } from 'app/shared/model/treat.model';
import { TreatService } from 'app/entities/treat/treat.service';

@Component({
  selector: 'jhi-donator-update',
  templateUrl: './donator-update.component.html',
})
export class DonatorUpdateComponent implements OnInit {
  isSaving = false;
  treats: ITreat[] = [];
  paymentDateDp: any;

  editForm = this.fb.group({
    id: [],
    name: [],
    surname: [],
    paymentDate: [],
    amount: [],
    message: [],
    anonymous: [],
    donators: [],
  });

  constructor(
    protected donatorService: DonatorService,
    protected treatService: TreatService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ donator }) => {
      this.updateForm(donator);

      this.treatService.query().subscribe((res: HttpResponse<ITreat[]>) => (this.treats = res.body || []));
    });
  }

  updateForm(donator: IDonator): void {
    this.editForm.patchValue({
      id: donator.id,
      name: donator.name,
      surname: donator.surname,
      paymentDate: donator.paymentDate,
      amount: donator.amount,
      message: donator.message,
      anonymous: donator.anonymous,
      donators: donator.donators,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const donator = this.createFromForm();
    if (donator.id !== undefined) {
      this.subscribeToSaveResponse(this.donatorService.update(donator));
    } else {
      this.subscribeToSaveResponse(this.donatorService.create(donator));
    }
  }

  private createFromForm(): IDonator {
    return {
      ...new Donator(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      surname: this.editForm.get(['surname'])!.value,
      paymentDate: this.editForm.get(['paymentDate'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      message: this.editForm.get(['message'])!.value,
      anonymous: this.editForm.get(['anonymous'])!.value,
      donators: this.editForm.get(['donators'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDonator>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: ITreat): any {
    return item.id;
  }
}
