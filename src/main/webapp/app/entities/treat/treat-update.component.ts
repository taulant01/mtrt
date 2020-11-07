import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITreat, Treat } from 'app/shared/model/treat.model';
import { TreatService } from './treat.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IImage } from 'app/shared/model/image.model';
import { ImageService } from 'app/entities/image/image.service';

type SelectableEntity = IUser | IImage;

@Component({
  selector: 'jhi-treat-update',
  templateUrl: './treat-update.component.html',
})
export class TreatUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  images: IImage[] = [];

  editForm = this.fb.group({
    id: [],
    crock: [],
    title: [],
    description: [],
    purchaseLink: [],
    generatedLink: [],
    status: [],
    user: [],
    image: [],
  });

  constructor(
    protected treatService: TreatService,
    protected userService: UserService,
    protected imageService: ImageService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ treat }) => {
      this.updateForm(treat);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.imageService.query().subscribe((res: HttpResponse<IImage[]>) => (this.images = res.body || []));
    });
  }

  updateForm(treat: ITreat): void {
    this.editForm.patchValue({
      id: treat.id,
      crock: treat.crock,
      title: treat.title,
      description: treat.description,
      purchaseLink: treat.purchaseLink,
      generatedLink: treat.generatedLink,
      status: treat.status,
      user: treat.user,
      image: treat.image,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const treat = this.createFromForm();
    if (treat.id !== undefined) {
      this.subscribeToSaveResponse(this.treatService.update(treat));
    } else {
      this.subscribeToSaveResponse(this.treatService.create(treat));
    }
  }

  private createFromForm(): ITreat {
    return {
      ...new Treat(),
      id: this.editForm.get(['id'])!.value,
      crock: this.editForm.get(['crock'])!.value,
      title: this.editForm.get(['title'])!.value,
      description: this.editForm.get(['description'])!.value,
      purchaseLink: this.editForm.get(['purchaseLink'])!.value,
      generatedLink: this.editForm.get(['generatedLink'])!.value,
      status: this.editForm.get(['status'])!.value,
      user: this.editForm.get(['user'])!.value,
      image: this.editForm.get(['image'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITreat>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
