import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITreat } from 'app/shared/model/treat.model';
import { TreatService } from './treat.service';

@Component({
  templateUrl: './treat-delete-dialog.component.html',
})
export class TreatDeleteDialogComponent {
  treat?: ITreat;

  constructor(protected treatService: TreatService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.treatService.delete(id).subscribe(() => {
      this.eventManager.broadcast('treatListModification');
      this.activeModal.close();
    });
  }
}
