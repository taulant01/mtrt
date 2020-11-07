import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDonator } from 'app/shared/model/donator.model';
import { DonatorService } from './donator.service';

@Component({
  templateUrl: './donator-delete-dialog.component.html',
})
export class DonatorDeleteDialogComponent {
  donator?: IDonator;

  constructor(protected donatorService: DonatorService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.donatorService.delete(id).subscribe(() => {
      this.eventManager.broadcast('donatorListModification');
      this.activeModal.close();
    });
  }
}
